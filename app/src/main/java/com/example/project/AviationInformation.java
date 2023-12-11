package com.example.project;

import java.util.HashMap;

public class AviationInformation {
    public static HashMap<String,String> Airport = new HashMap<>();

    public static HashMap<String,String> Airline = new HashMap<>();

    public static void SetAirport(){
        Airport.put("아시아나","AAR");
        Airport.put("에어부산","ABL");
        Airport.put("에어서울","ASV");
        Airport.put("이스타항공","AAR");
        Airport.put("플라이강원","FGW");
        Airport.put("하이에어","HGG");
        Airport.put("제주항공","JJA");
        Airport.put("진에어","JNA");
        Airport.put("대한항공","KAL");
        Airport.put("티웨이항공","TWB");
    }

    public static void SetAirline(){
        Airline.put("무안","NAARKJB");
        Airline.put("광주","NAARKJJ");
        Airline.put("군산","NAARKJK");
        Airline.put("여수","NAARKJY");
        Airline.put("원주","NAARKNW");
        Airline.put("양양","NAARKNY");
        Airline.put("제주","NAARKPC");
        Airline.put("김해","NAARKPK");
        Airline.put("사천","NAARKPS");
        Airline.put("울산","NAARKPU");
        Airline.put("인천","NAARKSI");
        Airline.put("김포","NAARKSS");
        Airline.put("포항","NAARKTH");
        Airline.put("대구","NAARKTN");
        Airline.put("청주","NAARKTU");

    }
    public static String getAirline(String key) {
        return Airline.get(key);
    }

}
