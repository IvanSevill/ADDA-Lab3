package ejercicio1;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.geometria.Punto2D;

public class SolucionAlmacen {
	
	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;

	private SolucionAlmacen(List<Integer> ls) {
		//TODO
		Punto2D p = Punto2D.of(0.0, 0.0);
	}
	
	@Override
	public String toString() {
		return solucion.entrySet().stream()
		.map(p -> p.getKey().producto()+": Almacen "+p.getValue())
		.collect(Collectors.joining("\n", "Reparto de productos y almacen en el que se coloca cada uno de ellos:\n", String.format("\nProductos colocados: %d", numproductos)));
	}

}
