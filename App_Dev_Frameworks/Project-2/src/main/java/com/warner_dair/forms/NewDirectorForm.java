package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class NewDirectorForm {
    @NotBlank(message= "Sorry, the first name must not be blank")
    @Size(min = 2, max = 40, message= "Sorry, the first name must be between 2-40 characters")
    private String newDirectorFirstName;

    @NotBlank(message= "Sorry, the last name must not be blank")
    @Size(min = 2, max = 40, message= "Sorry, the last name must be between 2-40 characters")
    private String newDirectorLastName;
}
