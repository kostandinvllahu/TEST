package com.example.orthodoxsaintdatabase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.orthodoxsaintdatabase.R;
import com.example.orthodoxsaintdatabase.models.GetSaints;
import com.example.orthodoxsaintdatabase.viewmodels.AllSaintsViewModels;

public class MainActivity extends AppCompatActivity {

    private AllSaintsViewModels viewModels;
    GetSaints getSaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModels = new ViewModelProvider(this).get(AllSaintsViewModels.class);
        getAllSaints();
    }

    private void getAllSaints(){
        viewModels.getAllSaints().observe(this, getSaints ->
                        Toast.makeText(this, getSaints.getId(), Toast.LENGTH_LONG).show()
                );
    }
}