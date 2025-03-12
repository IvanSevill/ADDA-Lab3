package ejercicio1PLE;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import common.DatosAlmacenes;
import common.DatosAlmacenes.Almacen;
import common.DatosAlmacenes.Producto;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1 {

	private static final Integer EJERCICIO = 1;
	private static final Integer NUMERO_DE_ARCHIVOS = 1;

	private static List<Almacen> almacenes;
	private static List<Producto> productos;

	public static Integer getNumProductos() {
		return productos.size();
	}

	public static Integer getNumAlmacenes() {
		return almacenes.size();
	}

	public static Integer getMetrosCubicosAlmacen(Integer j) {
		return almacenes.get(j).metroscubicosdisponibles();
	}

	public static Integer getMetrosCubicosProducto(Integer i) {
		return productos.get(i).metroscubicosrequeridos();
	}

	public static Boolean sonIncompatibles(Integer i, Integer j) {
		return DatosAlmacenes.sonIncompatibles(i, j);
	}

	public static void ejercicio1(Integer num) throws IOException {

		String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + num + ".txt";
		String lsi = "modeloslsi/ejercicio" + EJERCICIO + ".lsi";
		String lp = "modeloslp/ejercicio" + EJERCICIO + "_" + num + ".lp";

		DatosAlmacenes.iniDatos(datosEntrada);

		almacenes = DatosAlmacenes.getAlmacenes();
		productos = DatosAlmacenes.getProductos();

		AuxGrammar.generate(DatosAlmacenes.class, lsi, lp);
		GurobiSolution solution = GurobiLp.gurobi(lp);
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s, d) -> d > 0.));
	}

	public static void main(String[] args) {
		for (int i = 1; i <= NUMERO_DE_ARCHIVOS; i++) {
			try {
				ejercicio1(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
