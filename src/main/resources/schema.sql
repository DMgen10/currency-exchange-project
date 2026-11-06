
CREATE TABLE Currencies (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR NOT NULL,
    FullName VARCHAR NOT NULL,
    Sign VARCHAR NOT NULL
);

CREATE UNIQUE INDEX idx_currencies_code ON Currencies (Code);

CREATE TABLE ExchangeRates (
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    BaseCurrencyId INTEGER NOT NULL,
    TargetCurrencyId INTEGER NOT NULL,
    Rate DECIMAL(6) NOT NULL,
    FOREIGN KEY (BaseCurrencyId) REFERENCES Currencies(ID),
    FOREIGN KEY (TargetCurrencyId) REFERENCES Currencies(ID)
);

CREATE UNIQUE INDEX idx_exchange_pair ON ExchangeRates (BaseCurrencyId, TargetCurrencyId);

INSERT INTO Currencies(Code, FullName, Sign) VALUES
('USD', 'Unites States dollar', '$'),
('EUR','Euro','€'),
('RUB','Russian Ruble', '₽'),
('AUD', 'Australian dollar', 'A$');

INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES
(1,2,0.92),
(1,3,90),
(1,4,1.52)
