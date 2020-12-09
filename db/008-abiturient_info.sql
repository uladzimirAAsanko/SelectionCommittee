create table abiturient_info
(
	users_idusers int not null
		primary key,
	certificate int not null,
	additional_info varchar(45) null,
	constraint fk_abiturient_info_users
		foreign key (users_idusers) references users (idusers)
);

create index fk_abiturient_info_users_idx
	on abiturient_info (users_idusers);

