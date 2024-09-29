

学院：省级示范性软件学院

题目：《 作业4： sql练习》

姓名：焦买涛

学号：2200770114

班级：软工2203

日期：2024-10-10



***

# sql练习

## 员工

1. 查询所有员工的姓名、邮箱和工作岗位。

 ```SELECT first_name, last_name, email, job_title FROM employees;```
2. 查询所有部门的名称和位置。

```sql
SELECT dept_name, location FROM departments;
```
3. 查询工资超过70000的员工姓名和工资。

```sql
SELECT first_name, last_name, salary FROM employees WHERE salary > 70000;

```
4. 查询IT部门的所有员工。

```sql
SELECT e.first_name, e.last_name, e.email, e.job_title
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
WHERE d.dept_name = 'IT';

```
5. 查询入职日期在2020年之后的员工信息。

```sql
SELECT *
FROM employees
WHERE hire_date > '2020-01-01';

```
6. 计算每个部门的平均工资。

```sql

SELECT d.dept_name, AVG(e.salary) AS average_salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_name;
```
7. 查询工资最高的前3名员工信息。

```sql
SELECT *
FROM employees
ORDER BY salary DESC
LIMIT 3;

```
8. 查询每个部门员工数量。

```sql
SELECT d.dept_name, COUNT(e.emp_id) AS number_of_employees
FROM departments d
LEFT JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_name;

```
9. 查询没有分配部门的员工。

```sql
SELECT *
FROM employees
WHERE dept_id IS NULL;

```
10. 查询参与项目数量最多的员工。

```sql
SELECT e.first_name, e.last_name, COUNT(ep.project_id) AS project_count
FROM employees e
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id
ORDER BY project_count DESC
LIMIT 1;

```
11. 计算所有员工的工资总和。

```sql
SELECT SUM(salary) AS total_salary
FROM employees;

```
12. 查询姓"Smith"的员工信息。

```sql
SELECT *
FROM employees
WHERE last_name = 'Smith';

```
13. 查询即将在半年内到期的项目。

```sql
SELECT *
FROM projects
WHERE end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 6 MONTH);

```
14. 查询至少参与了两个项目的员工。

```sql
SELECT e.*
FROM employees e
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id
HAVING COUNT(ep.project_id) >= 2;

```
15. 查询没有参与任何项目的员工。

```sql
SELECT e.*
FROM employees e
LEFT JOIN employee_projects ep ON e.emp_id = ep.emp_id
WHERE ep.project_id IS NULL;

```
16. 计算每个项目参与的员工数量。

```sql
SELECT p.project_name, COUNT(ep.emp_id) AS number_of_employees
FROM projects p
LEFT JOIN employee_projects ep ON p.project_id = ep.project_id
GROUP BY p.project_id;

```
17. 查询工资第二高的员工信息。

```sql
SELECT *
FROM employees
ORDER BY salary DESC
LIMIT 1 OFFSET 1;

```
18. 查询每个部门工资最高的员工。

```sql
SELECT d.dept_name, e.*
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
WHERE (e.dept_id, e.salary) IN (
    SELECT emp_id, MAX(salary)
    FROM employees
    GROUP BY dept_id
)
ORDER BY d.dept_name;

```
19. 计算每个部门的工资总和,并按照工资总和降序排列。

```sql
SELECT d.dept_name, SUM(e.salary) AS total_salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_name
ORDER BY total_salary DESC;

```
20. 查询员工姓名、部门名称和工资。

```sql
SELECT e.first_name, e.last_name, d.dept_name, e.salary
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id;

```
21. 查询每个员工的上级主管(假设emp_id小的是上级)。

```sql
SELECT e1.emp_id, e1.first_name, e1.last_name, e2.emp_id AS manager_id, e2.first_name AS manager_name
FROM employees e1
JOIN employees e2 ON e1.dept_id = e2.dept_id AND e2.emp_id < e1.emp_id
WHERE e2.emp_id IN (
    SELECT MIN(emp_id)
    FROM employees
    GROUP BY dept_id
);

```
22. 查询所有员工的工作岗位,不要重复。

