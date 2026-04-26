package com.pi.nutri.dto.Agenda;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendaRequestDto(
        LocalDate dataAgenda,
        LocalTime horaAgenda,
        Integer duracaoMinutos,
        boolean isDisponivel
) {}