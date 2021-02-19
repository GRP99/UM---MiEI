#include <stdio.h>
// < Vectores de inteiros >

//2.
void swapM (int *x, int *y){
	int temp = *x;
	* x = *y;
	* y = temp;
}

//3.
void swap (int v[], int i, int j){
	int temp = v[i];
	v[i] = v[j];
	v[j] = temp;
}

//4.
int soma (int v[], int N){
    int i;
    int *s = v[0];
    for (i=0; i<N; i++) {
	   *s = *s + v[i];
	}
    return 0;
}

//5.
int maximum (int v[], int N, int *m){
	int i;
	if (N<1) return 1;
	*m = v[0];
	for (i=1; i<N; i++){
		if ((v[i]) > *m) *m = v[i];
	}
	return 0;
}

//6.
void quadrados (int q [], int N){
	int i;
	int x = 1;
	for (i=0; i<N; i++){
	   q[i] = x * x;
	   x ++;
	}
}

//7.
void pascal (int v[], int N){
	int i, j;
	for (i=0; i<N; i++){
		v[i]= 1;
		for (j=i-1; j>0; j--){
			v [j] = v[j] + v[j-1];
		}
	}
}