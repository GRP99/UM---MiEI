/* [1] Listar todos os jobs cujo salario minimo seja maior que 10000 */
SELECT * FROM JOBS WHERE MIN_SALARY > 10000;

/* [2] Listar o nome do funcionario (primeiro + ultimo) e a data de entrada para todos os funcionarios que entraram entre 2001 e 2005 ordenados por data de entrada */
SELECT FIRST_NAME, HIRE_DATE FROM EMPLOYEES WHERE TO_CHAR(HIRE_DATE, 'YYYY') BETWEEN 2001 AND 2005 ORDER BY HIRE_DATE;

/* [3] Listar o nome do funcionario (primeiro + ultimo) e a data de entrada para todos os funcionarios que sejam quer Programmer ou Sales Representative */
select e.first_name, e.last_name, e.hire_date, j.job_title from employees e,jobs j where e.job_id=j.job_id and (j.job_title='Programmer' or j.job_title='Sales Representative');

/* [4] Listar todos os empregados que entraram para a empresa apos 1 de Janeiro de 2003 */
select * from employees e where e.hire_date > to_date('01-01-2003','DD-MM-YYYY');
    
/* [5] Listar todos os empregados cujo id esta entre 140 e 170 */
select * from employees e where e.employee_id >= 140 and e.employee_id <= 170;

/* [6] Listar o ultimo nome, comission_pct e data de admissao de todos os empregados cujo salario e menor que 12000 */
SELECT LAST_NAME, COMMISSION_PCT, HIRE_DATE FROM EMPLOYEES WHERE SALARY<12000;

/* [7] Listar o cargo, e a diferenca entre o salario maximo e minimo para posicoes ( job ) cujo salario maximo esta entre 10000 e 20000 */
SELECT J.JOB_TITLE,(J.MAX_SALARY-J.MIN_SALARY) AS DIFF FROM JOBS J WHERE (J.MAX_SALARY>=10000 AND J.MAX_SALARY<=20000);

/* [8] Listar o primeiro nome, salario e o salario arredondado aos milhares de todos os funcionarios */
SELECT FIRST_NAME, SALARY, ROUND(SALARY, -3) FROM EMPLOYEES;

/* [9] Listar os detalhes de todas as posicoes ordenadas em ordem descendente pelo titulo da posicao ( job_title ) */
select * from jobs j order by job_title desc;

/* [10] Listar todos os funcionarios cujo primeiro nome termina com a letra D e ultimo nome comeca pela letra G */
SELECT * FROM EMPLOYEES E WHERE SUBSTR(FIRST_NAME,-1,1)='d' AND SUBSTR(LAST_NAME,0,1)='G';

/* [11] Listar todos os empregados que comecaram a trabalhar no mes de Junho */
SELECT * FROM EMPLOYEES WHERE EXTRACT(month FROM HIRE_DATE) = 6;

/* [12] Listar os detalhes dos funcionarios cuja percentagem de comissao ( comission_pct ) e nula e o salario esta entre 2000 e 5000 e o departamento e o 30 */
SELECT * FROM EMPLOYEES WHERE COMMISSION_PCT is null AND SALARY>=2000 AND SALARY<=5000 AND DEPARTMENT_ID=30;

/* [13] Listar o nome (primeiro + ultimo) dos funcionarios que foram contratados em 1998 */
SELECT E.FIRST_NAME, E.LAST_NAME, E.HIRE_DATE FROM EMPLOYEES E WHERE EXTRACT(YEAR FROM E.HIRE_DATE)=98;
SELECT FIRST_NAME, HIRE_DATE FROM EMPLOYEES WHERE TO_CHAR(HIRE_DATE, 'YYYY')=1998

/* [14] Listar a primeira palavra da descricao da posicao */
select distinct regexp_substr(j.JOB_TITLE,'[A-z]*') as First_Word from jobs j;

/* [15] Listar o primeiro e ultimo nome dos funcionarios cujo ultimo nome contenha a letra b apos a terceira posicao */
SELECT FIRST_NAME, LAST_NAME FROM EMPLOYEES E WHERE INSTR(E.LAST_NAME,'B') > 3;

/* [16] Listar os funcionarios que iniciaram funcoes no ano de 2007 */
SELECT * FROM EMPLOYEES WHERE TO_CHAR(HIRE_DATE,'YYYY')='2007';

/* [17] Listar o numero de dias entre a data atual do sistema e o dia 01 de janeiro de 2011 */
select trunc(sysdate) - to_date('01-01-2011','DD-MM-YYYY') as diff from dual;

/* [18] Listar quantos funcionarios foram admitidos em cada mes no ano 2007 */
SELECT COUNT(*) as TOT, EXTRACT(MONTH FROM HIRE_DATE) AS MONTH FROM EMPLOYEES
    WHERE EXTRACT(YEAR FROM HIRE_DATE) = 2007
    GROUP BY (EXTRACT(MONTH FROM HIRE_DATE))
    ORDER BY EXTRACT(MONTH FROM HIRE_DATE) ASC;

/* [19] Listar o pais e o numero de cidades existentes em cada pais */
select c.country_name, count(l.city) as TOT_CITIES from countries c, locations l where l.country_id=c.country_id group by(c.country_name);

/* [20] Listar a media dos salarios dos funcionarios por departamento que tem percentagem de comissao ( commission_pct ) */
SELECT DEPARTMENT_ID, AVG(SALARY) FROM EMPLOYEES WHERE COMMISSION_PCT IS NOT NULL GROUP BY DEPARTMENT_ID;

