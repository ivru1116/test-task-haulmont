package com.haulmont.testtask.services;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.entity.Doctor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DoctorService {

    private DoctorDao doctorsDao = new DoctorDao();

    private Class<Doctor> doctorClass = Doctor.class;

    public Doctor findDoctor(int id) {
        return doctorsDao.find(doctorClass, id);
    }

    public void saveDoctor(Doctor doctor) {
        doctorsDao.save(doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        doctorsDao.delete(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorsDao.update(doctor);
    }

    public List<Doctor> findAllDoctors() {
        return doctorsDao.findAll(doctorClass);
    }

}
