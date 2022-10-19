package com.example.orthodoxsaintdatabase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orthodoxsaintdatabase.R;
import com.example.orthodoxsaintdatabase.SplashScreen.SplashScreenActivity;
import com.example.orthodoxsaintdatabase.models.GetSaints;
import com.example.orthodoxsaintdatabase.viewmodels.AllSaintsViewModels;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AllSaintsViewModels viewModels;
    Button btnLogout, btnSend;
    EditText receiverEmail, senderEmail, subject, header;
    MultiAutoCompleteTextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogout = findViewById(R.id.button2);
        btnSend = findViewById(R.id.btnSend);
        receiverEmail = findViewById(R.id.receiverEmail);
        senderEmail = findViewById(R.id.senderEmail);
        subject = findViewById(R.id.subject);
        header = findViewById(R.id.header);
        message = findViewById(R.id.message);
        viewModels = new ViewModelProvider(this).get(AllSaintsViewModels.class);

        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        btnSend.setOnClickListener(view -> {
            String url = "https://orthodoxsaintdatabase.tech/APIemailme.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            switch (response.trim()){
                                case "1":
                                    receiverEmail.setText("");
                                    senderEmail.setText("");
                                    subject.setText("");
                                    header.setText("");
                                    message.setText("");
                                    Toast.makeText(MainActivity.this, "Email sent succeesfully", Toast.LENGTH_SHORT).show();
                                    break;
                                case "2":
                                    Toast.makeText(MainActivity.this, "Email failed to send!", Toast.LENGTH_LONG).show();
                                    break;
                                case "4":
                                    Toast.makeText(MainActivity.this, "Fill the missing fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String>params = new HashMap<String, String>();
                    params.put("email", receiverEmail.getText().toString());
                    params.put("sender",senderEmail.getText().toString());
                    params.put("subject", subject.getText().toString());
                    params.put("header",header.getText().toString());
                    params.put("message", message.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        });

    }

}