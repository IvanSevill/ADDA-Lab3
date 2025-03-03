package ejercicio2;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes;
import ejercicio1.SolucionAlmacen;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class SolucionCursos {
	private static final Integer EJERCICIO = 2;

	public static SolucionCursos create(List<Integer> ls) {
		return new SolucionCursos(ls);
	}

	private Integer numCursos;
	private Map<Integer, Integer> solucion;
	private Double puntuacionTotal;
	private Integer costeTotal;

	private static void imprimeCabecera(String datosEntrada) {
		System.out.println("\n---------------------------------------------------------------------------------------");
		System.out.println(" | Ejecutando ejercicio " + EJERCICIO + " con datos de entrada: " + datosEntrada + "|");
		System.out.println("---------------------------------------------------------------------------------------\n");
	}

	private SolucionCursos(List<Integer> ls) {
		for (Integer num : ls) {
			String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + num + ".txt";
			String lsi = "resources/modeloslsi/ejercicio" + EJERCICIO + ".lsi";
			String lp = "resources/modeloslp/ejercicio" + EJERCICIO + "_" + num + ".lp";

			imprimeCabecera(datosEntrada);
			try {
				GurobiSolution s = ejercicio2(datosEntrada, lsi, lp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static GurobiSolution ejercicio2(String datosEntrada, String ficheroLsi, String ficheroLp)
			throws IOException {
		DatosCursos.iniDatos(datosEntrada);
		generaLpConAuxGrammar(ficheroLsi, ficheroLp);
		GurobiSolution solucion = ejecucionGurobi(ficheroLp);
		imprimeSolucion(solucion);
		return solucion;
	}

	private static void generaLpConAuxGrammar(String ficheroLsi, String ficheroLp) throws IOException {
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		AuxGrammar.generate(SolucionCursos.class, ficheroLsi, ficheroLp);
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
		System.out.println("\n---------------------------------------------------\n\n\n\n");
	}

	@Override
	public String toString() {
		return solucion.entrySet().stream().map(p -> "Curso " + p.getKey() + ": Seleccionado")
				.collect(Collectors.joining("\n", "Cursos seleccionados:\n",
						String.format("\nTotal de cursos seleccionados: %d\nPuntuación total: %.2f\nCoste total: %d",
								numCursos, puntuacionTotal, costeTotal)));
	}

	public Integer getNumCursos() {
		return numCursos;
	}

	public Map<Integer, Integer> getSolucion() {
		return solucion;
	}

	public Double getPuntuacionTotal() {
		return puntuacionTotal;
	}

	public Integer getCosteTotal() {
		return costeTotal;
	}

	public static void main(String[] args) {
		SolucionAlmacen s = SolucionAlmacen.create(List.of(1, 2, 3));
		System.out.println(s);
	}
}
