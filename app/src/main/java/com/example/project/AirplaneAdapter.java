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
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    private ArrayList<String> items1;
    private ArrayList<String> items2;
    private ArrayList<Long> items3;
    private ArrayList<Long> items4;
    public void setItems(ArrayList<String> items1, ArrayList<String> items2, ArrayList<Long> items3, ArrayList<Long> items4) {
        this.items1 = items1;
        this.items2 = items2;
        this.items3 = items3;
        this.items4 = items4;

        textView.setText(items1.toString());
        textView2.setText(items2.toString());
        textView3.setText(items3.toString());
        textView4.setText(items4.toString());
        notifyDataSetChanged();
    }
    public void clearData() {
        items.clear();
        notifyDataSetChanged();
    }
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

    public void addItem(String item1, String item2, Long item3, Long item4) {
        Airplane airplane = new Airplane();
        // Airplane 객체에 item1, item2, item3, item4의 값을 설정
        airplane.setArrAirportNm(item1);
        airplane.setDepAirportNm(item2);
        airplane.setDepPlandTime(item3);
        airplane.setArrPlandTime(item4);

        // Airplane을 어댑터에 추가
        items.add(airplane);
        notifyDataSetChanged();
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

        ViewHolder(View itemView) {
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
            textView3.setText(item.getDepPlandTime());
            textView4.setText(item.getArrPlandTime());

        }

    }

}
