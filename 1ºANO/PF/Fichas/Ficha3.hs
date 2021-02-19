-- PROBLEMAS MATEMÁTICOS
module Ficha3 where
import Data.List

--1
mult :: Int -> Int -> Int
mult x 0 = 0
mult x y = x + mult x (y-1)

mydiv :: Int -> Int -> Int
mydiv x y | x < y = 0 
          | otherwise = 1 + (mydiv (x - y) (y))
mymod :: Int -> Int -> Int
mymod x y | x < y = x
          | otherwise = x - y * div x y

power :: Int -> Int -> Int
power x 0 = 1
power x y = x * power x (y-1)


--2
type Polinomio = [Monomio]
type Monomio = (Float, Int)
p :: Polinomio
p = [(2,3), (3,4), (5,3), (4,5), (7,0)]

conta :: Int -> Polinomio -> Int
conta _ [] = 0
conta n ((c,e):t) = if n == e then 1 + conta n t else conta n t

grau :: Polinomio -> Int
grau [(c,e)] = e
grau ((c,e):t) = max e (grau t)

selgrau :: Int -> Polinomio -> Polinomio
selgrau g [] = []
selgrau g ((c,e):t) = if g == e then (c,e) : selgrau g t else selgrau g t

deriv :: Polinomio -> Polinomio
deriv [] = []
deriv ((c,e):t) | e > 0 = (c* (fromIntegral e),e-1) : deriv t 
                | otherwise = deriv t

calcula :: Float -> Polinomio -> Float
calcula x [] = 0
calcula x ((c,e):t) = c * x^e + calcula x t 

simp :: Polinomio -> Polinomio
simp [] = []
simp ((c,e):t) = if e == 0 then simp t else (c,e) : simp t

multp :: Monomio -> Polinomio -> Polinomio
multp (c,e) [] = []
multp (cm,em) ((c,e):t) = (cm*c,em+e) : multp (cm,em) t

normaliza :: Polinomio -> Polinomio
normaliza [] = []
normaliza (h:t) = aux h (normaliza t) where
    aux :: Monomio -> Polinomio -> Polinomio
    aux (c,e) [] = [(c,e)]
    aux (c,e) ((x,y):t) = if (e == y) then (c + x, e):t else (x,y) : (aux (c,e) t)

soma :: Polinomio -> Polinomio -> Polinomio
soma [] p = p
soma (h:t) p = soma t (aux h p) where
    aux :: Monomio -> Polinomio -> Polinomio
    aux (c,e) [] = [(c,e)]
    aux (c,e) ((x,y):t) = if (e == y) then (c + x, e):t else (x,y) : (aux (c,e) t)

produto :: Polinomio -> Polinomio -> Polinomio
produto [] p = []
produto p [] = []
produto [x] p = multp x p
produto (h:t) p = (multp h p) ++ (produto t p)

ordena :: Polinomio -> Polinomio
ordena [] = []
ordena (h:t) = aux h (ordena t) where
    aux p [] = [p]
    aux (c,e) ((x,y):t) = if (e < y) then (c,e):(x,y):t else (x,y) : (aux (c,e) t)

equiv :: Polinomio -> Polinomio -> Bool
equiv poli1 poli2 = (ordena (normaliza poli1)) == (ordena (normaliza poli2))


--3
type MSet a = [(a,Int)]

myunion :: (Eq a) => MSet a -> MSet a -> MSet a
myunion l [] = l
myunion l ((a,b):t) = myunion (aux (a,b) l) t where
    aux (w,z) [] = [(w,z)]
    aux (w,z) ((x,y):t) = if (w == x) then (x,(y + z)) : t else (x,y) : (aux (w,z) t)

myintersect :: (Eq a) => MSet a -> MSet a -> MSet a
myintersect [] _ = []
myintersect _ [] = []
myintersect (h:t) l = let result = aux h l in
     if (snd(result) == -1) then myintersect t l else result : myintersect t l where
        aux (x,y) [] = (x,-1)
        aux (x,y) ((a,b):t) = if (x == a) then if (y < b) then (x,y) else (x,b)
                                          else aux (x,y) t

diff :: (Eq a) => MSet a -> MSet a -> MSet a
diff [] l = l
diff l [] = l
diff (h:t) l = let result = aux h l [] in
    if ((snd(fst result)) == 0) then diff t (snd result) else (fst result) : diff t (snd result) where
        aux (x,y) [] n = ((x,y),n)
        aux (x,y) ((a,b):t) n = if (x == a) then if (y > b) then ((x,(y - b)), n ++ t) else ((x,(b - y)), n ++ t)
                                            else aux (x,y) t (n ++ [(a,b)])

myordena :: MSet a -> MSet a
myordena [] = []
myordena l = ordAux l [] where
    ordAux [] l = l
    ordAux (h:t) l = ordAux t (aux h l) where
        aux a [] = [a]
        aux (x,y) ((a,b):t) = if (y > b) then (a,b) : (aux (x,y) t) else (x,y) : (a,b) : t

moda :: MSet a -> [a]
moda [] = []
moda ((x,y):t) = aux t y [x] 
           where aux [] _ l = l
                 aux ((x,y):t) n l = if (n > y) then aux t n l
                                                else if (n == y) then aux t n (l ++ [x])
                                                                 else aux t y [x]