```sql
SELECT DISTINCT job_title FROM employees;

```
23. 查询平均工资最高的部门。

```sql
SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id
ORDER BY AVG(e.salary) DESC
LIMIT 1;

```
24. 查询工资高于其所在部门平均工资的员工。

```sql
SELECT e.*
FROM employees e
JOIN (
    SELECT dept_id, AVG(salary) AS avg_salary
    FROM employees
    GROUP BY dept_id
) d ON e.dept_id = d.dept_id
WHERE e.salary > d.avg_salary;

```
25. 查询每个部门工资前两名的员工。

```sql
SELECT *
FROM (
    SELECT e.*, DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS rank
    FROM employees e
) e
WHERE e.rank <= 2;

```
26. 查询跨部门的项目(参与员工来自不同部门)。

```sql
SELECT p.project_id, p.project_name
FROM projects p
JOIN employee_projects ep ON p.project_id = ep.project_id
JOIN employees e ON ep.emp_id = e.emp_id
GROUP BY p.project_id
HAVING COUNT(DISTINCT e.dept_id) > 1;

```
27. 查询每个员工的工作年限,并按工作年限降序排序。

```sql
SELECT first_name, last_name, TIMESTAMPDIFF(YEAR, hire_date, CURDATE()) AS years_of_service
FROM employees
ORDER BY years_of_service DESC;

```
28. 查询本月过生日的员工(假设hire_date是生日)。

```sql

SELECT *
FROM employees
WHERE MONTH(hire_date) = MONTH(CURDATE()) AND DAY(hire_date) = DAY(CURDATE());
```
29. 查询即将在90天内到期的项目和负责该项目的员工。

```sql
SELECT p.project_name, p.end_date, e.first_name, e.last_name
FROM projects p
JOIN employee_projects ep ON p.project_id = ep.project_id
JOIN employees e ON ep.emp_id = e.emp_id
WHERE p.end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 90 DAY);

```
30. 计算每个项目的持续时间(天数)。

```sql
SELECT project_name, end_date - start_date AS duration_days
FROM projects;

```
31. 查询没有进行中项目的部门。

```sql
SELECT d.dept_name
FROM departments d
WHERE d.dept_id NOT IN (
    SELECT DISTINCT ep.dept_id
    FROM employee_projects ep
    JOIN projects p ON ep.project_id = p.project_id
    WHERE p.end_date > CURDATE()
);

```
32. 查询员工数量最多的部门。

```sql
SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id
ORDER BY COUNT(e.emp_id) DESC
LIMIT 1;

```
33. 查询参与项目最多的部门。

```sql
SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY d.dept_id
ORDER BY COUNT(ep.project_id) DESC
LIMIT 1;

```
34. 计算每个员工的薪资涨幅(假设每年涨5%)。

```sql
SELECT first_name, last_name, salary, salary * 1.05 AS increased_salary
FROM employees;

```
35.查询入职时间最长的3名员工。

```sql
SELECT *
FROM employees
ORDER BY hire_date ASC
LIMIT 3;

```
36. 查询名字和姓氏相同的员工。

```sql

SELECT *
FROM employees
WHERE first_name = last_name;
```
37.查询每个部门薪资最低的员工。

```sql
SELECT d.dept_name, e.*
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
WHERE e.emp_id IN (
    SELECT MIN(emp_id)
    FROM employees
    GROUP BY dept_id
);

```

38. 查询哪些部门的平均工资高于公司的平均工资。

```sql
SELECT d.dept_name
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id
HAVING AVG(e.salary) > (
    SELECT AVG(salary)
    FROM employees
);

```
39. 查询姓名包含"son"的员工信息。

```sql
SELECT *
FROM employees
WHERE first_name LIKE '%son%' OR last_name LIKE '%son%';

```
40. 查询所有员工的工资级别(可以自定义工资级别)。

