package com.pi.nutri.dto.Login;

import lombok.Data;

@Data
public class RedefinirSenhaDto {
    private String novaSenha;
    private String confirmeSenha;
    private String token;
}
