package com.example.inlab.calculadora;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inlab.calculadora.Retrofit.Puntuacione;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.MyCustomViewHolder> {

    public ArrayList<String> dataSet;
    public RecyclerViewTouchDelegate delegate;

    @NonNull
    @Override
    public MyCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);

        return new MyCustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCustomViewHolder holder, int position) {
        String item = dataSet.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyCustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;


        public MyCustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.didSelectedItemAtRow(getAdapterPosition());
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    delegate.longPressSelectedItemAtRow(getAdapterPosition());
                    return true;
                }
            });
        }

        void setItem(String str) {
            textView.setText(str);
        }
    }
}
