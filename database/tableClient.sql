CREATE TABLE client (
                         id SERIAL PRIMARY KEY,
                         nombre VARCHAR(255) NOT NULL,
                         direccion TEXT
);
INSERT INTO client (nombre, direccion) VALUES
                                            ('Juan Perez', 'Calle 123, Ciudad A'),
                                            ('María Garcia', 'Avenida Siempre Viva 742, Ciudad B'),
                                            ('Carlos López', 'Calle Falsa 123, Ciudad C'),
                                            ('Ana Martinez', 'Avenida Central 456, Ciudad D'),
                                            ('Pedro Jiménez', 'Calle Nueva 789, Ciudad E'),
                                            ('Laura Torres', 'Plaza Mayor 987, Ciudad F'),
                                            ('José Ramírez', 'Avenida del Sol 321, Ciudad G'),
                                            ('Marta Rodríguez', 'Calle del Río 654, Ciudad H'),
                                            ('Luis Sánchez', 'Boulevard Principal 159, Ciudad I'),
                                            ('Lucía Gómez', 'Calle del Bosque 753, Ciudad J');
