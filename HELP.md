## Tools
- manven
- docker-compose
- java 21
- swagger

## Steps
- 1 access the project root C:\Users\Marcos\Desktop\bank-service-main\src\main\resources and execute the command below:
```
docker-compose up -d
```

- 2 access the project root C:\Users\Marcos\Desktop\bank-service-main and execute the command below:
```
mvn clean install
mvn spring-boot:run
```
- 3 Access the link below:
    * [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


- 4 create first account
```  
curl -X 'POST' \
'http://localhost:8080/accounts' \
-H 'accept: application/json' \
-H 'Content-Type: application/json' \
-d '{
"accountNumber": "1",
"holderName": "Marco"
}'
```

- 5 create second account

```  
curl -X 'POST' \
'http://localhost:8080/accounts' \
 -H 'accept: application/json' \
 -H 'Content-Type: application/json' \
 -d '{
 "accountNumber": "1",
 "holderName": "Claudemir"
}'
``` 

- 6 make a deposit into one of the accounts

``` 
  curl -X 'PUT' \
  'http://localhost:8080/transactions/deposit-funds' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "accountNumber": "1",
  "amount": 100,
  "description": "Deposito"
  }'
``` 

- 7 transfer the account with balance to the recipient
  curl -X 'PUT' \

``` 
  'http://localhost:8080/transactions/transfer-funds' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "sourceAccount": "1",
  "destinationAccount": "2",
  "amount": 50,
  "description": "TED"
  }'
``` 

- 8 make a withdrawal from one of the accounts

```
  curl -X 'PUT' \
  'http://localhost:8080/transactions/withdraw-funds' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "accountNumber": "2",
  "amount": 20,
  "description": "Marcos"
  }'
``` 

- 9 check account balance

``` 
  curl -X 'GET' \
  'http://localhost:8080/accounts/1/balance' \
  -H 'accept: application/json'
``` 