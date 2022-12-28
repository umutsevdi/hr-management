package com.hr.management.ui.components;

import com.hr.management.api.service.model.TeamDto;
import com.hr.management.ui.client.view.TeamView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TeamForm extends FormLayout implements FormComponent<TeamDto, TeamView> {

    private TeamView teamView;
    Avatar avatar = new Avatar();
    TextField name = new TextField("Name");
    TextField ipAddress = new TextField("IP Address");
    ComboBox<Boolean> isRemote = new ComboBox<>("Remote", Boolean.TRUE, Boolean.FALSE);
    DatePicker createdDate = new DatePicker("Start Date");
    TextField avatarUrl = new TextField("Team Image");

    public TeamForm(Boolean isVertical) {
        addClassName("contact-form");
        if (isVertical) {
            setWidth("25em");
        } else {
            setWidthFull();
        }
        setHeightFull();
        avatar.setMinHeight(60, Unit.PIXELS);
        avatar.setMaxHeight(60, Unit.PIXELS);
        avatar.setMinWidth(60, Unit.PIXELS);
        avatar.setMaxWidth(60, Unit.PIXELS);
        HorizontalLayout avatarLayout = new HorizontalLayout(avatar, name);
        add(new HorizontalLayout(
                        avatarLayout,
                        ipAddress,
                        isRemote,
                        createdDate,
                        avatarUrl
                )
        );
    }

    public TeamForm() {
        this(true);
    }

    @Override
    public void fillFieldsWith(TeamView teamView) {
        this.teamView = teamView;
        name.setValue(teamView.getName());
        avatar.setImage(teamView.getProfile());
        avatarUrl.setValue(teamView.getProfile());
        isRemote.setValue(teamView.isRemote());
        ipAddress.setValue(teamView.getIpAddress());
        createdDate.setValue(teamView.getCreatedDate());
    }

    @Override
    public void resetFields() {
        avatar.setImage(null);
        name.clear();
        avatarUrl.clear();
        isRemote.clear();
        ipAddress.clear();
        createdDate.clear();
    }

    @Override
    public TeamDto readFields() {
        if (teamView == null) {
            return null;
        }
        return new TeamDto(
                teamView.getId(),
                createdDate.getValue(),
                name.getValue(),
                teamView.getBoss().getId(),
                avatarUrl.getValue(),
                isRemote.getValue(),
                ipAddress.getValue()
        );
    }
}
