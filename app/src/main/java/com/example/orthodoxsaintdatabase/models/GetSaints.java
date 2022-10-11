package com.example.orthodoxsaintdatabase.models;

import com.google.gson.annotations.SerializedName;

public class GetSaints {
    @SerializedName("id")
    private String id;

    @SerializedName("Name")
    private String Name;

    @SerializedName("Country")
    private String Country;

    @SerializedName("PlaceofBirth")
    private String PlaceofBirth;

    @SerializedName("DayofBirth")
    private String DayofBirth;

    @SerializedName("MonthofBirth")
    private String MonthofBirth;

    @SerializedName("YearofBirth")
    private String YearofBirth;

    @SerializedName("CenturyofBirth")
    private String CenturyofBirth;

    @SerializedName("LifeofSaint")
    private String LifeofSaint;

    @SerializedName("DayofDeath")
    private String DayofDeath;

    @SerializedName("MonthofDeath")
    private String MonthofDeath;

    @SerializedName("YearofDeath")
    private String YearofDeath;

    @SerializedName("CenturyofDeath")
    private String CenturyofDeath;

    @SerializedName("PlaceofDeath")
    private String PlaceofDeath;

    @SerializedName("CauseofDeath")
    private String CauseofDeath;

    @SerializedName("FeastDay")
    private String FeastDay;

    @SerializedName("FeastMonth")
    private String FeastMonth;

    @SerializedName("status")
    private String status;


    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getCountry() {
        return Country;
    }

    public String getPlaceofBirth() {
        return PlaceofBirth;
    }

    public String getDayofBirth() {
        return DayofBirth;
    }

    public String getMonthofBirth() {
        return MonthofBirth;
    }

    public String getYearofBirth() {
        return YearofBirth;
    }

    public String getCenturyofBirth() {
        return CenturyofBirth;
    }

    public String getLifeofSaint() {
        return LifeofSaint;
    }

    public String getDayofDeath() {
        return DayofDeath;
    }

    public String getMonthofDeath() {
        return MonthofDeath;
    }

    public String getYearofDeath() {
        return YearofDeath;
    }

    public String getCenturyofDeath() {
        return CenturyofDeath;
    }

    public String getPlaceofDeath() {
        return PlaceofDeath;
    }

    public String getCauseofDeath() {
        return CauseofDeath;
    }

    public String getFeastDay() {
        return FeastDay;
    }

    public String getFeastMonth() {
        return FeastMonth;
    }

    public String getStatus() {
        return status;
    }
}
