#include <stdio.h>
#include <ctype.h>
#include <string.h>
// < Strings e Matrizes >

//1.
int minusculas (char s[]){
	int i = 0, sub=0;
	while (s[i]!= '\0'){
		if (s[i] >='A' && s[i]<='Z'){
			s[i] = s[i] + ('a'-'A');
			sub ++;
		}
	i ++;
	}
	return sub;
}

//2
int contalinhas (char s[]){
	int i,r = 0;
	for (i=0; s[i] != '\0'; i++){
		if (s[i] == '\n') r++;
	}
	return r;
}

//3.
int contaPal (char s[]){
   int conta = 0, i = 0, flag = 0;
   while (s[i] != '\0'){
   		if (isspace (s[i])) flag = 0;
		else {
		    if (flag== 0) conta ++;
		    flag = 1;	
		}
		i++;
   }
   return conta;
}


//6.
int pesqBin (int N,char *a[N], char *s){
	int res = -1;
	int l = 0;
	int h = N-1;
	int m;
	while (res < 0 && h < l){
		m = (h+l)/2;
		if (strcmp (s,a[m]) == 0) res = m;
		else if (strcmp (s,a[m]) < 0) h = m-1;
			 else l = m+1;
	}
	return res;
}

//7.
int zeros (int N,int M,int m [N] [M]){
   int i, j, conta=0;
   for (i=0;i<N; i++){
   	   for (j=0; j<M; j++){
   	   	  if (m[i][j] == 0) conta++;
	   }
   return conta;
   }
}

//8.
void identidade (int N, int m [N] [N]){
	int i, j, conta=0;
	for (i=0; i<N; i++){
   		for (j=0;j<M;j++){
   	   		if (i == j) m[i][j] = 1;
			else m[i][j] = 0;
		}
	}
}

//9.
void multV (int N, float t[N][N], float v[N], float r[N]){
	int i, j;
	for (i=0; i<N; i++){
		r[i]=0
		for (j=0;j<N;j++){
			r[i] += t[i][j] * v[j];
		}
	}
}
