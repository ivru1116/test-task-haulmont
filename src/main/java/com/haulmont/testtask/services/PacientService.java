package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.PacientDao;
import com.haulmont.testtask.entity.Pacient;

import java.util.List;

public class PacientService {

    private PacientDao pacientsDao = new PacientDao();

    private Class<Pacient> pacientClass = Pacient.class;

    public Pacient findPacient(int id) {
        return pacientsDao.find(pacientClass, id);
    }

    public void savePacient(Pacient pacient) {
        pacientsDao.save(pacient);
    }

    public void deletePacient(Pacient pacient) {
        pacientsDao.delete(pacient);
    }

    public void updatePacient(Pacient pacient) {
        pacientsDao.update(pacient);
    }

    public List<Pacient> findAllPacients() {
        return pacientsDao.findAll(pacientClass);
    }
}
