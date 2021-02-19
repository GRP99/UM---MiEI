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

seq_1_res1: t1 + 6 <= t7 + 100 - 100 * y1;
seq_1_res2: t7 + 6 <= t10 + 100 - 100 * y1;
seq_2_res1: t1 + 6 <= t10 + 100 - 100 * y2;
seq_2_res2: t10 + 8 <= t7 + 100 - 100 * y2;
seq_3_res1: t7 + 6 <= t1 + 100 - 100 * y3;
seq_3_res2: t1 + 6 <= t10 + 100 - 100 * y3;
seq_4_res1: t7 + 6 <= t10 + 100 - 100 * y4;
seq_4_res2: t10 + 8 <= t10 + 100 - 100 * y4;
seq_5_res1: t10 + 8 <= t1 + 100 - 100 * y5;
seq_5_res2: t1 + 6 <= t7 + 100 - 100 * y5;
seq_6_res1: t10 + 8 <= t7 + 100 - 100 * y6;
seq_6_res2: t7 + 6 <= t1 + 100 - 100 * y6;

so_1seq: y1 + y2 + y3 + y4 + y5 + y6 = 1;

bin y1,y2,y3,y4,y5,y6;