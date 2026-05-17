ALTER TABLE consultas
ADD atualizado_por_id BIGINT NULL;

ALTER TABLE consultas
ADD CONSTRAINT fk_consultas_usuarios_atualizado
FOREIGN KEY (atualizado_por_id) REFERENCES usuarios(id);