```sql
SELECT first_name, last_name, salary,
CASE
    WHEN salary < 30000 THEN 'Low'
    WHEN salary BETWEEN 30000 AND 60000 THEN 'Medium'
    WHEN salary > 60000 THEN 'High'
END AS salary_level
FROM employees;

```
41. 查询每个项目的完成进度(根据当前日期和项目的开始及结束日期)。

```sql

SELECT 
    project_name, 
    start_date, 
    end_date, 
    DATEDIFF(end_date, CURDATE()) AS days_left,
    DATEDIFF(end_date, start_date) AS total_days,
    (DATEDIFF(end_date, CURDATE()) / DATEDIFF(end_date, start_date)) * 100 AS completion_percentage
FROM projects
WHERE end_date > CURDATE();
```
42. 查询每个经理(假设job_title包含'Manager'的都是经理)管理的员工数量。

```sql
SELECT 
    m.first_name, 
    m.last_name, 
    COUNT(e.emp_id) AS number_of_employees
FROM employees m
JOIN employees e ON m.dept_id = e.dept_id AND m.emp_id <> e.emp_id
WHERE m.job_title LIKE '%Manager%'
GROUP BY m.emp_id;

```
43. 查询工作岗位名称里包含"Manager"但不在管理岗位(salary<70000)的员工。

```sql
SELECT *
FROM employees
WHERE job_title LIKE '%Manager%' AND salary < 70000;

```
44. 计算每个部门的男女比例(假设以名字首字母A-M为女性,N-Z为男性)。

```sql
SELECT 
    d.dept_name,
    COUNT(CASE WHEN first_name REGEXP '^[A-M]' THEN 1 END) AS female_count,
    COUNT(CASE WHEN first_name REGEXP '^[N-Z]' THEN 1 END) AS male_count,
    CONCAT(ROUND(COUNT(CASE WHEN first_name REGEXP '^[A-M]' THEN 1 END) / COUNT(*) * 100, 2), '%') AS female_percentage,
    CONCAT(ROUND(COUNT(CASE WHEN first_name REGEXP '^[N-Z]' THEN 1 END) / COUNT(*) * 100, 2), '%') AS male_percentage
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id
GROUP BY d.dept_id;

```
45.查询每个部门年龄最大和最小的员工(假设hire_date反应了年龄)。

```sql
SELECT 
    dept_id,
    MAX(hire_date) AS oldest_hire_date,
    MIN(hire_date) AS youngest_hire_date
FROM employees
GROUP BY dept_id;

```
46. 查询连续3天都有员工入职的日期。

```sql
SELECT 
    hire_date,
    COUNT(*) OVER (ORDER BY hire_date RANGE BETWEEN INTERVAL '2 day' PRECEDING AND CURRENT ROW) AS入职天数
FROM employees
GROUP BY hire_date
HAVING COUNT(*) = 3;

```
47. 查询员工姓名和他参与的项目数量。

```sql
SELECT 
    e.first_name, 
    e.last_name, 
    COUNT(ep.project_id) AS project_count
FROM employees e
JOIN employee_projects ep ON e.emp_id = ep.emp_id
GROUP BY e.emp_id;

```
48. 查询每个部门工资最高的3名员工。

```sql
SELECT *
FROM (
    SELECT e.*, DENSE_RANK() OVER (PARTITION BY e.dept_id ORDER BY e.salary DESC) AS rank
    FROM employees e
) e
WHERE e.rank <= 3;

```
49. 计算每个员工的工资与其所在部门平均工资的差值。

```sql
SELECT 
    e.first_name, 
    e.last_name, 
    e.salary,
    d.avg_salary,
    e.salary - d.avg_salary AS salary_difference
FROM employees e
JOIN (
    SELECT dept_id, AVG(salary) AS avg_salary
    FROM employees
    GROUP BY dept_id
) d ON e.dept_id = d.dept_id;

```
50. 查询所有项目的信息,包括项目名称、负责人姓名(假设工资最高的为负责人)、开始日期和结束日期。



