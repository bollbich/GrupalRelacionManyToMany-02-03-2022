INSERT INTO articulos (descripcion, imagen, nombre, precio_unidad, stock, stock_seguridad) VALUES ('cosa', NULL, 'raton', 12.45, 23, 5);
INSERT INTO articulos (descripcion, imagen, nombre, precio_unidad, stock, stock_seguridad) VALUES ('hola', NULL, 'balon', 1.45, 50, 5);
INSERT INTO articulos (descripcion, imagen, nombre, precio_unidad, stock, stock_seguridad) VALUES ('adios', NULL, 'lapiz', 11.32, 60, 5);

INSERT INTO cliente (apellido, cp, empresa, fecha_nacimiento, nombre, provincia, puesto, telefono) VALUES ('lopez Garcia', 33300, 'Capgemini', '2022-03-02', 'Raul', 'Asturias', 'Portero', 664738392);
INSERT INTO cliente (apellido, cp, empresa, fecha_nacimiento, nombre, provincia, puesto, telefono) VALUES ('Fernandez obidio', 33300, 'Capgemini', '2022-03-02', 'Radegundo', 'Valladolid', 'Administrativo', 657483902);


INSERT INTO compras (fecha, unidades, cod_articulo, cod_cliente) VALUES ('2022-03-02', 12, 1, 1);
INSERT INTO compras (fecha, unidades, cod_articulo, cod_cliente) VALUES ('2021-01-23', 1, 3, 1);
