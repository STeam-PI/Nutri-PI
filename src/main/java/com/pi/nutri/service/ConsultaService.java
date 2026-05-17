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
        consulta.setStatus("PENDENTE");

        return consultaRepository.save(consulta);
    }

    @Transactional
    public Consulta alterarStatus(Long consultaId, String novoStatus, Long responsavelId) {
        Consulta consulta = consultaRepository.findById(consultaId)
            .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada."));

        Usuario responsavel = usuarioRepository.findById(responsavelId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário responsável não encontrado."));

        if (!responsavel.isNutri()) {
            throw new IllegalArgumentException("Apenas membros da equipe (Nutricionistas/Recepção) podem alterar o status.");
        }

        if (!novoStatus.equals("PENDENTE") && !novoStatus.equals("CONFIRMADA") 
            && !novoStatus.equals("CANCELADA") && !novoStatus.equals("REALIZADA")) {
            throw new IllegalArgumentException("Status inválido.");
        }

        consulta.setStatus(novoStatus);
        consulta.setAtualizadoPor(responsavel);

        return consultaRepository.save(consulta);
    }
}
