package com.pi.nutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pi.nutri.model.Agenda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 'TRUE' ELSE 'FALSE' END FROM agendas WHERE " +
            "data_agenda = :data AND " +
            "(:horaInicio < CONVERT(varchar(5), DATEADD(minute, duracao_minutos, hora_agenda), 108)) AND " +
            "(:horaFim > CONVERT(varchar(5), hora_agenda, 108))", nativeQuery = true)
    boolean existsOverlapping(
            @Param("data") LocalDate data,
            @Param("horaInicio") String horaInicio,
            @Param("horaFim") String horaFim
    );
}
