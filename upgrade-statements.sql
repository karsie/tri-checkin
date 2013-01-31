create view vw_bad_ids as
select id from (select id, count(distinct sorting) as numrows from WeekReport_eatingIn group by id) where numrows = 1;

create table tmp (id int, second tinyint / long, sorting int);

insert into tmp
select * from (select id, eatingIn, @rownum:=@rownum+1 from WeekReport_eatingIn y, (select @rownum:=-1) r where id in (select id from vw_bad_ids)) t;

delete from WeekReport_eatingIn where id in (select id from vw_bad_ids);

insert into WeekReport_eatingIn select * from tmp;

drop table tmp;

drop view vw_bad_ids;