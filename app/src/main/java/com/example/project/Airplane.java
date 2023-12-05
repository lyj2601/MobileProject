package com.example.project;


import java.util.LinkedHashMap;
import java.util.Map;
//import javax.annotation.Generated;

//@Generated("jsonschema2pojo")
public class Airplane {

    private String airlineNm;  //항공사 명
    private String arrAirportNm;  //도착 공항
    private Long arrPlandTime; //도착 시간
    private String depAirportNm; //출발 공항
    private Long depPlandTime;// 출발 시간
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

    public Long getArrPlandTime() {
        return arrPlandTime;
    }

    public void setArrPlandTime(Long arrPlandTime) {
        this.arrPlandTime = arrPlandTime;
    }

    public String getDepAirportNm() {
        return depAirportNm;
    }

    public void setDepAirportNm(String depAirportNm) {
        this.depAirportNm = depAirportNm;
    }

    public Long getDepPlandTime() {
        return depPlandTime;
    }

    public void setDepPlandTime(Long depPlandTime) {
        this.depPlandTime = depPlandTime;
    }

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

}