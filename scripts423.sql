--Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах (достаточно получить только имя и возраст студента)
--школы Хогвартс вместе с названиями факультетов.

SELECT s.name, s.age, f.name
FROM student s
JOIN faculty f ON (s.faculty_id = f.id);

--Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.

SELECT *
FROM student s
JOIN avatar a ON (s.id = a.student_id);