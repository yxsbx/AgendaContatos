# Contact Agenda Application

## Overview

This application is a simple contact agenda designed to manage contacts and their information effectively. It features a console-based user interface for adding, removing, and editing contacts, as well as storing contact details using text files as a simulation of a database system.

## Features

The contact agenda has a straightforward menu-driven interface that allows users to perform the following actions:

- **Add Contact:** Register a new contact with unique ID, name, surname, and phone numbers.
- **Remove Contact:** Delete a contact from the agenda based on its ID.
- **Edit Contact:** Update the details of an existing contact.
- **Exit:** Close the application.

## Classes

### `Contato`

This class represents a contact in the agenda, including the following properties:

- `id`: A unique identifier for the contact.
- `nome`: The contact's first name.
- `sobreNome`: The contact's surname.
- `telefones`: A list of `Telefone` objects associated with the contact.

### `Telefone`

Represents a phone number with the following attributes:

- `id`: A unique identifier for the phone number.
- `ddd`: The area code of the phone number.
- `numero`: The phone number itself.

## Non-Functional Requirements

- **Data Storage:** The application uses text files to store and manage contact information, simulating a database environment.

## Functional Requirements

- **RN1:** Storing contacts with the same ID is not allowed.
- **RN2:** Storing contacts with already registered phone numbers is not permitted.
- **RN3:** To perform actions, the contact's ID must be provided.


---

## Visão geral

Este aplicativo é uma agenda de contatos simples projetada para gerenciar contatos e suas informações de forma eficaz. Ele apresenta uma interface de usuário baseada em console para adicionar, remover e editar contatos, bem como armazenar detalhes de contato usando arquivos de texto como uma simulação de um sistema de banco de dados.

## Características

A agenda de contatos possui uma interface simples baseada em menus que permite aos usuários realizar as seguintes ações:

- **Adicionar contato:** Registre um novo contato com ID, nome, sobrenome e números de telefone exclusivos.
- **Remover contato:** Exclua um contato da agenda com base em seu ID.
- **Editar contato:** Atualize os detalhes de um contato existente.
- **Sair:** Feche o aplicativo.

## Aulas

### `Contato`

Esta classe representa um contato na agenda, incluindo as seguintes propriedades:

- `id`: Um identificador exclusivo para o contato.
- `nome`: O primeiro nome do contato.
- `sobreNome`: O sobrenome do contato.
- `telefones`: Uma lista de objetos `Telefone` associados ao contato.

### `Telefone`

Representa um número de telefone com os seguintes atributos:

- `id`: Um identificador exclusivo para o número de telefone.
- `ddd`: O código de área do número de telefone.
- `numero`: O próprio número de telefone.

## Requisitos não Funcionais

- **Armazenamento de dados:** O aplicativo utiliza arquivos de texto para armazenar e gerenciar informações de contato, simulando um ambiente de banco de dados.

## Requisitos funcionais

- **RN1:** Não é permitido armazenar contatos com o mesmo ID.
- **RN2:** Não é permitido armazenar contatos com números de telefone já cadastrados.
- **RN3:** Para realizar ações é necessário informar o ID do contato.
