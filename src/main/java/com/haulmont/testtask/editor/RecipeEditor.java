package com.haulmont.testtask.editor;

import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Pacient;
import com.haulmont.testtask.entity.Recipe;
import com.haulmont.testtask.enums.RecipePriority;
import com.haulmont.testtask.services.DoctorService;
import com.haulmont.testtask.services.PacientService;
import com.haulmont.testtask.services.RecipeService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;
import java.util.EnumSet;

public class RecipeEditor extends HorizontalSplitPanel implements ComponentContainer {

    private final BeanItemContainer<Recipe> recipe = new BeanItemContainer<>(Recipe.class);
    private final BeanItemContainer<RecipePriority> enumContainer = new BeanItemContainer<>(RecipePriority.class);
    private final Table recipeTable = new Table();

    private final RecipeService recipeService = new RecipeService();
    private final DoctorService doctorService = new DoctorService();
    private final PacientService pacientService = new PacientService();

    private final TextField descriptionField = new TextField("Description");

    private final PopupDateField dateCreationField = new PopupDateField("Date Creation");
    private final DateField dateExposureField = new DateField("Exposure Creation");
    private final ComboBox recipePriorityCombo = new ComboBox("Recipe Priority");
    private final ComboBox doctorCombo = new ComboBox("Doctor");
    private final ComboBox pacientCombo = new ComboBox("Pacient");

    private final Button addNewButton = new Button("Add New Recipe");
    private final Button updateButton = new Button("Update");
    private final Button removeButton = new Button("Remove");
    private final Button refreshButton = new Button("Refresh");

    public RecipeEditor() {
        refreshRecipeList();

        recipeTable.setContainerDataSource(recipe);
        recipeTable.setVisibleColumns(new Object[]{"id", "description", "creation", "exposure", "doctors", "pacients", "priority"});
        recipeTable.setSelectable(true);
        recipeTable.setEnabled(true);
        recipeTable.setEditable(true);

        recipeTable.addValueChangeListener((Property.ValueChangeEvent event) -> {
            Recipe rec = (Recipe) recipeTable.getValue();
            populateFields(descriptionField.getValue(), dateCreationField.getValue(),
                    dateExposureField.getValue(), (String)doctorCombo.getValue(), (String)pacientCombo.getValue(),
                    (String)recipePriorityCombo.getValue());
        });
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setMargin(true);
        vlayout.addComponent(removeButton);
        removeButton.addClickListener((Button.ClickEvent event) -> {
            Recipe pac = (Recipe) recipeTable.getValue();
            if (pac != null) {
                recipeService.deleteRecipe(pac);
                refreshRecipeList();
            }
        });

        vlayout.addComponent(recipeTable);
        vlayout.setSizeUndefined();
        addComponent(vlayout);
        addComponent(createForm());
        setSplitPosition(75, Sizeable.Unit.PERCENTAGE);
    }

    private FormLayout createForm() {
        FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setSizeFull();

        HorizontalLayout hlayout = new HorizontalLayout();
        Label header = new Label("RECIPE");
        header.addStyleName(ValoTheme.LABEL_H2);
        form.addComponent(header);

        hlayout.addComponent(updateButton);
        updateButton.addClickListener((Button.ClickEvent event) -> {
            Recipe rec = (Recipe) recipeTable.getValue();
                rec.setDescription(descriptionField.getValue().trim());
                rec.setCreation(dateCreationField.getValue());
                rec.setExposure(dateExposureField.getValue());
                rec.setPriority((RecipePriority) recipePriorityCombo.getValue());
                rec.setDoctors((String) doctorCombo.getValue());
                rec.setPacients((String) pacientCombo.getValue());
                recipeService.updateRecipe(rec);
                Notification.show("Recipe updated");
                refreshRecipeList();
                refreshDoctorList();
                refreshPacientList();
        });

        hlayout.addComponent(refreshButton);
        refreshButton.addClickListener((Button.ClickEvent event) -> {
            populateFields("", new Date(), new Date(), "", "", "");
            refreshRecipeList();
            refreshDoctorList();
            refreshPacientList();
        });

        form.addComponent(hlayout);

        form.addComponent(descriptionField);
        descriptionField.setRequired(true);
        form.addComponent(dateCreationField);
        dateCreationField.setRequired(true);
        form.addComponent(dateExposureField);
        dateExposureField.setRequired(true);

        refreshDoctorList();
        refreshPacientList();
        form.addComponent(doctorCombo);
        form.addComponent(pacientCombo);

        enumContainer.addAll(EnumSet.allOf(RecipePriority.class));
        recipePriorityCombo.setContainerDataSource(enumContainer);
        form.addComponent(recipePriorityCombo);

        form.addComponent(addNewButton);
        addNewButton.addClickListener((Button.ClickEvent event) -> {
            if(isValidFieldData()) {
                Recipe rec = new Recipe(descriptionField.getValue(), dateCreationField.getValue(),
                        dateExposureField.getValue(), (String) doctorCombo.getValue(),
                        (String) pacientCombo.getValue(), (RecipePriority) recipePriorityCombo.getValue());
                recipeService.saveRecipe(rec);
                recipe.addBean(rec);
            }
            else Notification.show("Please insert the Description");
        });
        return form;

    }

    private boolean isValidFieldData() {
        return descriptionField.isValid();
    }
    private void refreshRecipeList() {
        recipe.removeAllItems();
        recipe.addAll(recipeService.findAllRecipes());
    }
    private void refreshDoctorList() {
        for (Doctor d : doctorService.findAllDoctors()) {
            doctorCombo.addItem(d.getPatronymic());
        }
    }
    private void refreshPacientList() {
        for (Pacient p : pacientService.findAllPacients()) {
            pacientCombo.addItem(p.getPatronymic());
        }
    }
    private void populateFields(String descriptor, Date dateCreation, Date dateExposure, String doctor,
                                String pacient, String priority) {
        descriptionField.setValue(descriptor);
        dateCreationField.setValue(dateCreation);
        dateExposureField.setValue(dateExposure);
        doctorCombo.setValue(doctor);
        pacientCombo.setValue(pacient);
        recipePriorityCombo.setValue(priority);
    }


}
