# Sistema de Gestão Logística e E-Commerce — JDBC + PostgreSQL

Projeto integrador do Módulo 11 (Java Database Connectivity), implementando
a camada de persistência de um sistema de e-commerce com **JDBC puro**
sobre **PostgreSQL**.

## Estrutura do projeto

```
src/main/java/br/com/ecommerce/
├── modelo/
│   └── Produto.java                  # POJO que espelha a tabela "produto"
├── jdbc/
│   ├── FabricaConexao.java           # Factory de conexões (Nível 1)
│   ├── TesteConexaoPostgreSQL.java   # Teste de infraestrutura (Nível 1)
│   ├── ProdutoDAO.java               # CRUD com PreparedStatement (Níveis 2 e 3)
│   └── RecursosAvancadosDAO.java     # Cursor rolável + CallableStatement (Nível 5)
├── excecao/
│   └── EstoqueInsuficienteException.java
├── servico/
│   └── ServicoVendaTransacional.java # Transação manual ACID (Nível 4)
└── ui/
    └── MainEcommerce.java            # Menu no terminal (ponto de entrada)

sql/
└── schema.sql                        # Criação do banco, tabelas e stored procedure
```

## Pré-requisitos

- JDK 17+
- Maven 3.8+
- PostgreSQL rodando em `localhost:5432`

## Como preparar o banco de dados

1. Abra o `psql` ou o pgAdmin e execute o script `sql/schema.sql`.
2. Ajuste, se necessário, usuário e senha em `FabricaConexao.java`
   (por padrão: `postgres` / `postgres`).

## Como executar

```bash
mvn clean package
java -cp target/ecommerce-jdbc.jar:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout) br.com.ecommerce.ui.MainEcommerce
```

Ou, de forma mais simples, rode `MainEcommerce.java` diretamente pela sua IDE
(IntelliJ, Eclipse, VS Code), garantindo que o driver `postgresql` esteja
no classpath (o Maven resolve isso automaticamente ao importar o projeto).

## O que cada nível demonstra

| Nível | Conceito                                              | Classe(s) principal(is)             |
|-------|--------------------------------------------------------|--------------------------------------|
| 1     | Driver, `DriverManager`, conexão, exceções verificadas | `FabricaConexao`, `TesteConexaoPostgreSQL` |
| 2     | DML seguro com `PreparedStatement`                      | `ProdutoDAO` (inserir/atualizar/excluir) |
| 3     | Consulta e cursor sequencial com `ResultSet`             | `ProdutoDAO` (listarTodos/buscarPorCodigo) |
| 4     | Transação manual e integridade ACID                     | `ServicoVendaTransacional`           |
| 5     | Cursor rolável e `CallableStatement` (stored procedure)  | `RecursosAvancadosDAO`               |

## Referência

Material de apoio: Módulo 11 — Java Database Connectivity (Prof. Alessandro Cerqueira).
