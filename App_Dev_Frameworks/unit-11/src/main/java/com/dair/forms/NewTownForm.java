package com.dair.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewTownForm {

    @Size(min= 4, max= 30)
    private String newTownName;

    @NotNull
    private int countyId;
}
