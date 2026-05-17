package com.pi.nutri.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.nutri.dto.AgendamentoRequest;
import com.pi.nutri.model.Consulta;
import com.pi.nutri.service.ConsultaService;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody AgendamentoRequest request) {
        try {
            Consulta novaConsulta = consultaService.agendarConsulta(request.getUsuarioId(), request.getAgendaId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
