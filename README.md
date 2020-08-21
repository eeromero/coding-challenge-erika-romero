# coding-challenge-erika-romero
Este es un proyecto maven por lo que será necesario ejecutar los comandos ´clean´ and ´install´ para generar el archivo .jar que nos servirá para ejecutar y probar las diferentes partes de la aplicación.

 ```
java -cp coding-challenge-1.0-SNAPSHOT.jar <class_name> <parameters>
 ```

##1) Palindrome 
Para esta implementación de palindrome se han asumido los siguientes puntos: 

- Solo caracteres alfanumérico [a-zA-Z0-9] son tomados en cuenta en este algoritmo
- Las letras con acentos se ignoran por lo que esto solo funciona para palabras en inglés.
- La solucion es case sensitive
- Palabras y frases pueden ser palindrome
- Signos de puntuacion son ignorados 
 
 ```
Por ejemplo:
'.../;Never odd or even.' es palindrome porque solo se comprueba caracters alfanumerico

 ```
###Time and Space complexity

Time complexity: El algoritmo compara la mitad de la palabra/frase con su otra mitad mediante 2 punteros al inicio y al final de la palabra/frase. Esto seria O(N/2) que seria igual a O(N) al no tomarse en cuenta las constantes.

Space complexity: O(1) porque solo utilizamos constantes y recorremos sobre el propio array

###Test
  ```
TEST:
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.Palindrome 'radar'
radar is palindrome
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.Palindrome 'Never odd or even.'
Never odd or even. is palindrome
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.Palindrome 'table'
tableis not palindrome
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.Palindrome '123321'
123321 is palindrome
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.Palindrome '123456'
123456is not palindrome
(base) Erikas-Air:target erikaromero$ 
  ```

##2) K-complementary
Para esta implementación de k-complementary se han asumido los siguientes puntos: 

- Los elementos del array son enteros y pueden 0, negativos o positivos
- k es un entero que puede ser 0, negativo o positivo
- La respuesta no contiene pares repetidos. (1,9) y (9,1) son lo mismo
- Para probar el array tendra que tener el siguiente formato [num1,num2,num3]

###Time and Space complexity

Time complexity: El algoritmo ordena el array para poder usar 2 punteros y recorrer de 2 en 2. Ordenar el array cuesta O(nlogn) y luego recorrer el array con los 2 punteros puede ser N/2 o N -> O(N). Esto conlleva a que el algoritmo tenga un coste de O(nlogn).

Space complexity: O(result.size())

###Test

 ```
TEST:
The k-complementary of [1,3,4,5] 6 is [(1,5)]
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.KComplementary [1,3,4,5] 10
The k-complementary of [1,3,4,5] 10 is []
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.KComplementary [1,3,4,5] 8
The k-complementary of [1,3,4,5] 8 is [(3,5)]
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.algorithms.KComplementary [1,3,4,5,2] 7
The k-complementary of [1,3,4,5,2] 7 is [(2,5), (3,4)]
(base) Erikas-Air:target erikaromero$ 
 ```

##3) TF/IDF

TF: Time frecuency
IDF: Inverse document frequency

- Solo se procesan ficheros .txt
- Para la ejecucion del programa se puede enviar los parametros en diferente orden siempre y cuando se envien todos.


### TfIdf.java

Este program tiene validaciones para los diferentes parametros requeridos: -d, -t, -n, -p.

- d :url directory
- t :terms
- n :número de file names to display with the best ftidf
- p :how often the result will be displayed(in seconds)
example: ./tdIdf -d dir -n 5 -p 300 -t 'password try again

Se ha implementado con ScheduledExecutorService que ejecuta un hilo cada cierto tiempo que se le indique

```
ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
service.scheduleWithFixedDelay(new TfIdfRunnable(urlDir, terms, n), 0, period, TimeUnit.SECONDS);
 ```

###TfIdfRunnable.java

Implementacion del hilo que procesar ficheros y calculara tfidf.
Para nuestro caso el hilo se ejecuta cada cierto tiempo indicado en el valor enviado en -p al ejecutar el programa. 
Cada vez que el hilo se ejecuta mira si hay algun fichero nuevo, lo procesa , recalcula el tfidf para todos los ficheros e imprime el resultado. Si no hay nada nuevo que procesar solo imprime el resultado.

###TfIdfService.java

Contiene toda la logica para procesar ficheros pendientes e imprimir el resultado

###TfIdfCalculator.java

Contiene toda la logica para el calculo de tf, idf and tfidf para cada fichero

###Test

```
TEST:
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.tfidf.TfIdf -d /Users/erikaromero/Downloads/DOCUMENTOS/logs -t example  -n 5 -p 5
-----------------
5 top files of 2 files
-----------------
0.13 doc2.txt
0.0 doc1.txt
-----------------
5 top files of 3 files
-----------------
0.08 doc5.txt
0.08 doc2.txt
0.0 doc1.txt
-----------------
5 top files of 3 files
-----------------
0.08 doc5.txt
0.08 doc2.txt
0.0 doc1.txt
-----------------
5 top files of 4 files
-----------------
0.13 doc5.txt
0.13 doc2.txt
0.0 doc10.txt
0.0 doc1.txt
-----------------
5 top files of 4 files
-----------------
0.13 doc5.txt
0.13 doc2.txt
0.0 doc10.txt
0.0 doc1.txt
-----------------
5 top files of 5 files
-----------------
0.1 doc4.txt
0.1 doc5.txt
0.1 doc2.txt
0.0 doc10.txt
0.0 doc1.txt
-----------------
5 top files of 5 files
-----------------
0.1 doc4.txt
0.1 doc5.txt
0.1 doc2.txt
0.0 doc10.txt
0.0 doc1.txt
-----------------
5 top files of 6 files
-----------------
0.09 doc20.txt
0.08 doc4.txt
0.08 doc5.txt
0.08 doc2.txt
0.0 doc10.txt

 ```

Para este test se encuentran disponibles 3 ficheros en la carpeta resource/examples
- trump_news.txt
- blackberry_news.txt
- sweden_news.txt

 ```

(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.tfidf.TfIdf -d /Users/erikaromero/Downloads/DOCUMENTOS/logs2 -t blackberry  -p 5 -n 10
-----------------
10 top files of 3 files
-----------------
0.02 blackberry_news.txt
0.0 trump_news.txt
0.0 sweden_news.txt
```
 ```
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.tfidf.TfIdf -d /Users/erikaromero/Downloads/DOCUMENTOS/logs2 -t sweden  -p 5 -n 10
-----------------
10 top files of 3 files
-----------------
0.01 sweden_news.txt
0.0 blackberry_news.txt
0.0 trump_news.txt

```

 ```
(base) Erikas-Air:target erikaromero$ java -cp coding-challenge-1.0-SNAPSHOT.jar org.com.challenge.tfidf.TfIdf -d /Users/erikaromero/Downloads/DOCUMENTOS/logs2 -t 'Donald Trump'  -n 5 -p 10
-----------------
5 top files of 3 files
-----------------
0.01 trump_news.txt
0.0 blackberry_news.txt
0.0 sweden_news.txt

```