create table ocorrencia (
	id bigint not null AUTO_INCREMENT,
	entrega_id bigint not null,
	descricao text not null,
	data_registro datetime not null,
	
	primary key (id)
	);
	
	alter table ocorrencia add constraint fk_ocorrencia_entega
	FOREIGN KEY (entrega_id) references entrega (id);