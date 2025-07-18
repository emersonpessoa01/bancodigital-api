### 💳 bancodigital-api

API RESTful bancária para gestão de clientes, contas e cartões. Permite criação de contas, movimentações financeiras, emissão de cartões e controle de faturas. Ideal para simulações e estudos com Spring Boot.

### ⚙️ Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Banco H2 (em memória)
- Maven
- Lombok

### ▶️ Como executar

```bash
git clone https://github.com/emersonpessoa01/bancodigital-api.git
cd bancodigital-api
./mvnw spring-boot:run

Console do banco H2: http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:testdb — Usuário: sa — Senha: (vazio)
```


📦 Importação da coleção de testes no Insomnia:

[Download da coleção](./src/docs/Insomnia_2025-07-18.yaml)

path: ./src/docs/Insomnia_2025-07-18.yaml

👤 Cliente

| Método | Endpoint       | Descrição                |
| ------ | -------------- | ------------------------ |
| POST   | /clientes      | Criar cliente            |
| GET    | /clientes/{id} | Buscar cliente           |
| PUT    | /clientes/{id} | Atualizar cliente        |
| DELETE | /clientes/{id} | Remover cliente          |
| GET    | /clientes      | Listar todos os clientes |

🏦 Conta

| Método | Endpoint                   | Descrição                             |
| ------ | -------------------------- | ------------------------------------- |
| POST   | /contas                    | Criar conta                           |
| GET    | /contas/{id}               | Buscar conta                          |
| POST   | /contas/{id}/transferencia | Transferência entre contas            |
| GET    | /contas/{id}/saldo         | Consultar saldo                       |
| POST   | /contas/{id}/pix           | Realizar pagamento via Pix            |
| POST   | /contas/{id}/deposito      | Realizar depósito                     |
| POST   | /contas/{id}/saque         | Realizar saque                        |
| PUT    | /contas/{id}/manutencao    | Aplicar taxa de manutenção (corrente) |
| PUT    | /contas/{id}/rendimentos   | Aplicar rendimentos (poupança)        |


💳 Cartão

| Método | Endpoint                       | Descrição                                |
| ------ | ------------------------------ | ---------------------------------------- |
| POST   | /cartoes                       | Emitir cartão                            |
| GET    | /cartoes/{id}                  | Obter detalhes do cartão                 |
| POST   | /cartoes/{id}/pagamento        | Realizar pagamento com o cartão          |
| PUT    | /cartoes/{id}/limite           | Alterar limite do cartão de crédito      |
| PUT    | /cartoes/{id}/status           | Ativar ou desativar cartão               |
| PUT    | /cartoes/{id}/senha            | Alterar senha do cartão                  |
| GET    | /cartoes/{id}/fatura           | Consultar fatura do cartão de crédito    |
| POST   | /cartoes/{id}/fatura/pagamento | Realizar pagamento da fatura             |
| PUT    | /cartoes/{id}/limite-diario    | Alterar limite diário (cartão de débito) |


🧑‍💻 Autor
Desenvolvido por Emerson Pessoa <br>
[Linkedin](https://www.linkedin.com/in/emersonpessoa01/)