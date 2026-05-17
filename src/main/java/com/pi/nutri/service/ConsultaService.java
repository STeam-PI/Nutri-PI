package com.pi.nutri.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pi.nutri.model.Agenda;
import com.pi.nutri.model.Consulta;
import com.pi.nutri.model.Usuario;
import com.pi.nutri.repository.AgendaRepository;
import com.pi.nutri.repository.ConsultaRepository;
import com.pi.nutri.repository.UsuarioRepository;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final AgendaRepository agendaRepository;

    public ConsultaService(ConsultaRepository consultaRepository, UsuarioRepository usuarioRepository, AgendaRepository agendaRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.agendaRepository = agendaRepository;
    }

    @Transactional
    public Consulta agendarConsulta(Long usuarioId, Long agendaId) {
        
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (usuario.isNutri()) {
            throw new IllegalArgumentException("Apenas pacientes podem realizar agendamentos diretos.");
        }

        Agenda agenda = agendaRepository.findById(agendaId)
            .orElseThrow(() -> new IllegalArgumentException("Agenda não encontrada."));

        if (!agenda.isDisponivel()) {
            throw new IllegalArgumentException("O horário selecionado não está mais disponível.");
        }

        boolean existeConsulta = consultaRepository.existsByAgendaId(agendaId);
        if (existeConsulta) {
            throw new IllegalArgumentException("Este horário já possui uma consulta agendada.");
        }

        Consulta consulta = new Consulta();
        consulta.setUsuario(usuario);
        consulta.setAgenda(agenda);
        consulta.setStatus("AGENDADA");

        return consultaRepository.save(consulta);
    }
}
