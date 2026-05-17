package com.pi.nutri.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pi.nutri.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    
    @Query("SELECT a FROM Agenda a WHERE a.dataAgenda = :data AND a.isDisponivel = true AND NOT EXISTS (SELECT c FROM Consulta c WHERE c.agenda = a AND c.status IN ('AGENDADA', 'CONFIRMADA'))")
    List<Agenda> findHorariosDisponiveisPorData(@Param("data") LocalDate data);
}
