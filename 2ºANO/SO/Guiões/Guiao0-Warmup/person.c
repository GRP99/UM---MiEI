#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
#include "person.h"

Person new_person(char* name, int age){
	size_t n = strlen(name) + 1;
	char* s = malloc(sizeof(char[n]));
	memcpy(s, name, n);
	return (Person){
		.name = s,
		.age = age,
	};
}

Person clone_person(Person* p){
	return (Person){
		.name = strdup(p->name),
		.age = p-> age,
	};
}

void destroy_person(Person* p){
	free(p->name);
}

int person_age(Person* p){
	return p->age;
}

void person_change_age(Person* p, int age){
	p->age = age;
}