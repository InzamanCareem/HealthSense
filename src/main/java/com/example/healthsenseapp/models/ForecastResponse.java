package com.example.healthsenseapp.models;

import java.util.List;

public class ForecastResponse {
    public String response;
    public List<Double> forecast;
    public List<String> dates;
    public ForecastStatistics statistics;
}