package common;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class GurobiCommon {

	private static final Integer NUMERO = 140;
	private static final String CARACTER = "-";

	
	public static <E> void generaLpConAuxGrammar(Class<E> clase, String ficheroLsi, String ficheroLp) throws IOException {
	    separador(CARACTER, NUMERO);
	    System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
	    AuxGrammar.generate(clase, ficheroLsi, ficheroLp);
	    separador(CARACTER, NUMERO);
	}

	public static GurobiSolution ejecucionGurobi(String ficheroLp) throws IOException {
		separador(CARACTER, NUMERO);
		System.out.println("\n<--- Ejecucion de Gurobi --->\n");
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		separador(CARACTER, NUMERO);
		return solution;
	}

	public static void imprimeSolucion(GurobiSolution solucion) throws IOException {
		separador(CARACTER, NUMERO);
		System.out.println(solucion.toString((s, d) -> d > 0.));
		separador(CARACTER, NUMERO);
	}

	public static void imprimeCabecera(String datosEntrada, Integer ejercicio) {

		separador(CARACTER, NUMERO);
		String s = "Ejecutando ejercicio " + ejercicio + " con datos de entrada: " + datosEntrada;
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		String blanco = " ";
		System.out.println(" |" + blanco.repeat(espaciosPorLado) + s + blanco.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
	}

	public static void separador(String caracter, Integer n) {
		System.out.println(caracter.repeat(n));
	}
	
}
