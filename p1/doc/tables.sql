CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(200),
	last_name VARCHAR(200),
	username VARCHAR(200) UNIQUE,
	password VARCHAR(200)
);

CREATE TABLE managers(
    manager_id SERIAL PRIMARY KEY,
    first_name VARCHAR(200),
	last_name VARCHAR(200),
	username VARCHAR(200) UNIQUE,
	password VARCHAR(200)
);

CREATE TABLE expenses(
    expense_id SERIAL PRIMARY KEY,
    title VARCHAR(200),
    description VARCHAR(200) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(200),
    user_id INT,
    CONSTRAINT fk_expenses_users FOREIGN KEY (user_id) REFERENCES users(user_id)
)
