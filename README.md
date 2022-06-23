# Gerenciamento de Pedidos

### Resumo
API para realização de pedidos em restaurantes. Cada restaurante possui uma cozinha agregada com clientes e pedidos

## Tecnologias utilizadas
* Java
* Spring Boot
* JPA/Hibernate
* Mysql
* Flyway
* ModelMapper


## Recursos disponíveis para acesso via API:
* [**Cozinhas**]
* [**Restaurantes**]
* [**Clientes**]
* [**Pedidos**]


## Métodos
Requisições para a API devem seguir os padrões:
| Método | Descrição |
|---|---|
| GET | Retorna informações de um ou mais registros |
| POST | Utilizado para criar um novo registro |
| PUT | Atualiza dados de um registro ou altera sua situação |
| DEL | Remove um registro do sistema |
| PATCH | Utilizado para atualiação parcial de um registro |

## Respostas
| Código | Descrição |
|---|---|
| 200 | Requisição executada com sucesso |
| 201 | Incluído no Banco de Dados com sucesso |
| 400 | Erros de validação |
| 404 | Registro pesquisado não encontrado |
| 409 | O registro não pode ser deletado pois viola regra de negócio |
| 500 | Erro de Sistema. Contactar administrador do sistema |





