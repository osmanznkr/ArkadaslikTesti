package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void mainClicks(View v) {
        switch (v.getId()) {
            case R.id.main_activity_btnCreateTest:

                Intent createTestIntent = new Intent(this, CreateTestActivity.class);
                finish();
                startActivity(createTestIntent);
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);


                break;

            case R.id.main_activity_btnSolveTest:

                Intent SolveTestIntent = new Intent(this, SolveTestActivity.class);
                finish();
                startActivity(SolveTestIntent);
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
                ReadyToSolveActivity.trueAnswerCount = 0;
                ReadyToSolveActivity.falseAnswerCount = 0;

                break;

            case R.id.main_activity_btnStatistics:

                database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);

                database.execSQL("CREATE TABLE IF NOT EXISTS SONUCLAR (id INTEGER PRIMARY KEY, hazirlayan VARCHAR, cozen VARCHAR, dogru VARCHAR, yanlis VARCHAR)");

                Intent ResultIntent = new Intent(this, ResultsActivity.class);
                finish();
                startActivity(ResultIntent);
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);


                break;

            case R.id.main_activity_btnExit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setMessage("Çıkmak İstediğinize Emin Misiniz ?")
                        .setCancelable(false)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Çıkmak İstediğinize Emin Misiniz ?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


}



