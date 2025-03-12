package ejercicio1AG;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import common.DatosAlmacenes;
import common.DatosAlmacenes.Producto;
import us.lsi.common.Multiset;
import us.lsi.p3.ej_1.DatosMulticonjunto;

public class SolucionAlmacen {
	// VARIABLES
	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	// CREACIÓN VACIO
	public static SolucionAlmacen empty(List<Integer> ls) {
		return new SolucionAlmacen();
	}

	public SolucionAlmacen() {
		this.numproductos = 0;
		this.solucion = new HashMap<Producto, Integer>();
	}

	// CREACIÓN CON LISTA
	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}
	
	private SolucionAlmacen(List<Integer> ls) {
		this.numproductos = DatosAlmacenes.getNumProductos();
		this.solucion = new HashMap<Producto, Integer>();

		for (Integer i = 0; i < ls.size(); i++) {
			
			if (ls.get(i) > 0) {
				Producto producto = DatosAlmacenes.getProducto( i % numproductos);
				Integer almacen = i / numproductos;
				solucion.put(producto, almacen);
				this.solucion.put(producto, almacen);
			}
		}
	}

	public String toString() {
		return solucion.entrySet().stream().map(p -> p.getKey().producto() + ": Almacen " + p.getValue())
				.collect(Collectors.joining("\n",
						"Reparto de productos y almacen en el que se coloca cada uno de ellos:\n",
						String.format("\nProductos colocados: %d", numproductos)));
	}

}
