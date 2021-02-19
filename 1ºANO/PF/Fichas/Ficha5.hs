-- LISTAS POR COMPREENSÃO E OPTIMIZAÇÃO COM TUPLING E ACUMULADORES
module Ficha5 where
import Data.Char

--3
mydigitAlpha :: String -> (String,String)
mydigitAlpha [] = ([],[])
mydigitAlpha (h:t) | isDigit h = (h:ld, ll)
                   | isAlpha h = (ld, h:ll) 
                   | otherwise = (ld,ll)
                where (ld,ll)  = mydigitAlpha t


--4
mynzp :: [Int] -> (Int,Int,Int)
mynzp [] = (0,0,0)
mynzp (h:t) | h < 0     = (n+1,z,p)
            | h == 0    = (n,z+1,p)
            | otherwise = (n,z,p+1)
        where (n,z,p)   = mynzp t

--5
mydivMod :: Integral a => a -> a -> (a,a)
mydivMod x y | x == 0 = (0,0)
             | x == y = (1,0)
             | x < y  = (0,x)
             | otherwise = aux x y (0,0)
        where aux 0 y (d,m) = (d,m)
              aux x y (d,m) | x-y > 0  = aux (x-y) y (d+1,m)
                            | x-y == 0 = aux (x-y) y (d+1,0)
                            | x < y = (d,x)

--6
fromDigits :: [Int] -> Int
fromDigits [] = 0
fromDigits (h:t) = h*10^(length t) + fromDigits t
--------------------------------------------------
fromDigitsatualizado :: [Int] -> Int
fromDigitsatualizado t = fst (auxfromDigitsatualizado t)
auxfromDigitsatualizado :: [Int] -> (Int,Int)
auxfromDigitsatualizado [] = (0,0)
auxfromDigitsatualizado (h:t) = (h * 10 ^ e + r, e + 1)
                        where (r,e) = auxfromDigitsatualizado t 
