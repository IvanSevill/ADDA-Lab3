package ejemplo1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejemplo1 {

	// Dado un conjunto de números enteros estrictamente positivos, encontrar el
	// multiconjunto (se puede repetir varias veces cada número) formado por números
	// del conjunto anterior que sume exactamente n, y que tenga el menor tamaño. El
	// tamaño de un multiconjunto es la suma de todas las multiplicidades para cada
	// uno de sus elementos.

	// DATOS DE ENTRADA
	// n: entero que corresponde a la suma a conseguir
	// m: entero que corresponde al tamaño del conjunto
	// e_i: entero que corresponde al elemento en la posicion : i=0,2,...,m-1

	// VARIABLES
	// Xi: número de veces que se repite el elemento en i : i=0,2,...,m-1

	// FUNCION OBJETIVO
	// min sum(Xi) : i=0,1,...,m-1

	// RESTRICCIONES
	// 1. sum(Xi*e_i) = n : i=0,1,...,m-1
	// 2. 0 <= Xi <= n/e_i : i=0,1,...,m-1

	// TIPO VARIABLES
	// int Xi, i = 0,1,...,m-1

	public static Integer suma;
	public static List<Integer> elementos;

	public static Integer getSuma() {
		return suma;
	}

	public static Integer getNumElementos() {
		return elementos.size();
	}

	public static Integer getElemento(Integer i) {
		return elementos.get(i);
	}

	public static Integer getMultiplicidad(Integer i) {
		return suma / elementos.get(i);
	}

	public static void ejemplo1_model(String fichero, String ficheroLsi, String ficheroLp) throws IOException {
		DatosMulticonjunto.iniDatos(fichero);

		suma = DatosMulticonjunto.getSuma();
		elementos = DatosMulticonjunto.getListaNumeros();

		AuxGrammar.generate(Ejemplo1.class, ficheroLsi, ficheroLp);
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s, d) -> d > 0.));
	}

	public static void main(String[] args) throws IOException {
		ejemplo1_model("resources/ejemplo1/ejemplo1.txt", "modeloslsi/ejemplo1.lsi", "modeloslp/Ejemplo1-2.lp");
	}

}
