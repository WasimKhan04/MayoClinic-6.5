package com.hospitalManagement.service;

import com.hospitalManagement.payload.PatientDto;

import java.util.List;

public interface PatientService {
    PatientDto savePatient(long did,PatientDto patientDto);

    List<PatientDto> getPatientByDoctorId(long did);

    PatientDto getPatient(long did, long pid);

    PatientDto updatePatientById(long did, PatientDto patientDto, long pid);

    void deletePatient(long did, long pid);
}
