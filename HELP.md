## Tools
- manven
- docker-compose
- java 21
- swagger

## Steps
- 1 acessar a raiz do projeto e executar o comando abaixo:
```
- docker-compose up -d
```

- 2 acessar a raiz do projeto e executar o comando abaixo:
```
mvn spring-boot:run
```
- 3 Acessar o link abaixo:
  * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

4 criar a primeira conta
curl -X 'POST' \
'http://localhost:8080/accounts' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"accountNumber": "1",
"holderName": "Marco"
}'


5 criar a segunda conta
curl -X 'POST' \
'http://localhost:8080/accounts' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"accountNumber": "2",
"holderName": "Claudemir"
}'