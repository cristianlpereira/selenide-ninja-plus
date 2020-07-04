
# Selenide

Projeto de automação de testes com [Selenide](https://selenide.org/).
(Projeto desenvolvido no curso de Selenide da plataforma [QA Ninja](https://dojo.qaninja.com.br/curso/selenide-java/))


## Configuração do Ambiente

 - [Java JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
 - [IntelliJ IDEA Community](https://www.jetbrains.com/pt-br/idea/download/)
 - [Docker](https://docs.docker.com/get-docker/)
 - [Postman](https://www.postman.com/)
 - [Allure Framework](https://docs.qameta.io/allure/#_installing_a_commandline)


### Hosts

Adicionar ao arquivo hosts:

    127.0.0.1	pgdb
    127.0.0.1	pgadmin
    127.0.0.1	ninjaplus-api
    127.0.0.1	ninjaplus-web


#### Baixar as imagens Docker

    docker pull postgres
    docker pull dpage/pgadmin4
    docker pull qaninja/ninjaplus-api
    docker pull qaninja/ninjaplus-web


#### Criar a rede Docker

    docker network create --driver bridge skynet


#### Subir o banco de dados (PostgreSQL e pgAdmin) 
PostgreSQL

    docker run --name pgdb --network=skynet -e "POSTGRES_PASSWORD=qaninja" -p 5432:5432 -v var/lib/postgresql/data -d postgres

pdAgmin

    docker run --name pgadmin --network=skynet -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=root@qaninja.io" -e "PGADMIN_DEFAULT_PASSWORD=qaninja" -d dpage/pgadmin4


#### Subir aplicação (API e frontend)

API

    docker run --name ninjaplus-api --network=skynet -e "DATABASE=pgdb" -p 3000:3000 -d qaninja/ninjaplus-api


Frontend

    docker run --name ninjaplus-web --network=skynet -e "VUE_APP_API=http://ninjaplus-api:3000" -p 5000:5000 -d qaninja/ninjaplus-web


#### Subir todos os containers Docker

    docker start pgdb
    docker start pgadmin
    docker start ninjaplus-api
    docker start ninjaplus-web


#### Criar banco de dados

Acessar [pgadmin:15432](pgadmin:15432) no navegador

*Usuário:* root@qaninja.io
*Senha:* qaninja

*Criar servidor*
Object -> Create > Server


Aba General

*Name:* pgdb

Aba Connection

*Host:* pgdb

*Port:* 5432

*Maintenance:* postgres

*Username:* postgres

*Password:* qaninja


Criar banco de dados
Object -> Create > Database


Aba General

*Database:* ninjaplus

*Owner:* postgres


#### Inserir usuário
Com o Postman:
POST  - http://ninjaplus-api:3000/user

```json
{
    "full_name": "Papito",
    "email": "papito@ninjaplus.com",
    "password": "pwd123"
}
```

#### Executar testes
Usar o IntelliJ para executar os testes.

#### Relatórios
Para visualizar os relatórios basta rodar o servidor do [Allure](https://docs.qameta.io/allure/) no diretório raiz do projeto:

    allure serve
