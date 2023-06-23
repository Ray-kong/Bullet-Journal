module cs3500.pa05 {
  requires javafx.controls;
  requires javafx.fxml;

  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;

  opens cs3500.pa05 to javafx.fxml;
  opens cs3500.pa05.controller to javafx.fxml;
  opens cs3500.pa05.view to javafx.fxml;
  exports cs3500.pa05;
  exports cs3500.pa05.controller;
  exports cs3500.pa05.model.data;
  exports cs3500.pa05.model.json;
  exports cs3500.pa05.view;
}