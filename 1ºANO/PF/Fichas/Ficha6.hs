-- FUNÇÕES DE ORDEM SUPERIOR
module Ficha6 where
import Data.Char

--1
myany :: (a->Bool) -> [a] -> Bool
myany f [] = False
myany f (h:t) = if f h then True else myany f t

myzipWith :: (a->b->c) -> [a] -> [b] -> [c] 
myzipWith f _ [] = []
myzipWith f [] _ = []
myzipWith f (h:t) (x:y) = f h x : myzipWith f t y

mytakeWhile :: (a->Bool) -> [a] -> [a]
mytakeWhile f [] = [] 
mytakeWhile f (h:t) = if f h then h : mytakeWhile f t else []

mydropWhile :: (a->Bool) -> [a] -> [a]
mydropWhile f [] = []
mydropWhile f (h:t) = if f h then mydropWhile f t else (h:t)

myspan :: (a->Bool) -> [a] -> ([a],[a])
myspan f [] = ([],[]) 
myspan f (h:t) | f h = (h:x, y)
               | otherwise = ([],h:t)
            where (x,y) = myspan f t

mydeleteBy :: (a -> a -> Bool) -> a -> [a] -> [a]
mydeleteBy f x [] = []
mydeleteBy f x (h:t) = if f x h then mydeleteBy f x t else h: mydeleteBy f x t

mysortOn :: Ord b => (a->b) -> [a] -> [a]
mysortOn f [] = []
mysortOn f (h:t) = myinsertOn f h (mysortOn f t)
                where myinsertOn p x [] = [x]
                      myinsertOn p x (h:t) | p h <= p x = h:x:t
                                           | otherwise = x : myinsertOn p x t


--2
type Polinomio = [Monomio]
type Monomio = (Float,Int)
exemplo2 = [(2,3), (3,4), (5,3), (4,5)]

selgrau :: Int -> Polinomio -> Polinomio
selgrau g p = filter (\ (c,e) -> e == g) p 

conta :: Int -> Polinomio -> Int
conta g p = length $filter (\ (c,e) -> e == g) p

grau :: Polinomio -> Int
grau p = maximum $map (\ (c,e) -> e) p

deriv :: Polinomio -> Polinomio
deriv p = map aux p
        where aux (c,e) = (c * fromIntegral e, e-1)


--3
type Mat a = [[a]]
exemplo3 = [[1,2,3],[0,4,5],[0,0,6]]

dimOk :: Mat a -> Bool
dimOk [] = False
dimOk (h:t) = (aux (length h) t)
           where aux x [] = if (x > 0) then True else False
                 aux x (h:t) = if (x == (length h)) then (aux x t) else False

dimMat :: Mat a -> (Int,Int)
dimMat m = if dimOk m then (length m, length (head m)) else undefined

addMat :: Num a => Mat a -> Mat a -> Mat a
addMat [] [] = []
addMat m1 m2 = zipWith (zipWith (+)) m1 m2

transpose :: Mat a -> Mat a
transpose [] = []
transpose m = if (length (head m) > 1) then (map head m) : transpose (map tail m) else [(map head m)]

multMat :: Num a => Mat a -> Mat a -> Mat a
multMat [] _ = []
multMat m1 m2 = if snd (dimMat m1) == fst (dimMat m2) then (map (\x -> (sum (zipWith (*) (head m1) x))) (transpose m2)) : (multMat (tail m1) m2) else undefined
