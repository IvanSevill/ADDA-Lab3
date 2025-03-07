package ejercicio1;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class SolucionAlmacen {
    private static final Integer EJERCICIO = 1;
	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	private SolucionAlmacen(List<Integer> ls) {
		for (Integer num : ls) {

			String datosEntrada = "resources/ejercicio"+EJERCICIO+"/DatosEntrada" + num + ".txt";
			String lsi = "resources/modeloslsi/ejercicio"+EJERCICIO+".lsi";
			String lp = "resources/modeloslp/ejercicio"+EJERCICIO+"_" + num + ".lp";

			imprimeCabecera(datosEntrada);

			try {
				ejercicio1(datosEntrada, lsi, lp);
				
//				this.solucion = s.values.entrySet().stream()
//				.filter(e -> e.getValue() > 0)
//				.collect(Collectors.toMap(
//						e -> DatosAlmacenes.getProducto(Integer.valueOf(e.getKey().split("_")[1])),
//						e -> Integer.valueOf(e.getKey().split("_")[1])));
//
//				this.numproductos = solucion.size();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
		System.out.println("\n---------------------------------------------------");
		System.out.println("\n<--- Transformacion de AuxGrammar --->\n");
		AuxGrammar.generate(SolucionAlmacen.class, ficheroLsi, ficheroLp);
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
		System.out.println(" | Ejecutando ejercicio "+EJERCICIO+" con datos de entrada: " + datosEntrada + "|");
		System.out.println("---------------------------------------------------------------------------------------\n");
	}

	public static void ejercicio1(String datosEntrada, String ficheroLsi, String ficheroLp) throws IOException {
		DatosAlmacenes.iniDatos(datosEntrada);
		generaLpConAuxGrammar(ficheroLsi, ficheroLp);
		GurobiSolution solucion = ejecucionGurobi(ficheroLp);
		imprimeSolucion(solucion);
	}

	public static void main(String[] args) {
		SolucionAlmacen s = SolucionAlmacen.create(List.of(1, 2, 3));
	}

}
