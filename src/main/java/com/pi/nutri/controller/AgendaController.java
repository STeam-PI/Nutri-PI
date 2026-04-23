package com.pi.nutri.controller;

import com.pi.nutri.dto.Agenda.AgendaRequestDto;
import com.pi.nutri.model.Agenda;
import com.pi.nutri.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService agendaService;

    @Autowired
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * Listar todos os horários (Aux progresso)
     */
    @GetMapping
    public ResponseEntity<List<Agenda>> listarTodas() {
        return ResponseEntity.ok(agendaService.listarTodos());
    }

    /**
     * EAP 4.3.3: Cadastra um novo slot de horário.
     */
    @PostMapping
    public ResponseEntity<Agenda> cadastrarHorario(@RequestBody AgendaRequestDto dto) {
        // A conversão de DTO para Model pode ser feita no Service
        Agenda novaAgenda = agendaService.criarHorario(dto);
        return new ResponseEntity<>(novaAgenda, HttpStatus.CREATED);
    }
}