package com.lonar.artofliving.repository;

import java.io.Serializable;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.lonar.artofliving.model.LtConfigurartion;

public interface LtConfigurationRepository extends DataTablesRepository<LtConfigurartion, Serializable> {

}
