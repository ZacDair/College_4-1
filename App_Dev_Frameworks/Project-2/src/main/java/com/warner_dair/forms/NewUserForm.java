package com.warner_dair.forms;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class NewUserForm {

    @NotBlank(message= "Please enter a username")
    private String newUsername;

    @NotBlank(message= "Please enter a password")
    private String newPassword;

    @NotBlank(message= "Please ensure a role is selected")
    private String newRole;
}