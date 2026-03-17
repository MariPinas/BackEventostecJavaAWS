CREATE TABLE coupon (
                        id CHAR(36) PRIMARY KEY,
                        code VARCHAR(100) NOT NULL,
                        discount INT NOT NULL,
                        valid TIMESTAMP NOT NULL,
                        event_id CHAR(36),
                        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);