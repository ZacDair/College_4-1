package com.dair.entities;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "county")
public class County {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int countyId;
	
	@Column(nullable = false, unique = true  )
	private String countyName;
	
	// One County has many towns
	// When fetching a County from a query, do not fetch the list of towns. 
	// If the county is removed from the database, also remove the towns. 
	@OneToMany(mappedBy = "townCounty", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Town> countyTowns = new ArrayList<>();

	public County(String countyName) {
		this.countyName = countyName;
	}
}