```sql
SELECT 
    p.project_name,
    p.start_date,
    p.end_date,
    m.first_name || ' ' || m.last_name AS manager_name
FROM projects p
JOIN (
    SELECT dept_id, MAX(salary) AS max_salary
    FROM employees
    GROUP BY dept_id
) e ON p.dept_id = e.dept_id
JOIN employees m ON e.max_salary = m.salary;

```


## 学生查询


1. 查询所有学生的信息。



```sql
SELECT * FROM student;
```
2. 查询所有课程的信息。



```sql
SELECT * FROM course;
```
3. 查询所有学生的姓名、学号和班级。



```sql
SELECT name, student_id, my_class FROM student;
```
4. 查询所有教师的姓名和职称。

```sql
SELECT name, title FROM teacher;
```
5. 查询所有教师的姓名和职称。



```sql
SELECT name, title FROM teacher;
```

6. 查询每个学生的平均分数。




```sql
SELECT student_id, AVG(score) AS average_score FROM score GROUP BY student_id;
```
7. 查询分数大于85分的学生学号和课程号。



```sql

```
8. 查询每门课程的选课人数。



```sql
SELECT course_id, COUNT(DISTINCT student_id) AS number_of_students FROM score GROUP BY course_id;
```


9. 查询选修了"高等数学"课程的学生姓名和分数。



```sql
SELECT s.name, sc.score FROM student s JOIN score sc ON s.student_id = sc.student_id WHERE sc.course_id = 'C001';
```
10. 查询没有选修"大学物理"课程的学生姓名。


```sql
SELECT name FROM student WHERE student_id NOT IN (SELECT student_id FROM score WHERE course_id = 'C002');
```


11. 查询C001比C002课程成绩高的学生信息及课程分数。


```sql
SELECT s.name, sc1.course_id AS 'course1', sc1.score AS 'score1', sc2.score AS 'score2' 
FROM student s 
JOIN score sc1 ON s.student_id = sc1.student_id 
JOIN score sc2 ON s.student_id = sc2.student_id 
WHERE sc1.course_id = 'C001' AND sc2.course_id = 'C002' AND sc1.score > sc2.score;
```

12. 统计各科成绩各分数段人数：课程编号，课程名称，[100-85]，[85-70]，[70-60]，[60-0] 及所占百分比


```sql
SELECT 
  c.course_id,
  c.course_name,
  SUM(CASE WHEN sc.score BETWEEN 85 AND 100 THEN 1 ELSE 0 END) AS '[100-85]',
  SUM(CASE WHEN sc.score BETWEEN 70 AND 84 THEN 1 ELSE 0 END) AS '[85-70]',
  SUM(CASE WHEN sc.score BETWEEN 60 AND 69 THEN 1 ELSE 0 END) AS '[70-60]',
  SUM(CASE WHEN sc.score < 60 THEN 1 ELSE 0 END) AS '[60-0]',
  ROUND(SUM(CASE WHEN sc.score BETWEEN 85 AND 100 THEN 1 ELSE 0 END) / COUNT(sc.student_id) * 100, 2) AS 'Percentage_[100-85]',
  ROUND(SUM(CASE WHEN sc.score BETWEEN 70 AND 84 THEN 1 ELSE 0 END) / COUNT(sc.student_id) * 100, 2) AS 'Percentage_[85-70]',
  ROUND(SUM(CASE WHEN sc.score BETWEEN 60 AND 69 THEN 1 ELSE 0 END) / COUNT(sc.student_id) * 100, 2) AS 'Percentage_[70-60]',
  ROUND(SUM(CASE WHEN sc.score < 60 THEN 1 ELSE 0 END) / COUNT(sc.student_id) * 100, 2) AS 'Percentage_[60-0]'
FROM score sc
JOIN course c ON sc.course_id = c.course_id
GROUP BY c.course_id, c.course_name;
```

13. 查询选择C002课程但没选择C004课程的成绩情况(不存在时显示为 null )。


