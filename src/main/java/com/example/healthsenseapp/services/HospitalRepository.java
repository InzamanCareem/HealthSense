package com.example.healthsenseapp.services;

import com.example.healthsenseapp.models.CountryHospitalList;

public class HospitalRepository {

    private static final CountryHospitalList countryHospitalList = new CountryHospitalList();

    public static CountryHospitalList getCountryHospitalList() {
        return countryHospitalList;
    }
}
