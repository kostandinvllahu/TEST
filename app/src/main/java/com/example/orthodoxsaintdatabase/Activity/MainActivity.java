package com.example.orthodoxsaintdatabase.Activity;

import static com.example.orthodoxsaintdatabase.Activity.LoginActivity.CREDIT;
import static com.example.orthodoxsaintdatabase.Activity.LoginActivity.SHARED_PREFS;
import static com.example.orthodoxsaintdatabase.Activity.LoginActivity.TEXT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.orthodoxsaintdatabase.R;
import com.example.orthodoxsaintdatabase.fragment.SettingsFragment;
import com.example.orthodoxsaintdatabase.models.GetValues;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Button btnLogout, btnSend;
    EditText receiverEmail, senderEmail, subject, header;
    TextView emailCounter;
    MultiAutoCompleteTextView message;
    ImageView settings;
    String ID;
    String CREDITS;
    String removeOne = "1";




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
        settings = findViewById(R.id.settings);
        emailCounter = findViewById(R.id.emailCounter);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        ID = sharedPreferences.getString(TEXT,"");
        CREDITS = sharedPreferences.getString(CREDIT,"");
        emailCounter.setText(CREDITS);
        popup();
       // getAccountKey(ID);


        btnLogout.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsFragment.class);
                startActivity(i);
            }
        });

        btnSend.setOnClickListener(view -> {
            String url = "https://orthodoxsaintdatabase.tech/APIemailme.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        switch (response.trim()){
                            case "1":
                                receiverEmail.setText("");
                                senderEmail.setText("");
                                subject.setText("");
                                header.setText("");
                                message.setText("");
                                Toast.makeText(MainActivity.this, "Email sent succeesfully", Toast.LENGTH_SHORT).show();
                                countEmails();
                                popup();
                                try{
                                   int counter = Integer.parseInt(emailCounter.getText().toString().trim());
                                       counter -= 1;
                                       emailCounter.setText(String.valueOf(counter));
                                }catch (Exception ex){
                                    Toast.makeText(MainActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
                                }
                                break;
                            case "2":
                                Toast.makeText(MainActivity.this, "Email failed to send!", Toast.LENGTH_LONG).show();
                                break;
                            case "442":
                                receiverEmail.setError("Fill receiver email");
                                senderEmail.setError("Fill sender email");
                                subject.setError("Fill subject");
                                header.setError("Fill header");
                                message.setError("Fill message");
                                Toast.makeText(MainActivity.this, "Fill the missing fields", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String>params = new HashMap<>();
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

    private void trialEmails(){
        /**
         * vlera key do merret me api nga cloud qe do thot nqs eshte trial ose jo
         * nqs eshte trial do vazhdoj te dali ky popup, perndryshe nuk do shfaqet fare.
         * */

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ERROR!");
                builder.setMessage("You have spent all your credit. Total credit left is: " + emailCounter.getText().toString() +" ." +
                        "If you want more emails please purchase more for $1 = 1 credit." +
                        "Thank you for using EMS!").setNegativeButton("OK", (dialog, id) -> {

                        });
                builder.setPositiveButton("Buy credit", (dialogInterface, i) -> {
                    Uri uri = Uri.parse("https://www.fiverr.com/kostandinvllahu/give-you-email-credit"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                });
                builder.create();
                builder.show();
    }

//    private void getAccountKey(String ID){
//        /**
//         * ketu do behet nje servis qe do coj request user ID dhe do marri mbrapsht
//         * accountType nqs eshte trial ose premium.
//         * Ne baze te accountType do dali popup ose jo
//         * dhe vlera qe do vij do futet ne int Key qe i kalohet trialEmails
//         * nqs nuk eshte trial do merret shuma e email qe ka blere dhe do kalohet te
//         * countEmails si vlere.
//         * */
//    }

    private void countEmails(){
        String url = "https://orthodoxsaintdatabase.tech/APIgetCredit.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(CREDIT, response);
                        editor.apply();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("USER", ID);
                params.put("CREDIT", removeOne);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private void popup(){
        if(emailCounter.getText().toString().trim().equals("0")){
            btnSend.setEnabled(false);
            trialEmails();
        }
    }


}