package com.example.project;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView selectedDateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        ImageButton showCalendarBtn = findViewById(R.id.showCalendarBtn);
        ImageButton ResetBtn = findViewById(R.id.resetBtn);
        Spinner startingPoint = findViewById(R.id.spinnerStartingPoint);
        Spinner endPoint = findViewById(R.id.spinnerEndPoint);


        showCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });


        ArrayAdapter<CharSequence> startingPointAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.starting_points,
                android.R.layout.simple_spinner_item
        );
        startingPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startingPoint.setAdapter(startingPointAdapter);


        ArrayAdapter<CharSequence> endPointAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.end_points,
                android.R.layout.simple_spinner_item
        );
        endPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endPoint.setAdapter(endPointAdapter);
    }

    private void showDatePickerDialog() {
        // 현재 날짜 가져오기
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // DatePickerDialog 띄우기
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, yearSelected, monthOfYear, dayOfMonth) -> {
                    String selectedDate = yearSelected + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일";
                    selectedDateTextView.setText(selectedDate);
                    selectedDateTextView.setAlpha(1.0f);
                    selectedDateTextView.setTextColor(Color.BLACK);
                },
                year, month, day
        );

        datePickerDialog.show();
    }


    private void reset() {

        selectedDateTextView.setText("선택된 날짜 ");
        selectedDateTextView.setAlpha(0.7f);
        selectedDateTextView.setTextColor(Color.GRAY);
    }

}