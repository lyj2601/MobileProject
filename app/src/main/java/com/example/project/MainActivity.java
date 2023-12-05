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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView selectedDateTextView;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        ImageButton showCalendarBtn = findViewById(R.id.showCalendarBtn);
        ImageButton ResetBtn = findViewById(R.id.resetBtn);
        Spinner startingPoint = findViewById(R.id.spinnerStartingPoint);
        Spinner endPoint = findViewById(R.id.spinnerEndPoint);

        //캘린더 버튼
        showCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        //리셋 버튼
        ResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });



        //출발지 스피너
        ArrayAdapter<CharSequence> startingPointAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.starting_points,
                android.R.layout.simple_spinner_item
        );
        startingPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startingPoint.setAdapter(startingPointAdapter);



        //도착지 스피너
        ArrayAdapter<CharSequence> endPointAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.end_points,
                android.R.layout.simple_spinner_item
        );
        endPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endPoint.setAdapter(endPointAdapter);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
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


    public void makeRequest(String depAirportId, String arrAirportId, int depPlandTime){
        String url="http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?" +
                "serviceKey=FyWbLjBWJm2G5c3rHDUKeOL5RNX6rQY5nguIbbYUV49FeKBacJHtNcRGIRLT5KlmdbvdciGC599ApxTqp5TAug%3D%3D" +
                "&pageNo=1" +
                "&numOfRows=10" +
                "&_type=json" +
                "&depAirportId="+ AviationInformation.Airport.get(depAirportId) +
                "&arrAirportId="+ AviationInformation.Airport.get(arrAirportId) +
                "&depPlandTime="+ depPlandTime;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {processResponse(response);}, // 응답 시의 동작 || actions when response
                error -> System.out.println("error") // 적절한 에러처리(정답은 따로 없음, 로그출력용) || error handling, freely make it!
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        System.out.println("요청 보냄.");
    }


    public void processResponse(String response) {
        // 서버 응답을 처리하는 로직을 여기에 구현합니다.
        try {
            // JSON 형식의 응답 데이터를 객체로 변환
            Gson gson = new Gson();
            Airplane airplane = gson.fromJson(response, Airplane.class);

            // 필요한 작업 수행
            String airlineName = airplane.getAirlineNm();
            String departureAirport = airplane.getDepAirportNm();
            String arrivalAirport = airplane.getArrAirportNm();
            Long departureTime = airplane.getDepPlandTime();
            Long arrivalTime = airplane.getArrPlandTime();
            Integer economyCharge = airplane.getEconomyCharge();
            Integer prestigeCharge = airplane.getPrestigeCharge();

            // 예시: 텍스트뷰에 정보 표시
            TextView resultTextView = findViewById(R.id.resultTextView);
            String resultText = "항공사: " + airlineName +
                    "\n출발지: " + departureAirport +
                    "\n도착지: " + arrivalAirport +
                    "\n출발 시간: " + departureTime +
                    "\n도착 시간: " + arrivalTime +
                    "\n일반석 운임: " + economyCharge +
                    "\n비즈니스석 운임: " + prestigeCharge;
            resultTextView.setText(resultText);

        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리: JSON 파싱 오류 등
        }
    }


}