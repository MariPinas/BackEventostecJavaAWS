# 🚀 API de Eventos com Integração AWS (EventosTec)

Este projeto é uma API REST robusta desenvolvida em Spring Boot, focada no gerenciamento de eventos. O grande diferencial deste repositório não é apenas o código, mas a **arquitetura de nuvem** utilizada para o deploy, integrando diversos serviços da AWS.

---

## 🛠 Tecnologias Utilizadas

* **Java 21 / Spring Boot 3**
* **Spring Data JPA / Hibernate**
* **Flyway** (Migração de Banco de Dados)
* **MySQL** (Persistência)
* **Lombok** (Produtividade)
* **S3** (Armazenar o link público gerado da imagem)
* **EC2** Rodar o serviço na nuvem
* **RDS** Banco de dados MySQL no RDS da AWS

---

## ☁️ Arquitetura AWS

O projeto foi validado em um ambiente real de nuvem, utilizando as melhores práticas de comunicação entre serviços:

1.  **Amazon EC2:** Hospedagem da aplicação em uma instância Linux (Amazon Linux 2023).
2.  **Amazon RDS:** Banco de dados MySQL gerenciado, com acesso restrito via Security Groups apenas para a instância da API.
3.  **Amazon S3:** Armazenamento escalável de imagens dos eventos, garantindo alta disponibilidade.
4.  **Security Groups:** Configuração de regras de entrada e saída para garantir que o banco de dados não fique exposto à internet.

---

## 📸 Provas de Execução

Como a infraestrutura foi encerrada para fins de economia, abaixo estão os registros do projeto em pleno funcionamento no ambiente AWS:

### 1. Aplicação Rodando na EC2
> <img width="617" height="329" alt="image" src="https://github.com/user-attachments/assets/4c3f77cb-727f-4ebc-a263-de7ebe170946" />
> <img width="1239" height="394" alt="image" src="https://github.com/user-attachments/assets/a2003477-3ead-49fe-bc61-7819c33de5c6" />
> <img width="769" height="527" alt="image" src="https://github.com/user-attachments/assets/fa0f2574-f4d4-4e0e-becf-aad50310d787" />
> <img width="1341" height="544" alt="image" src="https://github.com/user-attachments/assets/6315748c-0901-40b8-b32d-fc07ad7f54d9" />

### 2. Banco de Dados RDS Conectado
> <img width="1323" height="409" alt="image" src="https://github.com/user-attachments/assets/84741005-0b8e-4aac-837f-1ffa3964752a" />

### 3. Integração S3 (Upload de Imagens)
> <img width="1337" height="537" alt="image" src="https://github.com/user-attachments/assets/855623ac-879a-4724-9442-264743d623bd" />

---

## 🔧 Como Rodar o Projeto

1.  Clone o repositório.
2.  Configure as variáveis de ambiente para o banco de dados e AWS:
    * `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`, `AWS_REGION`
    * `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
3.  Execute `./mvnw spring-boot:run`

---

## 💡 Desafios Superados

* **Conectividade:** Configuração de regras de Inbound/Outbound nos Security Groups para permitir a comunicação EC2 -> RDS.
* **Flyway Baseline:** Resolução de conflitos de schemas pré-existentes no RDS MySQL.
* **Deploy via SSH:** Gerenciamento de arquivos e execução de JARs via linha de comando no Amazon Linux.

---
Agradecimentos à Professora Fernanda Kipper,  


Feito com ❤️ por Mariana Santos
