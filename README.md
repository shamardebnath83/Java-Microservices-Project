
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