```sql
SELECT 
  sc2.student_id, 
  sc2.course_id AS 'C002', 
  sc2.score AS 'C002_Score', 
  sc4.course_id AS 'C004', 
  sc4.score AS 'C004_Score'
FROM score sc2
LEFT JOIN score sc4 ON sc2.student_id = sc4.student_id AND sc4.course_id = 'C004'
WHERE sc2.course_id = 'C002';
```

14. 查询平均分数最高的学生姓名和平均分数。


```sql
SELECT s.name, AVG(sc.score) AS average_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
ORDER BY average_score DESC
LIMIT 1;
```

15. 查询总分最高的前三名学生的姓名和总分。


```sql
SELECT s.name, SUM(sc.score) AS total_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
ORDER BY total_score DESC
LIMIT 3;
```

16. 查询各科成绩最高分、最低分和平均分。要求如下：
    以如下形式显示：课程 ID，课程 name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率
    及格为>=60，中等为：70-80，优良为：80-90，优秀为：>=90
    要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列


```sql
SELECT 
  c.course_id,
  c.course_name,
  MAX(sc.score) AS 'Highest',
  MIN(sc.score) AS 'Lowest',
  AVG(sc.score) AS 'Average',
  ROUND(SUM(CASE WHEN sc.score >= 60 THEN 1 ELSE 0 END) / COUNT(sc.score) * 100, 2) AS 'Pass Rate',
  ROUND(SUM(CASE WHEN sc.score BETWEEN 70 AND 79 THEN 1 ELSE 0 END) / COUNT(sc.score) * 100, 2) AS 'Average Rate',
  ROUND(SUM(CASE WHEN sc.score BETWEEN 80 AND 89 THEN 1 ELSE 0 END) / COUNT(sc.score) * 100, 2) AS 'Good Rate',
  ROUND(SUM(CASE WHEN sc.score >= 90 THEN 1 ELSE 0 END) / COUNT(sc.score) * 100, 2) AS 'Excellent Rate',
  COUNT(sc.student_id) AS 'Number of Students'
FROM score sc
JOIN course c ON sc.course_id = c.course_id
GROUP BY c.course_id
ORDER BY COUNT(sc.student_id) DESC, c.course_id ASC;
```

17. 查询男生和女生的人数。


```sql
SELECT gender, COUNT(*) AS 'Number of Students'
FROM student
GROUP BY gender;
```

18. 查询年龄最大的学生姓名。


```sql
SELECT name
FROM student
ORDER BY birth_date ASC
LIMIT 1;
```

19. 查询年龄最小的教师姓名。


```sql
SELECT name
FROM teacher
ORDER BY birth_date DESC
LIMIT 1;
```

20. 查询学过「张教授」授课的同学的信息。


```sql
SELECT s.*
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id
WHERE c.teacher_id = (SELECT teacher_id FROM teacher WHERE name = '张教授');
```

21. 查询查询至少有一门课与学号为"2021001"的同学所学相同的同学的信息 。


```sql
SELECT DISTINCT s.*
FROM student s
JOIN score sc ON s.student_id = sc.student_id
WHERE sc.course_id IN (SELECT course_id FROM score WHERE student_id = '2021001') AND s.student_id != '2021001';
```

22. 查询每门课程的平均分数，并按平均分数降序排列。


```sql
SELECT course_id, AVG(score) AS average_score
FROM score
GROUP BY course_id
ORDER BY average_score DESC;
```



```sql

```

23. 查询学号为"2021001"的学生所有课程的分数。


```sql
SELECT c.course_name, sc.score
FROM course c
JOIN score sc ON c.course_id = sc.course_id
WHERE sc.student_id = '2021001';
```

24. 查询所有学生的姓名、选修的课程名称和分数。


```sql
SELECT s.name, c.course_name, sc.score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id;
```

25. 查询每个教师所教授课程的平均分数。


```sql
SELECT t.name, t.teacher_id, AVG(sc.score) AS average_score
FROM teacher t
JOIN course c ON t.teacher_id = c.teacher_id
JOIN score sc ON c.course_id = sc.course_id
GROUP BY t.teacher_id;
```

