-- ÁRVORES BINÁRIAS COM CONTEÚDO NOS NÓS
module Ficha7 where

--1
data BTree a = Empty
             | Node a (BTree a) (BTree a)
instance Show a => Show (BTree a) where
    show Empty = "X"
    show (Node x e d) = "(" ++ (show e) ++ "-|" ++ (show x) ++ "|-" ++ (show d) ++ ")"
{-         1 
          / \
         0   3
            /
           2                
-}
ex = (Node 1 (Node 0 Empty Empty) (Node 3 (Node 2 Empty Empty) Empty))

altura :: BTree a -> Int
altura Empty = 0 
altura (Node _ e d) = 1 + max (altura e) (altura d)

contaNodos :: BTree a -> Int
contaNodos Empty = 0
contaNodos (Node _ e d) = 1 + contaNodos e + contaNodos d

folhas :: BTree a -> Int
folhas Empty = 0
folhas (Node _ Empty Empty) = 1
folhas (Node _ e d) = folhas e + folhas d

prune :: Int -> BTree a -> BTree a
prune 0 _ = Empty 
prune _ Empty = Empty
prune n (Node a e d) = if n > 0 then Node a (prune (n-1) e) (prune (n-1) d) else Empty

path :: [Bool] -> BTree a -> [a]
path [] (Node a _ _) = [a]
path _ Empty = []
path (h:t) (Node a e d) | h     = a: path t d
                        | not h = a: path t e

mirror :: BTree a -> BTree a
mirror Empty = Empty
mirror (Node v e d) = Node v (mirror d) (mirror e)

zipWhitBT :: (a -> b-> c) -> (BTree a) -> (BTree b) -> BTree c
zipWhitBT f (Node n1 e1 d1) (Node n2 e2 d2) = Node (f n1 n2) (zipWhitBT f e1 e2) (zipWhitBT f d1 d2)
zipWhitBT _ _ _ = Empty

unzipBT :: (BTree (a,b,c)) -> (BTree a, BTree b, BTree c)
unzipBT (Node (a,b,c) e d) = (Node a q z, Node b w x, Node c r v) 
                     where (q,w,r) = unzipBT e 
                           (z,x,v) = unzipBT d 
unzipBT Empty = (Empty,Empty,Empty)


--2
minimo :: Ord a => BTree a -> a
minimo Empty = undefined
minimo (Node a Empty Empty) = a
minimo (Node a e d) = minimo e

semMinimo :: Ord a => BTree a -> BTree a
semMinimo Empty = Empty
semMinimo (Node a Empty d) = d
semMinimo (Node a e d) = Node a (semMinimo e) d

minSmin :: Ord a => BTree a -> (a,BTree a)
minSmin Empty = undefined
minSmin (Node a Empty d) = (a,d)
minSmin (Node a e d) = let (x,y) = minSmin e
                       in  (x,Node a y d)

remove :: Ord a => a -> BTree a -> BTree a
remove _ Empty = Empty
remove n (Node a e d) | n < a  = Node a (remove n e) d
                      | n > a  = Node a e (remove n d)
                      | n == a = case d of 
                                   Empty -> Empty
                                   _ -> Node (fst (minSmin d)) e (snd (minSmin d))


--3
type Aluno = (Numero,Nome,Regime,Classificacao)
type Numero = Int
type Nome = String
data Regime = ORD | TE | MEL deriving Show
data Classificacao = Aprov Int
                   | Rep
                   | Faltou
    deriving Show
type Turma = BTree Aluno -- arvore binaria de procura (ordenada por numero)

inscNum :: Numero -> Turma -> Bool
inscNum _ Empty = False
inscNum n (Node (x,_,_,_) e d) | n == x = True
                               | n < x  = inscNum n e
                               | n > x  = inscNum n d

inscNome :: Nome -> Turma -> Bool 
inscNome _ Empty = False
inscNome n (Node (_,y,_,_) e d) = n == y || inscNome n e || inscNome n d

trabEst :: Turma -> [(Numero, Nome)]
trabEst Empty = []
trabEst (Node (x,y,TE,_) e d) = ((x,y):trabEst e) ++ (trabEst d)
trabEst (Node (x,y,_,_) e d) = (trabEst e) ++ (trabEst d) 

nota :: Numero -> Turma -> Maybe Classificacao
nota _ Empty = Nothing 
nota n (Node (x,_,_,w) e d) | n == x = Just w
                            | n < x = nota n e
                            | n > x = nota n d

percFaltas :: Turma -> Float
percFaltas t = let todos = contaNodos t
                   faltas = contaFaltas t
               in  (((fromIntegral faltas) / (fromIntegral todos)) * 100)
contaFaltas :: Turma -> Int
contaFaltas Empty = 0 
contaFaltas (Node (_,_,_,Faltou) e d) = 1 + (contaFaltas e) + (contaFaltas d)
contaFaltas (Node _ e d) = contaFaltas e + contaFaltas d

mediaAprov :: Turma -> Float
mediaAprov t = let soma = contaNota t
                   total = contaAprov t
               in ((fromIntegral soma) / (fromIntegral total))
contaNota :: Turma -> Int
contaNota Empty = 0 
contaNota (Node (_,_,_,Aprov x) e d) = x + (contaNota e) + (contaNota d)
contaNota (Node _ e d) = contaNota e + contaNota d
contaAprov :: Turma -> Int
contaAprov Empty = 0 
contaAprov (Node (_,_,_,Aprov x) e d) = 1 + (contaAprov e) + (contaAprov d)
contaAprov (Node _ e d) = contaAprov e + contaAprov d

aprovAv :: Turma -> Float
aprovAv t = let (a,b) = aux t in a / b 
           where aux Empty = (0,0)
                 aux (Node (_, _, _, Aprov x) lnode rnode) = (1 + al + ar, 1 + bl + br)
                                                           where (al,bl) = aux lnode
                                                                 (ar,br) = aux rnode
                 aux (Node (_, _, _, Rep) lnode rnode) = (0 + al + ar, 1 + bl + br) 
                                                       where (al,bl) = aux lnode
                                                             (ar,br) = aux rnode
                 aux (Node _ lnode rnode) = (0 + al + ar, 0 + bl + br) 
                                          where (al,bl) = aux lnode
                                                (ar,br) = aux rnode
