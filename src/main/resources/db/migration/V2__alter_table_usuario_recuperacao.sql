ALTER TABLE usuarios ADD reset_token VARCHAR(255) null;

ALTER TABLE usuarios ADD expiracao_token DATETIME NULL;