26. 查询分数在80到90之间的学生姓名和课程名称。


```sql
SELECT s.name, c.course_name
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id
WHERE sc.score BETWEEN 80 AND 90;
```

27. 查询每个班级的平均分数。


```sql
SELECT s.my_class, AVG(sc.score) AS average_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.my_class;
```

28. 查询没学过"王讲师"老师讲授的任一门课程的学生姓名。


```sql
SELECT s.name
FROM student s
WHERE s.student_id NOT IN (
  SELECT sc.student_id
  FROM score sc
  JOIN course c ON sc.course_id = c.course_id
  JOIN teacher t ON c.teacher_id = t.teacher_id
  WHERE t.name = '王讲师'
);
```

29. 查询两门及其以上小于85分的同学的学号，姓名及其平均成绩 。


```sql
SELECT s.student_id, s.name, AVG(sc.score) AS average_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
HAVING COUNT(sc.course_id) >= 2 AND AVG(sc.score) < 85;
```

30. 查询所有学生的总分并按降序排列。


```sql
SELECT s.name, SUM(sc.score) AS total_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
ORDER BY total_score DESC;
```

31. 查询平均分数超过85分的课程名称。


```sql
SELECT c.course_name
FROM course c
         JOIN score sc ON c.course_id = sc.course_id
GROUP BY c.course_id
HAVING AVG(sc.score) > 85;
```

32. 查询每个学生的平均成绩排名。


```sql
SELECT s.name, s.student_id, AVG(sc.score) AS average_score, RANK() OVER (ORDER BY AVG(sc.score) DESC) AS rank
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id;
```

33. 查询每门课程分数最高的学生姓名和分数。


```sql
SELECT c.course_name, s.name, MAX(sc.score) AS highest_score
FROM course c
JOIN score sc ON c.course_id = sc.course_id
JOIN student s ON sc.student_id = s.student_id
GROUP BY c.course_id
ORDER BY highest_score DESC;
```

34. 查询选修了"高等数学"和"大学物理"的学生姓名。


```sql
SELECT s.name
FROM student s
WHERE s.student_id IN (
  SELECT sc.student_id FROM score sc WHERE sc.course_id IN ('C001', 'C002')
) AND s.student_id NOT IN (
  SELECT sc.student_id FROM score sc WHERE sc.course_id NOT IN ('C001', 'C002')
);
```

35. 按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩（没有选课则为空）。


```sql
SELECT s.name, c.course_name, sc.score, AVG(sc.score) OVER (PARTITION BY s.student_id) AS average_score
FROM student s
LEFT JOIN score sc ON s.student_id = sc.student_id
LEFT JOIN course c ON sc.course_id = c.course_id
ORDER BY average_score DESC;
```

36. 查询分数最高和最低的学生姓名及其分数。


```sql
SELECT name, score FROM (
  SELECT s.name, sc.score, RANK() OVER (ORDER BY sc.score DESC) AS rank_high, RANK() OVER (ORDER BY sc.score ASC) AS rank_low
  FROM student s
  JOIN score sc ON s.student_id = sc.student_id
) AS ranked_scores
WHERE rank_high = 1 OR rank_low = 1;
```

37. 查询每个班级的最高分和最低分。


```sql
SELECT s.my_class, MAX(sc.score) AS highest_score, MIN(sc.score) AS lowest_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.my_class;
```

38. 查询每门课程的优秀率（优秀为90分）。


```sql
SELECT c.course_name, COUNT(*) / (SELECT COUNT(*) FROM score WHERE course_id = c.course_id) * 100 AS excellent_rate
FROM score sc
JOIN course c ON sc.course_id = c.course_id
WHERE sc.score >= 90
GROUP BY c.course_id;
```

39. 查询平均分数超过班级平均分数的学生。


