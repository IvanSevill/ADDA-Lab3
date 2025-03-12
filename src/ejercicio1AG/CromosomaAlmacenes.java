package ejercicio1AG;

import java.util.ArrayList;
import java.util.List;

import common.DatosAlmacenes;
import common.DatosAlmacenes.Producto;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.IntPair;
import us.lsi.p3.ej_1.DatosMulticonjunto;

public class CromosomaAlmacenes implements BinaryData<SolucionAlmacen> {

	public static final Double PUNISHMENT = 10000.0;

	public ChromosomeType type() {
		return ChromosomeType.Binary;
	}

	public CromosomaAlmacenes(String linea) {
		DatosAlmacenes.iniDatos(linea);
	}

	// Tamaño del cromosoma x_producto_almacen
	public Integer size() {
		return DatosAlmacenes.getNumAlmacenes() * DatosAlmacenes.getNumProductos();
	}

	// Rango max y min que en binario no hace falta pero en el cromosoma de rango si
	public Integer min(Integer i) {
		return 0;
	}

	public Integer max(Integer i) {
		return 1;
	}

	public SolucionAlmacen solucion(List<Integer> value) {
		return SolucionAlmacen.create(value);
	}

	public Integer getProductoAG(Integer i) {
		return i % DatosAlmacenes.getNumProductos();
	}

	public Integer getAlmacenAG(Integer i) {
		return i / DatosAlmacenes.getNumProductos();
	}

	// Capacidad de los almacenes
	public Double restriccionCapacidadAlmacenes(Integer n, List<Integer> acum) {
		Double res = 0.;
		Integer capacidadActual = 0;
		IntPair pa1 = IntPair.of(getProductoAG(n), getAlmacenAG(n));

		for (Integer i : acum) {

			IntPair pa2 = IntPair.of(getProductoAG(i), getAlmacenAG(i));

			Boolean mismoAlmacen = pa1.second() == pa2.second();
			if (mismoAlmacen) {
				capacidadActual += DatosAlmacenes.getMetrosCubicosProducto(pa2.first());
			}

		}

		Boolean capacidadSuperada = capacidadActual > DatosAlmacenes.getMetrosCubicosAlmacen(pa1.second());
		return capacidadSuperada ? PUNISHMENT * PUNISHMENT : 0.;
	}

	// Cada producto se almacena en un solo almacen o no se almacena
	public Double restriccionAlmacenarVariasVeces(Integer n, List<Integer> acum) {
		Double res = 0.;

		IntPair pa1 = IntPair.of(getProductoAG(n), getAlmacenAG(n));

		for (Integer i : acum) {

			IntPair pa2 = IntPair.of(getProductoAG(i), getAlmacenAG(i));

			Boolean mismoProducto = pa1.first() == pa2.first();
			Boolean diferenteAlmacen = pa1.second() != pa2.second();
			if (mismoProducto && diferenteAlmacen) {
				return PUNISHMENT * PUNISHMENT;
			}
		}

		return res;
	}

	// Restricciones de incompatibilidad
	public Double restriccionIncompatibilidad(Integer n, List<Integer> acum) {

		IntPair pa1 = IntPair.of(getProductoAG(n), getAlmacenAG(n));

		for (Integer i : acum) {
			IntPair pa2 = IntPair.of(getProductoAG(i), getAlmacenAG(i));

			Boolean mismoAlmacen = pa1.second() == pa2.second();
			Boolean sonIncompatibles = DatosAlmacenes.sonIncompatibles(pa1.first(), pa2.second());

			if (mismoAlmacen && sonIncompatibles) {
				return PUNISHMENT * PUNISHMENT;
			}
		}

		Double res = 0.;
		return res;
	}

	public Double restricciones(Integer i, List<Integer> acum) {
		return restriccionCapacidadAlmacenes(i, acum) + restriccionAlmacenarVariasVeces(i, acum)
				+ restriccionIncompatibilidad(i, acum);
	}

	public Double fitnessFunction(List<Integer> value) {
		double goal = 0., error = 0.;
		List<Integer> acum = new ArrayList<Integer>();

		for (int i = 0; i < value.size(); i++) {
			goal += value.get(i);
			if (i > 0) {
				acum.add(i);
			}
			error += restricciones(i, acum);
		}
		return -goal - 10000 * error * error ;
	}

}
