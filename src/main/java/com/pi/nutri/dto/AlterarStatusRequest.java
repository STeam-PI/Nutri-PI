package com.pi.nutri.dto;

import lombok.Data;

@Data
public class AlterarStatusRequest {
    private String novoStatus;
    private Long responsavelId;
}
