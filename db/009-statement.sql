create table statement
(
	statement_id int auto_increment,
	number_of_max_students int not null,
	created_at date not null,
	expired_at date not null,
	faculty_id_faculty int not null,
	admin_info_users_idusers int not null,
	constraint admin_info_users_idusers
		unique (admin_info_users_idusers),
	constraint faculty_id_faculty
		unique (faculty_id_faculty),
	constraint statement_id
		unique (statement_id),
	constraint fk_statement_admin_info1
		foreign key (admin_info_users_idusers) references admin_info (users_idusers),
	constraint fk_statement_faculty1
		foreign key (faculty_id_faculty) references faculty (id_faculty)
);

create index statement_idx
	on statement (statement_id);

alter table statement
	add primary key (statement_id);

