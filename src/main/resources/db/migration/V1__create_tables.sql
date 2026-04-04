CREATE TABLE usuarios(
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    is_nutri BIT NOT NULL DEFAULT 0,
    crn VARCHAR(20) NULL
);

CREATE TABLE agendas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    data_agenda DATE NOT NULL,
    hora_agenda TIME NOT NULL,
    is_disponivel BIT NOT NULL DEFAULT 1
);

CREATE TABLE consultas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    agenda_id BIGINT NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'AGENDADA',
    
    CONSTRAINT fk_consulta_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_consulta_agenda FOREIGN KEY (agenda_id) REFERENCES agendas(id)
);