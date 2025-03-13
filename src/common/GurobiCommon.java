package common;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class GurobiCommon {

	private static final Integer NUMERO = 140;
	private static final String BLANCO = " ";
	public static final String CARACTER = "-";

	public static <E> void generaLpConAuxGrammar(Class<E> clase, String ficheroLsi, String ficheroLp)
			throws IOException {
		System.out.println();
		separador(CARACTER, NUMERO);
		String s = "Transformacion de AuxGrammar";
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		System.out.println(" |" + BLANCO.repeat(espaciosPorLado) + s + BLANCO.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
		AuxGrammar.generate(clase, ficheroLsi, ficheroLp);
		separador(CARACTER, NUMERO);
	}

	public static GurobiSolution ejecucionGurobi(String ficheroLp) throws IOException {
		System.out.println();
		separador(CARACTER, NUMERO);
		String s = "Ejecucion de Gurobi";
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		System.out.println(" |" + BLANCO.repeat(espaciosPorLado) + s + BLANCO.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
		GurobiSolution solution = GurobiLp.gurobi(ficheroLp);
		Locale.setDefault(Locale.of("en", "US"));
		separador(CARACTER, NUMERO);
		return solution;
	}

	public static void imprimeSolucion(GurobiSolution solucion) throws IOException {
		System.out.println();
		separador(CARACTER, NUMERO);
		String s = "SOLUCION";
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		System.out.println(" |" + BLANCO.repeat(espaciosPorLado) + s + BLANCO.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
		System.out.println(solucion.toString((a, d) -> d > 0.));
		separador(CARACTER, NUMERO);

	}

	public static void imprimeCabecera(String datosEntrada, Integer ejercicio) {
		System.out.println("\n\n");
		separador(CARACTER, NUMERO);
		String s = "Ejecutando ejercicio " + ejercicio + " con datos de entrada: " + datosEntrada;
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		System.out.println(" |" + BLANCO.repeat(espaciosPorLado) + s + BLANCO.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
	}

	public static void separador(String caracter, Integer n) {
		System.out.println(caracter.repeat(n));
	}

}
