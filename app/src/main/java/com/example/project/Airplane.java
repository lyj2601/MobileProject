package com.example.project;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
//import javax.annotation.Generated;

//@Generated("jsonschema2pojo")
public class Airplane {

    private String airlineNm;  //항공사 명
    private String arrAirportNm;  //도착 공항
    private String arrPlandTime; //도착 시간
    private String depAirportNm; //출발 공항
    private String depPlandTime;// 출발 시간
    private Integer economyCharge; // 일반석 운임 가격
    private Integer prestigeCharge; //비즈니스석 운임 가격
    private String vihicleId; //항공편 명
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public String getAirlineNm() {
        return airlineNm;
    }

    public void setAirlineNm(String airlineNm) {
        this.airlineNm = airlineNm;
    }

    public String getArrAirportNm() {
        return arrAirportNm;
    }

    public void setArrAirportNm(String arrAirportNm) {
        this.arrAirportNm = arrAirportNm;
    }
//
    public String getArrPlandTime() {
        return arrPlandTime;
    }

    public void setArrPlandTime(Long arrPlandTime) {

        String timeString = extractTimeFromTimestamp(arrPlandTime);
        this.arrPlandTime = timeString;
    }
//
    public String getDepAirportNm() {
        return depAirportNm;
    }

    public void setDepAirportNm(String depAirportNm) {
        this.depAirportNm = depAirportNm;
    }
///
    public String getDepPlandTime() {
        return depPlandTime;
    }

    public void setDepPlandTime(Long depPlandTime) {
        String timeString = extractTimeFromTimestamp(depPlandTime);
        this.depPlandTime = timeString;
    }
///
    public Integer getEconomyCharge() {
        return economyCharge;
    }

    public void setEconomyCharge(Integer economyCharge) {
        this.economyCharge = economyCharge;
    }

    public Integer getPrestigeCharge() {
        return prestigeCharge;
    }

    public void setPrestigeCharge(Integer prestigeCharge) {
        this.prestigeCharge = prestigeCharge;
    }

    public String getVihicleId() {
        return vihicleId;
    }

    public void setVihicleId(String vihicleId) {
        this.vihicleId = vihicleId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    private String extractTimeFromTimestamp(Long depPlandTime) {
        // Long 값을 문자열로 변환
        String timestampString = String.valueOf(depPlandTime);

        // 맨 뒤 4자리 추출
        String last4Digits = timestampString.substring(timestampString.length() - 4);

        // 시간과 분으로 나타내는 문자열로 변환
        String timeString = last4Digits.substring(0, 2) + ":" + last4Digits.substring(2);

        return timeString;
    }


}