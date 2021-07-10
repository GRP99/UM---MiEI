#!/usr/bin/python3
from classe_sqlite import autodb
from jjcli import *
import os

# create a default path to connect to and create (if necessary) a database. (In the same dir as script)
DEFAULT_PATH = os.path.join(os.path.dirname(__file__), 'gestao-alunos.db')


######################################################################################

# SELECTS

def students():

	sql = f"SELECT id, nome, curso from alunos;"
	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
	formatted_result = [f'A{id:<14}{nome:<20}{curso:>10}' for id, nome, curso in r]
	
	id, nome, curso = "Id","Nome","Curso"
	
	print('')
	print('\n'.join([f"{id:<15}{nome:<20}{curso:>10}"] + formatted_result))
	print('')

def subjects():
	
	sql = f"SELECT id, cadeira, docente from cadeiras;"
	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
	formatted_result = [f'{cadeira:<20}{docente:>20}' for id, cadeira, docente in r]

	cadeira, docente = "Cadeira","Docente"

	print('')
	print('\n'.join([f"{cadeira:<20}{docente:>10}"] + formatted_result))
	print('')

def studsubs(s: str):
	
	subject = s
	sql = f"select alunos.id, alunos.nome, cadeiras.cadeira from alunos_cadeiras inner join alunos on alunos.id = alunos_cadeiras.aluno inner join cadeiras on cadeiras.id = alunos_cadeiras.cadeira where cadeiras.cadeira = '{s}';"

	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()

	if not r:
		print(f"\tNo matches for {s}. {s} must not be a valid subject.")
		return

	print('')
	print(f'\tStudents that enroll {s}:')
	for st, st_n, c in r: print(f'\t   . A{st} - {st_n}') 
	print('')

def substudents(s: str):
	
	matched = re.match("[Aa][0-9]{5}", s)
	if bool(matched) == False:
		print(f"\tInput Error: Expected aXXXXX as a student id, given {s}")
		return

	student = int(s[1:])
	sql = f"select alunos.id, alunos.nome, cadeiras.cadeira, cadeiras.docente from alunos_cadeiras inner join alunos on alunos.id = alunos_cadeiras.aluno inner join cadeiras on cadeiras.id = alunos_cadeiras.cadeira where alunos.id = {student};"

	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()

	if not r:
		print(f"\tNo matches for {s}. {s} must not be a valid student id.")
		return

	print('')
	print(f'\tSubjects that A{student} - {r[0][1]} enrolls:')
	for st, st_n, c, d  in r: print(f'\t   . {c} teached by {d}')
	print('')


######################################################################################

# INSERTS
def insert_student(fields):

    # TRATAMENTO DOS INPUTS
    if not len(fields) == 3:
        print(f"Input Error: Expected 3 inputs, given {len(fields)}.")
        return
    else:
        matched = re.match("[Aa][0-9]{5}", fields[0])
        if bool(matched) == False:
            print(f"Input Error: Expected aXXXXX as a student id, given {field[0]}")
            return
        id, name, course = ','.join(
            [str(elem) for elem in fields]).split(',')

        sql = f"INSERT INTO alunos (id, nome, curso) VALUES ({int(id[1:])}, '{name}', '{course}');"
        try:
        	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        except sqlite3.Error as er:
        	print("Error on Inserting. Note that ID is unique.")
        	return
        
        return


def insert_subject(fields):

    # TRATAMENTO DOS INPUTS
    if not len(fields) == 2:
        print(f"Input Error: Expected 2 inputs, given {len(fields)}.")
        return
    else:
        name, t = ','.join([str(elem) for elem in fields]).split(',')

        sql = f"INSERT INTO cadeiras (cadeira, docente) VALUES ('{name}','{t}');"
        try:
        	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        except sqlite3.Error as er:
        	print("Error on Inserting. Values may not be correct.")
        	return
        
        return


def insert_row_ss(fields):

    # TRATAMENTO DOS INPUTS
    if not len(fields) == 2:
        print(f"Input Error: Expected 2 inputs, given {len(fields)}.")
        return
    else:
        matched = re.match("[Aa][0-9]{5}", fields[0])
        if bool(matched) == False:
            print(f"Input Error: Expected aXXXXX as student_id, given {field[0]}")
            return
        id_a, sub = ','.join([str(elem) for elem in fields]).split(',')
        sql = f"SELECT id, cadeira from cadeiras where cadeiras.cadeira='{sub.strip()}';"
        r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        (id_c,c) = r[0]

        sql = f"INSERT INTO alunos_cadeiras (aluno, cadeira) VALUES ({int(id_a[1:])}, {int(id_c)});"
        try:
        	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        except sqlite3.Error as er:
        	print("Error on Inserting. Values may not be correct.")
        	return

        return

######################################################################################

# DELETES
def delete_student(fields):

    # TRATAMENTO DOS INPUTS
    if not len(fields) == 1:
        print(f"Input Error: Expected 1 inputs, given {len(fields)}.")
        return
    else:
        matched = re.match("[Aa][0-9]{5}", fields[0])
        if bool(matched) == False:
            print(f"Input Error: Expected aXXXXX as student_id, given {field[0]}")
            return
        id = str(fields[0])

        sql = f"DELETE FROM alunos WHERE id={int(id[1:])};"
        try:
        	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        except sqlite3.Error as er:
        	print("Error on Delete. ID is incorrect or nonexistent.")
        	return

        return


def delete_subject(fields):

    # TRATAMENTO DOS INPUTS
    if not len(fields) == 1:
        print(f"Input Error: Expected 1 inputs, given {len(fields)}.")
        return
    else:
        name = str(fields[0])

        sql = f"DELETE FROM cadeiras WHERE cadeira='{name}';"
        r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        try:
        	r = autodb(dbname=DEFAULT_PATH, query=sql, params=None, debug=False).corresql()
        except sqlite3.Error as er:
        	print("Error on Delete. Name is incorrect or nonexistent.")
        	return

        return

######################################################################################