package com.warner_dair.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

	private int filmId;
	private String filmName;
	private int filmReleaseYear;

}