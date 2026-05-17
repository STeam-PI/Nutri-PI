package com.pi.nutri.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pi.nutri.model.Agenda;
import com.pi.nutri.repository.AgendaRepository;

@Service
public class AgendaService {
    
    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public List<Agenda> buscarHorariosLivres(LocalDate data) {
        return agendaRepository.findHorariosDisponiveisPorData(data);
    }
}
