create database if not exists gymcontrol;

use gymcontrol;

create table if not exists alunos(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255),
    cpf VARCHAR(255) UNIQUE,
    dataNasc VARCHAR(255),
    altura DOUBLE
);

create table if not exists histPeso(
	id INT PRIMARY KEY AUTO_INCREMENT,
    dataReg VARCHAR(255) UNIQUE,
    peso DOUBLE,
    idAluno INT,
    FOREIGN KEY (idAluno) REFERENCES alunos(id)
    
);