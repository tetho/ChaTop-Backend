# ChaTop

## Description
ChaTop un portail en ligne pour permettre aux locataires potentiels de contacter les propriétaires des différentes propriétés qu'ils souhaitent louer.

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
- Angular 18.0.6
- Node.js 20.13.1

### Configuration du Back-end

Clonez le dépôt : 

> git clone https://github.com/tetho/ChaTop-Backend.git

Configurez la base de données dans le fichier : 

> src/main/resources/application.properties

Construisez le projet et démarrez l'application : 

> mvn clean install

> mvn spring-boot:run

### Configuration du Front-end

Clonez le dépôt : 

> git clone https://github.com/OpenClassrooms-Student-Center/P3-Full-Stack-portail-locataire

Allez dans le répertoire :

> cd P3-Full-Stack-portail-locataire

Installer les dépendances :

> npm install

Lancer le Front-end :

> npm run start

## Documentation

La documentation interactive de l'API est générée par Swagger. Vous pouvez y accéder à l'adresse suivante :

> http://localhost:3001/swagger-ui/index.html