package ejemplo1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejemplo1 {
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

		System.out.println("\n---------------------------------------------------");
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		AuxGrammar.generate(Ejemplo1.class, "C:\\Users\\ivans\\git\\ADDA2-Lab2\\resources\\modeloslsi\\ejemplo1.lsi", ficheroLp);
		System.out.println("\n---------------------------------------------------");

		System.out.println("\n<--- Ejecucion de Gurobi --->\n");
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println("\n---------------------------------------------------");

		System.out.println("\n<--- Solución ---> \n");
		System.out.println(solution.toString((s, d) -> d > 0.));
		System.out.println("\n---------------------------------------------------\n\n");
	}

	public static void main(String[] args) throws IOException {
		Integer max = 3;
		for (int num = 1; num < max + 1; num++) {

			System.out.println("---------------------------------------------------");
			System.out.println(" | Test del modelo en ficheros/p3/ejemplo1_" + num + ".txt | ");
			System.out.println("---------------------------------------------------\n");

			ejemplo1_model("resources/ejemplo1/ejemplo1_" + num + ".txt", "resources/modeloslsi/ejemplo1.lsi","resources/modeloslp/ejemplo1_" + num + ".lp");
		}

	}
}
