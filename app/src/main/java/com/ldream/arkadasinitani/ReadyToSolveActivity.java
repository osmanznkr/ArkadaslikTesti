package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ReadyToSolveActivity extends AppCompatActivity {

    private TextView textViewQuestion, pageCount, trueAnswerCountTxt, falseAnswerCountTxt;
    private int questionIndex = 0;
    private ArrayList<String> questions;
    private ArrayList<String> trueAnswers;
    private ArrayList<String> answers;
    private ArrayList<String> questionsCodeList;
    SQLiteDatabase database;
    private Cursor cursor;
    private Button saveBtn, resultExitBtn;
    private ImageView closeImg;

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, rBtn;
    static public int trueAnswerCount = 0;
    static public int falseAnswerCount = 0;
    private Dialog resultDialog;
    private Toolbar toolbar;
    MediaPlayer correct, wrong, tadaa, goodScore, badScore;


    private Dialog nameDialogg;
    private ImageView imgclosee;
    private Button btnSaveNamee;
    public EditText rEditTxt;
    static public String rGetEditTxt;

    private String sQuery;
    private SQLiteStatement statement;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_to_solve);

        showDialog();

        correct = MediaPlayer.create(this, R.raw.correct);
        wrong = MediaPlayer.create(this, R.raw.wrong);
        tadaa = MediaPlayer.create(this, R.raw.tada);
        goodScore = MediaPlayer.create(this, R.raw.goodresult);
        badScore = MediaPlayer.create(this, R.raw.negative);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar = (Toolbar) findViewById(R.id.readyToSolve_bar);
        }


        init();

        questions = new ArrayList<>();
        trueAnswers = new ArrayList<>();
        questionsCodeList = new ArrayList<>();
        answers = new ArrayList<>();

        getQuestions();

        getTrueAnswers();

        getQKod();

        getAnswers();

        firstQuestionAndAnswer(0);


    }

    public void getQuestions() {

        TestAdapter.sTestName.toString();

        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM  '" + TestAdapter.sTestName + "'", null);


            int soruIndex = cursor.getColumnIndex("soru");

            while (cursor.moveToNext()) {
                questions.add(cursor.getString(soruIndex));

            }


            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        textViewQuestion.setText(questions.get(questionIndex));

    }

    public void getTrueAnswers() {
        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM  '" + TestAdapter.sTestName + "'", null);


            int qIndex = cursor.getColumnIndex("cevap");

            while (cursor.moveToNext()) {
                trueAnswers.add(cursor.getString(qIndex));

            }


            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQKod() {
        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM  '" + TestAdapter.sTestName + "'", null);


            int soruIndex = cursor.getColumnIndex("sKod");

            while (cursor.moveToNext()) {
                questionsCodeList.add(cursor.getString(soruIndex));

            }


            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAnswers() {
        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            cursor = database.rawQuery("SELECT * FROM Cevaplar WHERE cKod = ?", new String[]{questionsCodeList.get(questionIndex)});


            int cevapIndex = cursor.getColumnIndex("cevap");

            while (cursor.moveToNext()) {
                answers.add(cursor.getString(cevapIndex));

            }


            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyToSolveActivity_saveBtn(View v) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        rBtn = (RadioButton) findViewById(selectedId);
        String compareAnswer = rBtn.getText().toString();
        String firstClick = "CEVAPLA";
        String secondClick = "DİĞER SORU";
        String thirdClick = "TESTİ BİTİR";


        init();


        if (saveBtn.getText().equals(firstClick)) {
            if (compareAnswer.equals(trueAnswers.get(questionIndex).toString())) {
                rBtn.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                if (correct.isPlaying()) {
                    correct.seekTo(0);
                    correct.start();
                } else
                    correct.start();


                trueAnswerCount += 1;

            } else {
                rBtn.setBackgroundColor(getResources().getColor(R.color.rBtn_redColor));
                if (wrong.isPlaying()) {
                    wrong.seekTo(0);
                    wrong.start();
                } else
                    wrong.start();

                if (radioButton1.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton1.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                } else if (radioButton2.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton2.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                } else if (radioButton3.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton3.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                } else if (radioButton4.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton4.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                } else if (radioButton5.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton5.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                } else if (radioButton6.getText().toString().equals(trueAnswers.get(questionIndex).toString())) {
                    radioButton6.setBackgroundColor(getResources().getColor(R.color.rBtn_greenColor));
                }


                falseAnswerCount += 1;

            }


            if (questionIndex < 9) {
                saveBtn.setText(secondClick);

            } else {
                saveBtn.setText(thirdClick);
            }


            answers.clear();
        } else if (saveBtn.getText().equals(secondClick)) {

            radioButton1.setBackgroundColor(getResources().getColor(R.color.trans));
            radioButton2.setBackgroundColor(getResources().getColor(R.color.trans));
            radioButton3.setBackgroundColor(getResources().getColor(R.color.trans));
            radioButton4.setBackgroundColor(getResources().getColor(R.color.trans));
            radioButton5.setBackgroundColor(getResources().getColor(R.color.trans));
            radioButton6.setBackgroundColor(getResources().getColor(R.color.trans));

            if (questionIndex < 9) {
                questionIndex += 1;
                pageCount.setText(questionIndex + 1 + " / 10");

                getQuestions();

                getQKod();

                getAnswers();

                getTrueAnswers();

                firstQuestionAndAnswer(questionIndex);


                saveBtn.setText(firstClick);
                radioButton1.setChecked(true);


            } else {
                Toast.makeText(getApplicationContext(), "Testi Bitirdiniz", Toast.LENGTH_SHORT).show();
                saveBtn.setText(thirdClick);
            }


        } else {

            resultDialog();
            if (trueAnswerCount < 6) {
                badScore.start();
            } else {
                goodScore.start();
            }
            trueAnswerCount = 0;
            falseAnswerCount = 0;
        }
    }


    public void init() {
        pageCount = (TextView) findViewById(R.id.readySolveActivity_txtCount);
        textViewQuestion = (TextView) findViewById(R.id.readySolveActivity_txtQuestion);
        saveBtn = (Button) findViewById(R.id.readySolveActivity_btnSave);


        radioGroup = (RadioGroup) findViewById(R.id.readySolveActivity_radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn1);
        radioButton2 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn2);
        radioButton3 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn3);
        radioButton4 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn4);
        radioButton5 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn5);
        radioButton6 = (RadioButton) findViewById(R.id.readySolveActivity_radio_btn6);


    }

    public void firstQuestionAndAnswer(int a) {


        if (answers.size() == 2) {
            radioButton3.setVisibility(View.INVISIBLE);
            radioButton4.setVisibility(View.INVISIBLE);
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton1.setText(answers.get(0));
            radioButton2.setText(answers.get(1));

        } else if (answers.size() == 3) {
            radioButton4.setVisibility(View.INVISIBLE);
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton1.setText(answers.get(0));
            radioButton2.setText(answers.get(1));
            radioButton3.setText(answers.get(2));
        } else if (answers.size() == 4) {
            radioButton5.setVisibility(View.INVISIBLE);
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton1.setText(answers.get(0));
            radioButton2.setText(answers.get(1));
            radioButton3.setText(answers.get(2));
            radioButton4.setText(answers.get(3));
        } else if (answers.size() == 5) {
            radioButton6.setVisibility(View.INVISIBLE);
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton5.setVisibility(View.VISIBLE);
            radioButton1.setText(answers.get(0));
            radioButton2.setText(answers.get(1));
            radioButton3.setText(answers.get(2));
            radioButton4.setText(answers.get(3));
            radioButton5.setText(answers.get(4));
        } else {
            radioButton3.setVisibility(View.VISIBLE);
            radioButton4.setVisibility(View.VISIBLE);
            radioButton5.setVisibility(View.VISIBLE);
            radioButton6.setVisibility(View.VISIBLE);
            radioButton1.setText(answers.get(0));
            radioButton2.setText(answers.get(1));
            radioButton3.setText(answers.get(2));
            radioButton4.setText(answers.get(3));
            radioButton5.setText(answers.get(4));
            radioButton6.setText(answers.get(5));
        }
    }

    public void resultDialog() {
        resultDialog = new Dialog(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(resultDialog.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        resultDialog.setCancelable(false);
        resultDialog.setContentView(R.layout.custom_dialog_results);

        trueAnswerCountTxt = (TextView) resultDialog.findViewById(R.id.results_txtView_trueAnswers);
        falseAnswerCountTxt = (TextView) resultDialog.findViewById(R.id.results_txtView_falseAnswers);
        closeImg = (ImageView) resultDialog.findViewById(R.id.results_imgClose);
        resultExitBtn = (Button) resultDialog.findViewById(R.id.results_btnExit);


        trueAnswerCountTxt.setText("Doğru Cevap Sayısı: " + trueAnswerCount);
        falseAnswerCountTxt.setText("Yanlış Cevap Sayısı: " + falseAnswerCount);


        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS SONUCLAR (id INTEGER PRIMARY KEY, hazirlayan VARCHAR, cozen VARCHAR, dogru VARCHAR, yanlis VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            sQuery = "INSERT INTO SONUCLAR (hazirlayan, cozen, dogru, yanlis ) VALUES (?, ?, ?, ?)";
            statement = database.compileStatement(sQuery);
            statement.bindString(1, TestAdapter.sTestName);
            statement.bindString(2, rGetEditTxt);
            statement.bindString(3, trueAnswerCountTxt.getText().toString());
            statement.bindString(4, falseAnswerCountTxt.getText().toString());
            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }


        resultDialog.getWindow().setAttributes(params);
        resultDialog.show();

    }

    public void customDialog_saveBtnn(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }

    public void results_imgClose(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }

    @Override
    public void onBackPressed() {
        trueAnswerCount = 0;
        falseAnswerCount = 0;

        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);

    }

    public void showDialog() {


        nameDialogg = new Dialog(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(nameDialogg.getWindow().getAttributes());
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        nameDialogg.setCancelable(false);
        nameDialogg.setContentView(R.layout.custom_dialog_name);

        imgclosee = (ImageView) nameDialogg.findViewById(R.id.custom_dialog_imgClose);
        rEditTxt = (EditText) nameDialogg.findViewById(R.id.custom_dialog_name_editTxt);
        btnSaveNamee = (Button) nameDialogg.findViewById(R.id.custom_dialog_btnSave);


        nameDialogg.getWindow().setAttributes(params);
        nameDialogg.show();

    }

    public void imgClose(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up, R.anim.slide_in_down);
    }

    public void customDialog_saveBtn(View v) {

        rGetEditTxt = rEditTxt.getText().toString();


        nameDialogg.dismiss();

    }


}



