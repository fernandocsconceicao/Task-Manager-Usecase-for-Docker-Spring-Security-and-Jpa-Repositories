Para executar o projeto é necessário instalar e executar o Docker

Rode os seguintes comando no terminal deste projeto

docker build -t alpha7 .
docker compose up

Caso não execute o projeto

docker run --name alpha7 alpha7 -p 8080:8080

Para testar com Postman importe a coleção "postman.json"

Portas livres:
3306 - Mysql
8080- Backend

A imagem imagem do diagrama da aplicação tem nome de "TaskManagerDiagram.png" na raiz do projeto
