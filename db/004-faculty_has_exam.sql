create table faculty_has_exam
(
	record_id int auto_increment,
	faculty_id_faculty int not null,
	exam_id_exam int not null,
	minimal_score int not null,
	constraint record_id
		unique (record_id),
	constraint fk_faculty_has_exam_exam1
		foreign key (exam_id_exam) references exam (id_exam),
	constraint fk_faculty_has_exam_faculty1
		foreign key (faculty_id_faculty) references faculty (id_faculty)
);

create index fk_faculty_has_exam_exam1_idx
	on faculty_has_exam (exam_id_exam);

create index fk_faculty_has_exam_faculty1_idx
	on faculty_has_exam (faculty_id_faculty);

alter table faculty_has_exam
	add primary key (record_id);