/* [21] Inserir um novo funcionario com todos os campos obrigatorios */
INSERT INTO EMPLOYEES  (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE,JOB_ID, SALARY, DEPARTMENT_ID) 
VALUES (626, 'HUGO', 'MACHADO','HM','256 256 5050', SYSDATE, 'SA_MAN', 10000, 80);

/* [22] Atualizar o salario do funcionario 115 para 8000 se o salario atual for menor que 6000 */
update employees set salary = 8000 where employee_id=115 and salary < 6000;

/* [23] Listar o nome do departamento e o numero de funcionarios nesse departamento */
SELECT DEPARTMENT_NAME, COUNT(*) AS TOT_EMPLOYEES FROM EMPLOYEES E, DEPARTMENTS D WHERE E.DEPARTMENT_ID=D.DEPARTMENT_ID GROUP BY DEPARTMENT_NAME;

/* [24] Listar a descricao da posicao, o id do funcionario e o numero de dias entre a data de fim e a data de inicio para todos os trabalhos do departamento 20 no historico */
SELECT EMPLOYEE_ID, JOB_TITLE, END_DATE-START_DATE DAYS FROM JOB_HISTORY NATURAL JOIN JOBS WHERE DEPARTMENT_ID=20;

/* [25] Listar o titulo da posicao, o nome do departamento, o ultimo nome do funcion�rio e data de inicio de todos os trabalhos de 1998 a 2000 */
select j.job_title,d.department_name,e.last_name,e.hire_date
    from jobs j,employees e,departments d,job_history jh
    where jh.job_id = j.job_id and d.department_id=jh.department_id and 
        jh.employee_id=e.employee_id and jh.start_date >= to_date('01-01-1998','DD-MM-YYYY') 
        and jh.start_date <= to_date('31-12-2000','DD-MM-YYYY');

/* [26] Listar o titulo da posicao e media dos salarios */
SELECT J.JOB_TITLE, AVG(E.SALARY) AS AVG_SALARY FROM EMPLOYEES E, JOBS J WHERE E.JOB_ID = J.JOB_ID GROUP BY J.JOB_TITLE;
    
/* [27] Listar o titulo da posicao e a diferenca entre o salario maximo e o salario do funcionario */
SELECT J.JOB_TITLE, (J.MAX_SALARY-E.SALARY) AS DIFFERENCE FROM JOBS J, EMPLOYEES E WHERE J.JOB_ID=E.JOB_ID;

/* [28] Liste os detalhes da posicao que tenha sido executada por qualquer funcionario que atualmente recebe mais que 15000 de salario */
SELECT JH.* FROM JOB_HISTORY JH, EMPLOYEES E WHERE (JH.EMPLOYEE_ID = E.EMPLOYEE_ID) AND SALARY > 15000;

/* [29] Listar o nome dos funcionarios que iniciaram funcoes antes do manager */
SELECT E.FIRST_NAME, E.LAST_NAME FROM EMPLOYEES E
    WHERE E.HIRE_DATE < (SELECT E.HIRE_DATE FROM EMPLOYEES E WHERE E.MANAGER_ID IS NULL);


/* [30] Listar o nome do funcionario e o pais onde esta a trabalhar */
SELECT E.FIRST_NAME, C.COUNTRY_NAME FROM EMPLOYEES E, DEPARTMENTS D, LOCATIONS L, COUNTRIES C
    WHERE D.DEPARTMENT_ID = E.DEPARTMENT_ID AND L.LOCATION_ID=D.LOCATION_ID AND L.COUNTRY_ID=C.COUNTRY_ID;

/* [31] Listar o mes no qual mais de 5 funcionarios entraram para o departamento de Seattle */
SELECT EXTRACT(month FROM E.HIRE_DATE) AS Month
    FROM EMPLOYEES E, DEPARTMENTS D, LOCATIONS L
    WHERE  CITY = 'Seattle' AND E.DEPARTMENT_ID=D.DEPARTMENT_ID AND D.LOCATION_ID=L.LOCATION_ID
    GROUP BY EXTRACT(month FROM E.HIRE_DATE)
    HAVING COUNT(*) > 5;
  
/* [32] Listar o nome do pais, cidade, e numero de departamentos que possuem mais de 5 funcionarios */    
SELECT COUNTRY_NAME, CITY, COUNT(DEPARTMENT_ID) AS NUM_DEP FROM COUNTRIES C, LOCATIONS L, DEPARTMENTS D 
    WHERE C.COUNTRY_ID=L.COUNTRY_ID AND D.LOCATION_ID=L.LOCATION_ID 
        AND DEPARTMENT_ID IN (SELECT DEPARTMENT_ID FROM EMPLOYEES GROUP BY DEPARTMENT_ID HAVING COUNT(DEPARTMENT_ID)>5)
    GROUP BY COUNTRY_NAME, CITY;
    
/* [33] Listar o primeiro nome do manager que tenha mais do que 5 funcioarios a seu encargo */
SELECT FIRST_NAME FROM EMPLOYEES EMP WHERE (SELECT COUNT(*) FROM EMPLOYEES E WHERE E.MANAGER_ID=EMP.EMPLOYEE_ID)>5;
    
/* [34] Listar a cidade do funcionario cujo id e 105 */
SELECT CITY FROM LOCATIONS L, DEPARTMENTS D, EMPLOYEES E WHERE L.LOCATION_ID = D.LOCATION_ID AND D.DEPARTMENT_ID=E.DEPARTMENT_ID AND E.EMPLOYEE_ID=105;