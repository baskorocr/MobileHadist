package com.alarm.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;

public class adapter  extends RecyclerView.Adapter<adapter.ViewHolder> {
    Context Ctx;
    LayoutInflater inflater;
    List<Hadist> hadists;

    public adapter(Context ctx,List<Hadist> Hadists){
        this.Ctx=ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.hadists = Hadists;
    }


    //yntyj menambahkan layout baru
    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list,viewGroup,false);
        return new ViewHolder(view);
    }

    //untuk memasukan item yang telah kita dapat dan telah masuk dilist
    //kedalam textview
    @Override
    public void onBindViewHolder(@NonNull adapter.ViewHolder viewHolder, int i) {
        viewHolder.number.setText(hadists.get(i).getNumber());
        viewHolder.id.setText(hadists.get(i).getId());
        viewHolder.arab.setText(hadists.get(i).getArab());


    }

    //menghitung banyaknya data yang telah didapat
    @Override
    public int getItemCount() {
        return hadists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,arab,id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.nomer);
            arab = itemView.findViewById(R.id.arab);
            id = itemView.findViewById(R.id.id);
            itemView.setOnClickListener(new View.OnClickListener() {
               //Mengirim data ke detail hadist activity
                @Override
                public void onClick(View view) {
                    int i = getAdapterPosition();
                    Log.d("data", String.valueOf(i));
                    Intent intent = new Intent(view.getContext(),detail_hadist.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("arab", hadists.get(i).getArab());
                    intent.putExtra("id", hadists.get(i).getId());
                    view.getContext().startActivity(intent);
                }
            });


        }
    }
}
