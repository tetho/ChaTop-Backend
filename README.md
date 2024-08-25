# ChaTop Back-end

## Description
ChaTop un portail en ligne pour permettre aux locataires potentiels de contacter les propriétaires des différentes propriétés qu'ils souhaitent louer.

Le back-end de ChaTop est une application Spring Boot qui fournit une API RESTful pour gérer les propriétés à louer, les utilisateurs, et les interactions entre locataires et propriétaires.

## Technologies utilisées
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- MySQL
- Maven

## Librairies et outils

- Lombok
- MapStruct
- Swagger
- Mockito
- Postman

## Installation

### Prérequis

- Java 11 ou version ultérieure
- Maven
- MySQL

### Configuration

Cloner le dépôt : 

> git clone https://github.com/tetho/ChaTop-Backend.git

Configurez la base de données dans le fichier : 

> src/main/resources/application.properties

Construisez le projet et démarrez l'application : 

> mvn clean install

> mvn spring-boot:run

## Documentation

La documentation interactive de l'API est générée par Swagger. Vous pouvez y accéder à l'adresse suivante :

> http://localhost:3001/swagger-ui/index.html