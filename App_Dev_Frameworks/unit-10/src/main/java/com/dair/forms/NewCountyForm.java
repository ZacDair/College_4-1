package com.dair.forms;

import lombok.Data;
import javax.validation.constraints.Size;

@Data
public class NewCountyForm {

    @Size(min= 4, max= 30)
    private String newCountyName;
}
