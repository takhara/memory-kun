
insert into countId values(1);
insert into countSubjectId values(1);
insert into countSphereId values(1);

/*idをfor文で回してデータをupdate句で挿入、subjectにラジオボタンを持たせ、ラジオボタンが押されることでidを返すようにする。
 * 副問い合わせを用いて(select subno from subject where subname=('name'))subnoを挿入
 */
