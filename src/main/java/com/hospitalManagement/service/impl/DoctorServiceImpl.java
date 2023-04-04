package com.hospitalManagement.service.impl;

import com.hospitalManagement.entity.Doctor;
import com.hospitalManagement.exception.ResourceNotFoundException;
import com.hospitalManagement.payload.DoctorDto;
import com.hospitalManagement.payload.DoctorResponse;
import com.hospitalManagement.repository.DoctorRepository;
import com.hospitalManagement.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {


    private DoctorRepository doctorRepository;
    private ModelMapper modelMapper;

    public DoctorServiceImpl(DoctorRepository doctorRepository,ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public DoctorDto saveDoctor(DoctorDto doctorDto) {
        Doctor doctor = mapToEntity(doctorDto);
        Doctor saveDoctor = doctorRepository.save(doctor);
        DoctorDto dto = mapToDto(saveDoctor);
        return dto;
    }

    @Override
    public DoctorResponse getALlList(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Doctor> all = doctorRepository.findAll(pageable);
        List<Doctor> contents = all.getContent();

        List<DoctorDto> dtos = contents.stream().map(doctor -> mapToDto(doctor)).collect(Collectors.toList());

        DoctorResponse doctorResponse=new DoctorResponse();
        doctorResponse.setContent(dtos);
        doctorResponse.setPageNo(all.getNumber());
        doctorResponse.setPageSize(all.getSize());
        doctorResponse.setTotalElements(all.getTotalElements());
        doctorResponse.setTotalPages(all.getTotalPages());
        doctorResponse.setLast(all.isLast());
        return doctorResponse;

    }

    @Override
    public DoctorDto getDoctorById(long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "id", id)
        );
        return mapToDto(doctor);
    }

    @Override
    public DoctorDto updateById(long id, DoctorDto doctorDto) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "id", id)
        );
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setLastName(doctorDto.getLastName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setMobile(doctorDto.getMobile());
        Doctor save = doctorRepository.save(doctor);
       return mapToDto(save);
    }

    @Override
    public void deleteById(long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Doctor", "id", id)
        );
        doctorRepository.delete(doctor);
    }
//Entity to DTO
    private DoctorDto mapToDto(Doctor doctor) {
        DoctorDto dto = modelMapper.map(doctor, DoctorDto.class);
//        DoctorDto dto=new DoctorDto();
//        dto.setDid(doctor.getId());
//        dto.setFirstName(doctor.getFirstName());
//        dto.setLastName(doctor.getLastName());
//        dto.setEmail(doctor.getEmail());
//        dto.setMobile(doctor.getMobile());
        return dto;

    }

    //DTO TO ENTITY
    private Doctor mapToEntity(DoctorDto doctorDto) {
        Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
//     Doctor doctor=new Doctor();
//     doctor.setFirstName(doctorDto.getFirstName());
//     doctor.setLastName(doctorDto.getLastName());
//     doctor.setEmail(doctorDto.getEmail());
//     doctor.setMobile(doctorDto.getMobile());
     return doctor;
    }
}
