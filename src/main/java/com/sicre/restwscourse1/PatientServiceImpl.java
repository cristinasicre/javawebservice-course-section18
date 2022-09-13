package com.sicre.restwscourse1;

import exceptions.PatientBusinessException;
import model.Patient;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class PatientServiceImpl implements PatientService {

    Map<Long, Patient> patients = new HashMap<>();
    long currentId = 123;

    public PatientServiceImpl() {
        init();
    }

    void init() {
        Patient patient = new Patient();
        patient.setId(currentId);
        patient.setName("Cristina");
        patients.put(patient.getId(), patient);
    }

    @Override
    public List<Patient> getPatients() {
        Collection<Patient> results = patients.values();
        ArrayList<Patient> response = new ArrayList(results);
        return response;

    }

    @Override
    public Patient getPatient(long id) {
        if(patients.get(id)==null){
            throw new NotFoundException();
        }
        return patients.get(id);
    }

    @Override
    public Response createPatient(Patient patient) {
        patient.setId(++currentId);
        patients.put(patient.getId(), patient);
        return Response.ok(patient).build();
    }

    @Override
    public Response updatePatient(Patient patient) {
        Patient currentPatient = patients.get(patient.getId());
        Response response;

        if (currentPatient != null) {
            patients.put(patient.getId(), patient);
            response = Response.ok(patient).build();
        } else {
            throw new PatientBusinessException();
        }
        return response;

    }

    @Override
    public Response deletePatient(long id) {
        Patient patient = patients.get(id);
        Response response;

        if (patient != null) {
            patients.remove(patient.getId(), patient);
            response = Response.ok(patient).build();
        } else {
            response = Response.notModified().build();
        }
        return response;
    }
}
