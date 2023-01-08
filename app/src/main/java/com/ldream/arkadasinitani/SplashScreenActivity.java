package com.ldream.arkadasinitani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {

    //Sorular İçin Listeler

    private String[] questionsList = {"Günlük hayatında seyahet etmek için hangisini tercih ediyorsun?",
            "Hangi tür oyunları seviyorsun?",
            "Adroid'e mi yoksa IPhone'a mı sahipsiniz?",
            "Hangi Rengi Daha Çok Tercih Edersiniz?",
            "Boş zamanlarınızda neler yaparsınız?",
            "Favori dondurman hangisi?",
            "Eğer evin yansaydı evden kendinle alacağın tek şey ne olurdu?",
            "Eğer piyangoyu kazansaydın ilk ne alırdın?",
            "Favori içeceğin hangisi?",
            "Yılın en sevdiğiniz mevsimi hangisidir?",
            "En çok hangisini kullanırsınız?",
            "Nereye tatile gitmek istersiniz?",
            "Sizin için hangisi daha önemlidir?",
            "Favori tatlın hangisi?",
            "Hangisini seçersiniz?",
            "Köpeklerden korkar mısınız?"};

    private String[] questionsCodeList = {"soru1", "soru2", "soru3", "soru4", "soru5", "soru6", "soru7", "soru8", "soru9", "soru10", "soru11", "soru12", "soru13", "soru14", "soru15", "soru16"};

    //Cevaplar İçin Listeler

    private String[] answersList = {"Bisiklet", "Araba", "Otobüs", "Yaya",
            "Mobil Oyunlar", "PC Oyunları", "Sokak Oyunları", "Zeka Oyunları",
            "Android", "IPhone",
            "Kırmızı", "Mavi", "Yeşil", "Sarı", "Siyah",
            "Uyumak", "Kitap Okumak", "Gezmek", "Oyun Oynamak", "Yürüyüş Yapmak",
            "Vanilyalı", "Çikolatalı", "Çilekli", "Karamelli", "Limonlu",
            "Telefonum", "Bilgisayarım", "Dosyalarım", "Cüzdanım",
            "Ev", "Araba", "Ada", "Uçak",
            "Çay", "Kahve", "Su", "Kola", "Soda", "Limonata",
            "İlkbahar", "Yaz", "Sonbahar", "Kış",
            "Whatsapp", "Facebook", "Twitter", "İnstagram", "Youtube", "Snapchat",
            "Roma", "Paris", "Venedik", "Bahamalar", "Kayseri:D",
            "Para", "Aşk", "Aile Ve Arkadaşlar", "Kariyer",
            "Sütlaç", "Baklava", "Kadayıf", "Yaş Pasta",
            "Güneş", "Ay",
            "Evet", "Hayır"};
    private String[] answersCodeList = {"soru1", "soru1", "soru1", "soru1",
            "soru2", "soru2", "soru2", "soru2",
            "soru3", "soru3",
            "soru4", "soru4", "soru4", "soru4", "soru4",
            "soru5", "soru5", "soru5", "soru5", "soru5",
            "soru6", "soru6", "soru6", "soru6", "soru6",
            "soru7", "soru7", "soru7", "soru7",
            "soru8", "soru8", "soru8", "soru8",
            "soru9", "soru9", "soru9", "soru9", "soru9", "soru9",
            "soru10", "soru10", "soru10", "soru10",
            "soru11", "soru11", "soru11", "soru11", "soru11", "soru11",
            "soru12", "soru12", "soru12", "soru12", "soru12",
            "soru13", "soru13", "soru13", "soru13",
            "soru14", "soru14", "soru14", "soru14",
            "soru15", "soru15",
            "soru16", "soru16",
    };

    private ProgressBar mProgress;
    private TextView mTextView;
    private SQLiteDatabase database;
    private float maxProgress = 100f, changedProgress, progressState = 0;
    private Cursor cursor;
    static public HashMap<String, String> questionsHashmap;
    private String sQuery;
    private SQLiteStatement statement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgress = (ProgressBar) findViewById(R.id.splashScreenAvtivity_progressBar);
        mTextView = (TextView) findViewById(R.id.splashScreenActivity_txtView);
        questionsHashmap = new HashMap<>();


        //SQLite Database

        try {
            database = this.openOrCreateDatabase("ArkadasiniTani", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Sorular (id INTEGER PRIMARY KEY, sKod VARCHAR UNIQUE, soru VARCHAR)");
            database.execSQL("DELETE FROM Sorular");
            sqlAddQuestions();

            database.execSQL("CREATE TABLE IF NOT EXISTS Cevaplar (cKod VARCHAR, cevap VARCHAR, FOREIGN KEY (cKod) REFERENCES Sorular (sKod))");
            database.execSQL("DELETE FROM Cevaplar");
            sqlAddAnswers();


            cursor = database.rawQuery("SELECT * FROM Sorular", null);
            changedProgress = maxProgress / cursor.getCount();

            int qCodeIndex = cursor.getColumnIndex("sKod");
            int aCodeIndex = cursor.getColumnIndex("soru");


            mTextView.setText("Sorularr Yükleniyor...");

            while (cursor.moveToNext()) {
                questionsHashmap.put(cursor.getString(qCodeIndex), cursor.getString(aCodeIndex));
                progressState += changedProgress;
                mProgress.setProgress((int) progressState);
            }


            mTextView.setText("Sorular Alındı, Uygulama Başlatılıyor...");
            cursor.close();

            new CountDownTimer(1100, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    finish();
                    startActivity(mainIntent);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sqlAddQuestions() {


        try {
            for (int s = 0; s < questionsList.length; s++) {
                sQuery = "INSERT INTO Sorular (sKod, soru) VALUES (?, ?)";
                statement = database.compileStatement(sQuery);
                statement.bindString(1, questionsCodeList[s]);
                statement.bindString(2, questionsList[s]);
                statement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void sqlAddAnswers() {
        try {
            for (int c = 0; c < answersList.length; c++) {
                sQuery = "INSERT INTO Cevaplar (cKod, cevap) VALUES (?, ?)";
                statement = database.compileStatement(sQuery);
                statement.bindString(1, answersCodeList[c]);
                statement.bindString(2, answersList[c]);
                statement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}