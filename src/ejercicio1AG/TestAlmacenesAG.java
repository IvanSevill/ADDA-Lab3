package ejercicio1AG;

import java.util.List;
import java.util.Locale;

import common.GurobiCommon;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;;

public class TestAlmacenesAG {

	private static final Integer EJERCICIO = 1;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;
	private static final Integer NUMERO = 140;


	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;

		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;

		for (int i = 1; i <= NUMERO_DE_ARCHIVOS; i++) {
			String datosEntrada = "resources/ejercicio" + EJERCICIO + "/DatosEntrada" + i + ".txt";

			CromosomaAlmacenes cromosomaAlmacenes = new CromosomaAlmacenes(datosEntrada);
			AlgoritmoAG<List<Integer>, SolucionAlmacen> ap = AlgoritmoAG.of(cromosomaAlmacenes);
			ap.ejecuta();

			GurobiCommon.imprimeCabecera(datosEntrada, i);
			System.out.println(ap.getBestChromosome());
			System.out.println(ap.bestSolution());
			GurobiCommon.separador(GurobiCommon.CARACTER, NUMERO);
		}

	}

}
