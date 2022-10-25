package com.example.orthodoxsaintdatabase.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.orthodoxsaintdatabase.models.GetValues;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public String response ="";
    EditText username, password;
    Button btnLogin;
    TextView txtforgot;
    Boolean verify = false;
    Boolean click = false;
    GetValues values = new GetValues();
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verifyClick();

        btnLogin = findViewById(R.id.button);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        txtforgot = findViewById(R.id.txtforgot);
        txtforgot.setOnClickListener(view -> {
            if(!verify){
                verify = true;
                verifyClick(1);
            }else{
                verify = false;
                verifyClick(2);
            }

        });
        btnLogin.setOnClickListener(view -> {
            Login();
        });
    }

    private void verifyClick(int option) {
        if(verify) {
            txtforgot.setText("Return to Login");
            username.setHint("Email");
            password.setEnabled(false);
            username.setText("");
            password.setText("");
            password.setVisibility(View.INVISIBLE);
            btnLogin.setText("Reset Password");
            if(option == 1) {
                btnLogin.setOnClickListener(view -> {
                    String url = "https://orthodoxsaintdatabase.tech/APIforgotpassword.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    switch (response.trim()) {
                                        case "1":
                                            Toast.makeText(LoginActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "2":
                                            if(username.getText().toString().equals("")){
                                                username.setError("Please insert the email!");
                                                Toast.makeText(LoginActivity.this, "Please insert the email!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(LoginActivity.this, "Email doesnt exist in our database!"  , Toast.LENGTH_LONG).show();
                                            }
                                            break;
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", username.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(stringRequest);
                });
            }else{
                Toast.makeText(this, "Vlera nuk eshte 1", Toast.LENGTH_SHORT).show();
            }
        }else{
            txtforgot.setText("Forgot Password");
            username.setHint("Username");
            password.setEnabled(true);
            password.setVisibility(View.VISIBLE);
            btnLogin.setText("Login");
            if(option == 2) {
                btnLogin.setOnClickListener(view -> {
                    String url = "https://orthodoxsaintdatabase.tech/APIuserVerificationLogin.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    switch (response.trim()) {
                                        case "1":
                                            //username.getText().toString()
                                            username.setText("");
                                            password.setText("");
                                            click = false;
                                            Intent intent = new Intent(LoginActivity.this, SplashScreenActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("ID",username.getText().toString());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                            //Toast.makeText(LoginActivity.this, String.valueOf(option)  , Toast.LENGTH_LONG).show();
                                            break;
                                        case "2":
                                            Toast.makeText(LoginActivity.this, "We are sorry to notify you but your account has been blocked!", Toast.LENGTH_LONG).show();
                                            click = false;
                                            break;
                                        case "3":
                                            username.setError("Wrong username");
                                            password.setError("Wrong password");
                                            Toast.makeText(LoginActivity.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                                            click = false;
                                            break;
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("USERNAME", username.getText().toString());
                            params.put("PASSWORD", password.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                    requestQueue.add(stringRequest);
                });
            }else{
                Toast.makeText(this, "Vlera nuk eshte 2", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void Login(){
        String url = "https://orthodoxsaintdatabase.tech/APIuserVerificationLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (response.trim()) {
                            case "1":

                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(TEXT, username.getText().toString());
                                editor.apply();
                                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));

                                username.setText("");
                                password.setText("");
                                click = false;
                                startActivity(new Intent(LoginActivity.this, SplashScreenActivity.class));
                                finish();
                                //Toast.makeText(LoginActivity.this, String.valueOf(option)  , Toast.LENGTH_LONG).show();
                                break;
                            case "2":
                                Toast.makeText(LoginActivity.this, "We are sorry to notify you but your account has been blocked!", Toast.LENGTH_LONG).show();
                                click = false;
                                break;
                            case "3":
                                Toast.makeText(LoginActivity.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                                click = false;
                                break;
                            case "44":
                                username.setError("Please fill the username field!");
                                password.setError("Please fill the password field!");
                                Toast.makeText(LoginActivity.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("USERNAME", username.getText().toString());
                params.put("PASSWORD", password.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
