package ejemplo2;

public class Ejemplo2 {

	// Se tiene un conjunto U de m elementos de tipo entero ej, j ∈ [0,m), (llamado
	// el universo) y un conjunto S de n conjuntos si, i ∈ [0,n), cuya unión es
	// igual al universo. Cada conjunto si tiene un peso wi ≥ 0 asociado. El
	// problema de cobertura de conjuntos consiste en identificar los conjuntos de S
	// cuya unión es igual al universo U, de forma que se minimice la suma de los
	// pesos de los conjuntos elegidos.

	// DATOS DE ENTRADA
	// n: entero, tamaño
	// m: entero, tamaño
	// Wi: entero, peso del conjunto i : i = 0,1,...,n-1
	// Cij: binario, el conjunto i contiene el elemento j : i = 0,1,...,n-1, j =
	// 0,1,...,m-1

	// VARIABLES
	// Xi: binario, 0 si el conjunto i es seleccionado : i = 0,1,...,n-1

	// FUNCIÓN OBJETIVO
	// min(Xi*Wi) : i = 0,1,...,n-1)

	// RESTRICCIONES
	// 1. sum(Cij*Xi) >= 1 : j = 0,1,...,m-1

	// TIPO VARIABLES
	// bin Xi : i = 0,1,...,n-1
	
	

}
