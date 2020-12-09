create table admin_codes
(
	code varchar(100) not null
		primary key,
	faculty_info_faculty_id_faculty int not null,
	constraint fk_admin_code_code1
		foreign key (faculty_info_faculty_id_faculty) references faculty (id_faculty)
);

