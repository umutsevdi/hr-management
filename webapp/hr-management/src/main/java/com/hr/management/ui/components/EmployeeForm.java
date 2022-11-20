package com.hr.management.ui.components;

import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.client.view.ExperienceData;
import com.hr.management.ui.pages.dashboard.App;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmployeeForm extends FormLayout {
    private final Map<Long, TeamDto> teamMap;
    Avatar avatar;

    Details accordion = new Details("Details");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");

    DatePicker dateOfBirth = new DatePicker("Date of Birth");
    ComboBox<String> gender = new ComboBox<>("Gender", "M", "F");
    EmailField email = new EmailField("Email Address");
    TextField phoneNumber = new TextField("Phone Number", "(***) *******");
    TextField education = new TextField("Education");
    NumberField experience = new NumberField("Work Experience");
    TextField cv = new TextField("CV Address");

    ComboBox<String> teamName;

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public EmployeeForm(List<TeamDto> teams, App app) {
        addClassName("contact-form");
        avatar = new Avatar();
        avatar.setMinHeight(120, Unit.PIXELS);
        avatar.setMaxHeight(120, Unit.PIXELS);
        avatar.setMinWidth(120, Unit.PIXELS);
        avatar.setMaxWidth(120, Unit.PIXELS);
        VerticalLayout avatarLayout = new VerticalLayout(avatar);
        avatarLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        teamMap = teams.stream().collect(Collectors.toMap(TeamDto::getId, Function.identity()));
        teamName = new ComboBox<>("Team", teamMap.values().stream().map(TeamDto::getName).collect(Collectors.toList()));
        add(
                avatarLayout,
                firstName,
                lastName,
                dateOfBirth,
                gender,
                email,
                phoneNumber,
                education,
                experience,
                cv,
                teamName,
                accordion,
                createButtonsLayout());
save.addClickListener(event -> app.getClient().get )
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, close);
    }

    public void fillFieldsWith(EmployeeView employeeView) {
        avatar.setImage(employeeView.getProfile());
        firstName.setValue(employeeView.getFirstName());
        lastName.setValue(employeeView.getLastName());

        dateOfBirth.setValue(employeeView.getDateOfBirth());
        gender.setValue(employeeView.getGender().toString());
        email.setValue(employeeView.getEmail());
        phoneNumber.setValue(employeeView.getPhoneNumber());
        education.setValue(employeeView.getEducation());
        experience.setValue(Double.valueOf(employeeView.getExperience()));
        cv.setValue(employeeView.getCv());
        teamName.setValue(teamMap.get(employeeView.getEmployeeStatus().getTeamId()).getName());
        accordion.addContent(new ExperienceData(employeeView));
    }

    public void resetFields() {
        avatar.setImage(null);
        firstName.setValue("");
        lastName.setValue("");

        dateOfBirth.setValue(LocalDate.now());
        gender.setValue("F");
        email.setValue("");
        phoneNumber.setValue("");
        education.setValue("");
        experience.setValue(0d);
        cv.setValue("");
        accordion.setContent(null);
    }
}


