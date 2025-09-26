Sistema de Streaming 
Este projeto é uma demonstração de um sistema de streaming construído com Spring Boot 3 e Spring Data JPA. O objetivo principal é ilustrar o design da arquitetura e o mapeamento eficiente dos dados.

Tecnologias Principais
O projeto usa ferramentas modernas e populares:

Spring Boot 3 para iniciar e configurar a aplicação.

Spring Data JPA / Hibernate para a persistência de dados no banco.

Lombok para reduzir o código repetitivo (getters, setters) nas Entidades.

H2 Database (em memória), um banco de dados leve, usado para testes de desenvolvimento.

Arquitetura e Estrutura de Dados
O projeto segue o padrão de camadas do Spring, onde as responsabilidades são bem definidas por pacotes:

O pacote entity contém os modelos de dados (as tabelas do SQL): Video, User, Category e WatchHistory.

O pacote repository define as consultas e o acesso aos dados, gerenciado pelo Spring Data JPA.

O pacote service contém a lógica de negócio principal do sistema, como o cálculo de rankings no AnalyticsService.

O pacote controller expõe a funcionalidade como uma API REST.

Mapeamento Objeto-Relacional (Entidades)
O mapeamento das entidades reflete a estrutura de um serviço de streaming:

A entidade Video é ligada à entidade Category por um relacionamento "muitos para um" (ManyToOne).

A entidade WatchHistory (Histórico de Visualização) é a tabela de ligação central. Ela registra quem assistiu o quê e quando, conectando os objetos User e Video.

Consultas JPA em Destaque
O projeto utiliza os Query Methods do Spring Data JPA para buscar informações complexas de forma simples:

Métodos como findTop10ByOrderByViewsDesc() e findTop10ByOrderByAvaliacaoDesc() permitem criar rankings de popularidade e qualidade com apenas uma linha de código.

O AnalyticsService utiliza consultas com GROUP BY para calcular o usuário que mais assistiu vídeos, demonstrando a capacidade de agregação do JPA.

Acesso e Execução de Consultas SQL
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

Configuração do Banco de Dados
O arquivo application.properties configura a aplicação para usar um banco de dados H2 em memória. Isso significa que o banco é criado e populado (pelo CommandLineRunner) a cada vez que a aplicação é iniciada.

A configuração ddl-auto=update instrui o Hibernate a criar e manter as tabelas a partir das classes Java.

O console H2 está ativado, permitindo que os desenvolvedores inspecionem as tabelas diretamente pelo navegador.