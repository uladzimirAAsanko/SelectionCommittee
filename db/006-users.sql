create table users
(
	idusers int auto_increment,
	first_name varchar(45) not null,
	last_name varchar(45) not null,
	fathers_name varchar(45) not null,
	login varchar(45) not null,
	password varchar(100) null,
	email varchar(45) null,
	id_role int not null,
	avatar_picture varchar(150) null,
	id_status int null,
	constraint idusers_UNIQUE
		unique (idusers),
	constraint fk_users_admin_info1
		foreign key (id_role) references roles (id_role),
	constraint users_ibfk_1
		foreign key (id_status) references status (id_status)
);

create index fk_users_admin_info1_idx
	on users (id_role);

create index id_status
	on users (id_status);

alter table users
	add primary key (idusers);

