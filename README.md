# ADDA2-Lab2
Base para copiar y pegar el proyecto de laboratorio número 3 de la asignatura ADDA de la Universidad de Sevilla

# Práctica Individual 3 – Ejercicios PLE/AG

## EJEMPLOS

1. **Dado un conjunto de números enteros estrictamente positivos**, encontrar el multiconjunto (se puede repetir varias veces cada número) formado por números del conjunto anterior que sume exactamente `n`, y que tenga el menor tamaño. El tamaño de un multiconjunto es la suma de todas las multiplicidades para cada uno de sus elementos.  

2. **Se tiene un conjunto U de m elementos de tipo entero ej, j ∈ [0,m) (llamado el universo)** y un conjunto S de n conjuntos si, i ∈ [0,n), cuya unión es igual al universo. Cada conjunto si tiene un peso wi ≥ 0 asociado. El problema de cobertura de conjuntos consiste en identificar los conjuntos de S cuya unión es igual al universo U, de forma que se minimice la suma de los pesos de los conjuntos elegidos. 

3. **Una academia de inglés tiene n alumnos a ser repartidos en m grupos (n múltiplo de m).** Cada grupo tiene distinto horario y profesor. De cada alumno se conoce la afinidad que tiene para pertenecer a cada uno de los grupos (valor entero en el rango [0,5]). Se desea conocer el reparto de alumnos en grupos, de forma que todos los grupos deben tener el mismo número de alumnos, maximizando la afinidad total conseguida para todos los alumnos, y teniendo en cuenta que no está permitido asignar un alumno a un grupo para el que presente afinidad 0.  

## EJERCICIOS

1. **Una empresa dispone de varios almacenes**, cada uno con una capacidad máxima en metros cúbicos. Se desea planificar la distribución de distintos productos, cada uno con un volumen específico. Cada producto puede ser enviado a un único almacén o no enviarse. Además, algunos productos son incompatibles para almacenarse juntos en el mismo almacén, ya sea por motivos de seguridad o logística. El objetivo es maximizar la cantidad de productos almacenados, cumpliendo con las restricciones de capacidad de los almacenes y de compatibilidad entre los productos. 

2. **Se desea diseñar un programa de formación que incluya varios cursos.** Cada curso pertenece a un área de conocimiento (tecnología, ciencias, humanidades, …), tiene un coste de inscripción, una puntuación de relevancia (entero entre 1 y 5), y una duración en horas. Determine la selección de cursos de forma que: 

    - El programa debe incluir al menos un curso de cada área de conocimiento. 
    - El número de cursos de tecnología seleccionados debe ser superior o igual a los seleccionados de cualquiera otra área. 
    - Se debe garantizar que la duración media de los cursos seleccionados sea de al menos 20 horas. 
    - El coste total de los cursos seleccionados no debe superar un presupuesto total asignado para el programa. 
    - Se debe maximizar la suma total de las puntuaciones de los cursos seleccionados.

3. **Un organizador de festivales debe decidir cuántas entradas de diferentes tipos (general, VIP, premium) asignar a distintas áreas del recinto del festival.** Cada área tiene un aforo máximo que no puede excederse, y se requiere garantizar una cantidad mínima de entradas de cada tipo que deben asignarse. Además, cada tipo de entrada tiene un costo de implementación distinto para cada área (por ejemplo, infraestructura adicional para entradas VIP). El objetivo es determinar cuántas entradas de cada tipo asignar a cada área para minimizar los costos de implementación, asegurando que se cumplan los límites de aforo y las cuotas mínimas de cantidad de entradas por tipo. 

4. **Se tiene un grafo conexo, no dirigido y ponderado**, en el que los vértices representan estaciones de tren y las aristas representan tramos entre estaciones. De cada estación se conoce el número medio de pasajeros diarios, el número de empleados, y la satisfacción media de los clientes (real en [0,10]). De cada tramo se conoce el tiempo medio que se tarda en recorrerlo y el coste del billete. Determine un camino que pase por todas las estaciones exactamente una vez y vuelva al origen, que cumpla que: 

    - Minimice el tiempo medio total del camino. 
    - El coste total del trayecto sea menor o igual que ¾ de la suma total de los costes de todos los tramos del grafo.  
    - Deben existir al menos 2 estaciones consecutivas cuya satisfacción sea mayor o igual que 7. 

Debe resolver todos los ejercicios de forma eficiente. Tenga en cuenta que:  

- Ejercicios 1-3: se debe resolver por PL y por AG. Para cada problema, se recomienda realizar la formalización del/los modelo/s para la solución por PL y por AG. 
- Ejercicio 4: se debe resolver por AG. Se recomienda realizar la formalización del modelo. 
- En cada ejercicio:  
  - Se leen los datos de entrada de un fichero, y se debe mostrar la salida por pantalla. 
  - Se debe implementar una clase para modelar las soluciones. 
