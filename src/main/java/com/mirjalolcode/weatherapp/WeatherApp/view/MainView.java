package com.mirjalolcode.weatherapp.WeatherApp.view;

import com.mirjalolcode.weatherapp.WeatherApp.service.WeatherService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@SpringUI(path = "")
public class MainView extends UI {

    @Autowired
    private WeatherService weatherService;

    private VerticalLayout mainLayout;

    private NativeSelect<String> unitSelect;

    private TextField cityTextField;

    private Button searchButton;

    private HorizontalLayout dashboard;

    private Label location, currentTemp, weatherDescription, tempMax, tempMin, humidity, pressure, wind, feelsLike;

    private HorizontalLayout mainDescriptionLayout;

    private Image iconImage;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        mainLayout();
        setHeader();
        setLogo();
        setForm();
        dashboardTitle();
        dashboardDetails();
        searchButton.addClickListener(clickEvent -> {
            if (!cityTextField.getValue().equals("")){
                try {
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Notification.show("City Name is required");
        });
    }

    private void mainLayout() {
        iconImage = new Image();

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
        currentTemp = new Label();
        currentTemp.setStyleName(ValoTheme.LABEL_BOLD);
        currentTemp.setStyleName(ValoTheme.LABEL_H1);

        dashboard.addComponents(location, iconImage, currentTemp);
    }

    private void dashboardDetails(){
        mainDescriptionLayout = new HorizontalLayout();
        mainDescriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // description layout
        VerticalLayout descriptionLayout = new VerticalLayout();
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // description for Weather
        weatherDescription = new Label();
        weatherDescription.setStyleName(ValoTheme.LABEL_SUCCESS);

        descriptionLayout.addComponent(weatherDescription);

        // Minimum Temperature
        tempMin = new Label();
        descriptionLayout.addComponents(tempMin);

        // Maximum Temperature
        tempMax = new Label();
        descriptionLayout.addComponents(tempMax);

        // Pressure
        VerticalLayout pressureLayout = new VerticalLayout();
        pressureLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        pressure = new Label();
        pressureLayout.addComponent(pressure);

        humidity = new Label();
        pressureLayout.addComponent(humidity);

        wind = new Label();
        pressureLayout.addComponent(wind);

        feelsLike = new Label();
        pressureLayout.addComponent(feelsLike);

        mainDescriptionLayout.addComponents(descriptionLayout, pressureLayout);
    }

    private void updateUI() throws JSONException {
        String city = cityTextField.getValue();
        String defaultUnit;
        weatherService.setCityName(city);

        if(unitSelect.getValue().equals("F")){
            weatherService.setUnit("imperials");
            unitSelect.setValue("C");

            defaultUnit = "\u00b0"+" F";
        }
        else {
            weatherService.setUnit("metric");
            unitSelect.setValue("C");

            defaultUnit = "\u00b0"+" C";
        }

        location.setValue("Currently in "+city);
        JSONObject mainObject = weatherService.returnMain();
        int temp = mainObject.getInt("temp");
        currentTemp.setValue(temp + defaultUnit);

        // getting icon from API
        String iconCode=null;
        String weatherDescriptionNew = null;
        JSONArray jsonArray = weatherService.returnWeatherArray();

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject weatherObj = jsonArray.getJSONObject(i);
            iconCode = weatherObj.getString("icon");
            weatherDescriptionNew = weatherObj.getString("description");
        }

        iconImage.setSource(new ExternalResource("http://openweathermap.org/img/wn/"+iconCode+"@2x.png"));

        weatherDescription.setValue("Description: "+weatherDescriptionNew);
        tempMin.setValue("Minimum Temperature: "+weatherService.returnMain().getInt("temp_min")+unitSelect.getValue());
        tempMax.setValue("Maximum Temperature: "+weatherService.returnMain().getInt("temp_max")+unitSelect.getValue());
        pressure.setValue("Pressure: "+weatherService.returnMain().getInt("pressure")+unitSelect.getValue());
        humidity.setValue("Humidity: "+weatherService.returnMain().getInt("humidity")+unitSelect.getValue());
        wind.setValue("Wind speed: "+weatherService.returnWind().getInt("speed"));
        feelsLike.setValue("Real Feel: "+weatherService.returnMain().getDouble("feels_like"));

        mainLayout.addComponents(dashboard, mainDescriptionLayout);
    }
}
