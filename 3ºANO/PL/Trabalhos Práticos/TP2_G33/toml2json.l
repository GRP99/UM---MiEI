%{
#include "y.tab.h"

int flag_array = 0;

//Function that remove a char from a given string
void removeChar(char* s, int c){
    int j;
    int n = strlen(s);
    for (int i = j = 0; i < n; i++) {
        if (s[i] != c) {
            s[j++] = s[i];
        }
    }
    s[j] = '\0';
}
%}
%option noyywrap

%x Chave Valor Tabela ArrayTabela Igual

%%
[A-Za-z0-9_-]+                              { yylval.str=strdup(yytext); return string; }

\"([^"\\]|\\.)+\"                           { yytext[yyleng-1]='\0'; yylval.str=strdup(yytext+1); return string; }

=                                           { BEGIN Valor; return yytext[0]; }


\[                                          { BEGIN Tabela; return 't'; }

\[\[                                        { BEGIN Tabela; return 'a'; }


<Tabela>[A-Za-z0-9_-]+                      { yylval.str=strdup(yytext); return string; } 

<Tabela>\"([^"\\]|\\.)+\"                   { yytext[yyleng-1]='\0'; yylval.str=strdup(yytext+1); return string; }

<Tabela>\]                                  { BEGIN Chave; return ':'; }

<Tabela>\]\]                                { BEGIN Chave; return ':'; }


<Chave>[A-Za-z0-9_-]+                       { yylval.str=strdup(yytext); return string; }

<Chave>\"([^"\\]|\\.)+\"                    { yytext[yyleng-1]='\0'; yylval.str=strdup(yytext+1);return string; }

<Chave>\[                                   { BEGIN Tabela; return 't';}

<Chave>\[\[                                 { BEGIN Tabela;return 'a';}

<Chave>=                                    { BEGIN Valor; return yytext[0];}


<Valor>(?i:true)                            {   yylval.num = 1;
                                                if (flag_array==0 ) { BEGIN Chave; }
                                                return (trueOrfalse); 
                                            }

<Valor>(?i:false)                          	{   yylval.num = 0;
                                                if (flag_array==0 ) { BEGIN Chave; }
                                                return (trueOrfalse); 
                                            }

<Valor>([+\-])?(i?nan)                      {   yylval.str = strdup("NaN");
                                                if (flag_array==0 ) { BEGIN Chave; }
                                                return string;
                                            }
<Valor>([+\-])?(i?inf)                      {   if (strlen(yytext) == 4)
    												asprintf(&yylval.str, "%cInfinity", yytext[0]);
												else
    											    yylval.str = strdup("Infinity");
                                                if (flag_array==0 ) { BEGIN Chave; }
												return string;
                                            } 

<Valor>([+\-])?[0-9]+                       {   yylval.num=atoi(yytext);
                                                if (flag_array==0) { BEGIN Chave; }
                                                return numero;
                                            }

<Valor>([+\-])?[0-9]+(_[0-9]+)+             {   removeChar(yytext,'_');
                                                yylval.num=atoi(yytext); 
                                                if (flag_array==0) { BEGIN Chave; }
                                                return numero;
									        }

<Valor>([+\-])?[0-9]+(\.[0-9]+)?            {   yylval.fnum=atof(yytext); 
                                                if (flag_array==0 ) { BEGIN Chave; } 
                                                return numero_decimal; 
                                            }

<Valor>([+\-])?[0-9]+(_[0-9]+)+(\.[0-9]+(_[0-9]+)+)?	            {   removeChar(yytext,'_'); 
                                                                        yylval.fnum=atof(yytext); 
                                                                        if (flag_array==0) { BEGIN Chave; } 
                                                                        return numero_decimal; 
                                                                    }

<Valor>([+\-])?[0-9](\.[0-9]+)?[eE]([+\-])?[0-9]+                   {   if (yytext[0] == '+') {
    												                        yylval.str = strdup(yytext + 1);
											 	                        }
											 	                        else {
    												                        yylval.str = strdup(yytext);
												                        }
                                                                        if (flag_array==0) { BEGIN Chave; }
											 	                        return expo;
											                        }

<Valor>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])[T ](0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]|60):([0-5][0-9](\.[0-9]+)?|60)(Z|[\-+](0|1)[0-9]:([0-5][0-9]|60))? 			    { yylval.str = strdup(yytext); if (flag_array==0) { BEGIN Chave; } return datahora; }

<Valor>[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01]) 																														{ yylval.str = strdup(yytext); if (flag_array==0) { BEGIN Chave; } return dataa; }

<Valor>(0[0-9]|1[0-9]|2[0-4]):([0-5][0-9]|60):([0-5][0-9](\.[0-9]+)?|60) 																										{ yylval.str = strdup(yytext); if (flag_array==0) { BEGIN Chave; } return hora; }

<Valor>0(x[0-9a-fA-F]{8}|x[0-9a-fA-F]{4}_[0-9a-fA-F]{4}|o[0-8]{8}|o[0-8]{3}|b[0-1]{8})            { yylval.str=strdup(yytext); if (flag_array==0){BEGIN Chave;} return string; }

<Valor>[A-Za-z0-9_-]+                                               {   yylval.str=strdup(yytext);
                                                                        if (flag_array==0)BEGIN Chave;
                                                                        return string;
                                                                    }

<Valor>\"([^"\\]|\\.)+\"                                            { yytext[yyleng-1]='\0'; yylval.str=strdup(yytext+1); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>\"\"\"\n([^"\\]|\\.)+\"\"\"         		                    { yytext[yyleng-3]='\0'; yylval.str=strdup(yytext+4); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>\"\"\"([^"\\]|\\.)+\"\"\"           		                    { yytext[yyleng-3]='\0'; yylval.str=strdup(yytext+3); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>'[^']+'                                                      { yytext[yyleng-1]='\0'; yylval.str=strdup(yytext+1); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>'''\n[^']+'''         		                                { yytext[yyleng-3]='\0'; yylval.str=strdup(yytext+4); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>'''[^']+'''           		                                { yytext[yyleng-3]='\0'; yylval.str=strdup(yytext+3); if(flag_array==0) { BEGIN Chave; } return string; }

<Valor>\[                                                           { flag_array++; return yytext[0]; }

<Valor>,                                                            { return yytext[0]; }

<Valor>\]                                                           { flag_array--; if(flag_array==0) { BEGIN Chave; } return yytext[0];}


<*>#.+                                                              { ; } 

<*>.|\n                                                             { ; }

%%