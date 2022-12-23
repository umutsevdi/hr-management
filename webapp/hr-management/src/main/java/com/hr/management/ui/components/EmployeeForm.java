package com.hr.management.ui.components;

import com.hr.management.api.service.model.EmployeeDto;
import com.hr.management.api.service.model.EmployeeStatusDto;
import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.EmployeeView;
import com.hr.management.ui.client.view.ExperienceData;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
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
    private EmployeeView employeeView;
    Avatar avatar;
    Avatar teamAvatar = new Avatar();

    VerticalLayout detailsBlock = new VerticalLayout();
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

    public EmployeeForm(List<TeamDto> teams, Boolean isVertical) {
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
        teamMap = teams.stream().collect(Collectors.toMap(TeamDto::getId, Function.identity()));
        teamName = new ComboBox<>("Team", teamMap.values().stream().map(TeamDto::getName).collect(Collectors.toList()));

        teamName.addValueChangeListener(
                i -> {
                    if (employeeView != null) {
                        TeamDto team = null;
                        for (TeamDto t : teams) {
                            if (t.getName().equals(teamName.getValue())) {
                                team = t;
                                break;
                            }
                        }
                        assert team != null;
                        teamAvatar.setImage(team.getProfile());
                        teamAvatar.setName(team.getName());
                    }
                }
        );
        if (isVertical) {
            add(new VerticalLayout(avatarLayout,
                    firstName,
                    lastName,
                    dateOfBirth,
                    gender,
                    email,
                    phoneNumber,
                    education,
                    experience,
                    cv,
                    new HorizontalLayout(teamAvatar, teamName),
                    createButtonsLayout()));
        } else {
            detailsBlock.setSizeFull();
            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setSizeFull();
            verticalLayout.add(
                    new HorizontalLayout(
                            new VerticalLayout(
                                    avatarLayout,
                                    firstName,
                                    lastName,
                                    dateOfBirth,
                                    gender,
                                    email,
                                    phoneNumber,
                                    education,
                                    experience,
                                    cv
                            ),
                            new VerticalLayout(
                                    new HorizontalLayout(teamAvatar, teamName),
                                    detailsBlock
                            )),
                    createButtonsLayout()
            );

            add(verticalLayout);
        }
    }

    public EmployeeForm(List<TeamDto> teams) {
        this(teams, true);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        save.addClickShortcut(Key.ENTER);
        return new HorizontalLayout(save, delete);
    }

    public void fillFieldsWith(EmployeeView employeeView) {
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
        teamName.setValue(teamMap.get(employeeView.getEmployeeStatus().getTeamId()).getName());
        detailsBlock.add(new ExperienceData(employeeView));
    }

    public void resetFields() {
        this.employeeView = null;
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
        detailsBlock.removeAll();
    }

    public EmployeeView readFields() {
        if (employeeView == null) {
            return null;
        }
        EmployeeDto employeeDto = new EmployeeDto(
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
        TeamDto teamDto = teamMap.values().stream()
                .filter(i -> i.getName().equals(teamName.getValue()))
                .findFirst().orElse(null);
        EmployeeStatusDto employeeStatusDto =
                ((ExperienceData) detailsBlock.getComponentAt(0)).toStatus();
        return new EmployeeView(employeeDto, teamDto, employeeView.getPastEmployeeStatus(), employeeStatusDto);
    }
}


