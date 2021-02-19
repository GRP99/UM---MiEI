import Data.Char
import Data.List
import Data.Either
import Data.Maybe

myenumfromto :: Int -> Int -> [Int]
myenumfromto a b = if a <= b then a : (myenumfromto (a+1) b) else []

myenumfromthento :: Int -> Int -> Int -> [Int]
myenumfromthento a b c 
                       | a>c && a<b = []
                       | c>a && a>b = []
                       | a==b && a<=c = repeat a
                       | a==b && a>c = []
                       | a<b && b>c = [a]
                       | otherwise = a:myenumfromthento b (b+(b-a)) c

maismais :: [a] -> [a] -> [a]
maismais [] l = l
maismais (h:t) l = h : (maismais t l)

exex :: [a] -> Int -> a 
exex (h:t) 0 = h
exex (h:t) i = exex t (i-1)

myreverse :: [a] -> [a]
myreverse [] = []
myreverse (h:t) = myreverse t ++ [h]

mytake :: Int -> [a] -> [a]
mytake n [] = []
mytake 0 l = []
mytake n (h:t) = h: mytake (n-1) t

mydrop :: Int -> [a] -> [a]
mydrop n [] = []
mydrop 0 l = l
mydrop n (h:t) = drop (n-1) t

myzip :: [a] -> [b] -> [(a,b)]
myzip l [] = []
myzip [] l = []
myzip (x:xs) (y:ys) = (x,y) : zip xs ys

myelem :: Eq a => a -> [a] -> Bool
myelem n [] = False
myelem n (h:t) = if n == h then True else myelem n t

myreplicate :: Int -> a -> [a]
myreplicate x y = if x > 0 then y : (myreplicate (x-1) y) else []

myintersperse :: a -> [a] -> [a]
myintersperse x [] = []
myintersperse x [l] = [l] 
myintersperse x (h:t) = h : x : myintersperse x t

mygroup :: Eq a => [a] -> [[a]]
mygroup [] = []
mygroup l = auxmygroup l : mygroup (drop (length (auxmygroup l)) l)
    where auxmygroup :: Eq a => [a] -> [a]
          auxmygroup [l] = [l]
          auxmygroup (h:x:t) = if h==x then h: auxmygroup (x:t) else [h]

myconcat :: [[a]] -> [a]
myconcat [] = []
myconcat (h:t) = h ++ myconcat t

myinits :: [a] -> [[a]]
myinits l = initsaux 0 l
          where initsaux :: Int -> [a] -> [[a]]
                initsaux _ [] = [[]]
                initsaux x l =if x < length l then ((take x l) : (initsaux (x+1) l)) else [l]

mytails :: [a] -> [[a]]
mytails l = tailsaux 0 l
          where tailsaux :: Int -> [a] -> [[a]]
                tailsaux x l = if x < length l then ((drop x l) : (tailsaux (x+1) l)) else [[]]

myisprefixof :: Eq a => [a] -> [a] -> Bool
myisprefixof l1 l2 = l1 == (take (length l1) l2)

myissuffixof :: Eq a => [a] -> [a] -> Bool
myissuffixof l1 l2 = l1 == drop (length l1 - 1) l2

myissubsequenceof :: Eq a => [a] -> [a] -> Bool
myissubsequenceof [] _ = True
myissubsequenceof _ [] = False
myissubsequenceof (x:xs) (y:ys) = if x == y then myissubsequenceof xs ys else myissubsequenceof (x:xs) ys

myelemindices :: Eq a => a -> [a] -> [Int]
myelemindices _ [] = []
myelemindices x l = auxelemindices 0 x l
              where auxelemindices :: Eq a => Int -> a -> [a] -> [Int]
                    auxelemindices _ _ [] = []
                    auxelemindices i x (h:t) | x == h = i: auxelemindices (i+1) x t
                                             | otherwise = auxelemindices (i+1) x t

