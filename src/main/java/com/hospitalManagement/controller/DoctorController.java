package com.hospitalManagement.controller;

import com.hospitalManagement.payload.DoctorDto;
import com.hospitalManagement.payload.DoctorResponse;
import com.hospitalManagement.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
    //http://localhost:8080/api/doctors
    @PostMapping
    ResponseEntity<Object> saveDoctor(@Valid @RequestBody DoctorDto doctorDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        DoctorDto dto = doctorService.saveDoctor(doctorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/doctors?pageNo=0&pageSize=10&sortBy=did&sortDir=asc
    @GetMapping
    DoctorResponse getAllDoctor(@RequestParam(value="pageNo",defaultValue ="0",required = false) int pageNo,
                                @RequestParam(value="pageSize",defaultValue ="10",required = false) int pageSize,
                                @RequestParam(value="sortBy",defaultValue ="did",required = false) String sortBy,
                                @RequestParam(value="sortDir",defaultValue ="asc",required = false) String sortDir){
        return doctorService.getALlList(pageNo,pageSize,sortBy,sortDir);

    }
    //http://localhost:8080/api/doctors/1
    @GetMapping("/{id}")
    ResponseEntity<DoctorDto> getAllById(@PathVariable("id") long id){
        DoctorDto byId = doctorService.getDoctorById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }

    //http://localhost:8080/api/doctors/1
    @PutMapping("/{id}")
    ResponseEntity<DoctorDto> updateById(@PathVariable("id") long id,@RequestBody DoctorDto doctorDto){
        DoctorDto updateId = doctorService.updateById(id,doctorDto);
        return new ResponseEntity<>(updateId,HttpStatus.OK);
    }

    //http://localhost:8080/api/doctors/1
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable("id") long id){
        doctorService.deleteById(id);
        return new ResponseEntity<>("Doctor Deleted!!",HttpStatus.OK);
    }

}
