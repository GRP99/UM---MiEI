min: 200 rc01 + 100 rc02
     + 600 rc11 + 300 rc12
     + 1000 rc21 + 500 rc22
     + 200 rc31 + 100 rc32
     + 800 rc41 + 400 rc42
     + 180 rc61 + 90 rc62
     + 0 rc71 + 0 rc72
     + 200 rc81 + 100 rc82
     + 1000 rc101 + 500 rc102
     + 600 rc111 + 300 rc112;
/*restrições*/
tf<=26;
arco_01: t1>= t0 - rc01 -rc02 + 4 ;
arco_12: t2>= t1 - rc11 -rc12 + 6 ;
arco_23: t3>= t2 - rc21 -rc22 + 7 ;
arco_i0: t0>= ti + 0 ;
arco_04: t4>= t0 - rc01 -rc02 + 4 ;
arco_42: t2>= t4 - rc41 -rc42 + 9 ;

arco_3f: tf>= t3 - rc31 -rc32 + 2 ;


arco_i6: t6>= ti + 0 ;
arco_74: t4>= t7 - rc71 -rc72 + 6 ;


arco_67: t7>= t6 - rc61 -rc62 + 5 ;
arco_78: t8>= t7 - rc71 -rc72 + 6 ;

arco_610: t10>= t6 - rc61 -rc62 + 5 ;
arco_108: t8>= t10 - rc101 -rc102 + 8 ;

arco_1011: t11>= t10 - rc101 -rc102 + 8;

arco_43: t3>= t4 - rc41 -rc42 +9;
arco_4f: tf>= t4 - rc41 -rc42 +9;
arco_83: t3>= t8 - rc81 -rc82 +4;
arco_8f: tf>= t8 - rc81 -rc82 +4;
arco_11f: tf>= t11 - rc111 -rc112 +7;

//max reducao 1
rc01<= 0.5;
rc11<= 1;
rc21<= 3;
rc31<= 0.5;
rc41<= 2;
rc61<= 1;
rc71<= 0;
rc81<= 0.5;
rc101<= 0.5;
rc111<= 1;

//max reducao 2
rc02<= 0.5 y0;
rc12<= 1 y1;
rc22<= 1 y2;
rc32<= 0.5 y3;
rc42<= 1 y4;
rc62<= 1 y6;
rc72<= 0 y7;
rc82<= 0.5 y8;
rc102<=0.5 y10;
rc112<= 1 y11;

// so existe reducao de 2 quando reducao 1 esta no max
rc01>= 0.5 y0;
rc11>= 1 y1;
rc21>= 3 y2;
rc31>= 0.5 y3;
rc41>= 2 y4;
rc61>= 1 y6;
rc71>= 0 y7;
rc81>= 0.5 y8;
rc101>= 0.5 y10;
rc111>= 1 y11;

bin y0,y1,y2,y3,y4,y6,y7,y8,y10,y11;
int rc01,rc02,rc11,rc12,rc21,rc22,rc31,rc32,rc41,rc42,rc61,rc62,rc71,rc72,rc81,rc82,rc101,rc102,rc111,rc112;