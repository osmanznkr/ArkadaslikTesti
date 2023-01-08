package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class CreateTestActivity extends AppCompatActivity {

    public TextView textViewQuestion, pageCount;
    static public SQLiteDatabase database;
    public ArrayList<String> questionsList;
    public ArrayList<String> questionsCodeList;
    public ArrayList<String> answersList;
    private Button btnSave;
    public String sQuery;
    static public SQLiteStatement statement;
    DbHelper dbHelper;
    ImageButton changeQuestion;


    //Custom Dialog

    private Dialog nameDialog;
    private ImageView imgclose;
    private Button btnSaveName;
    public EditText editText;

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, rB;

    public Random rndQuestion;
    public int rndQuestNumber;
    static public int counter = 1;
    private Cursor cursor;
    static public String getEditTxt;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);
        changeQuestion = (ImageButton) findViewById(R.id.change_question_btn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.createTest_bar);

        dbHelper = new DbHelper(this);


        showDialog();

        init();

        getQuestions();

        getAnswers();

        showAnswers();

        counter = 1;


    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Testi Bitirmek İstediğinize Emin Misiniz ?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.execSQL("DROP TABLE '" + getEditTxt + "'");
                        dbHelper.removeTest(getEditTxt);


                        Intent mainIntent = new Intent(CreateTestActivity.this, MainActivity.class);
                        finish();
                        startActivity(mainIntent);
                        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
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


    // Kaydet Butonu

    public void createActivity_saveBtn(View v) {


        int selectedId = radioGroup.getCheckedRadioButtonId();
        rB = (RadioButton) findViewById(selectedId);

        if (counter == 1) {


            try {

                sQuery = "INSERT INTO '" + getEditTxt + "' (Soru, Cevap, sKod) VALUES (?, ?, ?)";
                statement = database.compileStatement(sQuery);
                statement.bindString(1, textViewQuestion.getText().toString());
                statement.bindString(2, rB.getText().toString());
                statement.bindString(3, questionsCodeList.get(rndQuestNumber).toString());
                statement.execute();


            } catch (Exception e) {
                e.printStackTrace();
            }

            questionsList.remove(rndQuestNumber);
            questionsCodeList.remove(rndQuestNumber);

            rndQuestNumber = rndQuestion.nextInt(questionsCodeList.size());
            textViewQuestion.setText(questionsList.get(rndQuestNumber));
            answersList = new ArrayList<>();
            getAnswers();

            showAnswers();

            counter += 1;
            pageCount.setText(counter + " / 10");
            radioButton1.setChecked(true);

        } else if (counter > 1 && counter < 10) {


            try {

                sQuery = "INSERT INTO '" + getEditTxt + "' (Soru, Cevap, sKod) VALUES (?, ?, ?)";
                statement = database.compileStatement(sQuery);
                statement.bindString(1, textViewQuestion.getText().toString());
                statement.bindString(2, rB.getText().toString());
                statement.bindString(3, questionsCodeList.get(rndQuestNumber).toString());
                statement.execute();


            } catch (Exception e) {
                e.printStackTrace();
            }

            questionsList.remove(rndQuestNumber);
            questionsCodeList.remove(rndQuestNumber);


            rndQuestNumber = rndQuestion.nextInt(questionsCodeList.size());
            textViewQuestion.setText(questionsList.get(rndQuestNumber));

            answersList = new ArrayList<>();


            getAnswers();

            showAnswers();


            counter += 1;
            pageCount.setText(counter + " / 10");
            radioButton1.setChecked(true);


        } else if (counter == 10) {
            try {

                sQuery = "INSERT INTO '" + getEditTxt + "' (Soru, Cevap, sKod) VALUES (?, ?, ?)";
                statement = database.compileStatement(sQuery);
                statement.bindString(1, textViewQuestion.getText().toString());
                statement.bindString(2, rB.getText().toString());
                statement.bindString(3, questionsCodeList.get(rndQuestNumber).toString());
                statement.execute();


            } catch (Exception e) {
                e.printStackTrace();
            }

            questionsList.remove(rndQuestNumber);
            questionsCodeList.remove(rndQuestNumber);


            Toast.makeText(getApplicationContext(), "Bütün Soruları Yanıtladınız", Toast.LENGTH_SHORT).show();


            Intent solveTestIntent = new Intent(CreateTestActivity.this, SolveTestActivity.class);
            finish();
            startActivity(solveTestIntent);
            overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
        }


    }

    public void showAnswers() {
        if (answersList.size() == 2) {
            radioButton3.setVisibility(View.INVISIBLE);
            radioButton4.setVisibility(View.INVISIBLE);
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton1.setText(answersList.get(0));
            radioButton2.setText(answersList.get(1));

        } else if (answersList.size() == 3) {
            radioButton4.setVisibility(View.INVISIBLE);
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton1.setText(answersList.get(0));
            radioButton2.setText(answersList.get(1));
            radioButton3.setText(answersList.get(2));
        } else if (answersList.size() == 4) {
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton1.setText(answersList.get(0));
            radioButton2.setText(answersList.get(1));
            radioButton3.setText(answersList.get(2));
            radioButton4.setText(answersList.get(3));
        } else if (answersList.size() == 5) {
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton5.setVisibility(View.VISIBLE);
            radioButton1.setText(answersList.get(0));
            radioButton2.setText(answersList.get(1));
            radioButton3.setText(answersList.get(2));
            radioButton4.setText(answersList.get(3));
            radioButton5.setText(answersList.get(4));
        } else {
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton5.setVisibility(View.VISIBLE);
            radioButton6.setVisibility(View.VISIBLE);
            radioButton1.setText(answersList.get(0));
            radioButton2.setText(answersList.get(1));
            radioButton3.setText(answersList.get(2));
            radioButton4.setText(answersList.get(3));
            radioButton5.setText(answersList.get(4));
            radioButton6.setText(answersList.get(5));
        }
    }

    private void init() {
        pageCount = (TextView) findViewById(R.id.createActivity_txtCount);
        textViewQuestion = (TextView) findViewById(R.id.createActivity_txtQuestion);
        questionsList = new ArrayList<>();
        questionsCodeList = new ArrayList<>();
        answersList = new ArrayList<>();
        rndQuestion = new Random();


        btnSave = (Button) findViewById(R.id.createActivity_btnSavee);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radio_btn1);
        radioButton2 = (RadioButton) findViewById(R.id.radio_btn2);
        radioButton3 = (RadioButton) findViewById(R.id.radio_btn3);
        radioButton4 = (RadioButton) findViewById(R.id.radio_btn4);
        radioButton5 = (RadioButton) findViewById(R.id.radio_btn5);
        radioButton6 = (RadioButton) findViewById(R.id.radio_btn6);

    }

    public void getAnswers() {
        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM Cevaplar WHERE cKod = ?", new String[]{questionsCodeList.get(rndQuestNumber)});


            int aIndex = cursor.getColumnIndex("cevap");

            while (cursor.moveToNext()) {
                answersList.add(cursor.getString(aIndex));

            }


            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQuestions() {
        for (Map.Entry soru : SplashScreenActivity.questionsHashmap.entrySet()) {
            questionsList.add(String.valueOf(soru.getValue()));
            questionsCodeList.add(String.valueOf(soru.getKey()));
        }


        rndQuestNumber = rndQuestion.nextInt(questionsCodeList.size());
        textViewQuestion.setText(questionsList.get(rndQuestNumber));


    }

    public void showDialog() {


        nameDialog = new Dialog(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(nameDialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        nameDialog.setCancelable(false);
        nameDialog.setContentView(R.layout.custom_dialog_name);

        imgclose = (ImageView) nameDialog.findViewById(R.id.custom_dialog_imgClose);
        editText = (EditText) nameDialog.findViewById(R.id.custom_dialog_name_editTxt);
        btnSaveName = (Button) nameDialog.findViewById(R.id.custom_dialog_btnSave);


        nameDialog.getWindow().setAttributes(params);
        nameDialog.show();


    }


    public void imgClose(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }

    @SuppressLint("Range")
    public void customDialog_saveBtn(View v) {

        getEditTxt = editText.getText().toString();


        if (!TextUtils.isEmpty(getEditTxt)) {

            if (!dbHelper.nameExistInDB(getEditTxt)) {
                addData();
                database.execSQL("CREATE TABLE IF NOT EXISTS '" + getEditTxt + "' ( soru VARCHAR, cevap VARCHAR, sKod VARCHAR)");
            } else {
                Toast.makeText(getApplicationContext(), "Böyle Bir Test Mevcut. Lütfen Başka Bir İsim Giriniz...", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(this, CreateTestActivity.class);
                finish();
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
            }


            nameDialog.dismiss();

        } else
            Toast.makeText(getApplicationContext(), "Lütfen İsminizi Giriniz...", Toast.LENGTH_SHORT).show();

    }

    private void addData() {
        long id = dbHelper.addTest(getEditTxt);
    }

    public void change_question(View v) {
        rndQuestNumber = rndQuestion.nextInt(questionsCodeList.size());
        textViewQuestion.setText(questionsList.get(rndQuestNumber));

        answersList = new ArrayList<>();


        getAnswers();

        showAnswers();
    }


}


