package com.ldream.arkadasinitani;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestCoz> {

    private Context context;
    private ArrayList<TestList> testsList;
    static public String sTestName;


    private int pos;


    DbHelper dbHelper;

    public TestAdapter(Context context, ArrayList<TestList> testList) {
        this.context = context;
        this.testsList = testList;

        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public TestCoz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_view, parent, false);

        return new TestCoz(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestCoz holder, int position) {


        int a = holder.getAdapterPosition();


        TestList testList = testsList.get(position);


        sTestName = testList.gettName();


        holder.testAdi.setText(sTestName);


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = holder.getAdapterPosition();

                silmeMetodu("" + sTestName);


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ReadySolve = new Intent(context, ReadyToSolveActivity.class);
                pos = holder.getAdapterPosition();
                context.startActivity(ReadySolve);
                TestList testRuntime = testsList.get(pos);
                sTestName = testRuntime.gettName();


            }

        });


    }

    private void silmeMetodu(String testAdi) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Testi Silmek İstediğinize Emin Misiniz ?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TestList testRuntime = testsList.get(pos);
                        sTestName = testRuntime.gettName();
                        dbHelper.removeTest(sTestName);
                        dbHelper.dropTable();
                        ((SolveTestActivity) context).onResume();
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


    @Override
    public int getItemCount() {
        return testsList.size();
    }

    class TestCoz extends RecyclerView.ViewHolder {


        TextView testAdi;
        ImageButton deleteBtn;

        public TestCoz(@NonNull View itemView) {
            super(itemView);

            testAdi = (TextView) itemView.findViewById(R.id.rv_testAdi);
            deleteBtn = itemView.findViewById(R.id.testDelete_button);


        }
    }


}
