package ejercicio3PLE;

import java.io.IOException;
import common.DatosFestival;
import common.GurobiCommon;
import ejercicio1PLE.Ejercicio1;
import us.lsi.gurobi.GurobiSolution;

public class Ejercicio3 {

	private static final Integer EJERCICIO = 3;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;

	public static void iniDatos(String fichero) {
		DatosFestival.iniDatos(fichero);
	}

	public static Integer getNumTiposEntrada() {
		return DatosFestival.getNumTiposEntrada();
	}

	public static Integer getNumAreas() {
		return DatosFestival.getNumAreas();
	}

	public static Integer getCosteAsignacion(Integer i, Integer j) {
		return DatosFestival.getCosteAsignacion(i, j);
	}

	public static Integer getAforoMaximoArea(Integer i) {
		return DatosFestival.getAforoMaximoArea(i);
	}

	public static Integer getTipoEntrada(Integer i) {
	    String tipo = DatosFestival.getTipoEntrada(i).tipo(); // Obtiene el tipo como "T0", "T1", etc.
	    if (tipo.startsWith("T")) { // Verifica si el string empieza con "T"
	        tipo = tipo.substring(1); // Elimina el primer carácter ("T")
	    }
	    return Integer.parseInt(tipo); // Convierte el string resultante en un entero
	}


	public static Integer getArea(Integer j) {
		return DatosFestival.getArea(j).aforoMaximo();
	}

	public static Integer getCuotaMinima(Integer i) {
		return DatosFestival.getCuotaMinima(i);
	}

	public static void ejercicio3(Integer num) throws IOException {

		String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + num + ".txt";
		String lsi = "modeloslsi/ejercicio" + EJERCICIO + ".lsi";
		String lp = "modeloslp/ejercicio" + EJERCICIO + "_" + num + ".lp";

		// Inicializa los datos antes de acceder a ellos
		iniDatos(datosEntrada);
		
		GurobiCommon.imprimeCabecera(datosEntrada, EJERCICIO);
		GurobiCommon.generaLpConAuxGrammar(Ejercicio3.class, lsi, lp);
		GurobiSolution solution = GurobiCommon.ejecucionGurobi(lp);
		GurobiCommon.imprimeSolucion(solution);
	}

	public static void main(String[] args) {
		for (int i = 1; i <= NUMERO_DE_ARCHIVOS; i++) {
			try {
				
				ejercicio3(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
