package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    GetPostApi getPostApi;
    TextView signupText, login, result;
    EditText signupName;
    EditText signupPassword;
    EditText signupMobile;
    EditText signupEmail;
    Button signup_btn;
    String email, password, mobile, name, myresult;
    private int loginStop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        result = findViewById(R.id.result);
        signupText = findViewById(R.id.signup_text);
        signupEmail = findViewById(R.id.signup_email);
        signupName = findViewById(R.id.signup_name);
        signup_btn = findViewById(R.id.signup_btn);
        signupMobile = findViewById(R.id.signup_mobile);
        signupPassword = findViewById(R.id.signup_password);
        login = findViewById(R.id.login);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://qa.homechef.pk/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getPostApi = retrofit.create(GetPostApi.class);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createPost();
                } else {
                    result.setText(myresult);
                    myresult ="";
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginStop == 0) {
                    loginStop = 1;
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public boolean validate() {
        email = String.valueOf(signupEmail.getText());
        password = String.valueOf(signupPassword.getText());
        mobile = String.valueOf(signupMobile.getText());
        name = String.valueOf(signupName.getText());
        if (isValidMail(email) == false) {
            myresult += "Enter right email format\n";
        }
        if (isValidPassword(password) == false) {
            myresult += "At least 1 Capital letter, 1 small letter,\n1 number, 1 character\nMinimum 8 characters required\n";
        }
        if (isValidMobile(mobile) == false) {
            myresult += "Enter correct Number like 03122804640, +923122804640, 00923122804640\n";
        }
        if (isValidMail(email) && isValidMobile(mobile) && isValidPassword(password)) {
            return true;
        }
        Toast.makeText(SignUpActivity.this, "Please fill all the fields accordingly", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void createPost() {
        Call<SignUpPost> call = getPostApi.createPost(name, mobile, password, email);

        call.enqueue(new Callback<SignUpPost>() {
            @Override
            public void onResponse(Call<SignUpPost> call, Response<SignUpPost> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUpPost postResponse = response.body();
                String content = "";
                content = postResponse.getMessage();
                if (content.equals("success")) {
                    Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, postResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpPost> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        Log.d("ResultDetails", "Password" + matcher.matches());
        return matcher.matches();
    }

    private boolean isValidMail(String email) {
        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Log.d("ResultDetails", "Email" + Pattern.compile(EMAIL_STRING).matcher(email).matches());
        return Pattern.compile(EMAIL_STRING).matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        String Phone_STRING = "^((\\+92)|(0092))-{0,1}\\d{3}-{0,1}\\d{7}$|^\\d{11}$|^\\d{4}-\\d{7}$";
        Log.d("ResultDetails", "Mobile" + (phone.length() > 9 && phone.length() <=14));
        return Pattern.compile(Phone_STRING).matcher(phone).matches() && (phone.length() > 9 && phone.length() <= 14);

    }
}
