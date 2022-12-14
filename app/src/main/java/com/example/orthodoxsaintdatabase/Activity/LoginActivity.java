package com.example.orthodoxsaintdatabase.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
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

    EditText username, password, txtUsername, txtEmail, txtPassword;
    Button btnLogin, btnCreate;
    TextView txtforgot,txtRegister;
    Boolean verify = false;
    Boolean click = false;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String CREDIT = "credit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verifyClick();

        btnLogin = findViewById(R.id.button);
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        txtforgot = findViewById(R.id.txtforgot);
        txtRegister = findViewById(R.id.register);
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

        txtRegister.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            AlertDialog dialog;
            final View createAccount = getLayoutInflater().inflate(R.layout.register, null);
            txtUsername = (EditText) createAccount.findViewById(R.id.txtUsername);
            txtEmail = (EditText) createAccount.findViewById(R.id.txtEmail);
            txtPassword = (EditText) createAccount.findViewById(R.id.txtPassword);
            btnCreate = (Button) createAccount.findViewById(R.id.btnCreate);
            builder.setView(createAccount);
            builder.setTitle("Create Account");
            dialog = builder.create();
            dialog.show();
//            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//            layoutParams.copyFrom(dialog.getWindow().getAttributes());
//            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setAttributes(layoutParams);;

            btnCreate.setOnClickListener(v1 -> {
                //API qe krijon llogari te re
                if(txtUsername.getText().toString().equals("")){
                    txtUsername.setError("Insert username");
                    return;
                }

                if(txtEmail.getText().toString().equals("")){
                    txtEmail.setError("Insert email");
                    return;
                }

                if(txtPassword.getText().toString().equals("")){
                    txtPassword.setError("Insert password");
                    return;
                }
                createAccount(txtUsername.getText().toString(), txtEmail.getText().toString(),txtPassword.getText().toString());
            });

        });
    }

    private void createAccount(String Username, String Email, String Password){
        String url = "https://orthodoxsaintdatabase.tech/APIregister.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (response.trim()){
                            case "1":
                                txtEmail.setError("Email already in use!");
                                Toast.makeText(LoginActivity.this,"This email is already in use!", Toast.LENGTH_LONG).show();
                                break;
                            case "2":
                                txtUsername.setError("Username is taken!");
                                Toast.makeText(LoginActivity.this, "This username is taken!", Toast.LENGTH_LONG).show();
                                break;
                            case "3":
                                Toast.makeText(LoginActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();
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
                    params.put("USERNAME", Username);
                    params.put("EMAIL", Email);
                    params.put("PASSWORD", Password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
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

    private void getCredit(String user){
        String url = "https://orthodoxsaintdatabase.tech/APIgetCredit.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CREDIT);
                        editor.putString(CREDIT, response);
                        editor.apply();
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
                params.put("USERNAME", user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    private void Login(){
        String url = "https://orthodoxsaintdatabase.tech/APIuserVerificationLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (response.trim()) {
                            case "1":
                                getCredit(String.valueOf(username.getText()));
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
