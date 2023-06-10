package com.example.captchav;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainAct extends AppCompatActivity {
    private static final String RECAPTCHA_SITE_KEY = "6LcER4UmAAAAANXxd-0u0k9sozVYrhLMcEiv7-JR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SafetyNet.getClient(MainAct.this)
                .verifyWithRecaptcha(RECAPTCHA_SITE_KEY)
                .addOnSuccessListener(MainAct.this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        // Process the Recaptcha token response
                    }
                })
                .addOnFailureListener(MainAct.this, new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            // Handle the ApiException accordingly
                            switch (statusCode) {
                                case SafetyNetStatusCodes.RECAPTCHA_INVALID_SITEKEY:
                                    // Invalid reCAPTCHA site key
                                    break;
                                // Handle other specific error codes as needed
                                default:
                                    // General error handling for ApiException
                                    break;
                            }
                        } else {
                            // Handle other types of exceptions
                            // You can log the exception or display an error message to the user
                            e.printStackTrace();
                            // or
                            Toast.makeText(MainAct.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}