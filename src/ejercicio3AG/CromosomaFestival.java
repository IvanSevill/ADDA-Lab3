package ejercicio3AG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DatosFestival;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class CromosomaFestival implements ValuesInRangeData<Integer, SolucionFestival> {

	private static final Double PUNISHMENT = 100.;

	public CromosomaFestival(String linea) {
		DatosFestival.iniDatos(linea);
	}

	public Integer size() {
		return DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada();
	}

	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	public SolucionFestival solucion(List<Integer> value) {
		return SolucionFestival.create(value);
	}

//	public Integer max(Integer k) {
//		Integer res = Integer.MIN_VALUE;
//		for (int i = 0; i < DatosFestival.getNumTiposEntrada(); i++) {
//			for (int j = 0; j < DatosFestival.getNumAreas(); j++) {
//				Integer areaMax =  DatosFestival.get / DatosFestival.getCosteAsignacion(i, j);
//				if (areaMax >= res) {
//					res = areaMax;
//				}
//			}
//
//		}
//
//		return res;
//	}
	

	
	public Integer max(Integer i) {
		return DatosFestival.getCuotaMinima(i/ DatosFestival.getNumAreas()) + 1;
	}


	public Integer min(Integer j) {
		return 0;

	}

	private Double penalizacion(List<Integer> value) {
		Double errorEntradasMinimas = 0.;
		Double errorLimiteAforo = 0.;

		Map<Integer, Integer> sumaAreas = new HashMap<>();
		Map<Integer, Integer> sumaEntradas = new HashMap<>();

		for (int i = 0; i < DatosFestival.getNumAreas(); i++) {
			sumaAreas.put(i, 0);
		}

		for (int i = 0; i < DatosFestival.getNumTiposEntrada(); i++) {
			sumaEntradas.put(i, 0);
		}

		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) > 0) {
				Integer area = i % DatosFestival.getNumAreas();
				Integer tipoEntrada = i / DatosFestival.getNumAreas();
				sumaAreas.put(area, sumaAreas.get(area) + value.get(i)); // Sumar las unidades asignadas a cada área
				sumaEntradas.put(tipoEntrada, sumaEntradas.get(tipoEntrada) + value.get(i)); // Sumar unidades por tipo
			}
		}

		// 1. Restricción entradas minimas
		for (int i = 0; i < DatosFestival.getNumTiposEntrada(); i++) {
			if (sumaEntradas.get(i) < DatosFestival.getCuotaMinima(i)) {
				errorEntradasMinimas += PUNISHMENT * (DatosFestival.getCuotaMinima(i) - sumaEntradas.get(i)); 
			}
		}

		// 2. Limite de aforo
		for (int area = 0; area < DatosFestival.getNumAreas(); area++) {
			if (sumaAreas.get(area) > DatosFestival.getAforoMaximoArea(area)) {
				errorLimiteAforo += PUNISHMENT * (sumaAreas.get(area) - DatosFestival.getAforoMaximoArea(area)); 
			}
		}

		return errorEntradasMinimas * errorEntradasMinimas + errorLimiteAforo * errorLimiteAforo;
	}

	private Double goal(List<Integer> value) {
		Double res = 0.;

		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) > 0) {
				res += DatosFestival.getCosteAsignacion(i / DatosFestival.getNumAreas(),
						i % DatosFestival.getNumAreas()) * value.get(i);
			}
		}
		return res;
	}

	public Double fitnessFunction(List<Integer> value) {
		return - goal(value) - PUNISHMENT * penalizacion(value);
	}

}