package ejercicio2AG;

import java.util.List;
import java.util.Map;
import java.util.Set;

import common.DatosAlmacenes;
import common.DatosCursos;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.Map2;
import us.lsi.common.Set2;

public class CromosomaCursos implements BinaryData<SolucionCursos> {

	public static final Double PUNISHMENT = 100.0;

	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}

	public CromosomaCursos(String linea) {
		DatosCursos.iniDatos(linea);
	}

	// Tamaño del cromosoma x_curso_area
	public Integer size() {
		return DatosCursos.getNumCursos();
	}

	public SolucionCursos solucion(List<Integer> value) {
		return SolucionCursos.create(value);
	}

	public Double goal(List<Integer> ls) {
		double res = 0.;
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				res += DatosCursos.getRelevancia(i);
			}

		}
		return res;
	}

	public Double penalizacion(List<Integer> ls) {
		Double error1 = 0.;
		Double error2 = 0.;
		Double error3 = 0.;
		Double error4 = 0.;

		Set<Integer> areas = Set2.empty();
		Double totalDuracion = 0.;
		Double totalCoste = 0.;
		Integer numCursosSeleccionados = 0;
		Map<Integer, Integer> sumaCursosSeleccionadosPorArea = Map2.empty();

		// Inicializo el diccionario
		for (int i = 0; i < DatosCursos.getNumAreas(); i++) {
			sumaCursosSeleccionadosPorArea.put(i, 0);
		}

		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				areas.add(DatosCursos.getArea(i));
				totalDuracion += DatosCursos.getDuracion(i);
				totalCoste += DatosCursos.getCoste(i);
				numCursosSeleccionados++;
				// En el caso de que no se haya puesto ya esa area lo pongo
				sumaCursosSeleccionadosPorArea.put(DatosCursos.getArea(i), sumaCursosSeleccionadosPorArea.get(DatosCursos.getArea(i)) + 1);
			}
		}

		// La duración media tiene que ser como mínimo 20 y sino penalizo con lo que me
		// falta hasta llegar a esa media
		if (numCursosSeleccionados > 0) {
			Double mediaDuracion = totalDuracion / numCursosSeleccionados;
			if (mediaDuracion < 20.) {
				error1 = 20. - mediaDuracion;
			}
		} else {
			error1 += PUNISHMENT;
		}

		// No puedo superar al presupuesto total y castigo con lo que me paso
		if (totalCoste > DatosCursos.getPresupuestoTotal()) {
			error2 = Math.abs(DatosCursos.getPresupuestoTotal() - totalCoste);
		}

		// Tengo que acercarme todo lo posible al area maxima y sino penalizo con el
		// número de áreas que faltan
		if (areas.size() != DatosCursos.getNumAreas()) {
			error3 += error3 + (DatosCursos.getNumAreas() - areas.size()) * PUNISHMENT;
		}

		// Tengo que tener mas de tecnologia y en el caso de que sea mayor otro penalizo
		// con la diferencia
		for (int i = 0; i < DatosCursos.getNumAreas(); i++) {
			if (sumaCursosSeleccionadosPorArea.get(0) < sumaCursosSeleccionadosPorArea.get(i)) {
				error4 += sumaCursosSeleccionadosPorArea.get(i) - sumaCursosSeleccionadosPorArea.get(0);
			}
		}

		return error1 * error1 + error2 * error2 + error3 * error3 + error4 * error4;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		return goal(value) - PUNISHMENT * penalizacion(value);
	}

}
