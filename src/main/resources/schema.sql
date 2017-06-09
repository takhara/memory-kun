drop table subject if exists;
create table subject(subno int constraint sub_pk primary key, subname varchar(10));

drop table sphere if exists;
create table sphere(sphno int , sphname varchar(10),subno int);

drop table word if exists;
create table word(wordno int,word varchar(15),mean varchar(50),subno int,shpno int);
