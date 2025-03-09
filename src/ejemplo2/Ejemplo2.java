package ejemplo2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import ejemplo1.*;
import ejemplo1.DatosMulticonjunto;
import ejemplo2.DatosSubconjunto.Subconjunto;
import ejercicio1.DatosAlmacenes;
import ejercicio1.Ejercicio1;
import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejemplo2 {

	private static final Integer EJERCICIO = 2;
	private static final Integer NUMERO = 140;
	private static final String CARACTER = "-";

	public static Ejemplo2 create(List<Integer> ls) {
		return new Ejemplo2(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	private Ejemplo2(List<Integer> ls) {
		for (Integer num : ls) {

			String datosEntrada = "resources/ejemplo" + EJERCICIO + "/ejemplo" + EJERCICIO + "_" + num + ".txt";
			String lsi = "resources/modeloslsi/ejemplo" + EJERCICIO + ".lsi";
			String lp = "resources/modeloslp/ejemplo" + EJERCICIO + "_" + num + ".lp";

			imprimeCabecera(datosEntrada);

			try {
				ejercicio1(datosEntrada, lsi, lp);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return solucion.entrySet().stream().map(p -> p.getKey().producto() + ": Almacen " + p.getValue())
				.collect(Collectors.joining("\n",
						"Reparto de productos y almacen en el que se coloca cada uno de ellos:\n",
						String.format("\nProductos colocados: %d", numproductos)));
	}



	private static void generaLpConAuxGrammar(String ficheroLsi, String ficheroLp) throws IOException {
		separador(CARACTER, NUMERO);
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		DatosAlmacenes.iniDatos(ficheroLsi);
		AuxGrammar.generate(DatosAlmacenes.class, ficheroLsi, ficheroLp);
		separador(CARACTER, NUMERO);

	}

	private static GurobiSolution ejecucionGurobi(String ficheroLp) throws IOException {
		separador(CARACTER, NUMERO);
		System.out.println("\n<--- Ejecucion de Gurobi --->\n");
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		separador(CARACTER, NUMERO);
		return solution;
	}

	private static void imprimeSolucion(GurobiSolution solucion) throws IOException {
		separador(CARACTER, NUMERO);
		System.out.println(solucion.toString((s, d) -> d > 0.));
		separador(CARACTER, NUMERO);

	}

	private static void imprimeCabecera(String datosEntrada) {

		separador(CARACTER, NUMERO);
		String s = "Ejecutando ejercicio " + EJERCICIO + " con datos de entrada: " + datosEntrada;
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		String blanco = " ";
		System.out.println(" |" + blanco.repeat(espaciosPorLado) + s + blanco.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
	}

	private static void separador(String caracter, Integer n) {
		System.out.println(caracter.repeat(n));
	}

	public static void ejercicio1(String datosEntrada, String ficheroLsi, String ficheroLp) throws IOException {
		DatosAlmacenes.iniDatos(datosEntrada);
		generaLpConAuxGrammar(ficheroLsi, ficheroLp);
		GurobiSolution solucion = ejecucionGurobi(ficheroLp);
		imprimeSolucion(solucion);
	}

	public static void main(String[] args) {
		 Ejemplo2 s = Ejemplo2.create(List.of(1, 2));
	}
}
