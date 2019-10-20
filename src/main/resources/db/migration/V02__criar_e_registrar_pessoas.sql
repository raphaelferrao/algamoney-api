CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	ativo BOOLEAN NOT NULL,
	
	cep VARCHAR(9) NOT NULL,
	logradouro VARCHAR(100) NOT NULL,
	numero VARCHAR(20) NULL,
	complemento VARCHAR(50) NULL,
	bairro VARCHAR(50) NOT NULL,
	cidade VARCHAR(50) NOT NULL,
	sigla_uf VARCHAR(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO pessoa (nome, cep, logradouro, numero, complemento, bairro, cidade, sigla_uf, ativo)
VALUES ('Strawlley Pessoa', '20511-140', 'Rua Major Ávila', '195', null, 'Tijuca', 'Rio de Janeiro', 'RJ', true); 

INSERT INTO pessoa (nome, cep, logradouro, numero, complemento, bairro, cidade, sigla_uf, ativo)
VALUES ('João Tortugo', '22775-045', 'Rua Alfredo Ceschiatti', '100', 'Bloco Z - Apto 2001', 'Jacarepaguá', 'Rio de Janeiro', 'RJ', true);

INSERT INTO pessoa (nome, cep, logradouro, numero, complemento, bairro, cidade, sigla_uf, ativo)
VALUES ('Warysson Gomes', '24440-710', 'Rua Sá Carvalho', '251', 'Bloco 10 - Apto 1105', 'Brasilandia', 'São Gonçalo', 'RJ', true);

INSERT INTO pessoa (nome, cep, logradouro, numero, complemento, bairro, cidade, sigla_uf, ativo)
VALUES ('Lara Lauriel', '24020-125', 'Rua XV de Novembro', '8', 'Loja Z', 'Centro', 'Niterói', 'RJ', true);
