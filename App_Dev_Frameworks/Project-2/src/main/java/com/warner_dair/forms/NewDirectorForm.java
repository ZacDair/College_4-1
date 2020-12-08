package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class NewDirectorForm {
    @NotBlank
    @Size(min = 2, max = 40)
    private String newDirectorFirstName;

    @NotBlank
    @Size(min = 2, max = 40)
    private String newDirectorLastName;
}
