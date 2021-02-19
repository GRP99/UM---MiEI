--GESTAO DE INFORMAÇÃO EM LISTAS
module Ficha4 where
    
--1
data Hora = H Int Int
          deriving Show
type Etapa = (Hora,Hora)
type Viagem = [Etapa]
ex1 :: Viagem
ex1 = [(H 9 30, H 10 25), (H 11 20, H 12 45), (H 13 30, H 14 45)]

-- exercicios da ficha 1 
horaValida :: Hora -> Bool
horaValida (H h m) = (h >= 0) && (h < 24) && (m >= 0) && (m < 60)
horaDepois :: Hora -> Hora -> Bool
horaDepois (H a b) (H x y) = (a < x) || ((a == x) && (b < y))
hora2Mins :: Hora -> Int
hora2Mins (H h m) = (h * 60) + m
mins2Hora :: Int -> Hora
mins2Hora m = (H (div m 60) (mod m 60))
diferHoras :: Hora -> Hora -> Int
diferHoras a b = if (horaDepois a b) then (hora2Mins b) - (hora2Mins a) else (hora2Mins a) - (hora2Mins b)
adicionaMinutos :: Hora -> Int -> Hora
adicionaMinutos hora mins = mins2Hora ((hora2Mins hora) + mins)
--
etapaValida :: Etapa -> Bool
etapaValida (x,y) = (horaValida x) && (horaValida y) && (horaDepois x y)

viagemValida :: Viagem -> Bool
viagemValida [] = True
viagemValida [x] = etapaValida x
viagemValida ((a,b):(x,y):t) = if ((etapaValida (a,b)) && (horaDepois b x)) then viagemValida ((x,y):t) else False

chegadaPartidaViagem :: Viagem -> (Hora,Hora)
chegadaPartidaViagem l = aux l (head l) 
                    where aux [] x = x
                          aux ((x,y):t) (i,f) = aux t (i,y)

tempoEmViagem :: Viagem -> Hora
tempoEmViagem [] = (H 0 0)
tempoEmViagem ((x,y):t) = adicionaMinutos (tempoEmViagem t) (diferHoras x y)

tempoEmEspera :: Viagem -> Hora
tempoEmEspera [] = (H 0 0)
tempoEmEspera [x] = (H 0 0)
tempoEmEspera ((a,b):(x,y):t) = adicionaMinutos (tempoEmEspera ((x,y):t)) (diferHoras b y)

tempoTotalViagem :: Viagem -> Hora
tempoTotalViagem v = let (horaComeco,horaFim) = chegadaPartidaViagem v in mins2Hora (diferHoras horaComeco horaFim)


--3
data Contacto = Casa Integer
              | Trab Integer
              | Tlm Integer
              | Email String
    deriving Show
type Nome = String
type Agenda = [(Nome,[Contacto])]

acrescEmail :: Nome -> String -> Agenda -> Agenda
acrescEmail nome email [] = [(nome,[Email email])]
acrescEmail nome email ((name,contact):t) = if (nome == name) then (nome, ((Email email) : contact)) : t else (name,contact) : (acrescEmail nome email t)

verEmails :: Nome -> Agenda -> Maybe [String]
verEmails _ [] = Nothing
verEmails nome ((name,contact):t) = if (nome == name) then Just (aux contact) else verEmails nome t 
                                 where aux [] = []
                                       aux ((Email m):t) = m : (aux t)
                                       aux (_:t) = aux t

consTelefs :: [Contacto] -> [Integer]
consTelefs [] = []
consTelefs ((Casa x) :t)  = x : (consTelefs t)
consTelefs ((Trab x) :t)  = x : (consTelefs t)
consTelefs ((Tlm x)  :t)  = x : (consTelefs t)
consTelefs (_:t)   = consTelefs t

casa :: Nome -> Agenda -> Maybe Integer
casa _ [] = Nothing
casa nome ((name,contact):t) = if (nome == name) then aux contact else casa nome t
                            where aux [] = Nothing 
                                  aux ((Casa x):t) = Just x
                                  aux (_:t) = aux t


--4
type Dia = Int
type Mes = Int
type Ano = Int
data Data = D Dia Mes Ano
    deriving Show
type TabDN = [(Nome,Data)]

procura :: Nome -> TabDN -> Maybe Data
procura _ [] = Nothing
procura nome ((name,date):t) = if (name == nome) then Just date else procura nome t

idade :: Data -> Nome -> TabDN -> Maybe Int
idade _ _ [] = Nothing
idade dat nome ((name,date):t) | (name == nome) = Just (aux date dat)
                               | otherwise     = idade dat nome t
              where aux (D d m a) (D dd mm aa) | (a >= aa) = 0
                                               | ((a < aa) && ((m > mm) || ((m <= mm) && (d > dd)))) = aa - a - 1
                                               | ((a < aa) && (m <= mm) && (d <= dd))                = aa - a

anterior :: Data -> Data -> Bool
anterior (D d m a) (D dd mm aa) = (a < aa) || ((a == aa) && (m < mm)) || ((a == aa) && (m == mm) && (d < dd))

ordena :: TabDN -> TabDN
ordena tab = aux [] tab 
          where aux n [] = n
                aux n (h:t) = aux (insereOrdData h n) t
                insereOrdData (nm,dat) [] = [(nm,dat)]
                insereOrdData (nm,dat) ((name,date):t) = if (anterior dat date) then (nm,dat) : ((name,date) : t) else (name,date) : (insereOrdData (nm,dat) t)

porIdade :: Data -> TabDN -> [(Nome,Int)]
porIdade dat tab = let (a,b) = (unzip (ordena tab)) in zip a (map (\x -> aux x dat) b)
            where aux (D d m a) (D dd mm aa) | (a >= aa) = 0
                                             | ((a < aa) && ((m > mm) || ((m <= mm) && (d > dd)))) = aa - a - 1
                                             | ((a < aa) && (m <= mm) && (d <= dd))                = aa - a


--5
data Movimento = Credito Float | Debito Float
    deriving Show
data Data5 = D5 Int Int Int
    deriving Show
data Extracto = Ext Float [(Data, String, Movimento)]
    deriving Show

extValor :: Extracto -> Float -> [Movimento]
extValor (Ext _ []) _ = []
extValor (Ext v ((_, _, Credito y):t)) valor = if (y > valor) then (Credito y) : (extValor (Ext v t) valor) else extValor (Ext v t) valor
extValor (Ext v ((_, _, Debito y):t)) valor  = if (y > valor) then (Debito y)  : (extValor (Ext v t) valor) else extValor (Ext v t) valor

filtro :: Extracto -> [String] -> [(Data,Movimento)]
filtro (Ext _  []) f = []
filtro (Ext v ((dat, str, mov):t)) f = if (elem str f) then (dat,mov) : (filtro (Ext v t) f) else filtro (Ext v t) f

creDeb :: Extracto -> (Float,Float)
creDeb (Ext _ []) = (0,0)
creDeb (Ext v ((_, _, Credito x):t)) = let (a,b) = creDeb (Ext v t) in (x + a, b)
creDeb (Ext v ((_, _, Debito y):t))  = let (a,b) = creDeb (Ext v t) in (a, b + y)
    
saldo :: Extracto -> Float
saldo (Ext inicial movimentos) = let (a,b) = creDeb (Ext inicial movimentos) in inicial + a - b