```sql
SELECT
    s.name AS student_name,
    s.student_id,
    s.my_class,
    AVG(sc.score) AS student_average_score,
    class_avg.average_score AS class_average_score
FROM
    student s
        JOIN
    score sc ON s.student_id = sc.student_id
        JOIN
    (SELECT
         my_class,
         AVG(score) AS average_score
     FROM
         student
             JOIN
         score ON student.student_id = score.student_id
     GROUP BY
         my_class) AS class_avg ON s.my_class = class_avg.my_class
GROUP BY
    s.student_id,
    class_avg.average_score
HAVING
    AVG(sc.score) > class_avg.average_score;
```




40. 查询每个学生的分数及其与课程平均分的差值。


```sql
SELECT s.name, c.course_name, sc.score, AVG(sc.score) OVER (PARTITION BY sc.course_id) AS course_average, sc.score - AVG(sc.score) OVER (PARTITION BY sc.course_id) AS score_difference
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id;
```

41. 查询至少有一门课程分数低于80分的学生姓名。


```sql
SELECT s.name
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
HAVING MIN(sc.score) < 80;
```

42. 查询所有课程分数都高于85分的学生姓名。


```sql
SELECT s.name
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
HAVING MIN(sc.score) > 85;
```

43. 查询查询平均成绩大于等于90分的同学的学生编号和学生姓名和平均成绩。


```sql
SELECT s.student_id, s.name, AVG(sc.score) AS average_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
HAVING AVG(sc.score) >= 90;
```

44. 查询选修课程数量最少的学生姓名。


```sql
SELECT s.name
FROM student s
JOIN score sc ON s.student_id = sc.student_id
GROUP BY s.student_id
ORDER BY COUNT(sc.course_id) ASC
LIMIT 1;
```

45. 查询每个班级的第2名学生（按平均分数排名）。


```sql
SELECT s.name
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN (
  SELECT my_class, student_id, RANK() OVER (PARTITION BY my_class ORDER BY AVG(score) DESC) AS rank
  FROM student
  JOIN score ON student.student_id = score.student_id
  GROUP BY my_class, student_id
) ranked ON s.student_id = ranked.student_id
WHERE ranked.rank = 2;
```




46. 查询每门课程分数前三名的学生姓名和分数。


```sql
SELECT s.name, c.course_name, sc.score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id
WHERE (
  c.course_id, sc.score
) IN (
  SELECT course_id, score
  FROM score
  GROUP BY course_id
  ORDER BY course_id, score DESC
  LIMIT 3
);
```

47. 查询平均分数最高和最低的班级。


```sql
SELECT my_class, AVG(score) AS average_score
FROM student
JOIN score ON student.student_id = score.student_id
GROUP BY my_class
ORDER BY average_score DESC
LIMIT 1;
SELECT my_class, AVG(score) AS average_score
FROM student
JOIN score ON student.student_id = score.student_id
GROUP BY my_class
ORDER BY average_score ASC
LIMIT 1;
```

48. 查询每个学生的总分和他所在班级的平均分数。


```sql
SELECT
    st.name AS student_name,
    st.my_class,
    SUM(sc.score) AS total_score,
    cl_avg.average_score
FROM
    student st
        LEFT JOIN
    score sc ON st.student_id = sc.student_id
        CROSS JOIN (
        SELECT
            my_class,
            AVG(score) AS average_score
        FROM
            student
                JOIN
            score ON student.student_id = score.student_id
        GROUP BY
            my_class
    ) AS cl_avg ON st.my_class = cl_avg.my_class
GROUP BY
    st.student_id,
    st.name,
    st.my_class,
    cl_avg.average_score
ORDER BY
    st.my_class,
    st.student_id;
```






49. 查询每个学生的最高分的课程名称, 学生名称，成绩。


```sql
SELECT s.name, c.course_name, MAX(sc.score) AS highest_score
FROM student s
JOIN score sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id
GROUP BY s.student_id, c.course_id
ORDER BY highest_score DESC;
```

50. 查询每个班级的学生人数和平均年龄。



```sql
SELECT s.my_class, COUNT(*) AS number_of_students, AVG(TIMESTAMPDIFF(YEAR, s.birth_date, CURDATE())) AS average_age
FROM student s
GROUP BY s.my_class;
```