mynub :: Eq a => [a] -> [a]
mynub [] = []
mynub (h:t) = h : mynub (nubaux h t)
            where nubaux :: Eq a => a -> [a] -> [a]
                  nubaux _ [] = []
                  nubaux x (h:t) = if x == h then nubaux x t else h : nubaux x t 

mydelete :: Eq a => a -> [a] -> [a]
mydelete _ [] = []
mydelete x (h:t) = if x == h then t else h : mydelete x t 

barrabarra :: Eq a => [a] -> [a] -> [a]
barrabarra l [] = l
barrabarra [] _ = []
barrabarra l (h:t) = barrabarra (delete h l) t 

myunion :: Eq a => [a] -> [a] -> [a]
myunion [] l1 = mynub l1 
myunion l [] = l
myunion l (h:t) = if elem h l then myunion l t else myunion ( maismais l [h]) t 

myintersect :: Ord a => [a] -> [a] -> [a]
myintersect [] _ = []
myintersect _ [] = []
myintersect (h:t) l = if elem h l then h : (myintersect t l) else myintersect t l

myinsert :: Ord a => a -> [a] -> [a]
myinsert x [] = [x]
myinsert x (h:t) = if x < h then x : h : t else h : myinsert x t

myunwords :: [String] -> String
myunwords [] = ""
myunwords [x] = x
myunwords (h:t) = (h ++ " ") ++ myunwords t

myunlines :: [String] -> String
myunlines [] = []
myunlines [x] = x ++ "\n"
myunlines (h:t) = (h ++ "\n") ++ myunlines t

pmaior :: Ord a => [a] -> Int
pmaior [] = undefined
pmaior (h:t) = auxpmaior 0 0 h t
            where auxpmaior :: Ord a => Int -> Int -> a -> [a] -> Int
                  auxpmaior i _ _ [] = i
                  auxpmaior i j x (h:t) = if x >= h then auxpmaior i (j+1) x t else auxpmaior (j+1) (j+1) h t

temrepetidos :: Eq a => [a] -> Bool
temrepetidos [] = False
temrepetidos (h:t) = elem h t || temrepetidos t

algarismos :: [Char] -> [Char]
algarismos [] = []
algarismos (h:t) = if isDigit h then h : algarismos t else algarismos t

posimpares :: [a] -> [a]
posimpares [] = []
posimpares [x] = []
posimpares (h:x:t) = x : posimpares t

pospares :: [a] -> [a]
pospares [] = []
pospares [x] = [x]
pospares (h:x:t) = h : pospares t

issorted :: Ord a => [a] -> Bool
issorted [] = True
issorted [x] = True
issorted (h:h1:t) = h <= h1 && issorted (h1:t)

isort :: Ord a => [a] -> [a]
isort [] = []
isort (h:t) = insert h (isort t)

menor :: String -> String -> Bool
menor _ [] = False
menor [] _ = True
menor (h:t) (h1:t1) | ord(h) < ord(h1) = True
                    | ord(h) > ord(h1) = False
                    | otherwise = menor t t1

elemmset ::Eq a => a -> [(a,Int)] -> Bool
elemmset _ [] = False
elemmset x (h:t) = x == (fst h) || elemmset x t

lengthmset :: [(a,Int)] -> Int
lengthmset [] = 0 
lengthmset (h:t) = snd (h) + lengthmset t

convertmset :: [(a,Int)] -> [a]
convertmset [] = []
convertmset ((x,y):t) = ((replicate y x) ++ (convertmset t))

inseremset :: Eq a => a -> [(a,Int)] -> [(a,Int)]
inseremset a [] = [(a,1)]
inseremset a ((x,y):t) = if a == x then ((x,y + 1) : t) else ((x,y) : (inseremset a t))

removemset :: Eq a => a -> [(a,Int)] -> [(a,Int)]
removemset _ [] = []
removemset a ((x,y):t) = if a == x then (if y==1 then t else ((x,y-1):t)) else ((x,y) : removemset a t)

