package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView rView;
    DbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        rView = findViewById(R.id.rv_resultss);

        dbHelper = new DbHelper(this);

        addData();
    }

    private void addData() {
        ResultAdapter resultAdapter = new ResultAdapter(ResultsActivity.this, dbHelper.getResults());
        rView.setAdapter(resultAdapter);
    }

    @Override
    protected void onResume() {
        addData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);

        super.onBackPressed();
    }
}