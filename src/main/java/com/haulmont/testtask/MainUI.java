package com.haulmont.testtask;

import com.haulmont.testtask.editor.DoctorEditor;
import com.haulmont.testtask.editor.PacientEditor;
import com.haulmont.testtask.editor.RecipeEditor;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)

public class MainUI extends UI {

    TabSheet tabSheet = new TabSheet();
    VerticalLayout vlayout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        PacientEditor pacientEditor = new PacientEditor();
        tabSheet.addTab(pacientEditor, "Pacient");

        DoctorEditor doctorEditor = new DoctorEditor();
        tabSheet.addTab(doctorEditor, "Doctor");

        RecipeEditor recipeEditor = new RecipeEditor();
        tabSheet.addTab(recipeEditor, "Recipe");

        vlayout.addComponent(tabSheet);
        setContent(vlayout);
    }

}