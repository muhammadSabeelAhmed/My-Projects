package com.example.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    Intent itnent;
    EditText loginEmail;
    EditText loginPassword;
    Button btn_login;
    TextView signup;
    GetPostApi getPostApi;
    private String email;
    private String password;
    private int signupStop = 0;
    int stopbtnlogin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signUp);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signupStop == 0) {
                    signupStop = 1;
                    itnent = new Intent(LogInActivity.this, SignUpActivity.class);
                    startActivity(itnent);
                }
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qa.homechef.pk/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getPostApi = retrofit.create(GetPostApi.class);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stopbtnlogin == 0) {
                    createpost();
                }
            }
        });
    }

    private void createpost() {
        password = String.valueOf(loginPassword.getText());
        email = String.valueOf(loginEmail.getText());
        final Call<SignInGet> call = getPostApi.createGetPost(password, email);
        try {
            call.enqueue(new Callback<SignInGet>() {
                @Override
                public void onResponse(Call<SignInGet> call, Response<SignInGet> response) {
                    stopbtnlogin = 1;
                    Log.d("LoginActivity", "on Response" + response.code());
                    if (!response.isSuccessful()) {
                        stopbtnlogin = 0;
                        Log.d("LoginActivity", "not success" + response.raw());
                        Toast.makeText(LogInActivity.this, "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SignInGet posts = (SignInGet) response.body();
                    Log.d("LoginActivity", "on Response Message" + posts.getMessage());

                    if (posts.getMessage().equals("success")) {
                        Log.d("LoginActivity", "success" + response.code());
                        stopbtnlogin = 1;
                        Toast.makeText(LogInActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        itnent = new Intent(LogInActivity.this, WelcomeActivity.class);
                        startActivity(itnent);
                    }
                    stopbtnlogin = 0;
                    Toast.makeText(LogInActivity.this, posts.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SignInGet> call, Throwable t) {
                    stopbtnlogin = 0;
                    Log.d("LoginActivity", "onFailure" + t.getMessage());
                    Toast.makeText(LogInActivity.this, "The server couldn't send a response:\n" +
                            "Ensure that the backend is working properly" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
