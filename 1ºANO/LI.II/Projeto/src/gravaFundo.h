#ifndef ___GRAVAFUNDO_H___
#define ___GRAVAFUNDO_H___
/**
@file gravaFuno.h
Funcoes uteis para ler e gravar nos diferentes ficheiros utilizados
*/

void debug(char *teste);

ESTADO ler_estado_ficheiro(char *filename);

void gravar_estado_ficheiro(ESTADO e, char *user);

ESTADO ler_ancora_ficheiro(ESTADO e);

void gravar_ancora_ficheiro(ESTADO e);

void gravar_undo_ficheiro(ESTADO e);

#endif