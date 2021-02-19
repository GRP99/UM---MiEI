/* [1] Apresentar os detalhes dos departamentos ordenados pelo nome de forma ascendente */
SELECT * FROM departments d ORDER BY d.department_name ASC ;

/* [2] Apresentar os nomes dos departamentos e os responsaveis por estes */
SELECT d.department_name, e.first_name, e.last_name FROM departments d, employees e WHERE d.manager_id=e.employee_id;

/* [3] Apresentar os nomes dos departamentos e o numero de funcionarios de cada um */
SELECT department_name, COUNT(*) AS tot_employees FROM employees e, departments d WHERE e.department_id=d.department_id GROUP BY department_name;

/* [4] Apresentar os identificadores dos gestores e o numero de funcionarios geridos por cada um */
SELECT manager_id, COUNT(*) AS tot_employees FROM employees GROUP BY manager_id ;

/* [5] Apresentar os detalhes dos funcionarios cuja percentagem de comissao seja nula, o salario esteja entre 10000 e 15000 e o gestor possua o identificador 101 */
SELECT * FROM employees WHERE commission_pct IS NULL AND salary>=10000 AND salary<=15000 AND manager_id=101;

/* [6] Apresentar os funcionarios cujo primeiro nome e ultimo nome comecam pela letra G */
SELECT first_name, last_name FROM employees WHERE SUBSTR(first_name,0,1)='G' AND SUBSTR(last_name,0,1)='G';

/* [7] Apresentar todos os funcionarios que entraram para a empresa apos 19 de Dezembro de 2007 */
SELECT * FROM employees e WHERE e.hire_date > to_date('19-12-2007','DD-MM-YYYY');

/* [8] Apresentar o nome dos funcionarios que foram contratados em 2008 */
SELECT first_name, last_name, hire_date FROM employees WHERE EXTRACT(YEAR FROM hire_date)=2008 ORDER BY hire_date;

/* [9] Apresentar quantos funcionarios foram admitidos em cada mes do ano 2008 */
SELECT COUNT(*) AS tot, EXTRACT(MONTH FROM hire_date) AS MONTH FROM employees WHERE EXTRACT(YEAR FROM hire_date) = 2008 GROUP BY (EXTRACT(MONTH FROM hire_date)) ORDER BY EXTRACT(MONTH FROM hire_date) ASC;

/* [10] Apresentar o nome dos funcionarios que iniciaram funcoes antes do seu gestor */
SELECT e1.first_name, e1.last_name FROM employees e1 JOIN employees e2 ON (e1.manager_id=e2.employee_id) WHERE e1.hire_date < e2.hire_date;

/* [11] Apresentar o cargo, o numero de funcionarios, o somatorio dos salarios e a diferenca entre o maior salario e o menor dos funcionarios desse cargo */
SELECT job_id, COUNT(*) as total_employees, SUM(salary) AS tot_salaries, MAX(salary)-MIN(salary) AS diff_salary FROM employees GROUP BY job_id ORDER BY diff_salary desc;

/* [12] Apresentar o nome dos funcionarios que ocupam o cargo de Programmer ou President */
SELECT e.first_name, e.last_name, j.job_title FROM employees e,jobs j WHERE e.job_id=j.job_id AND (j.job_title='Programmer' OR j.job_title='President');

/* [13] Apresentar o nome do cargo e a media dos salarios */
SELECT j.job_title, AVG(e.salary) AS avg_salary FROM employees e, jobs j WHERE e.job_id = j.job_id GROUP BY j.job_title;

/* [14] Apresentar simultaneamente o primeiro nome do funcionario, o seu cargo e a sua experiencia, ordenando do mais experiente para o menos experiente */
SELECT e.first_name, e.hire_date, FLOOR((SYSDATE-e.hire_date)/365) AS experience, j.job_title  FROM employees e, jobs j  WHERE e.job_id = j.job_id ORDER BY experience DESC;

/* [15] Apresentar os cargos cujo salario maximo seja menor ou igual que 6000 */
SELECT * FROM jobs WHERE max_salary <= 6000;

/* [16] Apresentar o cargo e a diferenca entre o salario maximo e o salario minimo desse cargo, em que o salario maximo esteja entre 6000 e 10000 */
SELECT j.job_title, (j.max_salary-j.min_salary) AS diff FROM jobs j WHERE (j.max_salary>=6000 AND j.max_salary<=10000);

/* [17] Apresentar o identificador dos funcionarios que tiveram mais que um cargo no passado */
SELECT employee_id FROM job_history GROUP BY employee_id HAVING COUNT(*) > 1;

/* [18] Apresentar os detalhes dos cargos que tenham sido executados por funcionarios que actualmente recebem mais que 10000 de salario */
SELECT jh.* FROM job_history jh, employees e WHERE (jh.employee_id = e.employee_id) AND salary > 10000;

/* [19] Apresentar os detalhes dos cargos atuais dos funcionarios que trabalharam como IT_PROG no passado */
SELECT * FROM jobs WHERE job_id IN(SELECT job_id FROM employees WHERE employee_id IN  (SELECT employee_id FROM job_history WHERE job_id='IT_PROG'));

/* [20] Apresentar os paises e o numero de cidades existentes associadas a cada pais */
SELECT country_id,  COUNT(*) as total_cities  FROM locations GROUP BY country_id;

/* [21] Apresentar o nome da regiao, o nome do pais, o nome da cidade, o nome do departamento e do gestor deste, bem como do seu cargo */
SELECT r.region_name, c.country_name, l.city, d.department_name, e.first_name, e.last_name, j.job_title
    FROM countries c, departments d, employees e, jobs j, locations l, regions r
    WHERE r.region_id = c.region_id AND c.country_id=l.country_id AND l.location_id=d.location_id AND d.manager_id=e.employee_id AND e.job_id=j.job_id; 

/* [22] Apresentar o nome do funcionario e o respetivo pais onde se encontra a trabalhar */
SELECT e.first_name, c.country_name FROM employees e, departments d, locations l, countries c 
    WHERE d.department_id = e.department_id AND l.location_id=d.location_id AND l.country_id=c.country_id;

/* [23] Apresentar o nome do pais, a cidade e o numero de departamentos que possuem mais de 5 funcionarios */    
SELECT country_name, city, COUNT(department_id) AS num_dep FROM countries c, locations l, departments d 
    WHERE c.country_id=l.country_id AND d.location_id=l.location_id AND department_id IN (SELECT department_id FROM employees GROUP BY department_id HAVING COUNT(department_id)>5)
    GROUP BY country_name, city;