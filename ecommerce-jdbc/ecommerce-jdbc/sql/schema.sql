-- ======================================================================
-- Script de criação do banco de dados bdecommerce
-- Disciplina: Programação Orientada a Objetos - Módulo 11 (JDBC)
-- ======================================================================

CREATE DATABASE bdecommerce;

-- Conecte-se ao banco bdecommerce antes de executar os comandos abaixo (\c bdecommerce no psql)

CREATE TABLE produto (
    codigo VARCHAR(10) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    quantidade_estoque INTEGER DEFAULT 0
);

CREATE TABLE pedido (
    id_pedido VARCHAR(20) PRIMARY KEY,
    codigo_produto VARCHAR(10) NOT NULL,
    quantidade_comprada INTEGER NOT NULL,
    data_pedido TIMESTAMP NOT NULL,
    CONSTRAINT fk_pedido_produto FOREIGN KEY (codigo_produto) REFERENCES produto (codigo)
);

CREATE OR REPLACE PROCEDURE sp_calcular_saldo_estoque(
    IN p_codigo VARCHAR,
    OUT p_quantidade INT,
    OUT p_total_reais NUMERIC
)
LANGUAGE plpgsql
AS $$
BEGIN
    SELECT quantidade_estoque, (quantidade_estoque * preco)
    INTO p_quantidade, p_total_reais
    FROM produto
    WHERE codigo = p_codigo;
END;
$$;

-- Dados de exemplo (opcional, útil para testar os cursores roláveis do Nível 5)
INSERT INTO produto (codigo, nome, preco, quantidade_estoque) VALUES
    ('P001', 'Teclado Mecânico', 250.00, 15),
    ('P002', 'Monitor 24 polegadas', 899.90, 8),
    ('P003', 'Mouse Sem Fio', 89.50, 30),
    ('P004', 'Headset Gamer', 320.00, 12);
