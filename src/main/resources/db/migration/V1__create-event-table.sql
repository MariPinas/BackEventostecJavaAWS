CREATE TABLE events (
                        id CHAR(36) PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        description VARCHAR(250) NOT NULL,
                        image_url VARCHAR(255) NOT NULL,
                        event_url VARCHAR(255) NOT NULL,
                        date TIMESTAMP NOT NULL,
                        remote BOOLEAN NOT NULL
);