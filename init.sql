CREATE TABLE products (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         alcohol_degree NUMERIC(3, 1) NOT NULL,
                         capacity NUMERIC(5, 2) NOT NULL,
                         price_ht NUMERIC(10, 2) NOT NULL,
                         stock INTEGER NOT NULL
);

CREATE TABLE users (
                        id SERIAL PRIMARY KEY,
                        last_name VARCHAR(255) NOT NULL,
                        first_name VARCHAR(255) NOT NULL,
                        address VARCHAR(255) NOT NULL,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        phone VARCHAR(20) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        is_admin BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE payments (
                         id SERIAL PRIMARY KEY,
                         provider VARCHAR(50) NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         amount NUMERIC(10, 2) NOT NULL,
                         label VARCHAR(255),
                         accounted BOOLEAN NOT NULL DEFAULT false,
                         date DATE NOT NULL
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        client INTEGER NOT NULL REFERENCES users(id),
                        delivery_address VARCHAR(255) NOT NULL,
                        billing_address VARCHAR(255) NOT NULL,
                        total_ht NUMERIC(10, 2) NOT NULL,
                        total_ttc NUMERIC(10, 2) NOT NULL,
                        payment INTEGER REFERENCES payments(id)
);

CREATE TABLE orders_products (
                               order_id INTEGER NOT NULL REFERENCES orders(id),
                               product_id INTEGER NOT NULL REFERENCES products(id),
                               quantity INTEGER NOT NULL,
                               PRIMARY KEY (order_id, product_id)
);
