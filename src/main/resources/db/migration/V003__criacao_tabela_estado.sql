create table estado (
	id bigint not null auto_increment ,
	nome varchar(80) not null ,
	sigla varchar(2) not null ,
	codigo_ibge integer not null ,
	primary key (id) 
) engine=InnoDB default charset=utf8;