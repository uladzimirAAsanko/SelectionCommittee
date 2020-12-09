create table admin_info
(
	users_idusers int not null
		primary key,
	faculty_id int null,
	constraint fk_admin_info_users1
		foreign key (users_idusers) references users (idusers)
);

create index fk_admin_info_users1_idx
	on admin_info (users_idusers);

