package ejemplos.ejemplo2;

import _datos.DatosSubconjuntos;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejemplo2Edge(Ejemplo2Vertex source, Ejemplo2Vertex target, Integer action, Double weight)
		implements SimpleEdgeAction<Ejemplo2Vertex, Integer> {

	public static Ejemplo2Edge of(Ejemplo2Vertex s, Ejemplo2Vertex t, Integer a) {
		return new Ejemplo2Edge(s, t, a, a * DatosSubconjuntos.getPeso(s.index()));
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
