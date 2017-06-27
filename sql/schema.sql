drop table subject if exists;
create table subject(id int constraint number_pk primary key,number int, name varchar(20));

drop table sphere if exists;
create table sphere(id int constraint number_pk primary key, number int , name varchar(20),subject_number int);

drop table word if exists;
create table word (id int constraint number_pk primary key,name varchar(30),mean varchar(150),subject_number int,sphere_number int,checked boolean);

drop table countId if exists;
create table countId (id int constraint number_pk primary key);
