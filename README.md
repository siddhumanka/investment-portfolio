# investment-portfolio

### Description

1. This service calculates portfolio based on provided risk level.

2. This service calculates investment portfolio based on provided monthly contribution, risk level, from and to date.

### API Version

There is no versioning as of now

## Application

In order to start the application please run :
`./gradlew bootRun`

In order to run the tests please run :
`./gradlew test`

## Possible requests

### Postman :

1. To find out the portfolio based on user's risk level :
   Send a GET request on
   `http://localhost:8080/users/me/investment-portfolio?riskLevel=<risk_level>`

   provided a valid risk level user gets a json response with code 200 as shown below:

```json
{
  "portfolio": [
    {
      "weight": 1.0,
      "ticker": "CAKE"
    },
    {
      "weight": 0.0,
      "ticker": "PZZA"
    },
    {
      "weight": 0.0,
      "ticker": "EAT"
    }
  ]
}
```

### Curl :

`curl --location --request GET 'localhost:8080/users/me/investment-portfolio?riskLevel=10' \
--header 'Content-Type: application/json'`

Possible error response is 400

2. To find out the portfolio based on user's risk level :
   Send a POST request on

### Postman :

1.

`localhost:8080/users/me/investment-portfolio/current-value`
with body

```json
{
  "from": "<from_date>",
  "to": "<to_date>",
  "monthlyContribution": 450.00,
  "riskLevel": "<risk_level>"
}
```

provided a valid request user gets a json response with code 200 as shown below:

```json
{
  "currentPortfolio": [
    {
      "ticker": "CAKE",
      "currentValue": 22059.781332144146
    },
    {
      "ticker": "PZZA",
      "currentValue": 7457.607124418326
    },
    {
      "ticker": "EAT",
      "currentValue": 5393.252561873633
    }
  ],
  "totalInvestment": 24300.0
}
```

### Curl :

`curl --location --request POST 'localhost:8080/users/me/investment-portfolio/current-value' \
--header 'Content-Type: application/json' \
--data-raw '{
"from" : "01.01.2017",
"to" : "09.03.2017",
"monthlyContribution" : 80.00,
"riskLevel" : "2"
}'`

Possible error response is 400
