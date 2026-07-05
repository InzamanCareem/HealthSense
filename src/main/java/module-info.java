module com.example.healthsenseapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;


    opens com.example.healthsenseapp to javafx.fxml;
    exports com.example.healthsenseapp;
    exports com.example.healthsenseapp.services;
    opens com.example.healthsenseapp.services to javafx.fxml;
    exports com.example.healthsenseapp.controllers;
    opens com.example.healthsenseapp.controllers to javafx.fxml;
    exports com.example.healthsenseapp.models;
    opens com.example.healthsenseapp.models to javafx.fxml;
}