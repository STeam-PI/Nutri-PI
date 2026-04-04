package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
}
