package com.mirjalolcode.weatherapp.WeatherApp.view;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ClassResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import java.util.ArrayList;

@SpringUI(path = "")
public class MainView extends UI {

    private VerticalLayout mainLayout;

    private NativeSelect<String> unitSelect;

    private TextField cityTextField;

    private Button searchButton;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        mainLayout();
        setHeader();
        setLogo();
        setForm();
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
        logo.setWidth("240px");
        logo.setHeight("240px");

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
}
