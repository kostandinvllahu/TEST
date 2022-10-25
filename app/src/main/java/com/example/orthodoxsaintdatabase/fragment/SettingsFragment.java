package com.example.orthodoxsaintdatabase.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orthodoxsaintdatabase.Activity.LoginActivity;
import com.example.orthodoxsaintdatabase.R;
import com.example.orthodoxsaintdatabase.SplashScreen.SplashScreenActivity;

import java.util.HashMap;
import java.util.Map;

public class SettingsFragment extends FragmentActivity {

    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    String ID;
    ImageView cancel;
    TextView txt1, txt2 , txt3;
    Button btnSubmit;
    EditText oldPassword, newPassword, authenticator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);


        cancel = findViewById(R.id.cancel);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        authenticator = findViewById(R.id.authenticator);
        btnSubmit = findViewById(R.id.btnSubmit);

        txt3.setVisibility(View.INVISIBLE);
        authenticator.setVisibility(View.INVISIBLE);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        ID = sharedPreferences.getString(TEXT,"");

        cancel.setOnClickListener(view -> finish());
        btnSubmit.setOnClickListener(view -> {
            if(oldPassword.getText().toString().equals("") || newPassword.getText().toString().equals("")){
                txt3.setVisibility(View.INVISIBLE);
                authenticator.setVisibility(View.INVISIBLE);
                oldPassword.setError("Insert old password");
                newPassword.setError("Insert new password");
                Toast.makeText(SettingsFragment.this, "Please fill both fields!", Toast.LENGTH_SHORT).show();
            }else{
                if(authenticator.getText().toString().equals("")){
                    authenticator.setError("Please insert authenticator code");
                }
                changePassword(ID,oldPassword.getText().toString(),newPassword.getText().toString(),authenticator.getText().toString());
                txt3.setVisibility(View.VISIBLE);
                authenticator.setVisibility(View.VISIBLE);
            }
        });

        //Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();
    }
    private void changePassword(String username, String oldPassword, String newPassword, String authenticator){

        if(authenticator.equals("")){
            String url = "https://orthodoxsaintdatabase.tech/APIchangepassword.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                    Toast.makeText(SettingsFragment.this, response.trim() , Toast.LENGTH_LONG).show();
                            }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsFragment.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("oldpassword", oldPassword);
                    params.put("newpassword",newPassword);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SettingsFragment.this);
            requestQueue.add(stringRequest);
        }else{
            String url = "https://orthodoxsaintdatabase.tech/APIchangepassword.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SettingsFragment.this, response.trim() , Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SettingsFragment.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("authenticator", authenticator);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(SettingsFragment.this);
            requestQueue.add(stringRequest);
        }


    }
}
