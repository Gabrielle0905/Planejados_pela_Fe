CREATE DATABASE planejadospelafe;

USE planejadospelafe;

CREATE TABLE Mae(
	idMae INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    dataNasci DATE NOT NULL,
    telefone INT,
    endereco VARCHAR(150)
);

CREATE TABLE Encontro(
	idEncontro INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    statusEncontro VARCHAR(50) NOT NULL
) ;



CREATE TABLE ServicodoEncontro(
	idServico INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    mae VARCHAR(50) NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    
    id_Encontro INT,
    FOREIGN KEY (id_encontro) REFERENCES Encontro(idEncontro)
);
    