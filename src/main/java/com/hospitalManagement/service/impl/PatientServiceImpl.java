package com.hospitalManagement.service.impl;

import com.hospitalManagement.entity.Doctor;
import com.hospitalManagement.entity.Patient;
import com.hospitalManagement.exception.BlogAPIException;
import com.hospitalManagement.exception.ResourceNotFoundException;
import com.hospitalManagement.payload.PatientDto;
import com.hospitalManagement.repository.DoctorRepository;
import com.hospitalManagement.repository.PatientRepository;
import com.hospitalManagement.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    private ModelMapper modelMapper;

    public PatientServiceImpl(PatientRepository patientRepository,DoctorRepository doctorRepository,ModelMapper modelMapper) {

        this.patientRepository = patientRepository;
        this.doctorRepository=doctorRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public PatientDto savePatient(long did,PatientDto patientDto) {
        Doctor doctor = doctorRepository.findById(did).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "did", did)
        );
        Patient patient = mapToEntity(patientDto);
        patient.setDoctor(doctor);
        Patient savePatient = patientRepository.save(patient);
        PatientDto dto = mapToDto(savePatient);
        return dto;
    }

    @Override
    public List<PatientDto> getPatientByDoctorId(long did) {
        Doctor doctor = doctorRepository.findById(did).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "did", did)
        );
        List<Patient> id = patientRepository.findByDoctorId(did);
        List<PatientDto> collect = id.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PatientDto getPatient(long did, long pid) {

        Doctor doctor = doctorRepository.findById(did).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "did", did)
        );
        Patient patient = patientRepository.findById(pid).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "pid", pid)
        );
        if(patient.getDoctor().getId()!=doctor.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Patient does not belong with this Doctor id");
        }
        return mapToDto(patient);
    }

    @Override
    public PatientDto updatePatientById(long did, PatientDto patientDto, long pid) {
        Doctor doctor = doctorRepository.findById(did).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "did", did)
        );
        Patient patient = patientRepository.findById(pid).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "pid", pid)
        );
        if(patient.getDoctor().getId()!=doctor.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Patient does not belong with this Doctor id");
        }
        patient.setPid(pid);
        patient.setName(patientDto.getName());
        patient.setCity(patientDto.getCity());
        patient.setMobile(patientDto.getMobile());

        Patient save = patientRepository.save(patient);
        PatientDto dto = mapToDto(save);

        return dto;
    }

    @Override
    public void deletePatient(long did, long pid) {
        Doctor doctor = doctorRepository.findById(did).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "did", did)
        );
        Patient patient = patientRepository.findById(pid).orElseThrow(
                () -> new ResourceNotFoundException("Patient", "pid", pid)
        );
        if(patient.getDoctor().getId()!=doctor.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Patient does not belong with this Doctor id");
        }

        patientRepository.delete(patient);
    }
//Entity to Dto
    private PatientDto mapToDto(Patient patient) {
     //   PatientDto dto=new PatientDto();
        PatientDto dto = modelMapper.map(patient, PatientDto.class);
//        dto.setPid(patient.getPid());
//        dto.setName(patient.getName());
//        dto.setCity(patient.getCity());
//        dto.setMobile(patient.getMobile());
        return dto;
    }
//DTO to Entity
    private Patient mapToEntity(PatientDto patientDto) {
        Patient patient = modelMapper.map(patientDto, Patient.class);
//        Patient patient=new Patient();
//        patient.setName(patientDto.getName());
//        patient.setCity(patientDto.getCity());
//        patient.setMobile(patientDto.getMobile());
//        patient.setDoctor(patientDto.getDoctor());
        return patient;
    }
}
