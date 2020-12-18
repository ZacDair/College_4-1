package com.dair.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "town")
public class Town {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int townId;
	
	@Column(nullable = false )
	private String townName;

	// This is the owning side of the relationship
	// Many towns are in one county. 
	@ManyToOne
	@JsonIgnore
	// Name this joining column "column_id"
	@JoinColumn(name = "county_id", nullable = false)
	private County townCounty;
	
	// This method is needed because when committing to the database, 
	// a Town object is first created but it does not have the townId field. 
	// Hence we need this constructor.
	public Town(String townName, County townCounty) {
		this.townName = townName;
		this.townCounty = townCounty;
	}
}