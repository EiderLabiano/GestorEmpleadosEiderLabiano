create database gestorEmpleados;
use gestorEmpleados;
create table Empleado(
id int primary key auto_increment,
nombre varchar(50) not null,
puesto varchar(50) not null,
salario int not null,
fecha date not null
);