<p align="center">
    <img src="https://www.svgrepo.com/show/184143/java.svg" width="130" />
    <img src="https://www.edureka.co/blog/wp-content/uploads/2019/08/recyclebin-data-1.png" width="220" />
</p>

# ZipCode Address Finder - v1.0.0


## Descrição do projeto

Este projeto é uma API para consulta de endereços baseados em CEPs (Códigos de Endereçamento Postal). 
Ele foi desenvolvido com uma arquitetura hexagonal, garantindo flexibilidade e permitindo a troca de componentes internos sem impactar diretamente o núcleo da aplicação. 
A aplicação interage com fontes externas, como banco de dados para armazernar os ceps e com apis fornecedoras no caso de não localizar o cep na base de dados, afim de entregar resposta satisfatorial ao cliente consumidor desta API.

## Funcionalidades implementadas

[x] Criação de cartão
[x] Consulta de ceps, utilizando de filtros como endereço, cidade, estado.
[x] Consulta de endereço por CEP.
[x] Inicialização com pré-carregamento de dados (Data Loader).
[x] Integração com serviços externos, como o [ViaCEP](https://viacep.com.br/) em caso de não localizar cep na base de dados.

## Pré-requisitos
Para execução do projeto é necessário ter instalado no ambiente os softwares abaixo nas versões descritas ou superiores:

- Java SDK 11
- Maven v3.8.5
- Docker (Opcional)

## Principais dependências

- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Spock Framework
- Lombok
- Log4j
- Apache Commons CSV
- Flyway

### Da estrutura do Projeto

O projeto foi desenvolvido com base na **Arquitetura Hexagonal**, organizando-se em camadas bem definidas para garantir flexibilidade e desacoplamento entre os componentes. Essa abordagem facilita a manutenção, evolução e substituição de partes do sistema sem impactar o núcleo da aplicação.

#### **Camadas do Projeto**

1. **Core (Domínio)**
    - Contém as **regras de negócios** da aplicação.
    - Mantém-se independente de frameworks e tecnologias específicas, garantindo que as regras de negócio não sejam impactadas por mudanças externas.

2. **Adaptadores**
    - **Entrada (Inbound)**:
        - Responsáveis por receber requisições (ex.: HTTP) e traduzi-las para comandos compreensíveis pelo domínio.
        - Implementados principalmente como controladores que processam as chamadas de APIs REST.
    - **Saída (Outbound)**:
        - Gerenciam conexões com fontes externas, como bancos de dados e APIs de terceiros (ex.: ViaCEP).
        - Atuam como intermediários entre o domínio e os recursos externos.

3. **Portas**
    - Interfaces que conectam os adaptadores ao domínio.
    - Garantem a flexibilidade ao permitir a troca de implementações sem modificar as regras de negócios.

4. **Configuração**
    - Centraliza a inicialização e configuração dos componentes da aplicação, como os **beans** gerenciados pelo Spring Framework.
    - Facilita a personalização e integração de novos serviços.

Essa estrutura modulariza o sistema e promove uma separação clara de responsabilidades, tornando-o mais robusto e escalável.

### Instalação de dependências

``` bash
   mvn install
```

## Environment Variables

O projeto se utiliza de variáveis de ambiente que podem ser definidas nos arquivos **application.yaml**

## Testes


``` bash
    mvn test
```

## Execução local

Para executar o projeto de forma local, faça a configuração do arquivo application.yml e execute os comandos abaixo.

``` bash
   mvn spring-boot:run
```


A aplicação por padrão estara disponivel em **http://127.0.0.1:8080** a porta pode ser configurada no arquivo **application.yml** no atributo **server.port**

## Instalação e execução com Docker

Para facilitar instalação e execução da aplicação foi implementado containers Docker, onde é realizado o processo de build, testes e execução.
O profile configurado para utilizanção no Docker é o **application-docker.yml**

Estrutura dos arquivos Docker:

- **Dockerfile:** responsável por realizar o build da imagem da aplicação
- **docker-compose.yml:** responsável por realizar o build e execução do servicos

Os passos abaixo devem ser executados na raiz do projeto.

### Build

``` bash
    docker-compose -p zipcode-address-finder -f docker/docker-compose.yml build
```

### Up

``` bash
    docker-compose -p zipcode-address-finder -f docker/docker-compose.yml up -d
```

### Down

``` bash
    docker-compose -p zipcode-address-finder -f docker/docker-compose.yml down
```

## Swagger

Para facilitar a documentação e interação com a API deste projeto, utilizamos o Swagger, uma ferramenta que
permite visualizar, testar e entender melhor os endpoints disponíveis. Abaixo estão as principais informações
relacionadas ao Swagger neste projeto:


### Acessando o Swagger
Após iniciar o serviço localmente ou através do Docker, você pode acessar a interface do Swagger no seguinte endereço: **http://localhost:8080/swagger-ui-custom.html**
### Explorando a API
Ao acessar o Swagger, você terá uma visão completa dos endpoints disponíveis, suas descrições, tipos de requisições
suportadas (GET, POST, etc.), parâmetros necessários e exemplos de resposta.


### Testando Endpoints
O Swagger permite que você teste os endpoints diretamente na interface, fornecendo entradas de dados e visualizando as
respostas. Isso facilita o processo de desenvolvimento e depuração.


## Autor

- Bruno Feliciano de Godoi - [Github](https://github.com/brunogodoif) - [Linkedin](https://www.linkedin.com/in/bruno-feliciano-de-godoi/) - [OutlookMail](malito:brunofgodoi@outlook.com.br)

## Licença

MIT
