package com.hospitalManagement.payload;

import com.hospitalManagement.entity.Doctor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PatientDto {
    private Long pid;
    @NotEmpty
    @Size(min=3, message = "Name should be atleast 3 characters")
    private String name;
    @NotEmpty
    @Size(min=3, message = "City should be atleast 3 characters")
    private String city;
    @Size(min=10,max=10,message = "Mobile number should be atleast 10 digits")
    @Pattern(regexp = "[6-9][0-9]{9}+",message = "Mobile number should countain 10 digit")
    private long mobile;

 //  private Doctor doctor;
}
