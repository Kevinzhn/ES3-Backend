
 # Protótipo
Segue o link do protótipo: [protótipo](https://app.quant-ux.com/#/test.html?h=a2aa10anCANXolYynMFNqLRy6n4yeVlMO9c2sAT9gx1UN5y0WDInbOmeIfke&ln=en)
 # ES3-Backend
 O meu projeto foi implementado utilizando a linguagem Java em conjunto com o framework Spring Boot e Maven.

 `./src/main/` Este é o diretório onde o código-fonte  é armazenado. 
 
`../controllers` armazena as classes que são responsáveis por lidar com o acesso e a manipulação de um banco de dados.

`../models` define as entidades principais, também tem os DTOs que são usadas para transferir informações entre diferentes camadas da aplicação e validar uma entrada de dados para banco de dados. 
`../services`
contém a lógica de negócios e coordena as operações entre a camada do Controller e o Repository.

`../controllers` é a camada responsável por lidar com as requisições HTTP.

`./resources/application.properties` define a configuração da conexão de banco de dados. Ela conecta a um banco de dados MySQL chamado vendaControle, no endereço localhost:3307 usando o nome e a senha do banco de dados.

