create table cliente (
    id bigint not null AUTO_INCREMENT,
    nome varchar (255) not null,
    telefone varchar (20) not null,
    email varchar(255) not null,
    
    PRIMARY key (id))