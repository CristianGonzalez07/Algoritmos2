import Data.List
{-
    Cada Delta esta formado por un conjunto de transiciones,
    cada transicion tiene el siguiente formato:
    ((estado inicial,caracter involucrado), estado final)
-}
type Delta = [((Int,Char),Int)] 

{-
	El tipo DFA tiene el siguiente formato:
	(conjunto de estados, sigma, delta, estado inicial, estado final)
-}
type DFA = ([Int],[Char],Delta,Int,[Int])  

{-
	Tiempo de ejecucion en el peor caso O(n!)
	donde n! = tamaño de la lista dada.

	Funcion que reconoceCadena una lista y retorna 
	todos los subconjuntos posibles de la misma
-}
subconjuntos :: [a]->[[a]]
subconjuntos [] = [[]]
subconjuntos (x:xs)= sub++[x:ys|ys<- sub]
                                where sub =subconjuntos xs

{-
	Tiempo de ejecucion en el peor caso O(n!) donde
	n = cantidad de estados + cantidad de caracteres del alfabeto.

  Funcion que dado un sigma como parametro retorna todos los
  posibles deltas  con el formato de el tipo definido 
  anteriormente para una cantidad maxima de estados
-} 

generarDelta :: [Int] ->[Char] -> Delta
generarDelta xs ys = [((qi,c),qf)|qi <- xs, c<-ys, qf<-xs]

{-
   	Tiempo de ejecucion en el peor caso O(n)donde 
   	n = longitud de la lista dada como delta.  

    Funcion que dado un caracter, un estado inicial y un delta
    retorna una tupla con el formato (bool,int) donde el bool
    sera true si el caracter es reconocido por ese delta
    (caso contrario false) y el int sera el estado final donde
    culmine esa evaluacion.
-}

reconoceCaracter :: Char -> Int -> Delta -> (Bool,Int) 
reconoceCaracter x n [] = (False,n)
reconoceCaracter x n d
                    	| (n,x) == fst d1 = (True,(snd d1))  
                        | otherwise     = ( (False || fst (reconoceCaracter x n (tail d))),snd (reconoceCaracter x n (tail d))) 
                       		where d1 = head d


{-
	Tiempo de ejecucion en el peor caso O(n*m) donde
	n = longitud de la cadena ,m es el tiempo en el peor
	caso de la funcion reconoceCaracter.

    Funcion que dados a una cadena, un alfabeto,un estado inicial,
    un estado final y un delta,retorna True si la cadena dada
    es reconocida por el delta dado.Caso contrario retorna
    false.
-}

reconoceCadena :: String -> [Char] -> Int ->Int -> Delta -> Bool 
reconoceCadena [] e ei ef d = (ei == ef)
reconoceCadena (x:xs) e ei ef d 
    							| elem x e = ((fst st)) && (reconoceCadena xs e (snd st) ef d)  
     							| otherwise        = False
      								where st = reconoceCaracter x ei d

{-
	Tiempo de ejecucion en el peor caso O(n*m) donde
	n = cantidad de cadenas positivas m es el tiempo 
  en el peor caso de reconoceCadena.

  Funcion que dada una lista de cadenas(positivas),
  un conjunto de estados,un estado inicial,un estado final
  y un delta, retorna true si la cadena dada es reconocida
  por el delta dado.Caso contrario retorna false.
-} 

evaluaCadenaPos :: [String] -> [Char] -> Int ->Int -> Delta ->Bool
evaluaCadenaPos [] _ _ _ _ = True
evaluaCadenaPos (x:xs) sigma ei ef delta = (reconoceCadena x sigma ei ef delta) && (evaluaCadenaPos xs sigma ei ef delta)

{-
	Tiempo de ejecucion en el peor caso O(n) donde
	n = longitud de lista de cadenas positivas.

	Funcion que dados un conjunto de estados,un alfabeto,un delta
	y un conjunto de cadenas(positivas) retorna un subconjunto
	de estados correspondiente a los estados finales del automata
-}

finales :: [Int] -> [Char]->Delta ->[String]-> [Int]
finales _ _ _ [] = []
finales st sigma delta (x:xs) = [s2 | s1<-st, s2<-st, reconoceCadena x sigma s1 s2 delta ] ++ (finales st sigma delta xs)

{-
	Tiempo de ejecucion en el peor caso O(n*m) donde
	n = cantidad de cadenas negativas m es el tiempo en el peor
  caso de reconoceCadena.

  Funcion que dada una lista de cadenas(negativas),
  un conjunto de estados,un estado inicial,un estado final
  y un delta, retorna true si la cadena dada es reconocida
  por el delta dado.
-}

