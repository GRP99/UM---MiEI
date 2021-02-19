-- | FUNÇÕES RECURSIVAS SOBRE LISTAS
module Ficha2 where
import Data.Char

--2
-- | Recebe uma lista e produz a lista em que cada elemento é o dobro do valor correspondente na lista de entrada. 
dobros :: [Float] -> [Float]
dobros [] = []
dobros (h:t) = h * 2 : dobros t

-- | Calcula o número de vezes que um caracter ocorre numa string. 
numOcorre :: Char -> String -> Int 
numOcorre c [] = 0
numOcorre c (h:t) = if c == h then 1 + numOcorre c t else numOcorre c t 

-- | Testa se uma lista só tem elementos positivos.
positivos :: [Int] -> Bool 
positivos [] = True 
positivos (h:t) = (h >= 0) && (positivos t)

-- | Retira todos os elementos não positivos de uma lista de inteiros.
soPos :: [Int] -> [Int]
soPos [] = []
soPos (h:t) = if h > 0 then h: (soPos t) else soPos t

-- | Soma todos os números negativos da lista de entrada. 
somaNeg :: [Int] -> Int
somaNeg [] = 0
somaNeg (h:t) = if h < 0 then h + somaNeg t else somaNeg t

-- | Devolve os últimos três elementos de uma lista. Se a lista de entrada tiver menos de três elementos, devolve a própria lista.
tresUlt :: [a] -> [a]
tresUlt (x:xs) = if length (x:xs) <= 3 then (x:xs) else tresUlt xs

-- | Recebe uma lista de pares e devolve a lista com as primeiras componentes desses pares.
primeiros :: [(a,b)] -> [a]
primeiros [] = []
primeiros ((x,y):t) = x : ( primeiros t )


--3
-- | Recebe uma lista de caracteres, e selecciona dessa lista os caracteres que são algarismos. 
soDigitos :: [Char] -> [Char]
soDigitos [] = []
soDigitos (h:t) = if isDigit h then h : soDigitos t else soDigitos t

-- | Recebe uma lista de caracteres, e conta quantos desses caracteres são letras minúsculas.
minusculas :: [Char] -> Int
minusculas [] = 0
minusculas (h:t) = if isLower h then 1 + minusculas t else minusculas t 

-- | Recebe uma string e devolve uma lista com os algarismos que occorem nessa string, pela mesma ordem.
nums :: String -> [Int] 
nums [] = []
nums (h:t) = if isDigit h then digitToInt h : nums t else nums t 


--4
-- | Calcula a lista das segundas componentes dos pares. 
segundos :: [(a,b)] -> [b]
segundos [] = []
segundos ((x,y): t) = y : ( segundos t )

-- | Testa se um elemento aparece na lista como primeira componente de algum dos pares.
nosPrimeiros :: (Eq a) => a -> [(a,b)] -> Bool
nosPrimeiros _ [] = False
nosPrimeiros a ((x,y):t) = a == x || nosPrimeiros a t

{- | Calcula a menor primeira componente.
  
  minFst [(10,21), (3, 55), (66,3)]
  = 3 
-}
minFst :: (Ord a) => [(a,b)] -> a
minFst [(x,y)] = x
minFst ((x,y):t) = min x (minFst t)

{- | Calcula a segunda componente associada à menor primeira componente.    
	
	sndMinFst [(10,21), (3, 55), (66,3)]
	= 55 
-}
sndMinFst :: (Ord a) => [(a,b)] -> b
sndMinFst [(x,y)] = y
sndMinFst ((x,y):t) = if min x (minFst t) == x then y else sndMinFst t

{- | Soma uma lista de triplos componente a componente.
	
	sumTriplos [(2,4,11), (3,1,-5), (10,-3,6)] 
	= (15,2,12) 
-}
sumTriplos :: (Num a, Num b, Num c) => [(a,b,c)] -> (a,b,c)
sumTriplos [(x,y,z)] = (x,y,z)
sumTriplos ((x,y,z):t) = aux (x,y,z) (sumTriplos t)
    where
         aux (x,y,z) (xs,ys,zs) = (x+xs, y+ys,z+zs)

{- | Calcula o máximo valor da soma das componentes de cada triplo de uma lista. 
	
	maxTriplo [(10,-4,21), (3, 55,20), (-8,66,4)] 
	= 78
-}
maxTriplo :: (Ord a, Num a) => [(a,a,a)] -> a
maxTriplo [(x,y,z)] = x + y + z
maxTriplo ((x,y,z):t) = max (x + y + z) (maxTriplo t)