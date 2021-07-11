%{
#include <sys/stat.h>	
#include <stdio.h>

char* nome_projeto;
char*var_email="";
char*var_autor="";
char*path_inicial="";

//ficheiro aberto
FILE * fp;

// temp para construir paths
char** dir;
int size_dirs=1;
int point_dirs=0;


// guarda os paths , nome file -> path completo 1
char *** dicionario_ficheiros;
int size_dicionario=1;
int pointer_dicionario=0;

// dado uma chave e  p paht completo adiciona ao dicionario
void adiciona_dicionario(char* chave, char * path_completo){

	if (pointer_dicionario>=size_dicionario){
		size_dicionario*=2;
		dicionario_ficheiros= (char ***) realloc(dicionario_ficheiros, sizeof(char *) * size_dirs);
	}

	dicionario_ficheiros[pointer_dicionario]= (char **) malloc( sizeof(char *) * 2);
	
	dicionario_ficheiros[pointer_dicionario][0]=strdup(chave);
	dicionario_ficheiros[pointer_dicionario][1]=strdup(path_completo);
	pointer_dicionario++;

}

// retorna o indice se existir ou -1 se nao existir
int existe_dicionario(char * chave){

	//printf("Conteudo Dicionario:\n ");
	for(int i=0;i<pointer_dicionario;i++){
		if (strcmp(chave,dicionario_ficheiros[i][0])==0) return i;
	}

	return -1;
}
// obtem path apartir do indice
char * get_path( int indice){

	if (indice>=0 && indice <=pointer_dicionario)
		return dicionario_ficheiros[indice][1];

	return NULL;
}


// cria a pasta juntado todas as strings(nivel de diretorias) que estao no array global dir
void criapasta(){
	
	if (point_dirs<size_dirs){


		int size=1+strlen(path_inicial); // null terminator
		for(int i=0;i<point_dirs+1;i++){
			size+=strlen(dir[i])+1;
		}
	
		char *result = malloc(sizeof(char)*size);
		strcat(result,path_inicial);

		for(int i=0;i<point_dirs+1;i++){
			strcat(result,dir[i]);
			strcat(result,"/");
		}

		result[strlen(result)-1]='\0';

 		int err = mkdir(result,0777);

	}
}

// cria o ficheiro juntado todas as strings(nivel de diretorias) que estao no array global dir
void criaficheiro(){

	if (point_dirs<size_dirs){

		int size=1+strlen(path_inicial); // null terminator + touch + path inicial
		for(int i=0;i<point_dirs+1;i++){
			size+=strlen(dir[i])+1;
		}
	
		char *result = malloc(sizeof(char)*size);
		strcat(result,path_inicial);

		for(int i=0;i<point_dirs+1;i++){
			strcat(result,dir[i]);
			strcat(result,"/");
		}
		result[strlen(result)-1]='\0';

		FILE* fp = fopen (result, "w");
		if (fp!=NULL){
			fclose(fp);
			adiciona_dicionario(dir[point_dirs],result);
		}
		else printf("Detetado erro a criar ficheiro");
	}
}

// duplica o tamanho do array global dir
void resize(){
	size_dirs*=2;
	dir=realloc(dir,sizeof(char*) * size_dirs);	
}

// insere no array dir a string
void inseredir(char * str){	
	if (size_dirs*2>point_dirs){
		if (point_dirs>= size_dirs) resize();
    	dir[point_dirs]=strdup(str);
	}
	else printf("Error:\n	Um ou mais pastas/ficheiros não foram criados devido a sintaxe incorreta na árvore");
}

// retorna 1 se abriu o ficheiro para o fp 0 se nao conseguiu
int abreficheiro(char * chave){
	int r=existe_dicionario(chave);

	if (r!=-1){
		fp=fopen(get_path(r),"a");		
		if (fp!=NULL){
			return 1;
		}
	}

	return 0;
}

// escreve para o ficheiro que esta aberto
void escreveficheiro(char * str){	
	if (fp!=NULL)
		fwrite(str,sizeof(char),strlen(str),fp);	
}

%}

%x meta email autor tree   namefile ficheiro

Abrev ER
%%
^[ ]*===[ ]*(?i:meta)					{BEGIN meta;}
^[ ]*===[ ]*(?i:tree) 					{BEGIN tree;}
^[ ]*===[ ]* 							{BEGIN namefile;}


<meta>^[ ]*(?i:email)[ ]*:[ ]* 			{BEGIN email;}
<meta>^[ ]*(?i:author)[ ]*:[ ]* 		{BEGIN autor;}

<email>[^ \n]+ 							{var_email=strdup(yytext);BEGIN meta;}
<autor>[^ \n]+ 							{var_autor=strdup(yytext);BEGIN INITIAL;}



<tree>\n/[ ]*===[ ]* 					{BEGIN INITIAL;} 

<tree>^[ ]*\{\%name\%\}\/				{inseredir(nome_projeto); criapasta();}
<tree>\{\%name\%\}\.[a-zA-Z]+			{char *result = malloc(strlen(nome_projeto) + strlen(yytext+8) + 1);
										strcpy(result, nome_projeto);strcat(result, yytext+8);
										inseredir(result); criaficheiro();}
<tree>\-+ 								{point_dirs+=strlen(yytext);}
<tree>\n   	        					{point_dirs=0;}
<tree>[^\/\n ]+/\/ 					    {inseredir(yytext);criapasta();}
<tree>[^\/\n ]+ 						{inseredir(yytext);criaficheiro();}




<namefile>\{\%name\%\}\.[a-zA-Z]+		{char *result = malloc(strlen(nome_projeto) + strlen(yytext+8) + 1);
										strcpy(result, nome_projeto);
										strcat(result, yytext+8);
										if (abreficheiro(result)==1) BEGIN ficheiro; else BEGIN INITIAL;}


<namefile>[a-zA-Z0-9.\-_]+  			{ if (abreficheiro(yytext)==1) BEGIN ficheiro; else BEGIN INITIAL;}



<ficheiro>^[ ]*===[ ]* 				    {fclose(fp);fp=NULL;BEGIN namefile;} 

<ficheiro>\{\%name\%\}					{escreveficheiro(nome_projeto);}
<ficheiro>\{\%author\%\}				{escreveficheiro(var_autor);}
<ficheiro>\{\%email\%\}					{escreveficheiro(var_email);}

<ficheiro>\{							{escreveficheiro(yytext);}
<ficheiro>\=							{escreveficheiro(yytext);}

<ficheiro>[^{\=]*						{escreveficheiro(yytext);}


.|\n           							{;}

%%


int yywrap()
{ 
	return(1); 
}

int main(int arg, char** args)
{ 	
	if(arg==2  || arg==3){
		
		if (arg==3) path_inicial=strdup(args[2]);

		dir = (char **) malloc(sizeof(char *) * size_dirs);
		dicionario_ficheiros = (char ***) malloc(sizeof(char **) * size_dirs);
		
		nome_projeto=strdup(args[1]);

		yylex();
		
	}
	return 0;
}