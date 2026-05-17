package com.pi.nutri.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.nutri.model.Agenda;
import com.pi.nutri.service.AgendaService;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {
    
    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<Agenda>> buscarHorariosLivres(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<Agenda> horariosLivres = agendaService.buscarHorariosLivres(data);
        return ResponseEntity.ok(horariosLivres);
    }
}
