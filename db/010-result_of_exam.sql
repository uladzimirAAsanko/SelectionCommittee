create table result_of_exam
(
	result int not null,
	exam_id_exam int not null,
	abiturient_info_users_idusers int not null,
	result_of_exam_id int auto_increment
		primary key,
	constraint fk_result_of_exam_abiturient_info1
		foreign key (abiturient_info_users_idusers) references abiturient_info (users_idusers),
	constraint fk_result_of_exam_exam1
		foreign key (exam_id_exam) references exam (id_exam)
);

create index fk_result_of_exam_exam1_idx
	on result_of_exam (exam_id_exam);

