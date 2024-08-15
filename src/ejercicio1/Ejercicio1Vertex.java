package ejercicio1;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import _datos.DatosEjercicio1;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio1Vertex(Integer index, List<Integer> remaining)
		implements VirtualVertex<Ejercicio1Vertex, Ejercicio1Edge, Integer> {

	public static Ejercicio1Vertex of(Integer i, List<Integer> rest) {
		return new Ejercicio1Vertex(i, rest);
	}

	public static Ejercicio1Vertex initial() {
		return of(0, DatosEjercicio1.getLsTipos());

	}

	// Es vertice final si el indice es igual al numero de variedades
	public static Predicate<Ejercicio1Vertex> goal() {
		return v -> v.index() == DatosEjercicio1.getmVariedades();
	}

	// Tiene solucion si todo los kg que quedan son >= 0 porque no podemos usar mas
	// kg de los que tenemos
	public static Predicate<Ejercicio1Vertex> goalHasSolution() {
		return v -> v.remaining.stream().allMatch(x -> x >= 0);
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();

		// i < m
		if (index < DatosEjercicio1.getmVariedades()) {
			Integer kgMax = DatosEjercicio1.getKgMaxVariedadGrafo(index, remaining);
			// i== m-1 -> ultimo vertice cogemos todos los kg posibles
			if (index == DatosEjercicio1.getmVariedades() - 1) {
				alternativas = List2.of(kgMax);
			} else {
				// Obtener cuanto es el min de los max kilos que puedo fabricar en cada variedad
				// -> 0 hasta max kilos posibles
				alternativas = List2.rangeList(0, kgMax + 1);

			}
		}
		Collections.reverse(alternativas);
		return alternativas;
	}

	@Override
	public Ejercicio1Vertex neighbor(Integer a) {
		// Copiamos las listas para que no se modifiquen las originales
		List<Integer> remaining2 = List2.copy(remaining);
		Set<String> tipos = DatosEjercicio1.getcafesDeVariedad(index);
		Set<Integer> indiceTipo = DatosEjercicio1.setIndicesTipos(tipos);
		// Para cada tipo en los tipos de cafes que tenemos en esa variedad cogemos los
		// kg que hemos usado y se los restamos en el remaining
		for (Integer i : indiceTipo) {
			Double porcentaje = DatosEjercicio1.getPorcentaje(i, index);
			Integer mult = (int) (porcentaje * a);
			remaining2.set(i, remaining2.get(i) - mult);
		}
		return of(index + 1, remaining2);
	}

	@Override
	public Ejercicio1Edge edge(Integer a) {
		return Ejercicio1Edge.of(this, neighbor(a), a);
	}

}
