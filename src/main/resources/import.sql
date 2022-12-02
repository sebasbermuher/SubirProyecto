INSERT INTO reservas_de_pistas.usuarios
(id, apellido1, apellido2, email, fecha_nacimiento, fecha_registro, localidad, nif, nombre, password, provincia, `role`, sexo, telefono, username)
VALUES(1, 'apellido1', 'apellido2', 'admin@gmail.com', '1999-07-10', '25/11/2022', 'Tomares', '00000001A', 'admin', '$2a$15$atTFmGG9dR.wAlpYyrIZ1uWL812Wv54mH241NEeVEu85lUjNEw0ne', 'Sevilla', 'ROLE_ADMIN', 'Hombre', '661000000', 'admin');

INSERT INTO reservas_de_pistas.pistas
(id, apertura, cierre, deporte, nombre)
VALUES(1, '10:00', '22:00', 'Padel', 'Pista Central');
