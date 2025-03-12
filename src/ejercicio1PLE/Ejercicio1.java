package ejercicio1PLE;

import java.io.IOException;
import java.util.List;

import common.DatosAlmacenes;
import common.GurobiCommon;
import common.DatosAlmacenes.Almacen;
import common.DatosAlmacenes.Producto;
import us.lsi.gurobi.GurobiSolution;

public class Ejercicio1 {

	private static final Integer EJERCICIO = 1;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;

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
		
		GurobiCommon.imprimeCabecera(datosEntrada, EJERCICIO);
		GurobiCommon.generaLpConAuxGrammar(Ejercicio1.class, lsi,lp);
		GurobiSolution solution = GurobiCommon.ejecucionGurobi(lp);
		GurobiCommon.imprimeSolucion(solution);
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
