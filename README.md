Run this port on Browser:

http://localhost:8761/


Database Insert:

INSERT INTO exchange_value (id, from_currency, to_currency, conversion_multiple, port)
VALUES (1, 'USD', 'BDT', 120, 0);

INSERT INTO exchange_value (id, from_currency, to_currency, conversion_multiple, port)
VALUES (2, 'EUR', 'BDT', 120, 0);

TEST FULL FLOW:

✅ Exchange API
http://localhost:8000/currency-exchange/from/USD/to/BDT

http://localhost:8000/currency-exchange/from/EUR/to/BDT

✅ Conversion API
http://localhost:8100/currency-converter/from/USD/to/BDT/quantity/10

http://localhost:8100/currency-converter/from/EUR/to/BDT/quantity/10

✅ Gateway
http://localhost:8081/currency-exchange-service/currency-exchange/from/EUR/to/BDT

http://localhost:8081/currency-exchange-service/currency-exchange/from/USD/to/BDT


                        ┌──────────────────────┐
                        │      Client /        │
                        │   Browser / Postman  │
                        └─────────┬────────────┘
                                  │
                                  ▼
                        ┌──────────────────────┐
                        │    API Gateway       │
                        │   (Port: 8081)       │
                        │ Spring Cloud Gateway │
                        └─────────┬────────────┘
                                  │
                 ┌────────────────┴────────────────┐
                 │                                 │
                 ▼                                 ▼
     ┌──────────────────────┐          ┌────────────────────────┐
     │ Currency Conversion  │          │ Currency Exchange      │
     │ Service              │          │ Service                │
     │ (Port: 8100)         │          │ (Port: 8000)           │
     │ Feign Client         │          │ JPA + H2 DB            │
     └─────────┬────────────┘          └─────────┬──────────────┘
               │                                 │
               │  REST Call (Feign)              │
               └──────────────►──────────────────┘
                                  │
                                  ▼
                         ┌──────────────────┐
                         │     H2 Database  │
                         │ (In-Memory DB)   │
                         └──────────────────┘


                        ▲
                        │
                        │ Service Registration
                        │
              ┌─────────┴────────────┐
              │   Naming Server      │
              │   (Eureka Server)    │
              │   Port: 8761         │
              └──────────────────────┘


Request Flow (Step-by-Step)
✅ 1. Client Request

User calls:

http://localhost:8081/currency-conversion-service/currency-converter/from/USD/to/BDT/quantity/10
✅ 2. API Gateway
Receives request
Uses service discovery (Eureka)
Routes to Currency Conversion Service
✅ 3. Currency Conversion Service
Endpoint:
/currency-converter/from/{from}/to/{to}/quantity/{qty}
Calls Currency Exchange Service using Feign:
proxy.getRate(from, to);
✅ 4. Currency Exchange Service
Endpoint:
/currency-exchange/from/USD/to/BDT
Fetches data from H2 Database
Returns conversion rate
✅ 5. Response Flow Back
Exchange → Conversion → Gateway → Client
Final response includes:
rate
quantity
total amount
🔗 Service Responsibilities
🟢 Naming Server (Eureka)
Registers all services
URL:
http://localhost:8761
🔵 API Gateway
Single entry point
Load balancing + routing
Uses:
spring.cloud.gateway.server.webflux.discovery.locator.enabled=true
🟡 Currency Exchange Service
Provides exchange rates
Uses:
Spring Data JPA
H2 Database
🟣 Currency Conversion Service
Business logic
Calls Exchange Service via Feign
Calculates:
total = qty * conversionMultiple

