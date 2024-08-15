package ejemplos.ejemplo2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejemplo2Vertex(Integer index, Set<Integer> remaining)
		implements VirtualVertex<Ejemplo2Vertex, Ejemplo2Edge, Integer> {

	public static Ejemplo2Vertex of(Integer i, Set<Integer> rest) {
		return new Ejemplo2Vertex(i, rest);
	}

	public static Ejemplo2Vertex initial() {
		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}

	public static Predicate<Ejemplo2Vertex> goal() {
		return v -> v.index() == DatosSubconjuntos.getNumSubconjuntos();
	}

	public static Predicate<Ejemplo2Vertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<Integer>();

		if (index < DatosSubconjuntos.getNumSubconjuntos()) {

			Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));

			if (rest.equals(remaining)) {
				alternativas = List2.of(0);
			} else if (index == DatosSubconjuntos.getNumSubconjuntos() - 1) {
				alternativas = rest.isEmpty() ? List2.of(1) : List2.empty();
			} else {
				alternativas = List2.of(0, 1);
			}
		}

		return alternativas;
	}

	@Override
	public Ejemplo2Vertex neighbor(Integer a) {
		Set<Integer> rest = a == 0 ? Set2.copy(remaining)
				: Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index + 1, rest);
	}

	@Override
	public Ejemplo2Edge edge(Integer a) {
		return Ejemplo2Edge.of(this, neighbor(a), a);
	}

	// Se explica en practicas.
	public Ejemplo2Edge greedyEdge() {
		Set<Integer> rest = Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return rest.equals(remaining) ? edge(0) : edge(1);
	}

	@Override
	public String toString() {
		return String.format("%d; %d", index, remaining.size());
	}

}