package ejercicio2;

import _datos.DatosEjercicio2;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejercicio2Edge(Ejercicio2Vertex source, Ejercicio2Vertex target, Integer action, Double weight)
		implements SimpleEdgeAction<Ejercicio2Vertex, Integer> {
	//La arista es multiplicar el precio del curso por la alternativa de esta forma
	//si el curso no se coge estamos multiplicando su precio * 0
	public static Ejercicio2Edge of(Ejercicio2Vertex s, Ejercicio2Vertex t, Integer a) {
		return new Ejercicio2Edge(s, t, a, a * DatosEjercicio2.getPrecio(s.index()));
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
