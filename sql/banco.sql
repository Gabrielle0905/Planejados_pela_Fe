CREATE DATABASE planejadospelafe;

USE planejadospelafe;

CREATE TABLE Mae(
	idMae INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    dataNasci DATE,
    telefone INT,
    endereco VARCHAR(150)
);

CREATE TABLE Encontro(
	idEncontro INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    status VARCHAR(50)
);
  
 CREATE TABLE ServicoEncontro(
	idServico INT AUTO_INCREMENT PRIMARY KEY,
	encontro_id INT NOT NULL,
	FOREIGN KEY (encontro_id) REFERENCES Encontro(idEncontro) ON DELETE CASCADE,
	servico VARCHAR(100) NOT NULL,
	mae_id INT,
	FOREIGN KEY (mae_id) REFERENCES Mae(idMae)
 )

    
