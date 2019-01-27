package com.absingh.oradroid;

/**
 * EmailVerify.java
 * Created by Abhijeet Singh
 * absingh.com
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.absingh.oradroid.helper.Functions;
import com.absingh.oradroid.helper.SessionManager;

import java.util.concurrent.TimeUnit;

public class EmailVerify extends AppCompatActivity {
    private static final String TAG = EmailVerify.class.getSimpleName();
    private TextInputLayout textVerifyCode;
    private Button btnVerify, btnResend;
    private TextView otpCountDown;
    private SessionManager session;
    private ProgressDialog pDialog;
    private static final String FORMAT = "%02d:%02d";
    Bundle bundle;
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);
        textVerifyCode = (TextInputLayout) findViewById(R.id.verify_code);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        btnResend = (Button) findViewById(R.id.btnResendCode);
        otpCountDown = (TextView) findViewById(R.id.otpCountDown);
        bundle = getIntent().getExtras();
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // Hide Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
    }
    private void init() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide Keyboard
                Functions.hideSoftKeyboard(EmailVerify.this);
                String email = bundle.getString("email");
                String otp = textVerifyCode.getEditText().getText().toString();
                if (!otp.isEmpty()) {
                    verifyCode(email, otp);
                    textVerifyCode.setErrorEnabled(false);
                } else {
                    textVerifyCode.setError("Please enter verification code");
                }
            }
        });
        btnResend.setEnabled(false);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = bundle.getString("email");
                resendCode(email);
            }
        });
        countDown();
    }
    private void countDown() {
        new CountDownTimer(70000, 1000) { // adjust the milli seconds here
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                otpCountDown.setVisibility(View.VISIBLE);
                otpCountDown.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)) ));
            }
            public void onFinish() {
                otpCountDown.setVisibility(View.GONE);
                btnResend.setEnabled(true);
            }
        }.start();
    }
    private void verifyCode(final String email, final String otp) {
        // Tag used to cancel the request
        String tag_string_req = "req_verify_code";
        pDialog.setMessage("Checking in ...");
        showDialog();
    }
    private void resendCode(final String email) {
        // Tag used to cancel the request
        String tag_string_req = "req_resend_code";
        pDialog.setMessage("Resending code ...");
        showDialog();
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    public void onResume(){
        super.onResume();
        countDown();
    }
}
