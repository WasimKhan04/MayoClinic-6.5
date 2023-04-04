package com.hospitalManagement.controller;

import com.hospitalManagement.payload.PatientDto;
import com.hospitalManagement.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {

        this.patientService = patientService;
    }

    //http://localhost:8080/api/doctors/did/patients

    @PostMapping("/{did}/patients")
    ResponseEntity<PatientDto> savePatient(@PathVariable("did") long did, @RequestBody PatientDto patientDto){
        PatientDto dto = patientService.savePatient(did, patientDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{did}/patients")
    List<PatientDto> getPatient(@PathVariable("did") long did){
        return patientService.getPatientByDoctorId(did);
    }

    //http://localhost:8080/api/doctors/1/patients/1

    @GetMapping("/{did}/patients/{pid}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable("did") long did,@PathVariable("pid") long pid){
        PatientDto dto = patientService.getPatient(did, pid);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/doctors/1/patients/1
    @PutMapping("/{did}/patients/{pid}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable("did") long did,@RequestBody PatientDto patientDto,@PathVariable("pid") long pid){
        PatientDto dto = patientService.updatePatientById(did, patientDto, pid);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/doctors/1/patients/1
    @DeleteMapping("/{did}/patients/{pid}")
    public ResponseEntity<String> deletePatient(@PathVariable("did") long did,@PathVariable("pid") long pid){
        patientService.deletePatient(did,pid);
        return new ResponseEntity<>("PATIENT DELETED!!",HttpStatus.OK);
    }

}
