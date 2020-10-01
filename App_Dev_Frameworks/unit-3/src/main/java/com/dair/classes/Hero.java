package com.dair.classes;

import com.dair.classes.Franchise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Hero {
    private String heroName;
    private Franchise franchise;
}
