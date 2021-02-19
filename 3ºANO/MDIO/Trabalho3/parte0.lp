/* Objective function */
min: tf;

/* Variable bounds */
arco_01: t1>= t0 + 4 ;
arco_12: t2>= t1 + 6 ;
arco_23: t3>= t2 + 7 ;
arco_i0: t0>= ti + 0 ;
arco_04: t4>= t0 + 4 ;
arco_42: t2>= t4 + 9 ;
arco_3f: tf>= t3 + 2 ;
arco_i6: t6>= ti + 0 ;
arco_74: t4>= t7 + 6 ;
arco_67: t7>= t6 + 5 ;
arco_78: t8>= t7 + 6 ;
arco_610: t10>= t6 + 5 ;
arco_108: t8>= t10 + 8 ;
arco_1011: t11>= t10 + 8;
arco_43: t3>= t4 + 9;
arco_4f: tf>= t4 + 9;
arco_83: t3>= t8 + 4;
arco_8f: tf>= t8 + 4;
arco_11f: tf>= t11 + 7;