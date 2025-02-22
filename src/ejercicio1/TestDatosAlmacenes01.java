package ejercicio1;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestDatosAlmacenes01 {

	public static Integer getNumProductos() {
		return DatosAlmacenes.getNumProductos();
	}

	public static Integer getNumAlmacenes() {
		return DatosAlmacenes.getNumAlmacenes();
	}

	public static Integer getMetrosCubicosAlmacen(Integer i) {
		return DatosAlmacenes.getMetrosCubicosAlmacen(i);
	}

	public static Integer getMetrosCubicosProducto(Integer i) {
		return DatosAlmacenes.getMetrosCubicosProducto(i);
	}

	public static Boolean sonIncompatibles(Integer i, Integer j) {
		return DatosAlmacenes.sonIncompatibles(i, j);
	}

	private static void generaLpConAuxGrammar(String ficheroLsi, String ficheroLp) throws IOException {
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		AuxGrammar.generate(TestDatosAlmacenes01.class, ficheroLsi, ficheroLp);
		System.out.println("\n---------------------------------------------------");

	}

	private static GurobiSolution ejecucionGurobi(String ficheroLp) throws IOException {
		System.out.println("\n<--- Ejecucion de Gurobi --->\n");
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println("\n---------------------------------------------------");
		return solution;
	}

	private static void imprimeSolucion(GurobiSolution solucion) throws IOException {
		System.out.println("\n<--- Solución ---> \n");
		System.out.println(solucion.toString((s, d) -> d > 0.));
		System.out.println("\n---------------------------------------------------\n\n");

	}

	private static void imprimeCabecera(String datosEntrada) {

		System.out.println("\n---------------------------------------------------------------------------------------");
		System.out.println(" | Ejecutando ejercicio1 con datos de entrada: " + datosEntrada + "|");
		System.out.println("---------------------------------------------------------------------------------------\n");
	}

	public static void ejercicio1(String datosEntrada, String ficheroLsi, String ficheroLp) throws IOException {
		DatosAlmacenes.iniDatos(datosEntrada);
		generaLpConAuxGrammar(ficheroLsi, ficheroLp);
		GurobiSolution solucion = ejecucionGurobi(ficheroLp);
		imprimeSolucion(solucion);
	}

	public static void main(String[] args) {
		Integer max = 3;
		for (int num = 1; num < max + 1; num++) {

			String datosEntrada = "resources/ejercicio1/DatosEntrada" + num + ".txt";
			String lsi = "resources/modeloslsi/ejercicio1.lsi";
			String lp = "resources/modeloslp/ejercicio1_" + num + ".lp";

			imprimeCabecera(datosEntrada);

			try {
				ejercicio1(datosEntrada, lsi, lp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
