package com.lonar.artofliving.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "LT_MAST_ANIMALS")
@JsonInclude(Include.NON_NULL)
public class LtAolBanners extends BaseClass{

	
	
}
