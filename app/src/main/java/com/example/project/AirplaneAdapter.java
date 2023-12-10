package com.example.project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AirplaneAdapter extends RecyclerView.Adapter<AirplaneAdapter.ViewHolder> {
    ArrayList<Airplane> items = new ArrayList<Airplane>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.airplane_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Airplane item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Airplane item) {
        items.add(item);
    }

    public void setItems(ArrayList<Airplane> items) {
        this.items = items;
    }

    public Airplane getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);

            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);

        }

        public void setItem(Airplane item) {
            textView.setText(item.getDepAirportNm());
            Log.d("영화명 : ", textView.getText().toString());
            textView2.setText(item.getArrAirportNm());
            textView3.setText(item.getDepPlandTime()+"시");
            textView4.setText(item.getArrPlandTime()+"시");

        }

    }

}
