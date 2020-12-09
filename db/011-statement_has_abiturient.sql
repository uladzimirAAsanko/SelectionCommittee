create table statement_has_abiturient
(
	record_id int auto_increment,
	statement_id_statement int not null,
	abiturient_id_abiturient int not null,
	constraint record_id
		unique (record_id),
	constraint fk_statement_has_abiturient_abiturient1
		foreign key (abiturient_id_abiturient) references abiturient_info (users_idusers),
	constraint fk_statement_has_abiturient_statement1
		foreign key (statement_id_statement) references statement (statement_id)
);

create index fk_statement_has_abiturient_abiturient1_idx
	on statement_has_abiturient (abiturient_id_abiturient);

create index fk_statement_has_abiturient_statement1_idx
	on statement_has_abiturient (statement_id_statement);

alter table statement_has_abiturient
	add primary key (record_id);

