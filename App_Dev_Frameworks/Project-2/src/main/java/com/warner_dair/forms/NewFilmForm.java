package com.warner_dair.forms;

import lombok.Data;
import javax.validation.constraints.*;


@Data
public class NewFilmForm {

    @NotBlank
    private String newFilmName;

    @NotNull
    @Min(1888)
    private int newFilmReleaseYear;

    @NotNull
    private int directorId;
}