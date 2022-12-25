package com.hr.management.ui.components;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.ui.client.view.EmployeeView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Map;

public class EmployeeForm extends FormLayout implements FormComponent<EmployeeDto> {
    private EmployeeView employeeView;
    Avatar avatar;
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");

    DatePicker dateOfBirth = new DatePicker("Date of Birth");
    ComboBox<String> gender = new ComboBox<>("Gender", "M", "F");
    EmailField email = new EmailField("Email Address");
    TextField phoneNumber = new TextField("Phone Number", "(***) *******");
    TextField education = new TextField("Education");
    NumberField experience = new NumberField("Work Experience");
    TextField cv = new TextField("CV Address");


    public EmployeeForm(Boolean isVertical) {
        addClassName("contact-form");
        if (isVertical) {
            setWidth("25em");
        } else {
            setWidthFull();
        }
        setHeightFull();
        avatar = new Avatar();
        avatar.setMinHeight(120, Unit.PIXELS);
        avatar.setMaxHeight(120, Unit.PIXELS);
        avatar.setMinWidth(120, Unit.PIXELS);
        avatar.setMaxWidth(120, Unit.PIXELS);
        VerticalLayout avatarLayout = new VerticalLayout(avatar);
        avatarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(avatarLayout,
                firstName,
                lastName,
                dateOfBirth,
                gender,
                email,
                phoneNumber,
                education,
                experience,
                cv
        );
    }

    public EmployeeForm() {
        this(true);
    }

    public EmployeeForm fillFieldsWith(EmployeeView employeeView) {
        this.employeeView = employeeView;
        avatar.setImage(employeeView.getProfile());
        firstName.setValue(employeeView.getFirstName());
        lastName.setValue(employeeView.getLastName());
        dateOfBirth.setValue(employeeView.getDateOfBirth());
        gender.setValue(employeeView.getGender().toString());
        email.setValue(employeeView.getEmail());
        phoneNumber.setValue(employeeView.getPhoneNumber());
        education.setValue(employeeView.getEducation());
        experience.setValue(employeeView.getExperience());
        cv.setValue(employeeView.getCv());
        return this;
    }

    public EmployeeForm resetFields() {
        this.employeeView = null;
        avatar.setImage(null);
        firstName.clear();
        lastName.clear();
        dateOfBirth.clear();
        gender.clear();
        email.clear();
        phoneNumber.clear();
        education.clear();
        experience.clear();
        cv.clear();
        return this;
    }

    public EmployeeDto readFields() {
        if (employeeView == null) {
            return null;
        }
        return new EmployeeDto(
                employeeView.getId(),
                employeeView.getCreatedDate(),
                firstName.getValue(),
                lastName.getValue(),
                dateOfBirth.getValue(),
                gender.getValue().charAt(0),
                email.getValue(),
                phoneNumber.getValue(),
                education.getValue(),
                experience.getValue(),
                cv.getValue(),
                employeeView.getProfile()

        );
    }

    public static Map.Entry<Button, Button> createButtonsLayout() {
        Button save = new Button("Save");
        Button delete = new Button("Delete");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        return Map.entry(save, delete);
    }
}