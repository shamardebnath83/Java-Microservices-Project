Microservices Project – Run & Test Guide
🌐 1. Start Naming Server (Eureka)

Open in browser:

http://localhost:8761/

👉 This is your Service Registry Dashboard
✔ Make sure all services are registered here

🗄️ 2. Insert Data into H2 Database

Run these SQL queries in H2 Console:

INSERT INTO exchange_value (id, from_currency, to_currency, conversion_multiple, port)
VALUES (1, 'USD', 'BDT', 120, 0);

INSERT INTO exchange_value (id, from_currency, to_currency, conversion_multiple, port)
VALUES (2, 'EUR', 'BDT', 120, 0);
🧪 3. API Testing
✅ Currency Exchange Service (Direct)
http://localhost:8000/currency-exchange/from/USD/to/BDT
http://localhost:8000/currency-exchange/from/EUR/to/BDT
✅ Currency Conversion Service (Direct)
http://localhost:8100/currency-converter/from/USD/to/BDT/quantity/10
http://localhost:8100/currency-converter/from/EUR/to/BDT/quantity/10
✅ API Gateway (Recommended)
http://localhost:8081/currency-exchange-service/currency-exchange/from/USD/to/BDT
http://localhost:8081/currency-exchange-service/currency-exchange/from/EUR/to/BDT
🏗️ System Architecture Diagram
                    ┌──────────────────────┐
                    │   Client / Browser   │
                    │      Postman         │
                    └─────────┬────────────┘
                              │
                              ▼
                    ┌──────────────────────┐
                    │     API Gateway      │
                    │     (Port: 8081)     │
                    └─────────┬────────────┘
                              │
             ┌────────────────┴────────────────┐
             │                                 │
             ▼                                 ▼
 ┌──────────────────────┐          ┌────────────────────────┐
 │ Currency Conversion  │          │ Currency Exchange      │
 │ Service (Port: 8100) │          │ Service (Port: 8000)   │
 │ Feign Client         │          │ JPA + H2 DB            │
 └─────────┬────────────┘          └─────────┬──────────────┘
           │                                 │
           │  Feign REST Call                │
           └──────────────►──────────────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │    H2 Database   │
                     │  (In-Memory DB)  │
                     └──────────────────┘

                    ▲
                    │
                    │ Service Registration
                    │
          ┌─────────┴────────────┐
          │   Naming Server      │
          │   (Eureka - 8761)    │
          └──────────────────────┘
🔄 Request Flow (Step-by-Step)
✅ Step 1: Client Request
http://localhost:8081/currency-conversion-service/currency-converter/from/USD/to/BDT/quantity/10
✅ Step 2: API Gateway
Receives request
Uses Eureka Service Discovery
Routes to Conversion Service
✅ Step 3: Currency Conversion Service
Endpoint:
/currency-converter/from/{from}/to/{to}/quantity/{qty}
Calls Exchange Service via Feign Client
✅ Step 4: Currency Exchange Service
Endpoint:
/currency-exchange/from/USD/to/BDT
Fetches data from H2 Database
✅ Step 5: Final Response

Flow:

Exchange → Conversion → Gateway → Client

✔ Response includes:

Conversion Rate
Quantity
Total Amount
🔗 Service Responsibilities
🟢 Naming Server (Eureka)
Registers all services
URL:
http://localhost:8761
🔵 API Gateway
Single entry point
Routing + Load balancing
Config:
spring.cloud.gateway.server.webflux.discovery.locator.enabled=true
🟡 Currency Exchange Service
Provides exchange rates
Uses:
Spring Data JPA
H2 Database
🟣 Currency Conversion Service
Handles business logic
Calls Exchange Service using Feign
Calculates:
total = quantity × conversionMultiple
✅ Final Notes

✔ Always start services in this order:

Naming Server
Exchange Service
Conversion Service
API Gateway

✔ Always verify services in Eureka dashboard

✔ Use API Gateway for real microservice flow
