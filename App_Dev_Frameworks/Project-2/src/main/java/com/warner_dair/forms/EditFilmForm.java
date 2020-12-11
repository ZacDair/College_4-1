package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EditFilmForm {

    @NotBlank(message= "Please ensure the film has a name")
    private String newFilmName;

    @NotNull(message="Please ensure a film was selected")
    private int filmId;
}
