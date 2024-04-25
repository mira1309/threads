--Получить всех студентов, возраст которых находится между 10 и 20
select * from student s where age > 10 and age < 20;

--Получить всех студентов, но отобразить только список их имен.
select name from student s;

--Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).
select name from student s where name like '%o%';

--Получить всех студентов, у которых возраст меньше идентификатора.
select * from student s where age < id;

--Получить всех студентов упорядоченных по возрасту.
select  * from student s order by age;