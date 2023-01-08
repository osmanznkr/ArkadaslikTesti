package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class SolveTestActivity extends AppCompatActivity {

    private RecyclerView rView;
    private Toolbar toolbar;


    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_test);

        toolbar = (Toolbar) findViewById(R.id.readyToSolve_bar);

        rView = findViewById(R.id.rvTestler);

        dbHelper = new DbHelper(this);

        addData();


    }

    private void addData() {


        TestAdapter testAdapter = new TestAdapter(SolveTestActivity.this, dbHelper.getAllTests());

        rView.setAdapter(testAdapter);

    }

    @Override
    protected void onResume() {
        addData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);

    }
}