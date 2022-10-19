package com.example.orthodoxsaintdatabase.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orthodoxsaintdatabase.R;
import com.example.orthodoxsaintdatabase.SplashScreen.SplashScreenActivity;
import com.example.orthodoxsaintdatabase.models.VerifyLogin;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public String response ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username, password;
        Button btnLogin;


        btnLogin = findViewById(R.id.button);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        VerifyLogin verifyLogin = new VerifyLogin();
        Context context = LoginActivity.this;


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://orthodoxsaintdatabase.tech/APIuserVerificationLogin.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                switch (response.trim()){
                                    case "1":
                                        username.setText("");
                                        password.setText("");
                                        startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                                        finish();
                                        break;
                                    case "2":
                                        Toast.makeText(LoginActivity.this, "We are sorry to notify you but your account has been blocked!", Toast.LENGTH_LONG).show();
                                        break;
                                    case "3":
                                        Toast.makeText(LoginActivity.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String>getParams(){
                        Map<String,String>params = new HashMap<String, String>();
                        params.put("USERNAME", username.getText().toString());
                        params.put("PASSWORD",password.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}
