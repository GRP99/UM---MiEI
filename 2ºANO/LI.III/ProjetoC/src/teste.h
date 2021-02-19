#ifndef TESTE
#define TESTE


/* Valido=1 ; Nao valido=0 */
int valida_filial(int filial); /*Valida uma filial (1 a 3).*/
int valida_mes(int mes); /*Valida um mes (1 a 12).*/
int valida_cliente(char* cliente); /*Valida um cliente (1 letra maiscula_4 digitos=1000-5000).*/
int valida_tipo(char tipo); /*Valida o tipo de venda (P=promocao_N=normal)*/
int valida_unidades(int unidades); /*Valida numero de unidades compradas (1-200).*/
int valida_preco(double preco); /*Valida o preco unitario decimal (0.0-999.99).*/
int valida_produto(char* produto); /*Valida um produto (2 letras maisculas_4 digitos=1000-9999).*/
int testeVenda(char* produto, double preco, int unidades, char tipo, char* cliente, int mes, int filial); /*Valida uma venda que e composta por um codigo de produto,preco unitario decimal,numero inteiro de unidades compradas,uma letra,codigo do cliente,mes de compra e a filial*/


#endif