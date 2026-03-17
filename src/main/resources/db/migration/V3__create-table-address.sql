CREATE TABLE address (
                         id CHAR(36) PRIMARY KEY,
                         city VARCHAR(100) NOT NULL,
                         uf VARCHAR(100) NOT NULL,
                         event_id CHAR(36),
                         FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
);