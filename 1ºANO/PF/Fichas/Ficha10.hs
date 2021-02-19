-- INPUT / OUTPUT
module Ficha10 where

import Data.Char
import Data.List
import System.Random
import System.IO

--1
-- a)
bingo ::  IO ()
bingo = aux [1..90]
      where aux :: [Int] -> IO ()
            aux [] = return ()
            aux l = do 
                    _ <- getChar
                    n <- randomRIO (0,(length l-1))
                    let x = (l !! n)
                    putStrLn ("número = " ++ show x)
                    aux (delete x l)

-- b)
codigoSecreto :: [Char] -> IO ([Char])
codigoSecreto list = do 
                       if length list == 4 then do 
                                                  putStrLn "Sequência Secreta gerada entre 0 e 9."
                                                  return (list)
                                           else do
                                                  n <- randomRIO (0,9)
                                                  codigoSecreto ((intToDigit n ): list)
lecodigo :: [Char] -> IO ([Char])
lecodigo list = do 
                  if length list == 4 then do 
                                             putStrLn "Jogada válida"
                                             return (list)
                                      else do 
                                             putStrLn "Introduza um número entre 0 e 9 e pressione Enter."
                                             x <- getChar
                                             getChar
                                             lecodigo (list ++ [x])
certoerrado :: Int -> [Char] -> [Char] -> IO ()
certoerrado  _ [] _ = return ()
certoerrado x (h:t) l | ((elem h l) && ((l !! x)) /= h) = do 
                                                              print x
                                                              certoerrado (x+1) t l
                     | otherwise = certoerrado (x+1) t l 
certocerto :: [Char] -> [Char] -> IO ()
certocerto [] [] = return ()
certocerto (h:t) (x:y) | h == x = do
                                     print x
                                     certocerto t y
                       | otherwise = certocerto t y
mastermind :: IO ()
mastermind = do
               x <- codigoSecreto []
               y <- lecodigo []
               putStrLn "Elementos corretos na posição correta: "
               certocerto x y
               putStrLn "Elementos corretos mas na posição incorreta: "
               certoerrado 0 x y


-- 2 
data Aposta = Ap [Int] (Int,Int)
exemplo2 = Ap [10,20,30,40,50] (5,9)
-- a)
valida :: Aposta -> Bool
valida (Ap l (x,y)) = (length l == 5) && (aux l (x,y))
                    where aux :: [Int] -> (Int,Int) -> Bool
                          aux [] (_,_) = True 
                          aux (h:t) (x,y) = (h >= 1 && h <= 50) && (not (elem h t)) && (aux t (x,y)) && (x >= 1 && x <=9) && (y >= 1 && y <= 9) && (x /= y)

-- b)
comuns :: Aposta -> Aposta -> (Int,Int)
comuns (Ap a (b,c)) (Ap x (y,z)) = (aux1 a x, aux2 (b,c) (y,z))
                                where aux1 :: [Int] -> [Int] -> Int
                                      aux1 [] _ = 0
                                      aux1 (h:t) l = if (elem h l) then (1 + aux1 t l) else (aux1 t l)
                                      aux2 :: (Int,Int) -> (Int,Int) -> Int
                                      aux2 (b,c) (y,z) | (b /= y)  && (b /= z)  && (c /= y)  && (c /= z)  = 0
                                                       | ((b == y) || (b == z)) && ((c == y) || (c == z)) = 2
                                                       | otherwise = 1

-- c)
-- i.
instance Eq Aposta where
    a==b = (comuns a b) == (5,2)
-- ii.
premio :: Aposta -> Aposta -> Maybe Int
premio a b | (comuns a b) == (5,2) = Just 1
           | (comuns a b) == (5,1) = Just 2
           | (comuns a b) == (5,0) = Just 3
           | (comuns a b) == (4,2) = Just 4
           | (comuns a b) == (4,1) = Just 5
           | (comuns a b) == (4,0) = Just 6
           | (comuns a b) == (3,2) = Just 7
           | (comuns a b) == (2,2) = Just 8
           | (comuns a b) == (3,1) = Just 9
           | (comuns a b) == (3,0) = Just 10
           | (comuns a b) == (1,2) = Just 11
           | (comuns a b) == (2,1) = Just 12
           | (comuns a b) == (2,0) = Just 13
           | otherwise             = Nothing

-- d)
-- i.
leAposta :: IO Aposta
leAposta = do 
             putStrLn "Insira uma aposta no formato (Ap [n,n,n,n,n](e,e))"
             x <- get
             ap <- (x :: Aposta)
             if (valida ap) then do 
                                 putStrLn "Aposta válida"
                                 return ap
                          else do 
                                 putStrLn "Aposta inválida"
                                 leAposta
-- ii.
joga :: Aposta -> IO ()
joga ch = do 
            ap <- leAposta
            case (premio ap ch) of
                 (Just n) -> putStrLn "O seu prémio é " ++ (show n)
                 Nothing  -> putStrLn  "Sem prémio"

-- e)
geraEstrelas :: IO (Int,Int)
geraEstrelas = do 
                 x <- randomRIO (1,9)
                 y <- randomRIO (1,9)
                 if (x == y) then geraEstrelas else return (x,y)
geraNumeros :: Int -> [Int] -> IO [Int]
geraNumeros 0 l = return l 
geraNumeros n l = do 
                    x <- randomRIO (1,50)
                    if elem x l then geraNumeros n l else geraNumeros (n-1) (x:l)
geraChave :: IO Aposta
geraChave = do 
              n <- geraNumeros 5 []
              e <- geraEstrelas
              return (Ap n e)

-- f)
main :: IO ()
main = do ch <- geraChave
          ciclo ch

ciclo :: Aposta -> IO ()
ciclo ch = do 5 <- menu
              case 5 of
                 ('1':_) -> do joga ch
                               ciclo ch
                 ('2':_) -> do ch' <-geraChave
                               ciclo ch
                 ('0':_) -> return ()
                  _  -> ciclo ch

menu :: IO String
menu = do { putStrLn menutxt
    ; putStr "Opcao: "
    ; c <- getLine
    ; return c
    }
     where menutxt = unlines ["",
            "Apostar ........... 1",
            "Gerar nova chave .. 2",
            "",
            "Sair .............. 0"]