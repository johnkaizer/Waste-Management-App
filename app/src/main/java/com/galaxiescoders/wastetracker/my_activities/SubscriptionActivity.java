package com.galaxiescoders.wastetracker.my_activities;

import static com.galaxiescoders.wastetracker.MpesaDaraja.Constants.BUSINESS_SHORT_CODE;
import static com.galaxiescoders.wastetracker.MpesaDaraja.Constants.CALLBACKURL;
import static com.galaxiescoders.wastetracker.MpesaDaraja.Constants.PARTYB;
import static com.galaxiescoders.wastetracker.MpesaDaraja.Constants.PASSKEY;
import static com.galaxiescoders.wastetracker.MpesaDaraja.Constants.TRANSACTION_TYPE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galaxiescoders.wastetracker.Interceptor.AccessToken;
import com.galaxiescoders.wastetracker.MpesaDaraja.DarajaApiClient;
import com.galaxiescoders.wastetracker.MpesaDaraja.STKPush;
import com.galaxiescoders.wastetracker.MpesaDaraja.Utils;
import com.galaxiescoders.wastetracker.R;
import com.galaxiescoders.wastetracker.models.Payment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SubscriptionActivity extends AppCompatActivity {
    TextView amountTxt;
    TextView titleTxt;
    EditText nameET;
    EditText hseNumEt;
    EditText locationEt;
    EditText phoneEt;
    Button payBtn;
    DarajaApiClient mApiClient;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        amountTxt = findViewById(R.id.numberTxt);
        titleTxt = findViewById(R.id.modelTxt);
        nameET = findViewById(R.id.prod_title);
        hseNumEt = findViewById(R.id.price);
        locationEt = findViewById(R.id.quantity);
        phoneEt = findViewById(R.id.description);
        payBtn = findViewById(R.id.submit_btn);
        // Initialize ProgressDialog
        mProgressDialog = new ProgressDialog(this);
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true);
        getAccessToken();

        // Retrieve values from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String amount = intent.getStringExtra("amount");
            String title = intent.getStringExtra("title");

            amountTxt.setText(amount);
            titleTxt.setText(title);
        }

        // Set onClickListener for payBtn
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Get user ID from SharedPreferences
                    String userId = getSharedPreferences("MyPreferences", MODE_PRIVATE)
                            .getString("idNo", "");

                    // Get current date
                    String paymentDate = getCurrentDate();

                    // Calculate expiry date (current date + 30 days)
                    String expiryDate = calculateExpiryDate();

                    // Retrieve other input fields
                    String name = nameET.getText().toString().trim();
                    String houseNumber = hseNumEt.getText().toString().trim();
                    String location = locationEt.getText().toString().trim();
                    String amount = intent.getStringExtra("amount");
                    String title = intent.getStringExtra("title");
                    String phone_number = phoneEt.getText().toString();
                    String amount1 = amount;
                    performSTKPush(phone_number,amount1);

                    // Create a Payment object
                    Payment payment = new Payment(userId, paymentDate, expiryDate, title, amount, houseNumber, name, location);

                    // Save the Payment object to Firebase or perform further actions
                    savePaymentToFirebase(payment);

            }
        });
    }

    private void performSTKPush(String phoneNumber, String amount1) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount1),
                Utils.sanitizePhoneNumber(phoneNumber),
                PARTYB,
                Utils.sanitizePhoneNumber(phoneNumber),
                CALLBACKURL,
                "ChangishaLTD", //Account reference
                "Donation"  //Transaction description
        );


        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currentDate = Calendar.getInstance().getTime();
        return dateFormat.format(currentDate);
    }

    private String calculateExpiryDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Add 30 days to the current date
        return dateFormat.format(calendar.getTime());
    }

    private void savePaymentToFirebase(Payment payment) {
        // Get a reference to your Firebase Realtime Database
        DatabaseReference paymentsReference = FirebaseDatabase.getInstance().getReference().child("Payments");

        // Generate a new unique key for the payment
        String paymentKey = paymentsReference.push().getKey();

        // Set the payment data to the corresponding key
        paymentsReference.child(paymentKey).setValue(payment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Successfully saved to Firebase
                        Toast.makeText(SubscriptionActivity.this, "Enter mpesa Pin to complete payment", Toast.LENGTH_SHORT).show();
                        // You can perform additional actions here if needed
                        nameET.setText("");
                        hseNumEt.setText("");
                        locationEt.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to save to Firebase
                        Toast.makeText(SubscriptionActivity.this, "Failed to save payment", Toast.LENGTH_SHORT).show();
                        // Handle the error, if needed
                    }
                });
    }

}
