package com.haulmont.testtask.editor;

import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.services.DoctorService;
import com.haulmont.testtask.services.RecipeService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


public class DoctorEditor extends HorizontalSplitPanel implements ComponentContainer {

    private final BeanItemContainer<Doctor> doctor = new BeanItemContainer<>(Doctor.class);

    private final Table doctorTable = new Table();

    private final DoctorService doctorService = new DoctorService();
    private final RecipeService recipeService = new RecipeService();

    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final TextField patronymicField = new TextField("Patronymic");
    private final TextField specializationField = new TextField("Specialization");

    private final ComboBox recipeCombo = new ComboBox("Recipe");

    private final Button addNewButton = new Button("Add New");
    private final Button updateButton = new Button("Update");
    private final Button removeButton = new Button("Remove");
    private final Button refreshButton = new Button("Refresh");

    public DoctorEditor() {
        refreshDoctorList();
        doctorTable.setContainerDataSource(doctor);
        doctorTable.setVisibleColumns("id", "firstName", "lastName", "patronymic", "specialization");
        doctorTable.setSelectable(true);
        doctorTable.setEditable(true);
        doctorTable.addValueChangeListener(
                (Property.ValueChangeEvent event) -> {
                    Doctor doc = (Doctor) doctorTable.getValue();
                    populateFields(firstNameField.getValue(), lastNameField.getValue(), patronymicField.getValue(),
                            specializationField.getValue());
                });
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(true);
        vlayout.addComponent(removeButton);
        removeButton.addClickListener((Button.ClickEvent event) -> {
            Doctor doc = (Doctor) doctorTable.getValue();
            if (doc != null) {
                doctorService.deleteDoctor(doc);
                refreshDoctorList();
            }
        });
        vlayout.addComponent(doctorTable);
        vlayout.setSizeUndefined();
        addComponent(vlayout);
        addComponent(createForm());
        setSplitPosition(60, Unit.PERCENTAGE);
    }

    private FormLayout createForm() {
        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setSizeFull();
        HorizontalLayout hlayout = new HorizontalLayout();
        Label header = new Label("DOCTOR");
        header.addStyleName(ValoTheme.LABEL_H2);
        form.addComponent(header);

        hlayout.addComponent(updateButton);
        updateButton.addClickListener((Button.ClickEvent event) -> {
            Doctor doc = (Doctor) doctorTable.getValue();
            doc.setFirstName(firstNameField.getValue().trim());
            doc.setLastName(lastNameField.getValue().trim());
            doc.setPatronymic(patronymicField.getValue().trim());
            doc.setSpecialization(specializationField.getValue().trim());
            doctorService.updateDoctor(doc);
            Notification.show("Doctor updated");
            refreshDoctorList();
        });

        hlayout.addComponent(refreshButton);
        refreshButton.addClickListener((Button.ClickEvent event) -> {
            populateFields("", "", "", "");
            refreshDoctorList();
        });

        form.addComponent(hlayout);
        form.addComponent(firstNameField);
        firstNameField.setRequired(true);
        firstNameField.setRequiredError("First Name cannot be empty");

        form.addComponent(lastNameField);
        lastNameField.setRequired(true);
        lastNameField.setRequiredError("Last Name cannot be empty");

        form.addComponent(patronymicField);
        patronymicField.setRequired(true);
        patronymicField.setRequiredError("Patronymic cannot be empty");

        form.addComponent(specializationField);
        specializationField.setRequired(true);

        form.addComponent(addNewButton);
        addNewButton.addClickListener((Button.ClickEvent event) -> {
            Doctor doc = new Doctor(firstNameField.getValue(), lastNameField.getValue(),
                    patronymicField.getValue(), specializationField.getValue());
            if (doc != null) {
                doctorService.saveDoctor(doc);
                doctor.addBean(doc);
            }
        });
        return form;

    }

    private void refreshDoctorList() {
        doctor.removeAllItems();
        doctor.addAll(doctorService.findAllDoctors());
    }

    private void populateFields(String fname, String lname, String patronymic, String sp) {
        firstNameField.setValue(fname);
        lastNameField.setValue(lname);
        patronymicField.setValue(patronymic);
        specializationField.setValue(sp);
    }


}

