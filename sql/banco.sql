CREATE DATABASE planejadospelafe;

USE planejadospelafe;

CREATE TABLE Mae(
	idMae INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    dataNasci DATE NOT NULL,
    telefone INT NOT NULL,
    endereco VARCHAR(150) NOT NULL
);

CREATE TABLE Servico(
	idServico INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    servico VARCHAR(100) NOT NULL,
    
    mae_id INT,
	FOREIGN KEY (mae_id) REFERENCES Mae(idMae)
);

CREATE TABLE Encontro(
	idEncontro INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    
    servico_id INT,
    FOREIGN KEY (servico_ID) REFERENCES Servico(idServico)
);
  

    
