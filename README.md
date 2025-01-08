# API de Gerenciamento de Clientes

Esta API permite o gerenciamento de clientes em um sistema, oferecendo operações CRUD (Criar, Ler, Atualizar, Excluir) para o cadastro e manutenção de informações dos clientes.

## Tecnologias Utilizadas

- **Java 17**
- **JDK:** Kit de desenvolvimento Java que fornece ferramentas, bibliotecas e o compilador necessários para desenvolver, compilar e executar aplicações Java.
- **MAVEN:** Ferramenta de automação de build e gerenciamento de dependências para projetos Java.
- **JDBC:** API do Java para conectar, interagir e manipular bancos de dados relacionais.
- **Spring Boot:** Framework para desenvolvimento de aplicações Java.
- **Spring Security:** Implementação de autenticação básica.
- **JPA/Hibernate:** Para persistência de dados no banco de dados.
- **H2:** Banco de dados em memória para fins de teste.
- **SLF4J:** Framework de logging para a aplicação.
- **POSTMAN:** Ferramenta para testar e documentar APIs.

## Funcionalidades

A API oferece as seguintes funcionalidades:

- **Criação de cliente**: Adiciona um novo cliente ao banco de dados.
- **Atualização de cliente**: Modifica os dados de um cliente existente.
- **Exclusão de cliente**: Remove um cliente do banco de dados.
- **Listagem de clientes**: Recupera todos os clientes cadastrados.
- **Busca de cliente por ID**: Localiza um cliente específico pelo seu ID.

## Pré-requisitos
Antes de rodar a API, você precisa ter as seguintes ferramentas instaladas:

- **Java 17 ou superior**
- **Maven**
- **Postman**

## Instalação
Clone este repositório na sua IDE e rode a aplicação localmente com os seguintes passos:

### Passo 1: Clone o repositório
```bash
git clone https://github.com/douglasadriano/ms-clientes.git
```
### Passo 2: Compile o projeto
```bash
mvn clean install
```

### Passo 3: Execute o servidor
```bash
mvn spring-boot:run
```
Agora o banco de dados da API estará disponível em http://localhost:8080/h2-console.
- **USER**: `sa`
- **PASSWORD**: `password`

## Endpoints

### 1. Criar Cliente
- **Método**: `POST`
- **URL**: `/api/clientes`
- **Descrição:** Adiciona um novo cliente ao banco de dados.
- **Corpo da Requisição**:
```json
{
  "nome": "João da Silva",
  "email": "joao.silva@exemplo.com",
  "telefone": "1234567890"
}
```

#### Resposta:
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@exemplo.com",
  "telefone": "(12) 3456-7890"
}
```
### 2. Atualizar Cliente
- **Método**: `PUT`
- **URL**: `/api/clientes/{id}`
- **Descrição:** Atualiza as informações de um cliente existente.
- **Corpo da Requisição**:
```json
{
"nome": "João da Silva",
"email": "joao.silva@novoemail.com",
"telefone": "0987654321"
}
```
#### Resposta:
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@novoemail.com",
  "telefone": "(09) 8765-4321"
}
```
### 3. Excluir Cliente
- **Método**: `DELETE`
- **URL**: `/api/clientes/{id}`
- **Descrição:** Remove um cliente do banco de dados.
#### Resposta:
```json
{
  "message": "Cliente excluído com sucesso."
}
```
### 4. Listar Todos os Clientes
- **Método**: `GET`
- **URL:** /api/clientes
- **Descrição:** Recupera a lista completa de clientes cadastrados.
#### Resposta:
```json
[
  {
    "id": 1,
    "nome": "João da Silva",
    "email": "joao.silva@exemplo.com",
    "telefone": "1234567890"
  }
]
```
### 5. Buscar Cliente por ID
- **Método**: `GET`
- **URL:** /api/clientes/{id}
- **Descrição:** Busca um cliente específico utilizando seu ID.
#### Resposta:
```json
{
  "id": 1,
  "nome": "João da Silva",
  "email": "joao.silva@exemplo.com",
  "telefone": "1234567890"
}
```

## Testando a API no Postman
### Passo 1: Configuração do Postman
Autenticação: A API utiliza autenticação básica (Basic Auth). Para testar, use as credenciais:

- **Usuário:** `user`
- **Senha:** `password`
- Desabilitar CSRF: Durante os testes, o CSRF (Cross-Site Request Forgery) foi desabilitado para facilitar as requisições via Postman.

### Passo 2: Testando os Endpoints
### 1. Criar Cliente
- **Método**: `POST`
- **URL**: http://localhost:8080/api/clientes
- **Corpo (JSON)**:
```json
{
  "nome": "Carlos Almeida",
  "email": "carlos.almeida@exemplo.com",
  "telefone": "9876543210"
}
```
#### Resposta:
```json
{
  "id": 1,
  "nome": "Carlos Almeida",
  "email": "carlos.almeida@exemplo.com",
  "telefone": "(98) 7654-3210"
}
```

### 2. Atualizar Cliente
- **Método**: `PUT`
- **URL**: http://localhost:8080/api/clientes/{id}
- **Corpo (JSON)**:
```json
{
  "nome": "Carlos Almeida",
  "email": "carlos.almeida@novoemail.com",
  "telefone": "01123456789"
}
```
#### Resposta:
```json
{
  "id": 1,
  "nome": "Carlos Almeida",
  "email": "carlos.almeida@novoemail.com",
  "telefone": "(01) 12345-6789"
}
```
### 3. Excluir Cliente
- **Método**: `DELETE`
- **URL**: http://localhost:8080/api/clientes/{id}
#### Resposta:
```json
Cliente excluído com sucesso.
```

### 4. Listar Todos os Clientes
- **Método**: `GET`
- **URL**: http://localhost:8080/api/clientes

#### Resposta:
```json
[
  {
    "id": 2,
    "nome": "Carlos Almeida",
    "email": "joao.silva@exemplo.com",
    "telefone": "(12) 3456-7890"
  },
  {
    "id": 3,
    "nome": "João da Silva",
    "email": "joao.silva@exemplo.com",
    "telefone": "(12) 3456-7890"
  }
]
```

### 5. Buscar Cliente por ID
- **Método**: `GET`
- **URL**: http://localhost:8080/api/clientes/{id}

#### Resposta:
```json
  {
    "id": 2,
    "nome": "Carlos Almeida",
    "email": "joao.silva@exemplo.com",
    "telefone": "(12) 3456-7890"
  }
```

## Contribuição
Se você deseja contribuir para este projeto, siga os passos abaixo:

##### 1. Faça um fork do repositório.
##### 2. Crie uma nova branch (git checkout -b minha-branch).
##### 3. Faça suas alterações.
##### 4. Commit suas mudanças (git commit -am 'Adicionar nova funcionalidade').
##### 5. Envie para a branch principal (git push origin minha-branch).
##### 6. Abra um pull request.