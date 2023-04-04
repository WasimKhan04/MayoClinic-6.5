package com.hospitalManagement.payload;

import com.hospitalManagement.entity.Patient;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class DoctorDto {

    private Long id;
    @NotEmpty(message = "Can not be empty")
    @Size(min=3, message = "FirstName should be min 3 alph")
    private String firstName;
    @NotEmpty
    @Size(min=3, message = "LastName should be min 3 alph")
    private String lastName;
    @Email
    private String email;
//    @Size(min=10,max=10,message = "Mobile number should be atleast 10 digits")
//    @Pattern(regexp = "[0-9]+",message = "Mobile number should countain 10 digit")
    private long mobile;



}
