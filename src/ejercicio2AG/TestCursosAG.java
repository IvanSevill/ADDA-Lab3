package ejercicio2AG;

import java.util.List;
import java.util.Locale;

import common.GurobiCommon;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class TestCursosAG {

	private static final Integer EJERCICIO = 2;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;

		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;

		for (int i = 1; i <= NUMERO_DE_ARCHIVOS; i++) {
			String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + i + ".txt";

			CromosomaCursos cromosomaCursos = new CromosomaCursos(datosEntrada);
			AlgoritmoAG<List<Integer>, SolucionCursos> ap = AlgoritmoAG.of(cromosomaCursos);
			ap.ejecuta();

			GurobiCommon.imprimeCabecera(datosEntrada, EJERCICIO);
			System.out.println(ap.getBestChromosome());
			System.out.println(ap.bestSolution());
			GurobiCommon.separador();
		}

	}

}
