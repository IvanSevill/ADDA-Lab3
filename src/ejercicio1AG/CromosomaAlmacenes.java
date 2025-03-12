package ejercicio1AG;

import java.util.ArrayList;
import java.util.List;

import common.DatosAlmacenes;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.IntPair;

public class CromosomaAlmacenes implements BinaryData<SolucionAlmacen> {

	public static final Double PUNISHMENT = 100000.0;

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
		Double capacidadActual = 0.0;
		IntPair pa1 = IntPair.of(getProductoAG(n), getAlmacenAG(n));

		for (Integer i : acum) {

			IntPair pa2 = IntPair.of(getProductoAG(i), getAlmacenAG(i));

			Boolean mismoAlmacen = pa1.second() == pa2.second();
			if (mismoAlmacen) {
				capacidadActual += DatosAlmacenes.getMetrosCubicosProducto(pa2.first());
			}

		}

		return capacidadActual;
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
				res++;
			}
		}
		return DatosAlmacenes.getNumProductos() - res;
	}

	// Restricciones de incompatibilidad
	public Double restriccionIncompatibilidad(Integer n, List<Integer> acum) {
		Double res = 0.;

		IntPair pa1 = IntPair.of(getProductoAG(n), getAlmacenAG(n));

		for (Integer i : acum) {
			IntPair pa2 = IntPair.of(getProductoAG(i), getAlmacenAG(i));

			Boolean mismoAlmacen = pa1.second() == pa2.second();
			Boolean sonIncompatibles = DatosAlmacenes.sonIncompatibles(pa1.first(), pa2.second())
					|| DatosAlmacenes.sonIncompatibles(pa2.first(), pa1.second());

			if (mismoAlmacen && sonIncompatibles) {
				res++;
			}
		}

		return res * PUNISHMENT;
	}

	private Double restricciones(List<Integer> value, List<Integer> acum) {

		double errorCapacidadAlmacenes = 0.0;
		double errorAlmacenarVariasVeces = 0.0;
		double errorIncompatibilidad = 0.0;
		// Vuelvo a recorrer la lista para guardar los errores con el acumulador
		for (int i = 0; i < value.size(); i++) {
			if (value.get(i) > 0) {
				errorCapacidadAlmacenes += restriccionCapacidadAlmacenes(i, acum);
				errorAlmacenarVariasVeces += restriccionAlmacenarVariasVeces(i, acum);
				errorIncompatibilidad += restriccionIncompatibilidad(i, acum);
			}
		}
		return Math.abs(errorCapacidadAlmacenes + errorAlmacenarVariasVeces + errorIncompatibilidad);
	}

	// value podría ser [0, 0, 1, 0, ..., 0, 1] por ser un cromosoma binario
    public Double fitnessFunction(List<Integer> value) {
        double capacidadMaximaAlmacenes = 0.0;
        List<Integer> acum = new ArrayList<>();

        for (int i = 0; i <= DatosAlmacenes.getNumAlmacenes(); i++) { // Corrección del límite
            capacidadMaximaAlmacenes += DatosAlmacenes.getMetrosCubicosAlmacen(getAlmacenAG(i));
        }

        for (int i = 0; i < value.size(); i++) {
            if (value.get(i) > 0) {
                acum.add(i);
            }
        }

        double restriccionesTotal = restricciones(value, acum);
        double resultado = - capacidadMaximaAlmacenes - PUNISHMENT * restriccionesTotal;

        System.out.println(resultado);
        return resultado;
    }

}
