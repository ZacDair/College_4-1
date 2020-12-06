package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EditFilmForm {

    @NotBlank
    @Size(min = 2, max = 40)
    private String newFilmName;

    @NotNull
    private int filmId;
}
