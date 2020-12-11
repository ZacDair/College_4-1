package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class FindFilmForm {

    @NotNull
    @Min(1888)
    private int filmReleaseYear;
}
