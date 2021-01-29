package com.mirjalolcode.weatherapp.WeatherApp.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

@SpringUI(path = "")
public class MainView extends UI {

    private VerticalLayout mainLayout;

    private NativeSelect<String> unitSelect;

    private TextField cityTextField;

    private Button searchButton;

    private HorizontalLayout dashboard;

    private Label location, currentTemp;

    private HorizontalLayout mainDescriptionLayout;

    private Label weatherDescription, maxDegree, minDegree, humidity, pressure, wind, realFeel;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        mainLayout();
        setHeader();
        setLogo();
        setForm();
        dashboardTitle();
        dashboardDetails();
    }

    private void mainLayout() {
        mainLayout = new VerticalLayout();

        mainLayout.setWidth("100%");
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        setContent(mainLayout);
    }

    private void setHeader(){
        HorizontalLayout header = new HorizontalLayout();
        header.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Label title = new Label("Weather Application by Mirjalol");

        header.addComponent(title);

        mainLayout.addComponent(header);
    }

    private void setLogo(){
        HorizontalLayout logo = new HorizontalLayout();
        logo.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        Image img = new Image(null, new ClassResource("/weatherLogo.jpg"));
        logo.setWidth("308px");
        logo.setHeight("164px");

        logo.addComponent(img);
        mainLayout.addComponent(logo);
    }

    private void setForm(){
        HorizontalLayout formLayout= new HorizontalLayout();
        formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        formLayout.setSpacing(true);
        formLayout.setMargin(true);

        // Component Selection
        unitSelect = new NativeSelect<>();
        ArrayList<String> items = new ArrayList<>();
        items.add("C");
        items.add("F");

        unitSelect.setItems(items);
        unitSelect.setValue(items.get(0));

        formLayout.addComponent(unitSelect);

        // cityTextField
        cityTextField = new TextField();
        cityTextField.setWidth("85%");
        formLayout.addComponent(cityTextField);

        // Search Button
        searchButton = new Button();
        searchButton.setIcon(VaadinIcons.SEARCH);
        formLayout.addComponent(searchButton);

        mainLayout.addComponent(formLayout);
    }

    private void dashboardTitle(){
        dashboard = new HorizontalLayout();
        dashboard.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // city location
        location = new Label("Currently, in Poznan");
        location.addStyleName(ValoTheme.LABEL_H2);
        location.addStyleName(ValoTheme.LABEL_LIGHT);

         // current TEMP
        currentTemp = new Label("10C");
        currentTemp.setStyleName(ValoTheme.LABEL_BOLD);
        currentTemp.setStyleName(ValoTheme.LABEL_H1);

        dashboard.addComponents(location, currentTemp);
        mainLayout.addComponent(dashboard);
    }

    private void dashboardDetails(){
        mainDescriptionLayout = new HorizontalLayout();
        mainDescriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // description layout
        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // description for Weather
        weatherDescription = new Label("Description: Clear");
        weatherDescription.setStyleName(ValoTheme.LABEL_SUCCESS);

        descriptionLayout.addComponent(weatherDescription);

        // Minimum Temperature
        minDegree = new Label("Min: 29");
        descriptionLayout.addComponents(minDegree);

        // Maximum Temperature
        maxDegree = new Label("Max: 71");
        descriptionLayout.addComponents(maxDegree);

        // Pressure
        VerticalLayout pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        pressure = new Label("Pressure: 240 Fa");
        pressureLayout.addComponent(pressure);

        // Humidity
        humidity = new Label("Humidity: 20");
        pressureLayout.addComponent(humidity);

        // Wind
        wind = new Label("Wind: 231");
        pressureLayout.addComponent(wind);

        // Real Feel
        realFeel = new Label("Real Feel: 231");
        pressureLayout.addComponent(realFeel);

        mainLayout.addComponents(mainDescriptionLayout, pressureLayout);
    }
}
