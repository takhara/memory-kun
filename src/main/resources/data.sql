
insert into subject values(1,null);
insert into subject values(2,null);
insert into subject values(3,null);

--idをfor文で回してデータをupdate句で挿入

insert into sphere values(1,1,null,1);
insert into sphere values(2,2,null,1);
insert into sphere values(3,3,null,1);
insert into sphere values(4,4,null,1);
insert into sphere values(5,5,null,1);
insert into sphere values(6,6,null,1);

insert into sphere values(7,1,null,2);
insert into sphere values(8,2,null,2);
insert into sphere values(9,3,null,2);
insert into sphere values(10,4,null,2);
insert into sphere values(11,5,null,2);
insert into sphere values(12,6,null,2);

insert into sphere values(13,1,null,3);
insert into sphere values(14,2,null,3);
insert into sphere values(15,3,null,3);
insert into sphere values(16,4,null,3);
insert into sphere values(17,5,null,3);
insert into sphere values(18,6,null,3);
/*idをfor文で回してデータをupdate句で挿入、subjectにラジオボタンを持たせ、ラジオボタンが押されることでidを返すようにする。
 * 副問い合わせを用いて(select subno from subject where subname=('name'))subnoを挿入
 */