evaluaCadenaNeg :: [String] -> [Char] -> Int ->Int -> Delta -> Bool 
evaluaCadenaNeg [] _ _ _ _ = True
evaluaCadenaNeg (x:xs) sigma ei ef delta 
                                   	    | (reconoceCadena x sigma ei ef delta== False)= (evaluaCadenaNeg xs sigma ei ef delta)
                                        | otherwise = False
{-
	Tiempo de ejecucion en el peor caso O(n) donde
	n = tamaño del delta.

	Funcion que dada una transicion y un delta,retorna true si
	la transicion es determinista dentro de ese delta. 
-}

validaDelta :: ((Int,Char),Int) -> Delta -> Bool
validaDelta (x1,y1) []     = True
validaDelta (x1,y1) (y:ys) 
                                | x1 == (fst y) = False
                                | otherwise =  (validaDelta (x1,y1) ys) 

{-	
	Tiempo de ejecucion en el peor caso O(n*m) donde 
	n = tamaño del delta m es el tiempo en el peor caso
  de validaDelta.
	
	Funcion que dado un delta retorna true si todas las transiciones
	del mismo lo hacen determinista.Caso contrario retorna false.
-}

filtraDeltas :: Delta -> Bool
filtraDeltas [] = True
filtraDeltas (x:xs) 
                | (length xs) == 0  = True 
                | otherwise  = (validaDelta x xs) && filtraDeltas xs

{-
	Tiempo de ejecucion en el peor caso O(n*m*p) donde
	n = tamaño del delta,m = tiempo en el peor caso de evaluaCadenaPos,
  p = tiempo en el peor caso de evaluaCadenaNeg
	
	Funcion que dados un conjunto de estados,un conjunto de cadenas
	positivas,un conjunto de cadenas negativas,una cantidad maxima de
	estados y una lista de deltas, retorna una tupla donde la primer
	componente es el primer delta que verifique reconocer todas las
	cadenas positivas y no reconocer ninguna cadena negativa.
	Y como segunda componente el conjunto con los estados finales
	de dicho delta.
-}

encuentraDelta :: [Char] -> [String] -> [String] -> Int -> [Delta] -> (Delta,[Int] )
encuentraDelta _ _ _ _ [] = ([],[])
encuentraDelta sigma pos neg k (x:xs)
                                                | (evaluaCadenaPos pos sigma 0 (k-1) x ) && (evaluaCadenaNeg neg sigma 0 (k-1) x ) = (x,f) 
                                                | otherwise = encuentraDelta sigma pos neg k xs
                                                where f = finales [0..(k-1)] sigma x pos

{- 
  Tiempo de ejecucion en el peor caso O(n*k)
  donde k es la cantidad de estados maximos  y n es el tiempo
  en el peor caso de encuentraDelta.

  Funcion que dados un conjunto de estados,un conjunto de cadenas
  positivas,un conjunto de cadenas negativas,una cantidad maxima de
  estados,un acumulador inicialmente como 0 y una lista de deltas,
  retorna una tupla donde la primer componente es (en caso de existir)
  el minimo delta que cumpla con las restricciones de ser valido para
  las cadenas positivas y no valido para ninguna de las cadenas
  negativas para el menor numero de estados posibles empezando de un 
  unico estado (representado como el estado "0").y
-}
minimoDelta :: [Char] -> [String] -> [String] -> Int -> Int -> [Delta] -> (Delta,[Int]) 
minimoDelta sigma pos neg n k b
                                            | (fst a == []) && (n<k) = (minimoDelta sigma pos neg (n+1) k b)
                                            | otherwise = (encuentraDelta sigma pos neg n b) --retorna el delta vacio
                                            where a =(encuentraDelta sigma pos neg n b)

{-
  Tiempo de ejecucion en el peor caso O(n+m+p).
  donde n es el tiempo en el peor caso de generarDelta,
  m es el tiempo en el peor caso de
  filter (filtraDeltas) ((subconjuntos a)) = 
  y p es el tiempo en el peor caso de minimoDelta.

  Funcion principal del programa,dado un alfabeto,un conjunto de 
  cadenas positivas,un conjunto de cadenas negativas y un k maximo 
  para limitar el numero de estados, retorna un DFA (en caso de que exista)
  que cumpla con las restricciones de ser valido para las cadenas positivas
  y no valido para ninguna de las cadenas negativas para el menor numero de
  estados posibles 
-}

main :: [Char] -> [String] -> [String] -> Int -> DFA 
main sigma pos neg k = 
                                        let 
                                          a = generarDelta [0..(k-1)] sigma
                                          b = filter (filtraDeltas) ((subconjuntos a)) -- Se aplica el reverse con el fin de empezar 
                                          c = (minimoDelta sigma pos neg 0 k b)        -- las evaluaciones con la minima cantidad 
                                        in                                             -- de estados posibles    
                                          ([0..(k-1)],sigma,(fst c),0, nub(snd c))