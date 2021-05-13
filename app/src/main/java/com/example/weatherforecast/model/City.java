package com.example.weatherforecast.model;




public class City {
    private String name;
    private String country;


    public City(String name, String country){
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {

        return String.format("%s, %s", this.name, this.country);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

