import Data.List
{-
    Cada Delta esta formado por un conjunto de transiciones,
    cada transicion tiene el siguiente formato:
    ((estado del cual se parte, caracter involucrado), estado final)
-}
type Delta = [((Int,Char),Int)] 

type DFA = ([Int],[Char],Delta,Int,[Int])  --(conjunto de estados, sigma, delta, estado inicial, estado final)

--O(n!)
--Retorna todos los subconjuntos
subconjuntos :: [a]->[[a]]
subconjuntos [] = [[]]
subconjuntos (x:xs)= sub++[x:ys|ys<- sub]
                                where sub =subconjuntos xs

{-
    Genera todos los posibles deltas con el formato de el tipo definido anteriormente
    para una cantidad maxima de estados y un sigma que toma como parametro.
-}
--O(n!)
generateDelta :: [Int] ->[Char] -> Delta
generateDelta xs ys = [((r,t),p)|r <- xs, t<-ys, p<-xs]



{-
    Hace la prueba sobre un delta  para cada caracter del string a reconocer(o no).
    Dado un caracter, un estado inicial y un delta retorna true si el caracter es reconocido por ese delta
    y tambien retorna el estado final en el cual queda luego de la evaluacion.
-}
--O(n)
pruebaDelta :: Char -> Int -> Delta -> (Bool,Int) 
pruebaDelta x n [] = (False,n)
pruebaDelta x n d
                             | (n,x) == fst d1 = (True,(snd d1))  
                             | otherwise     = ( (False || fst (pruebaDelta x n (tail d))),snd (pruebaDelta x n (tail d))) 
                         where d1 = head d


{-
    Funcion que en base a una cadena, un sigma, un estado inicial, un estado final y un delta,
    determina si la cadena es reconocido por el delta. 
-}
--O(n2)
recibe :: String -> [Char] -> Int ->Int -> Delta -> Bool 
recibe [] e ei ef d = (ei == ef)
recibe (x:xs) e ei ef d 
      | elem x e = ((fst st)) && (recibe xs e (snd st) ef d)  
      | otherwise        = False
      where st = pruebaDelta x ei d

{-
    Funcion que evalua si una cadena cumple con un delta en particular
    Los parametros que recibe son: 
    conjunto de cadenas -> sigma -> estado inicial -> estado final -> delta
-}
--O(n3)
----devolver lista y emezar desde aca
evaluaCadenaPos :: [String] -> [Char] -> Int ->Int -> Delta ->Bool
evaluaCadenaPos [] _ _ _ _ = True
evaluaCadenaPos (x:xs) sigma ei ef delta = (recibe x sigma ei ef delta) && (evaluaCadenaPos xs sigma ei ef delta)

--retorna el conj de estados finales
--estados->sigma->delta ->strings pos
--O(n)
finales :: [Int] -> [Char]->Delta ->[String]-> [Int]
finales _ _ _ [] = []
finales st sigma delta (x:xs) = [s2 | s1<-st, s2<-st, recibe x sigma s1 s2 delta ] ++ (finales st sigma delta xs)

--O(n3)
evaluaCadenaNeg :: [String] -> [Char] -> Int ->Int -> Delta -> Bool 
evaluaCadenaNeg [] _ _ _ _ = True
evaluaCadenaNeg (x:xs) sigma ei ef delta 
                                                                    | (recibe x sigma ei ef delta== False)= (evaluaCadenaNeg xs sigma ei ef delta)
                                                                    | otherwise = False



--O(n)
-- Funcion que evalua si una de las transiciones de un delta es valida para ser determinista.
validaDelta :: ((Int,Char),Int) -> Delta -> Bool
validaDelta (x1,y1) []     = True
validaDelta (x1,y1) (y:ys) 
                                | x1 == (fst y) = False
                                | otherwise =  (validaDelta (x1,y1) ys) 

--O(n2)
-- Funcion que dado un delta, determina si todas las transisciones del mismo lo hacen determinista o no.
filtraDeltas :: Delta -> Bool
filtraDeltas [] = True
filtraDeltas (x:xs) 
                | (length xs) == 0  = True 
                | otherwise  = (validaDelta x xs) && filtraDeltas xs

{-
    Funcion que busca el primer delta que verifique que se cumplan todas las cadenas positivas y no las negativas
    La funcion toma como parametros:
     sigma -> cadena de los "pos" -> cadena de los "neg" -> cantidad maxima de estados -> conjunto de todos los delta
-}
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
encuentraDelta :: [Char] -> [String] -> [String] -> Int -> [Delta] -> (Delta,[Int] )
encuentraDelta _ _ _ _ [] = ([],[])
encuentraDelta sigma pos neg k (x:xs)
                                                | (evaluaCadenaPos pos sigma 0 (k-1) x ) && (evaluaCadenaNeg neg sigma 0 (k-1) x ) = (x,f) 
                                                | otherwise = encuentraDelta sigma pos neg k xs
                                                where f = finales [0..(k-1)] sigma x pos

{- 
  Funcion que busca el minimo delta que cumpla con las restricciones de ser valido para los pos y no para los neg para 
  el menor numero de estados posibles empezando de un unico estado (representado como el estado "0"), si no 
  encuentra ninguno, retorna el delta vacio.
  Retorna el delta encontrado y el estado final que posee el automata.
-}
minimoDelta :: [Char] -> [String] -> [String] -> Int -> Int -> [Delta] -> (Delta,[Int]) 
minimoDelta sigma pos neg n k b
                                            | (fst a == []) && (n<k) = (minimoDelta sigma pos neg (n+1) k b)
                                            | otherwise = (encuentraDelta sigma pos neg n b) --retorna el delta vacio
                                            where a =(encuentraDelta sigma pos neg n b)

{-
    Funcion principal del programa con el cual se obtiene un automata (DFA) 
    La funcion recibe los parametros de la siguiente forma: 
      sigma -> lista de estados "pos", lista de estados "neg"-> cantidad maxima de estados k
-}
check :: [Char] -> [String] -> [String] -> Int -> DFA 
check sigma pos neg k = 
                                        let 
                                          a = generateDelta [0..(k-1)] sigma
                                          b = filter (filtraDeltas) ((subconjuntos a)) -- Se aplica el reverse con el fin de empezar 
                                          c = (minimoDelta sigma pos neg 0 k b)                      -- las evaluaciones con la minima cantidad 
                                        in                                                                                   --  de estados posibles    
                                          ([0..(k-1)],sigma,(fst c),0, nub(snd c))