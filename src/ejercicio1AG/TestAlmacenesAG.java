package ejercicio1AG;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;;

public class TestAlmacenesAG {

	private static final Integer EJERCICIO = 1;
	private static final Integer NUMERO_DE_ARCHIVOS = 3;
	private static final Integer NUMERO = 140;
	private static final String CARACTER = "-";

	private static void imprimeCabecera(String datosEntrada) {
		System.out.println();
		separador(CARACTER, NUMERO);
		String s = "Ejecutando ejercicio " + EJERCICIO + " con datos de entrada: " + datosEntrada;
		Integer espaciosPorLado = (NUMERO - s.length()) / 2 - 2;
		String blanco = " ";
		System.out.println(" |" + blanco.repeat(espaciosPorLado) + s + blanco.repeat(espaciosPorLado) + "|");
		separador(CARACTER, NUMERO);
	}

	private static void separador(String caracter, Integer n) {
		System.out.println(caracter.repeat(n));
	}

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

			CromosomaAlmacenes cromosomaAlmacenes = new CromosomaAlmacenes(datosEntrada);
			AlgoritmoAG<List<Integer>, SolucionAlmacen> ap = AlgoritmoAG.of(cromosomaAlmacenes);
			ap.ejecuta();

			imprimeCabecera(datosEntrada);
			System.out.println(ap.bestSolution());
			separador(CARACTER, NUMERO);
		}

	}

}
