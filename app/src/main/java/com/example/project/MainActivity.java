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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public void println(String data) {
        Log.d("MainActivity", data);
    }

    private TextView selectedDateTextView;
    private Thread apiRequestThread;

    static RequestQueue requestQueue;
    TextView editText;
    AirplaneAdapter adapter;
    RecyclerView recyclerView;
    Long depTime;
    String selectedStartingPoint;
    String selectedEndPoint;
    String url2;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> items2 = new ArrayList<String>();
    ArrayList<Long> items3 = new ArrayList<Long>();
    ArrayList<Long> items4 = new ArrayList<Long>();
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    Airplane airplane = new Airplane();

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


        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);



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

                makeApiRequest();

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

        items.clear();
        items2.clear();
        items3.clear();
        items4.clear();

        // 어댑터 갱신
        recyclerView.setVisibility(View.GONE);
        if (apiRequestThread != null && apiRequestThread.isAlive()) {
            apiRequestThread.interrupt();
        }


    }

    private void makeApiRequest(){

        Log.d("MainActivity", "makeApiRequest: Start");
        url2="http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?" +
                "serviceKey=FyWbLjBWJm2G5c3rHDUKeOL5RNX6rQY5nguIbbYUV49FeKBacJHtNcRGIRLT5KlmdbvdciGC599ApxTqp5TAug%3D%3D" +
                "&pageNo=1" +
                "&numOfRows=20" +
                "&_type=json" +
                "&depAirportId="+ AviationInformation.getAirline(selectedStartingPoint) +
                "&arrAirportId="+ AviationInformation.getAirline(selectedEndPoint) +
                "&depPlandTime="+ depTime;

        editText.setText(url2);
        new Thread(){
            public void run(){
                try {
                    URL url = new URL(url2);

                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        buffer.append(line + "\n");
                        line = reader.readLine();
                    }
                    String jsonData = buffer.toString();
                    JSONObject obj = new JSONObject(jsonData);
                    // obj의 "boxOfficeResult"의 JSONObject를 추출
                    JSONObject responseObj = obj.getJSONObject("response");

                    // "body" 키에 해당하는 JSONObject 가져오기
                    JSONObject bodyObj = responseObj.getJSONObject("body");

                    // "items" 키에 해당하는 JSONObject 가져오기
                    JSONObject itemsObj = bodyObj.getJSONObject("items");

                    // "item" 키에 해당하는 JSONArray 가져오기
                    JSONArray itemArray = itemsObj.getJSONArray("item");

                    for (int i = 0; i < itemArray.length(); i++) {
                        JSONObject temp = itemArray.getJSONObject(i);
                        String arrAirportNm = temp.getString("arrAirportNm");
                        String depAirportNm = temp.getString("depAirportNm");
                        Long depPlandTime = temp.getLong("depPlandTime");
                        Long arrPlandTime = temp.getLong("arrPlandTime");


                        // UI 업데이트를 메인 스레드에서 처리
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // addItem 메서드 호출
                                adapter.addItem(arrAirportNm, depAirportNm, depPlandTime, arrPlandTime);
                                // 어댑터 갱신
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

}