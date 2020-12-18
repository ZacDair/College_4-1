package com.warner_dair.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUser {

    @Id
    private String userEmail;

    @Column
    private String userPassword;

    @Column
    private String userRole;
}
