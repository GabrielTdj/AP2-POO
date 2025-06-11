
DROP TABLE IF EXISTS Voto;
DROP TABLE IF EXISTS Traducoes;
DROP TABLE IF EXISTS Texto;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Idioma;

CREATE TABLE Idioma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(10) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    tipo ENUM('Colaborador', 'Moderador') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Texto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conteudo TEXT NOT NULL,
    idioma_origem_id INT NOT NULL,
    idioma_destino_id INT NOT NULL,
    FOREIGN KEY (idioma_origem_id) REFERENCES Idioma(id),
    FOREIGN KEY (idioma_destino_id) REFERENCES Idioma(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Traducoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    texto_id INT NOT NULL,
    usuario_id INT NOT NULL,
    conteudo TEXT NOT NULL,
    FOREIGN KEY (texto_id) REFERENCES Texto(id),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE Voto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    traducao_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (traducao_id) REFERENCES Traducoes(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Populando os dados

INSERT INTO Idioma (nome, codigo) VALUES 
('Português', 'pt'),
('Inglês', 'en'),
('Espanhol', 'es');

INSERT INTO Usuario (nome, email, tipo) VALUES 
('João Silva', 'joao@gmail.com', 'Colaborador'),
('Maria Costa', 'maria@gmail.com', 'Colaborador'),
('Ana Rocha', 'ana@gmail.com', 'Moderador');

INSERT INTO Texto (conteudo, idioma_origem_id, idioma_destino_id) VALUES 
('Bom dia!', 1, 2),
('Como você está?', 1, 3);

INSERT INTO Traducoes (texto_id, usuario_id, conteudo) VALUES 
(1, 1, 'Good morning!'),
(1, 2, 'Morning!'),
(2, 1, '¿Cómo estás?');

INSERT INTO Voto (usuario_id, traducao_id) VALUES 
(2, 1),
(3, 1),
(1, 2),
(3, 3);
