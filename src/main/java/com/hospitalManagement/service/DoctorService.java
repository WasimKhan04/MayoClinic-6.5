package com.hospitalManagement.service
        ;

import com.hospitalManagement.payload.DoctorDto;
import com.hospitalManagement.payload.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorDto saveDoctor(DoctorDto doctorDto);

    DoctorResponse getALlList(int pageNo, int pageSize, String sortBy, String sortDir);

    DoctorDto getDoctorById(long id);

    DoctorDto updateById(long id, DoctorDto doctorDto);

    void deleteById(long id);
}
