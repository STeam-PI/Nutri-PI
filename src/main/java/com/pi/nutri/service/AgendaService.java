package com.pi.nutri.service;

import com.pi.nutri.dto.Agenda.AgendaRequestDto;
import com.pi.nutri.exception.HorarioOcupadoException;
import com.pi.nutri.model.Agenda;
import com.pi.nutri.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
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
     * EAP 4.3.3 & 4.3.2: Cria um novo slot de horário validando a duração e conflitos.
     */
    @Transactional // Requisito 4.3.2: Garante atomicidade na operação
    public Agenda criarHorario(AgendaRequestDto dto) {
        // 1. Cálculos de Tempo (4.3.3 e 4.3.2)
        LocalTime inicio = dto.horaAgenda();
        int duracao = dto.duracaoMinutos() != null ? dto.duracaoMinutos() : 30;
        LocalTime fim = inicio.plusMinutes(duracao);
        // 2. Validação de Choque (nucleo de 4.3.2)

        if (agendaRepository.existsOverlapping(dto.dataAgenda(), inicio, fim)) {
            throw new HorarioOcupadoException("Conflito de horários: Já existe um atendimento neste intervalo.");
        }

        // 3. Persistência (Lógica da 4.3.3)
        Agenda agenda = new Agenda();
        agenda.setDataAgenda(dto.dataAgenda());
        agenda.setHoraAgenda(inicio);
        agenda.setDuracaoMinutos(duracao);
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