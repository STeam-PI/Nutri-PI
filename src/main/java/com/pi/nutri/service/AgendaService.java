package com.pi.nutri.service;

import com.pi.nutri.dto.Agenda.AgendaRequestDto;
import com.pi.nutri.model.Agenda;
import com.pi.nutri.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    /**
     * EAP 4.3.3: Lista todos os horários.
     */
    public List<Agenda> listarTodos() {
        return agendaRepository.findAll();
    }

    /**
     * EAP 4.3.3: Cria um novo slot de horário na agenda.
     */
    public Agenda criarHorario(AgendaRequestDto dto) {
        Agenda agenda = new Agenda();
        agenda.setDataAgenda(dto.dataAgenda());
        agenda.setHoraAgenda(dto.horaAgenda());
        // Regra da 4.3.3: Se a duração for nula no DTO, aplicar 30 como default
        agenda.setDuracaoMinutos(dto.duracaoMinutos() != null ? dto.duracaoMinutos() : 30);
        agenda.setDisponivel(dto.isDisponivel());

        return agendaRepository.save(agenda);
    }

    /**
     * Busca agendas por ID
     */
    public Agenda buscarPorId(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário de agenda não encontrado"));
    }
}