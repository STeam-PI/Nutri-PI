package com.pi.nutri.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "agendas")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_agenda", nullable = false)
    private LocalDate dataAgenda;

    @Column(name = "hora_agenda", nullable = false)
    private LocalTime horaAgenda;

    @Column(name = "is_disponivel", nullable = false)
    private boolean isDisponivel;

    //4.3.3 nasce aqui
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;
}
