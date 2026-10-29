# ExercicioEmpregadosDepartamentos

Exercício de JDBC + PostgreSQL: sistema de gestão de empregados e departamentos.

**Enunciado:** "Uma empresa deseja guardar em seu banco de dados quais são os seus
empregados e quais são seus departamentos. Todo empregado tem CPF e Nome; todo
departamento possui sigla e nome. Todo empregado deve estar alocado em um departamento
e é importante poder registrar que um departamento tem um empregado que é o seu gerente."

## Modelo de dados

- `departamento`: **sigla** (PK), nome, gerente_cpf (FK → empregado.cpf, pode ser NULL)
- `empregado`: **cpf** (PK), nome, departamento_sigla (FK → departamento.sigla, NOT NULL)

O relacionamento é circular (departamento → empregado → departamento); por isso a coluna
`gerente_cpf` ganha sua FK via `ALTER TABLE` depois da criação das duas tabelas
(ver `sql/bddepartamentos.sql`).

## Tópicos abordados (JDBC)

- Conexão com `DriverManager` e `PreparedStatement`
- CRUD completo de empregados e departamentos
- `JOIN` (empregado × departamento) e `LEFT JOIN` (para exibir o nome do gerente)
- Restrições de integridade referencial (FK) tratadas como regras de negócio:
  - não exclui departamento que ainda tem empregados;
  - não exclui empregado que é gerente de um departamento (remova da gerência antes);
  - não aloca CPF inexistente como gerente.

## Estrutura

| Arquivo/Pasta | Função |
|---|---|
| `src/br/com/empresa/app/MainGestaoEmpresa.java` | Menu principal |
| `src/br/com/empresa/jdbc/FabricaConexao.java` | Conexão (postgres / senha postgres) |
| `src/br/com/empresa/jdbc/EmpregadoDAO.java` | CRUD de empregados |
| `src/br/com/empresa/jdbc/DepartamentoDAO.java` | CRUD de departamentos + gerência |
| `src/br/com/empresa/modelo/` | Entidades `Empregado`, `Departamento` e utilitário `Cpf` |
| `src/br/com/empresa/excecao/RegraNegocioException.java` | Exceção das regras de negócio |
| `lib/postgresql-42.7.2.jar` | Driver JDBC do PostgreSQL |
| `sql/bddepartamentos.sql` | Criação do banco e tabelas |

## Pré-requisitos

1. PostgreSQL instalado e em execução (porta 5432).
2. Executar `sql/bddepartamentos.sql` (cria o banco `bddepartamentos` e as tabelas).
3. Usuário `postgres` com senha `postgres` (configurada em `FabricaConexao`).
   Ajuste lá caso a sua senha seja diferente.

## Como executar no Eclipse

1. Importe o projeto: **File > Import > Existing Projects into Workspace**.
2. Confirme que o banco `bddepartamentos` existe e o PostgreSQL está rodando.
3. Rode `br.com.empresa.app.MainGestaoEmpresa` como **Java Application**.

## Como executar pelo terminal

```bash
mkdir bin
javac -encoding UTF-8 -cp lib/postgresql-42.7.2.jar -d bin $(find src -name "*.java")
java -cp "bin:lib/postgresql-42.7.2.jar" br.com.empresa.app.MainGestaoEmpresa
# No Windows, troque ':' por ';' no classpath
```

## Menu da aplicação

- **Empregados:** cadastrar, listar, buscar por CPF, alterar nome, transferir de
  departamento, excluir, listar por departamento.
- **Departamentos:** cadastrar, listar, buscar por sigla, alterar nome, alocar gerente,
  remover gerente, excluir.

> O CPF pode ser digitado com ou sem pontuação; o banco guarda só os 11 dígitos.
> Siglas são convertidas para maiúsculas.
