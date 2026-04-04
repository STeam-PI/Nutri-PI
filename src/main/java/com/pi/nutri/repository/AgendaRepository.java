package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    
}
