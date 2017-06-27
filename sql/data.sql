
insert into subject values(1,1,null);
insert into subject values(2,2,null);
insert into subject values(3,3,null);
insert into subject values(4,4,null);
insert into subject values(5,5,null);
--idをfor文で回してデータをupdate句で挿入

insert into sphere values(1,1,null,1);
insert into sphere values(2,2,null,1);
insert into sphere values(3,3,null,1);
insert into sphere values(4,4,null,1);
insert into sphere values(5,5,null,1);
insert into sphere values(6,6,null,1);
insert into sphere values(7,7,null,1);
insert into sphere values(8,8,null,1);
insert into sphere values(9,9,null,1);
insert into sphere values(10,10,null,1);

insert into sphere values(11,1,null,2);
insert into sphere values(12,2,null,2);
insert into sphere values(13,3,null,2);
insert into sphere values(14,4,null,2);
insert into sphere values(15,5,null,2);
insert into sphere values(16,6,null,2);
insert into sphere values(17,7,null,2);
insert into sphere values(18,8,null,2);
insert into sphere values(19,9,null,2);
insert into sphere values(20,10,null,2);

insert into sphere values(21,1,null,3);
insert into sphere values(22,2,null,3);
insert into sphere values(23,3,null,3);
insert into sphere values(24,4,null,3);
insert into sphere values(25,5,null,3);
insert into sphere values(26,6,null,3);
insert into sphere values(27,7,null,3);
insert into sphere values(28,8,null,3);
insert into sphere values(29,9,null,3);
insert into sphere values(30,10,null,3);

insert into sphere values(41,1,null,4);
insert into sphere values(42,2,null,4);
insert into sphere values(43,3,null,4);
insert into sphere values(44,4,null,4);
insert into sphere values(45,5,null,4);
insert into sphere values(46,6,null,4);
insert into sphere values(47,7,null,4);
insert into sphere values(48,8,null,4);
insert into sphere values(49,9,null,4);
insert into sphere values(50,10,null,4);

insert into sphere values(51,1,null,5);
insert into sphere values(52,2,null,5);
insert into sphere values(53,3,null,5);
insert into sphere values(54,4,null,5);
insert into sphere values(55,5,null,5);
insert into sphere values(56,6,null,5);
insert into sphere values(57,7,null,5);
insert into sphere values(58,8,null,5);
insert into sphere values(59,9,null,5);
insert into sphere values(60,10,null,5);

insert into countId values(1);
insert into countId values(2);
insert into countId values(3);
insert into countId values(4);
insert into countId values(5);
insert into countId values(6);
insert into countId values(7);
insert into countId values(8);
insert into countId values(9);
insert into countId values(10);
insert into countId values(11);
insert into countId values(12);
insert into countId values(13);
insert into countId values(14);
insert into countId values(15);
insert into countId values(16);
insert into countId values(17);
insert into countId values(18);
insert into countId values(19);
insert into countId values(20);
insert into countId values(21);
insert into countId values(22);
insert into countId values(23);
insert into countId values(24);
insert into countId values(25);
insert into countId values(26);
insert into countId values(27);
insert into countId values(28);
insert into countId values(29);
insert into countId values(30);

/*idをfor文で回してデータをupdate句で挿入、subjectにラジオボタンを持たせ、ラジオボタンが押されることでidを返すようにする。
 * 副問い合わせを用いて(select subno from subject where subname=('name'))subnoを挿入
 */
