-- OUTROS TIPOS DE ÁRVORES
module Ficha8 where

--1
data ExpInt = Const Int
            | Simetrico ExpInt
            | Mais ExpInt ExpInt
            | Menos ExpInt ExpInt
            | Mult ExpInt ExpInt
exemplo1 :: ExpInt
exemplo1 = (Mais (Const 3) (Menos (Const 2) (Const 5)))

calcula :: ExpInt -> Int 
calcula (Const x)     = x
calcula (Simetrico x) = calcula x * (-1)
calcula (Mais x y)    = calcula x + calcula y
calcula (Menos x y)   = calcula x - calcula y
calcula (Mult x y)    = calcula x * calcula y

infixa :: ExpInt -> String
infixa (Const x)     = show x
infixa (Simetrico x) = "(-" ++ (infixa x) ++ ")"
infixa (Mais x y)    = "("  ++ (infixa x) ++ " + " ++ (infixa y) ++ ")"
infixa (Menos x y)   = "("  ++ (infixa x) ++ " - " ++ (infixa y) ++ ")"
infixa (Mult x y)    = "("  ++ (infixa x) ++ " * " ++ (infixa y) ++ ")"

posfixa :: ExpInt -> String
posfixa (Const x)     = show x
posfixa (Simetrico x) = posfixa x ++ " Sim "
posfixa (Mais x y)    = posfixa x ++ " " ++ posfixa y ++ " + "
posfixa (Menos x y)   = posfixa x ++ " " ++ posfixa y ++ " - "
posfixa (Mult x y)    = posfixa x ++ " " ++ posfixa y ++ " * "


--2
data RTree a = R a [RTree a]
             deriving Show
exemplo2 = R 5 [R 3 [R 1 [], R 4[]], R 7 []]
{-       5
        / \
       3   7
      / \
     1   4
-}
soma :: Num a => RTree a -> a
soma (R x []) = x
soma (R x n)  = x + (sum (map soma n)) 

altura :: RTree a -> Int
altura (R v []) = 1
altura (R _ n)  = 1 + maximum (map altura n)

prune :: Int -> RTree a -> RTree a
prune 1 (R a n) = R a []
prune x (R a n) = R a (map (prune (x-1)) n)

mirror :: RTree a -> RTree a
mirror (R a n) = R a (map mirror (reverse n))

postorder :: RTree a -> [a]
postorder (R a n) = foldr (++) [a] (map postorder n)

--3
data BTree a = Empty | Node a (BTree a) (BTree a)
            deriving Show
data LTree a = Tip a | Fork (LTree a) (LTree a)
            deriving Show
exemplo3 = Fork (Fork (Tip 1) (Tip 2)) (Fork (Tip 3) (Tip 4))
{-      o
      /   \
     o     o
    / \   / \
   1   2 3   4
-}
ltSum :: Num a => LTree a -> a
ltSum (Tip a) = a 
ltSum (Fork x y) = (ltSum x) + (ltSum y)

listaLT :: LTree a -> [a]
listaLT (Tip a) = [a]
listaLT (Fork x y) = (listaLT x) ++ (listaLT y)

ltHeight :: LTree a -> Int
ltHeight (Tip a) = 1 
ltHeight (Fork x y) = 1 + (max (ltHeight x) (ltHeight y))

--4
data FTree a b = Leaf b | No a (FTree a b) (FTree a b)
     deriving Show
exemplo4 = (No 1 (No 0 (Leaf 1) (Leaf 2)) (No 3 (Leaf 3) (Leaf 4)))
exemplo04 = (Node 1 (Node 0 Empty Empty) (Node 3 Empty Empty),Fork (Fork (Tip 1) (Tip 2)) (Fork (Tip 3) (Tip 4)))
{-          1 
          /    \
         0      3
        / \    / \
       1   2  3   4         

-}
splitFTree :: FTree a b -> (BTree a, LTree b)
splitFTree (Leaf x) = (Empty,Tip x)
splitFTree (No x y z) = let (y1,y2) = splitFTree y
                            (z1,z2) = splitFTree z
                        in ((Node x y1 z1), (Fork y2 z2))

-- joinTrees