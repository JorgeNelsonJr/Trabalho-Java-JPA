📺 Sistema de Streaming (Spring Data JPA Demo)
Este projeto é uma demonstração de um sistema de streaming construído com Spring Boot 3 e Spring Data JPA. O objetivo principal é ilustrar o design da arquitetura e o mapeamento eficiente dos dados.

🛠️ Tecnologias Principais
O projeto usa ferramentas modernas e populares:

Spring Boot 3 para iniciar e configurar a aplicação.

Spring Data JPA / Hibernate para a persistência de dados no banco.

Lombok para reduzir o código repetitivo (getters, setters) nas Entidades.

H2 Database (em memória), um banco de dados leve, usado para testes de desenvolvimento.

💻 Acesso e Execução de Consultas SQL
Para inspecionar o banco de dados e rodar as consultas SQL diretamente nas tabelas (que são criadas a partir das Entidades), você deve usar o H2 Console.

1. Inicie a Aplicação
Certifique-se de que a aplicação Spring Boot está rodando (em um terminal).

2. Acesse o Console
Abra seu navegador e use a URL padrão:

URL: http://localhost:8080/h2-console

(Se você mudou a porta para 8081 devido a um erro, use http://localhost:8081/h2-console)

3. Conecte-se ao Banco de Dados
Na tela de login do H2 Console, insira as credenciais do application.properties:

JDBC URL: jdbc:h2:mem:streamingdb

Usuário: sa

Senha: (Deixe em branco)

Ao clicar em "Connectar", você terá acesso ao banco de dados populado para testar suas próprias queries SQL.

🏗️ Arquitetura e Estrutura de Dados
O projeto segue o padrão de camadas do Spring, onde as responsabilidades são bem definidas por pacotes:

O pacote entity contém os modelos de dados (as tabelas do SQL): Video, User, Category e WatchHistory.

O pacote repository define as consultas e o acesso aos dados, gerenciado pelo Spring Data JPA.

O pacote service contém a lógica de negócio principal do sistema, como o cálculo de rankings no AnalyticsService.

Mapeamento Objeto-Relacional
O mapeamento das entidades reflete a estrutura de um serviço de streaming:

A entidade Video é ligada à entidade Category por um relacionamento "muitos para um" (ManyToOne).

A entidade WatchHistory (Histórico de Visualização) é a tabela de ligação central. Ela registra quem assistiu o quê e quando, conectando os objetos User e Video.

Diagrama de Classe

classDiagram
    direction LR

    class User {
        + Long id
        + String nome
        + String email
        --
        + List<WatchHistory>
    }

    class Category {
        + Long id
        + String nome
        --
        + List<Video>
    }

    class Video {
        + Long id
        + String titulo
        + String descricao
        + Double avaliacao
        + Long views
        --
        + Category categoria
        + List<WatchHistory>
    }

    class WatchHistory {
        + Long id
        + LocalDateTime dataVisualizacao
        --
        + User usuario
        + Video video
    }

    User "1" -- "*" WatchHistory : registra
    Video "1" -- "*" WatchHistory : registra
    Category "1" -- "*" Video : pertence_a
