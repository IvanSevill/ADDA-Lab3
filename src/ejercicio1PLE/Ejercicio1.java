package ejercicio1PLE;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import common.DatosAlmacenes;
import common.DatosAlmacenes.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1 {
	private static final Integer EJERCICIO = 1;
	private static final Integer NUMERO = 140;
	private static final String CARACTER = "-";

	public static Ejercicio1 create(List<Integer> ls) {
		return new Ejercicio1(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	private Ejercicio1(List<Integer> ls) {
		for (Integer num : ls) {

			String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + num + ".txt";
			String lsi = "resources/modeloslsi/ejercicio" + EJERCICIO + ".lsi";
			String lp = "resources/modeloslp/ejercicio" + EJERCICIO + "_" + num + ".lp";

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
		separador(CARACTER, NUMERO);
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		AuxGrammar.generate(Ejercicio1.class, ficheroLsi, ficheroLp);
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
		Ejercicio1 s = Ejercicio1.create(List.of(1, 2, 3));
	}

}
