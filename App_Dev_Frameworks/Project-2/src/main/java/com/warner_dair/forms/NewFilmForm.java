package com.warner_dair.forms;

import lombok.Data;
import javax.validation.constraints.*;


@Data
public class NewFilmForm {

    @NotBlank(message= "Please ensure the film has a name")
    private String newFilmName;

    @NotNull(message= "Please ensure the film has a release year")
    @Min(value=1888, message= "Please ensure the film has a release year greater than 1888")
    private int newFilmReleaseYear;

    @NotNull(message= "Please ensure a director is selected")
    private int directorId;
}