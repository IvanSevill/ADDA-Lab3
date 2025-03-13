package ejercicio1AG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.DatosAlmacenes;
import us.lsi.ag.BinaryData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.common.IntPair;

public class CromosomaAlmacenes implements BinaryData<SolucionAlmacen> {

	public static final Double PUNISHMENT = 500000.0;

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
	public Double restriccionCapacidadAlmacenes(Integer almacen, List<Integer> acum) {
		double capacidadOcupada = 0.0;
		for (Integer i : acum) {
			if (getAlmacenAG(i) == almacen) {
				capacidadOcupada += DatosAlmacenes.getMetrosCubicosProducto(getProductoAG(i));
			}
		}
		double exceso = capacidadOcupada - DatosAlmacenes.getMetrosCubicosAlmacen(almacen);
		return exceso > 0 ? exceso * PUNISHMENT : 0.0;
	}

	// Restricción de incompatibilidades
	public Double restriccionIncompatibilidad(List<Integer> acum) {
		double penalizacion = 0.0;
		for (int i = 0; i < acum.size(); i++) {
			for (int j = i + 1; j < acum.size(); j++) {
				if (getAlmacenAG(acum.get(i)) == getAlmacenAG(acum.get(j)) &&
					DatosAlmacenes.sonIncompatibles(getProductoAG(acum.get(i)), getProductoAG(acum.get(j)))) {
					penalizacion += PUNISHMENT;
				}
			}
		}
		return penalizacion;
	}

	// Restricción para evitar productos repetidos
	public Double restriccionProductosUnicos(List<Integer> acum) {
		boolean[] productosAsignados = new boolean[DatosAlmacenes.getNumProductos()];
		double penalizacion = 0.0;
		for (Integer i : acum) {
			int producto = getProductoAG(i);
			if (productosAsignados[producto]) {
				penalizacion += PUNISHMENT;
			} else {
				productosAsignados[producto] = true;
			}
		}
		return penalizacion;
	}

	// Función de fitness
	public Double fitnessFunction(List<Integer> value) {
        List<Integer> acum = new ArrayList<>();
        Set<Integer> almacenesUsados = new HashSet<>();
        int productosAlmacenados = 0;

        for (int i = 0; i < value.size(); i++) {
            if (value.get(i) > 0) {
                acum.add(i);
                productosAlmacenados++;
                almacenesUsados.add(getAlmacenAG(i));
            }
        }

        double penalizacionCapacidad = 0.0;
        for (int i = 0; i < DatosAlmacenes.getNumAlmacenes(); i++) {
            penalizacionCapacidad += restriccionCapacidadAlmacenes(i, acum);
        }
        double penalizacionIncompatibilidad = restriccionIncompatibilidad(acum);
        double penalizacionProductosUnicos = restriccionProductosUnicos(acum);

        return productosAlmacenados * 100 + almacenesUsados.size() * 50 
            - penalizacionCapacidad - penalizacionIncompatibilidad - penalizacionProductosUnicos;
    }
}