-- CLASSES DE TIPOS
module Ficha9 where 

--1
data Frac = F Integer Integer
exemplo01 = (F (-33) (-51))
exemplo1 = (F 50 (-5))

mdc :: Integer -> Integer -> Integer
mdc x y | x > y  = mdc (x-y) y
        | x < y  = mdc x (y-x)
        | x == y = x 
normaliza :: Frac -> Frac
normaliza (F n 0) = undefined
normaliza (F 0 d) = F 0 1
normaliza (F n d) = F ((signum d) * (n `div` m)) ((abs d) `div` m)     
                  where m = mdc (abs n) (abs d)

instance Eq Frac where 
         (==) x y = (a1 == a2) && (b1 == b2) 
              where (F a1 b1) = normaliza x
                    (F a2 b2) = normaliza y

instance Ord Frac  where
         compare (F n1 d1) (F n2 d2) = compare (n1 * d2) (d1 * n2)

instance Show Frac  where
         show (F n d) = "(" ++ show n ++ "/" ++ show d ++ ")"     

instance Num Frac where
         (+) (F n1 d1) (F n2 d2) = normaliza (F ((n1 * d2) + (n2 * d1)) (d1 * d2))
         (-) (F n1 d1) (F n2 d2) = normaliza (F ((n1 * d2) - (n2 * d1)) (d1 * d2))
         (*) (F n1 d1) (F n2 d2) = normaliza (F (n1 * n2) (d1 * d2))
         abs    (F n d) = (F (abs n) (abs d))
         negate (F n d) = (F (-n) (-d))
         fromInteger x  = (F x 1)
         signum (F n d) = let (F a b) = normaliza (F n d) in if (a == 0) then 0 else if (a > 0) then 1 else (-1)

maiorqueDobro :: Frac -> [Frac] -> [Frac]
maiorqueDobro _ [] = []
maiorqueDobro f (h:ts) = if (h > (2 * f)) then h : (maiorqueDobro f ts) else (maiorqueDobro f ts)
{- signum => it returns -1 for negative numbers, 0 for zero, and 1 for positive numbers
   `div`  => returns how many times the first number can be divided by the second one
   abs    => absolute value of the number
-}


--2
data Exp a = Const a | Simetrico (Exp a) | Mais (Exp a) (Exp a) | Menos (Exp a) (Exp a) | Mult (Exp a) (Exp a)
exemplo02  = (Mais (Const 3) (Menos (Const 2) (Const 5)))
exemplo2   = (Mais (Const 7) (Menos (Const 10) (Const 5)))
 
showExp :: Show a => Exp a -> String
showExp (Const x)     = show x
showExp (Simetrico e) = "-" ++ showExp e
showExp (Mais e d)    = "(" ++ showExp e ++ "+" ++ showExp d ++ ")"
showExp (Menos e d)   = "(" ++ showExp e ++ "-" ++ showExp d ++ ")"
showExp (Mult e d)    = "(" ++ showExp e ++ "*" ++ showExp d ++ ")"
instance Show a => Show (Exp a) where 
         show a = showExp a

calcula :: Num a => Exp a -> a 
calcula (Const x)     = x
calcula (Simetrico x) = calcula x * (-1)
calcula (Mais x y)    = calcula x + calcula y
calcula (Menos x y)   = calcula x - calcula y
calcula (Mult x y)    = calcula x * calcula y
eqExp :: (Eq a, Num a) => Exp a -> Exp a -> Bool
eqExp x y = (calcula x) == (calcula y)
instance (Eq a, Num a) => Eq (Exp a) where
    (==) x y = (calcula x) == (calcula y)

instance (Ord a, Num a) => Num (Exp a) where
    x + y = Const (calcula x + calcula y)
    x - y = Const (calcula x - calcula y)
    x * y = Const (calcula x * calcula y)
    negate x = Simetrico x
    fromInteger x = Const (fromInteger x)
    abs x = if (calcula x) < 0 then (Simetrico x) else x
    signum x | (calcula x == 0) = (Const 0) 
             | (calcula x > 0)  = (Const 1)
             | otherwise        = (Const (-1))

--3
data Movimento = Credito Float | Debito Float
data Data = D Int Int Int
data Extracto = Ext Float [(Data, String, Movimento)]
exemplo03 = Ext 300 [(D 5 4 2010,"DEPOSITO",Credito 2000),(D 10 8 2010,"COMPRA",Debito 37.5),(D 1 9 2010,"LEV",Debito 60),(D 7 1 2011,"JUROS",Credito 100),(D 22 1 2011,"ANUIDADE",Debito 8)]
exemplo3  = D 13 6 1999

instance Eq Data where
        (==) (D d1 m1 a1) (D d2 m2 a2) = (a2 == a1) && (m2 == m1) && (d2 == d1)
instance Ord Data where
        compare (D d1 m1 a1) (D d2 m2 a2)  | ((a1 == a2) && (m1 == m2) && (d1 == d2)) = EQ
                                           | ((a2 > a1) || ((a2 == a1) && (m2 > m1)) || ((a2 == a1) && (m2 == m1) && (d2 > d1))) = GT 
                                           | otherwise = LT

instance Show Data where
    show (D x y z) = (show x) ++ "/" ++ (show y) ++ "/" ++ (show z)

ordena :: Extracto -> Extracto
ordena (Ext s m) = Ext s (aux m [])
                 where aux [] new = new
                       aux (h:t) new = aux t (insere h new)
                       insere x [] = [x]
                       insere (d,s,m) ((dat,str,mov):t) = if (d > dat) then (dat,str,mov) : (insere (d,s,m) t) else (d,s,m) : (dat,str,mov) : t

creDeb :: Extracto -> (Float,Float)
creDeb (Ext _ []) = (0,0)
creDeb (Ext v ((_, _, Credito x):ts)) = let (a,b) = creDeb (Ext v ts) in (x + a, b)
creDeb (Ext v ((_, _, Debito y):ts)) = let (a,b) = creDeb (Ext v ts) in (a, b + y)
saldo :: Extracto -> Float
saldo (Ext inicial movimentos) = let (a,b) = creDeb (Ext inicial movimentos) in inicial + a - b

instance Show Extracto where
        show (Ext s m) = "Saldo anterior: " ++ (show s) ++ "\n----------------------------------------------\nData\t\tDescricao\tCredito\tDebito\n----------------------------------------------\n" ++ (concat (map aux m)) ++ "----------------------------------------------\nSaldo actual: " ++ (show (saldo (Ext s m)))
                      where aux (dat,str,Credito x) = if (length str) < 7 then (show dat) ++ "\t" ++ str ++ "\t\t" ++ (show x) ++ "\n" else (show dat) ++ "\t" ++ str ++ "\t" ++ (show x) ++ "\n"
                            aux (dat,str,Debito x)  = if (length str) < 7 then (show dat) ++ "\t" ++ str ++ "\t\t\t" ++ (show x) ++ "\n" else (show dat) ++ "\t" ++ str ++ "\t\t" ++ (show x) ++ "\n"
