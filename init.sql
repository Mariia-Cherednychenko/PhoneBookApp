CREATE DATABASE contacts;
DROP TABLE contacts;
CREATE TABLE contacts
(
    contact_id    INT PRIMARY KEY,
    contact_name  VARCHAR(255) NOT NULL,
    contact_value VARCHAR(255) NOT NULL,
    contact_type  VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    user_id        SERIAL PRIMARY KEY,
    user_name      VARCHAR(255) NOT NULL,
    user_password  VARCHAR(255) NOT NULL,
    user_login     VARCHAR(255) NOT NULL,
    user_date_born VARCHAR(255) NOT NULL
);

CREATE TABLE users_contacts
(
    user_id    INT NOT NULL,
    contact_id INT NOT NULL,
    PRIMARY KEY (user_id, contact_id),

    CONSTRAINT fk_user_has_contact
        FOREIGN KEY (user_id)
            REFERENCES users (user_id) ON UPDATE NO ACTION,
    CONSTRAINT fk_contact_in_user
        FOREIGN KEY (contact_id)
            REFERENCES contacts (contact_id) ON UPDATE NO ACTION
);