#include <stdio.h>
// < 1.Estado e atribuções >

// < 2.Estruturas de controlo >

//2.
void quadrado (int n){
	int j,i;
	for (j=0; j<n; j++){
		for (i=0; i<n; i++){
			putchar('#');
		}
    putchar ('\n');
	}
}

//3.
void xadrez (int n){
	int i,j;
	for (i=0; i<n; i++){
		for (j=0; j<n; j++){
			if ((j/2) == 0)
			    putchar('#');
		    else
		        putchar('_');		    
		}
		putchar('\n');
	}
}

//4.
void tri1 (int n){
	int i,j;
	for (i=1; i<=n; i++){
		for (j=0; j>i; j++){
			   putchar ('#');	
		}
	putchar ('\n');
	}
	for (i=4; i>0; i--){
		for (j=0; j>i; j++){
			putchar ('#');	
		}
	putchar ('\n');
	}
}
void tri2 (int n){
	int i,j;
	for (i=1; i<=n; i++){
		for (j=0; j<n-i; j++) {
			putchar (' ');
		}
		for (j=0; j<(2*(i-1)); j++) {
			putchar ('#');
		}
		putchar ('\n');
	}
}