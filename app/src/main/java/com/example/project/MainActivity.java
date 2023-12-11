package com.example.project;

import static android.util.Log.println;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public void println(String data) {
        Log.d("MainActivity", data);
    }

    private TextView selectedDateTextView;
    static RequestQueue requestQueue;
    TextView editText;
    AirplaneAdapter adapter;
    RecyclerView recyclerView;
    Long depTime;
    String selectedStartingPoint;
    String selectedEndPoint;
    String url2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "makeRequest: Start");
        AviationInformation.SetAirport();
        AviationInformation.SetAirline();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        ImageButton showCalendarBtn = findViewById(R.id.showCalendarBtn);
        ImageButton ResetBtn = findViewById(R.id.resetBtn);
        Spinner startingPoint = findViewById(R.id.spinnerStartingPoint);
        Spinner endPoint = findViewById(R.id.spinnerEndPoint);
        Button button = findViewById(R.id.button);
        recyclerView=findViewById(R.id.RecyclerView);
        editText = findViewById(R.id.editText);



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

        startingPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 선택된 출발지 값을 변수에 저장
                selectedStartingPoint = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });


        //도착지 스피너
        ArrayAdapter<CharSequence> endPointAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.end_points,
                android.R.layout.simple_spinner_item
        );
        endPointAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endPoint.setAdapter(endPointAdapter);

        endPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // 선택된 도착지 값을 변수에 저장
                selectedEndPoint = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });


        //불러오기 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url2="http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?" +
                        "serviceKey=FyWbLjBWJm2G5c3rHDUKeOL5RNX6rQY5nguIbbYUV49FeKBacJHtNcRGIRLT5KlmdbvdciGC599ApxTqp5TAug%3D%3D" +
                        "&pageNo=1" +
                        "&numOfRows=20" +
                        "&_type=xml" +
                        "&depAirportId="+ AviationInformation.getAirline(selectedStartingPoint) +
                        "&arrAirportId="+ AviationInformation.getAirline(selectedEndPoint) +
                        "&depPlandTime="+ depTime;

                editText.setText(url2);
                /*new Thread(){
                    @Override
                    run();
                }.start();
                    */
            }


        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AirplaneAdapter();
        recyclerView.setAdapter(adapter);
    }




    private void showDatePickerDialog() {
        Log.d("MainActivity", "makeRequest: dialog");
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
                    String selectedDate1 =String.format("%04d%02d%02d", yearSelected, monthOfYear + 1, dayOfMonth);
                    selectedDateTextView.setText(selectedDate);
                    selectedDateTextView.setAlpha(1.0f);
                    selectedDateTextView.setTextColor(Color.BLACK);
                    depTime = Long.parseLong(selectedDate1);
                },
                year, month, day
        );

        datePickerDialog.show();
    }




    private void reset() {
        Log.d("MainActivity", "makeRequest: reset");

        selectedDateTextView.setText("선택된 날짜 ");
        selectedDateTextView.setAlpha(0.7f);
        selectedDateTextView.setTextColor(Color.GRAY);
    }


    public void makeRequest(String depAirportId, String arrAirportId, long time){

        String url="http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?" +
                "serviceKey=FyWbLjBWJm2G5c3rHDUKeOL5RNX6rQY5nguIbbYUV49FeKBacJHtNcRGIRLT5KlmdbvdciGC599ApxTqp5TAug%3D%3D" +
                "&pageNo=1" +
                "&numOfRows=20" +
                "&_type=xml" +
                "&depAirportId="+ AviationInformation.getAirline(depAirportId) +
                "&arrAirportId="+ AviationInformation.getAirline(arrAirportId) +
                "&depPlandTime="+ time;




        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {processResponse(response);}, // 응답 시의 동작 || actions when response
                error -> {
                    Log.e("MainActivity", "Error: " + error.toString());
                    error.printStackTrace();
                } // 적절한 에러처리(정답은 따로 없음, 로그출력용) || error handling, freely make it!
        ) {
            public RetryPolicy getRetryPolicy() {
                // 타임아웃 및 재시도 정책 설정
                int MY_SOCKET_TIMEOUT_MS = 5000; // 예: 5초
                return new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        println("요청 보냄.");
    }




    public void processResponse(String response) {
        try {
            Gson gson = new Gson();
            // JSON 형식이 아닌 응답을 받았을 때 처리
            if (response == null || !response.startsWith("{")) {
                Log.e("MainActivity", "Invalid JSON response: " + response);

                // 서버 응답 확인을 위해 로그에 출력
                println("Server Response: " + response);

                return;
            }

            AirplaneList airplanelist = gson.fromJson(response, AirplaneList.class);

            if (airplanelist != null && airplanelist.boxOfficeResult != null && airplanelist.boxOfficeResult.dailyBoxOfficeList != null) {
                println("항공기 수 : " + airplanelist.boxOfficeResult.dailyBoxOfficeList.size());

                for (int i = 0; i < airplanelist.boxOfficeResult.dailyBoxOfficeList.size(); i++) {
                    Airplane airplane = airplanelist.boxOfficeResult.dailyBoxOfficeList.get(i);

                    adapter.addItem(airplane);
                }

                adapter.notifyDataSetChanged();
            } else {
                Log.e("MainActivity", "Invalid server response format.");
            }
        } catch (JsonSyntaxException | IllegalStateException e) {
            // 예외 상황 로그 출력
            Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // 예외 상황 로그 출력
            Log.e("MainActivity", "Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /*public void run(){
        try{
            URL url= new URL();
        }
    }*/


}