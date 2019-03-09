package com.haulmont.testtask.editor;

import com.haulmont.testtask.entity.Pacient;
import com.haulmont.testtask.services.PacientService;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class PacientEditor extends HorizontalSplitPanel implements ComponentContainer {

        private final BeanItemContainer<Pacient> pacient = new BeanItemContainer<>(Pacient.class);

        private final Table pacientTable = new Table();

        private final PacientService pacientService = new PacientService();

        private final TextField firstNameField = new TextField("First Name");

        private final TextField lastNameField = new TextField("Last Name");
        private final TextField patronymicField = new TextField("Patronymic");
        private final TextField phoneField = new TextField("Phone");

        private final Button addNewButton = new Button("Add New Pacient");
        private final Button updateButton = new Button("Update");
        private final Button removeButton = new Button("Remove");
        private final Button refreshButton = new Button("Refresh");

        public PacientEditor() {
            refreshPacientList();
            pacientTable.setContainerDataSource(pacient);
            pacientTable.setVisibleColumns(new Object[]{"id", "firstName", "lastName", "patronymic", "phone"});
            pacientTable.setSelectable(true);
            pacientTable.setEnabled(true);
            pacientTable.setEditable(true);
            pacientTable.addValueChangeListener((Property.ValueChangeEvent event) -> {
                Pacient pac = (Pacient) pacientTable.getValue();
                populateFields(firstNameField.getValue(), lastNameField.getValue(), patronymicField.getValue(),
                        phoneField.getValue());
                });
            VerticalLayout vlayout = new VerticalLayout();
            vlayout.setMargin(true);
            vlayout.addComponent(removeButton);
            removeButton.addClickListener((Button.ClickEvent event) -> {
                Pacient pac = (Pacient) pacientTable.getValue();
                if (pac != null) {
                    pacientService.deletePacient(pac);
                    refreshPacientList();
                }
            });

            vlayout.addComponent(pacientTable);
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
            Label header = new Label("PACIENT");
            header.addStyleName(ValoTheme.LABEL_H2);
            form.addComponent(header);
            hlayout.addComponent(updateButton);
            updateButton.addClickListener((Button.ClickEvent event) -> {
                Pacient pac = (Pacient) pacientTable.getValue();
                    pac.setFirstName(firstNameField.getValue().trim());
                    pac.setLastName(lastNameField.getValue().trim());
                    pac.setPatronymic(patronymicField.getValue().trim());
                    pac.setPhone(new Integer(phoneField.getValue().trim()));
                    pacientService.updatePacient(pac);
                    Notification.show("Pacient updated");
                    refreshPacientList();
            });

            hlayout.addComponent(refreshButton);
            refreshButton.addClickListener((Button.ClickEvent event) -> {
                populateFields("", "", "", "");
                refreshPacientList();
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
            form.addComponent(phoneField);
            phoneField.setRequired(true);

            form.addComponent(addNewButton);
            addNewButton.addClickListener((Button.ClickEvent event) -> {
                String phone = phoneField.getValue();
                Pacient pac = new Pacient(firstNameField.getValue(), lastNameField.getValue(),
                        patronymicField.getValue(), new Integer(phone));
                if (pac != null) {
                    pacientService.savePacient(pac);
                    pacient.addBean(pac);
                }
            });
            return form;

        }

        private void refreshPacientList() {
            pacient.removeAllItems();
            pacient.addAll(pacientService.findAllPacients());
        }

        private void populateFields(String fname, String lname, String patronymic, String ph) {
            firstNameField.setValue(fname);
            lastNameField.setValue(lname);
            patronymicField.setValue(patronymic);
            phoneField.setValue(ph);
        }

}
