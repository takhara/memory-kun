drop table subject if exists;
create table subject(id int constraint number_pk primary key, name varchar(10));

drop table sphere if exists;
create table sphere(id int , number int , name varchar(10),subject_number int);

drop table word if exists;
create table word(id int ,name varchar(30) constraint name_unique UNIQUE,mean varchar(150),subject_number int,sphere_number int);

