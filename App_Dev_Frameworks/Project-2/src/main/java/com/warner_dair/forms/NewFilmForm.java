package com.warner_dair.forms;

import lombok.Data;
import javax.validation.constraints.*;


@Data
public class NewFilmForm {

    @NotBlank
    @Size(min = 2, max = 40)
    private String newFilmName;

    @NotNull
    @Min(1888) // Missing max = current year
    private int newFilmReleaseYear;

    @NotNull
    private int directorId;
}