--FUNÇÕES NÃO RECURSIVAS
module Ficha1 where
import Data.Char

--1
perimetro :: Double -> Double
perimetro raio = 2 * 3.14 * raio

dist :: (Double, Double) -> (Double, Double) -> Double
dist (x1, y1) (x2,y2) = sqrt ((x1-x2)^2 + (y1-y2)^2)

primUlt :: [a] -> (a,a)
primUlt l = (head l, last l)

multiplo :: (Integer,Integer) -> Bool 
multiplo (m,n) = mod m n == 0

truncaImpar :: [a] -> [a] 
truncaImpar l = if ( mod (length l) 2 == 0 ) then l else tail l

max2 :: (Integer, Integer) -> Integer
max2 (x, y) = if x<y then y else x

max3 :: (Integer, Integer, Integer) -> Integer
max3 (x, y, z) = if (x>=y)&&(x>=z) then x else 
                 if y>z then y else z   


--2
nRaizes :: (Floating a, Ord a) => a -> a -> a -> Int
nRaizes a b c | delta > 0   = 2
              | delta == 0  = 1
              | delta < 0   = 0
            where delta = (b^2) - (4 * a * c)
            
raizes :: (Floating a, Ord a) => a -> a -> a -> [a]
raizes a b c | nR == 0  = []
             | nR == 1  = [(-b) / (2 * a)]
             | nR == 2  = [((-b) + sqrt((b**2) - (4 * a * c))) / (2 * a), ((-b) - sqrt((b**2) - (4 * a * c))) / (2 * a)]
            where nR = nRaizes a b c


--3
type Hora = (Int,Int)

horaValida :: Hora -> Bool
horaValida (h,m) = (h >= 0) && (h < 24) && (m >= 0) && (m < 60)

horaDepois :: Hora -> Hora -> Bool
horaDepois (a,b) (x,y) = (a < x) || ((a == x) && (b < y))

hora2Mins :: Hora -> Int
hora2Mins (h,m) = (h * 60) + m

mins2Hora :: Int -> Hora
mins2Hora m = (div m 60, mod m 60)

diferHoras :: Hora -> Hora -> Int
diferHoras a b = if (horaDepois a b) then (hora2Mins b) - (hora2Mins a) else (hora2Mins a) - (hora2Mins b)

adicionaMinutos :: Hora -> Int -> Hora
adicionaMinutos hora mins = mins2Hora ((hora2Mins hora) + mins)


--5
data Semaforo = Verde | Amarelo | Vermelho 
    deriving (Show,Eq)

next :: Semaforo -> Semaforo
next  sem | sem  == Verde = Amarelo
          | sem  == Amarelo = Vermelho
          | sem  == Vermelho = Verde

stop :: Semaforo -> Bool    
stop Vermelho = True 
stop _ = False

safe :: Semaforo -> Semaforo -> Bool 
safe sem1 sem2 | sem1 == Vermelho && sem2 == Verde = True
               | otherwise = False
         

--6
data Ponto = Cartesiano Double Double | Polar Double Double 
           deriving (Show, Eq)
exemplo6  = Cartesiano (-1) 0
exemplo06 = Polar 1 pi

posx :: Ponto -> Double
posx (Cartesiano x y) = x 
posx (Polar r a) = r * cos a

posy :: Ponto -> Double
posy (Cartesiano x y) = y 
posy (Polar r a) = r * sin a

raio :: Ponto -> Double
raio (Polar r a) = r
raio (Cartesiano x y) = sqrt (x^2 + y^2)

angulo :: Ponto -> Double
angulo (Polar r a) = a
angulo (Cartesiano x y) | x == 0 && y > 0 = pi/2
                        | x == 0 && y < 0 = pi/2
                        | y == 0 && x > 0 = 0
                        | y == 0 && x < 0 = pi
                        | otherwise       = atan(y/x)

dist2 :: Ponto -> Ponto -> Double
dist2 (Cartesiano xa ya) (Cartesiano xb yb) = sqrt (((xb - xa)^2) + ((yb-ya)^2))
dist2 (Cartesiano xa ya) (Polar xb yb)      = sqrt ((((posx (Polar xb yb)) - xa)^2) + (((posy (Polar xb yb)) - ya)^2))
dist2 (Polar xa ya)      (Cartesiano xb yb) = sqrt (((xb - (posx (Polar xa ya)))^2) + ((yb - (posy (Polar xa ya)))^2))
dist2 (Polar xa ya)      (Polar xb yb)      = sqrt ((((posx (Polar xb yb)) - (posx (Polar xa ya)))^2) + (((posy (Polar xb yb)) - (posy (Polar xa ya)))^2))


--8
myisLower :: Char -> Bool
myisLower c = (c >= 'a') && (c <= 'z')

myisDigit :: Char -> Bool
myisDigit c = (c >= '0') && (c <= '9')

myisAlpha :: Char -> Bool
myisAlpha c = ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))

mytoUpper :: Char -> Char
mytoUpper c = if ((c >= 'a') && (c <= 'z')) then chr ((ord c) - 32) else c

myintToDigit :: Int -> Char
myintToDigit x | ((x >= 0) && (x <= 9)) = chr ((ord '0') + x)

mydigitToInt :: Char -> Int
mydigitToInt c | ((c >= '0') && (c <= '9')) = (ord c) - (ord '0')