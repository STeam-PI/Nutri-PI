package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Agenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query("SELECT COUNT(a) > 0 FROM Agenda a WHERE " +
            "a.dataAgenda = :data AND " +
            "(:horaInicio < FUNCTION('DATEADD', minute, a.duracaoMinutos, a.horaAgenda)) AND " +
            "(:horaFim > a.horaAgenda)")
    boolean existsOverlapping(
            @Param("data") LocalDate data,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim
    );
}