constroimset :: Ord a => [a] -> [(a,Int)]
constroimset [] = []
constroimset x = constroimsetaux [] x 
constroimsetaux :: Eq a => [(a,Int)] -> [a] -> [(a,Int)]
constroimsetaux x [] = x 
constroimsetaux x (h:t) = constroimsetaux (inseremset h x) t

mypartitioneithers :: [Either a b] -> ([a], [b])
mypartitioneithers [] = ([],[])
mypartitioneithers l = (auxlefts l, auxrights l)
                   where auxlefts [] = []
                         auxlefts (Left x:t) = x : auxlefts t
                         auxlefts (Right x:t) = auxlefts t
                         auxrights [] = []
                         auxrights (Left x:t) = auxrights t
                         auxrights (Right x:t) = x :auxrights t

mycatmaybes :: [Maybe a] -> [a]
mycatmaybes [] = []
mycatmaybes (Just a : t) = a : mycatmaybes t
mycatmaybes (Nothing : t) = mycatmaybes t


data Movimento = Norte | Sul | Este | Oeste
               deriving Show

posicao :: (Int,Int) -> [Movimento] -> (Int,Int)
posicao x [] = x
posicao (x,y) (Norte:t) = posicao (x,y + 1) t
posicao (x,y) (Sul:t)   = posicao (x,y - 1) t
posicao (x,y) (Oeste:t) = posicao (x - 1, y) t
posicao (x,y) (Este:t)  = posicao (x + 1, y) t

caminho :: (Int,Int) -> (Int,Int) -> [Movimento]
caminho (xi,yi) (xf,yf) | xi < xf = Este: caminho (xi+1,yi) (xf,yf)
                        | xi > xf = Oeste: caminho (xi-1,yi) (xf,yf)
                        | yi < yf = Norte: caminho (xi,yi+1) (xf,yf)
                        | yi > yf = Sul: caminho (xi,yi-1) (xf,yf)
                        | otherwise = [] 

vertical :: [Movimento] -> Bool
vertical [] = True
vertical (Norte:t) = True && vertical t
vertical (Sul:t) = True && vertical t
vertical (_:t) = False

data Posicao = Pos Int Int
             deriving Show

maiscentral :: [Posicao] -> Posicao
maiscentral [] = Pos 0 0
maiscentral (h:t) = auxmaiscentral t h
            where auxmaiscentral :: [Posicao] -> Posicao -> Posicao
                  auxmaiscentral (Pos x y : t) (Pos xmin ymin) = if xmin^2 + ymin^2 > x^2 + y^2 then auxmaiscentral t (Pos x y) else auxmaiscentral t (Pos xmin ymin)
                  auxmaiscentral [] min = min

vizinhos :: Posicao -> [Posicao] -> [Posicao]
vizinhos (Pos x y) (Pos x1 y1:t) = if (y==y1 && (x==x1+1)) || (y==y1 && (x==x1-1)) || (x==x1 && (y==y1+1)) || (x==x1 && (y==y1-1)) then (Pos x1 y1) : (vizinhos (Pos x y) t) else (vizinhos (Pos x y) t)
vizinhos _ [] = []

mesmaordenada :: [Posicao] -> Bool
mesmaordenada ((Pos x1 y1): (Pos x2 y2): t) = if y1 == y2 then mesmaordenada ((Pos x2 y2):t) else False
mesmaordenada [] = True
mesmaordenada [x] = True

data Semaforo = Verde | Amarelo | Vermelho
              deriving Show

interseccaook :: [Semaforo] -> Bool
interseccaook l = auxinterseccaook l False
             where auxinterseccaook :: [Semaforo] -> Bool -> Bool
                   auxinterseccaook (Vermelho:t) f = auxinterseccaook t f
                   auxinterseccaook ( _ : t ) False = auxinterseccaook t True
                   auxinterseccaook ( _ : t ) True = False
                   auxinterseccaook [] _ = True