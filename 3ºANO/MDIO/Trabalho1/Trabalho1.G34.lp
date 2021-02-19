/* Objective function */
min: 3 xOA + 3 xAB + 4 xBC + 12 xCD +4 xDE + 4 xEF + 10 xFO + 3 xAG + 6 xGI + 4 xIH + 2 xHG + 2 xJH + 4 xEJ + 4 xJB + 3 xJK + 3 xLK + 2 xKM + 5 xML +2 xCL +3 xMD + 2 xIF ;

/* Variable bounds */
xOA = xFO;
xOA = xAB + xAG;
xBC = xAB + xJB;
xBC = xCD + xCL;
xDE = xCD + xMD;
xDE = xEF + xEJ;
xFO = xEF + xIF;
xGI = xAG + xHG;
xGI = xIF + xIH;
xHG = xJH + xIH;
xKM = xLK + xJK;
xKM = xML + xMD;
xLK = xCL + xML;

xOA>=1;
xAB>=1;
xAG>=1;
xBC>=1;
xCD>=1;
xCL>=1;
xDE>=1;
xEF>=1;
xEJ>=1;
xFO>=1;
xGI>=1;
xHG>=1;
xIF>=1;
xIH>=1;
xJB>=1;
xJH>=1;
xJK>=1;
xKM>=1;
xLK>=1;
xMD>=1;
xML>=1;