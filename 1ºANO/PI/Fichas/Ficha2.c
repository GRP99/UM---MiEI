#include <stdio.h>
// < 1.Memória e endereçamento >

// < 2.Algoritmos Numéricos sobre inteiros >
//1.
float mult (int n, float m){
	int i; float r = 0; 
	for (i= 0; i<n; i++){
		r += m ;
	}
	return r;
}

//2.
float fastMult (int n, float m){
	float r = 0;
	while (n>0) {
	     if (n % 2 != 0) r+=m;
	     n = n / 2;
	     m = m * 2;
	}
	return r;
}

//4.
float mdc (int a, int b) {
	while (a>0 || b>0) {
		if (a>b) a = a%b;
		else b = b%a;
	}
	if (a > 0) return a;
	else return b;
}

//7.
int fib (int n){
	if (n>1) return (fib (n-1) + fib (n-2));
	else return 1;
}
int interactivefib (int n){
	int fib1=1, fib2=1;
	int i;
	for (i=2; i <= n; i++){
		int newfib = fib1 + fib2;
		fib2 = fib1;
		fib1 = newfib;
	}
	return fib1;
}