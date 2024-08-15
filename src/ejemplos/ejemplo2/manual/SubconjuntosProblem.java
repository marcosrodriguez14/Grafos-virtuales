package ejemplos.ejemplo2.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record SubconjuntosProblem(Integer index, Set<Integer> remaining) {

	// Factoria
	public static SubconjuntosProblem of(Integer i, Set<Integer> s) {
		return new SubconjuntosProblem(i, s);
	}

	// Problema inicial
	public static SubconjuntosProblem initial() {
		return of(0, Set2.copy(DatosSubconjuntos.getUniverso()));
	}

	// Alternativas
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

	// Vecino
	public SubconjuntosProblem neighbor(Integer a) {
		Set<Integer> rest = a == 0 ? Set2.copy(remaining)
				: Set2.difference(remaining, DatosSubconjuntos.getElementos(index));
		return of(index + 1, rest);
	}

	// Heuristica
	// Optimista: Si todavia nos quedaban elementos por cubrir, el subconjunto de
	// menor
	// peso de los que quede, lo hará
	public Double heuristic() {
		return remaining.isEmpty() ? 0.
				: IntStream.range(index, DatosSubconjuntos.getNumSubconjuntos())
						.filter(i -> !List2.intersection(remaining, DatosSubconjuntos.getElementos(i)).isEmpty())
						.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}

	@Override
	public String toString() {
		return "SubconjuntoProblem[" + index.toString() + ";" + remaining.toString() + "]";
	}

}
