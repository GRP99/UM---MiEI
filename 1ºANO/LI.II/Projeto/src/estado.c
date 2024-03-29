#include <stdio.h>
#include "estado.h"
#define MAX_BUFFER      10240

/**
\brief Funcao que converte um estado numa string
@param e Estado atual
@returns buffer Uma string correspondente ao estado e
*/
char *estado2str(ESTADO e) {
    static char buffer[MAX_BUFFER];
    unsigned char *p = (unsigned char *) &e;
    int i;
    buffer[0] = 0;
    for(i = 0; i < (int) sizeof(ESTADO); i++)
        sprintf(buffer, "%s%02x", buffer, p[i]);
    return buffer;
}

/**
\brief Funcao que converte uma string num estado 
@param argumentos String contendo os argumentos passados à CGI
@returns e Estado atualizado correspondente à string dos argumentos
*/
ESTADO str2estado(char *argumentos) {
    ESTADO e;
    unsigned char *p = (unsigned char *) &e;
    int i;
    for(i = 0; i < (int) sizeof(ESTADO); i++, argumentos += 2) {
        unsigned d;
        sscanf(argumentos, "%2x", &d);
        p[i] = (unsigned char) d;
    }
    return e;
}