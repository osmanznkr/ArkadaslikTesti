package com.ldream.arkadasinitani;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultHolder> {

    private Context context;
    private ArrayList<Results> resultsList;
    DbHelper dbHelper;
    private int pos;
    static public String idd;

    public ResultAdapter(Context context, ArrayList<Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;

        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_results, parent, false);

        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {


        Results results = resultsList.get(position);

        String id = results.getId();
        String hazirlayan = results.getMaker();
        String cozen = results.getSolver();
        String dogruCevap = results.getTrues();
        String yanlisCevap = results.getFalses();

        holder.maker.setText("Hazırlayan: " + hazirlayan);
        holder.solver.setText("Çözen: " + cozen);
        holder.trueCount.setText(dogruCevap);
        holder.falseCount.setText(yanlisCevap);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = holder.getAdapterPosition();
                deleteMethod("" + id);

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder {

        TextView maker, solver, trueCount, falseCount;
        ImageButton deleteBtn;

        public ResultHolder(@NonNull View itemView) {
            super(itemView);

            maker = itemView.findViewById(R.id.rv_maker);
            solver = itemView.findViewById(R.id.rv_solver);
            trueCount = itemView.findViewById(R.id.rv_true);
            falseCount = itemView.findViewById(R.id.rv_false);
            deleteBtn = itemView.findViewById(R.id.resultDelete_button);

        }
    }

    private void deleteMethod(String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Sonucu Silmek İstediğinize Emin Misiniz ?")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Results resultt = resultsList.get(pos);
                        idd = resultt.getId();
                        dbHelper.deleteResult(idd);
                        ((ResultsActivity) context).onResume();

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
