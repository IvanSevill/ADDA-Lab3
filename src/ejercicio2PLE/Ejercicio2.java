package ejercicio2PLE;

import java.io.IOException;

import common.DatosCursos;
import common.GurobiCommon;
import us.lsi.gurobi.GurobiSolution;

public class Ejercicio2 {
	private static final Integer EJERCICIO = 2;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;

	private static Integer areas;
	private static Integer cursos;
	private static Integer presupuestoTotal;

	public static Integer getNumCursos() {
		return cursos;
	}
			
	public static Integer getNumAreas() {
		return areas;
	}
	
	public static Integer getPresupuestoTotal() {
		return presupuestoTotal;
	}
	
	public static Integer getCoste(Integer i) {
		return DatosCursos.getCoste(i);
	}
	
	public static Integer getRelevancia(Integer i) {
		return DatosCursos.getRelevancia(i);
	}
	
	public static Integer getDuracion(Integer i) {
		return DatosCursos.getDuracion(i);
	}
	
	public static Integer getArea(Integer i) {
		return DatosCursos.getArea(i);
	}
	
	public static void ejercicio2(Integer num) throws IOException {

		String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + num + ".txt";
		String lsi = "modeloslsi/ejercicio" + EJERCICIO + ".lsi";
		String lp = "modeloslp/ejercicio" + EJERCICIO + "_" + num + ".lp";

		DatosCursos.iniDatos(datosEntrada);

		areas = DatosCursos.getNumAreas();
		cursos = DatosCursos.getNumCursos();
		presupuestoTotal = DatosCursos.getPresupuestoTotal();

		GurobiCommon.imprimeCabecera(datosEntrada, EJERCICIO);
		GurobiCommon.generaLpConAuxGrammar(Ejercicio2.class, lsi, lp);
		GurobiSolution solution = GurobiCommon.ejecucionGurobi(lp);
		GurobiCommon.imprimeSolucion(solution);
	}

	public static void main(String[] args) {
		for (int i = 1; i <= NUMERO_DE_ARCHIVOS; i++) {
			try {
				ejercicio2(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
