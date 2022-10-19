package com.example.orthodoxsaintdatabase.models;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orthodoxsaintdatabase.Activity.LoginActivity;
import com.example.orthodoxsaintdatabase.SplashScreen.SplashScreenActivity;

import java.util.HashMap;
import java.util.Map;


public class VerifyLogin extends AppCompatActivity {
    private static String getResponse;
    LoginActivity loginActivity = new LoginActivity();
    public void verifyUser(String Username, String Password, Context context){
        String url = "https://orthodoxsaintdatabase.tech/APIuserVerificationLogin.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        switch (response.trim()){
                            case "1":
                                Toast.makeText(context, "Credentials verified successfully", Toast.LENGTH_SHORT).show();
                                getResponse = response.trim();
                                break;
                            case "2":
                                Toast.makeText(context, "We are sorry to notify you but your account has been blocked!", Toast.LENGTH_LONG).show();
                                break;
                            case "3":
                                Toast.makeText(context, "Wrong username/password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String>params = new HashMap<String, String>();
                params.put("USERNAME", Username);
                params.put("PASSWORD",Password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public String getValue(){
        return getResponse;
    }
}
