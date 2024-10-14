CREATE TABLE product (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          descripcion TEXT,
                          precio DECIMAL(10, 2) NOT NULL
);
INSERT INTO product (nombre, descripcion, precio) VALUES
('Laptop', 'Laptop de alta gama', 1500.00),
('Smartphone', 'Teléfono inteligente con cámara avanzada', 899.99),
('Tablet', 'Tablet con pantalla de 10 pulgadas', 499.50),
('Monitor', 'Monitor Full HD de 24 pulgadas', 249.99),
('Teclado', 'Teclado mecánico retroiluminado', 99.99),
('Ratón', 'Ratón inalámbrico ergonómico', 39.99),
('Auriculares', 'Auriculares con cancelación de ruido', 199.99),
('Impresora', 'Impresora multifunción con Wi-Fi', 149.99),
('Disco Duro', 'Disco duro externo de 1TB', 89.99),
('Cámara', 'Cámara digital con lente intercambiable', 1200.00);
