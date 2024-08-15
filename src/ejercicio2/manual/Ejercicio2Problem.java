package ejercicio2.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record Ejercicio2Problem(Integer index, Set<Integer> remaining, Set<Integer> centers) {

	public static Integer contador = 0;

	public static Integer getContador() {
		return contador;
	}

	// Constructor/factoria
	public static Ejercicio2Problem of(Integer i, Set<Integer> rest, Set<Integer> centers) {
		contador += 1;
		return new Ejercicio2Problem(i, rest, centers);
	}

	// Problema inicial
	public static Ejercicio2Problem initial() {
		return of(0, Set2.copy(DatosEjercicio2.getTematicas()), Set2.empty());

	}

	// Alternativas
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<Integer>();

		if (index < DatosEjercicio2.getnCursos()) {
			if (remaining().isEmpty()) {
				alternativas = List2.of(0);
			} else {
				Set<Integer> rest = Set2.difference(remaining(), DatosEjercicio2.getTematicasCurso(index));
				if (index == DatosEjercicio2.getnCursos() - 1) {
					if (centers().contains(DatosEjercicio2.getCentroCurso(index))
							|| centers.size() < DatosEjercicio2.getmaxCentros()) {
						alternativas = rest.isEmpty() ? List2.of(1) : List2.empty();
					}
				} else if (rest.equals(remaining)) {
					alternativas = List2.of(0);
				} else {
					if (centers().contains(DatosEjercicio2.getCentroCurso(index))
							|| centers.size() < DatosEjercicio2.getmaxCentros()) {
						alternativas = List2.of(0, 1);
					} else {
						alternativas = List2.of(0);
					}
				}
			}
		}
		return alternativas;
	}

	// vecinos
	public Ejercicio2Problem neighbor(Integer a) {

		Set<Integer> remaining2 = Set2.copy(remaining);
		Set<Integer> centros2 = Set2.copy(centers);

		if (a == 1) {
			Set<Integer> diferencia = Set2.difference(remaining2, DatosEjercicio2.getTematicasCurso(index));
			centros2.add(DatosEjercicio2.getCentroCurso(index));
			Set<Integer> centrosdif = Set2.copy(centros2);
			return of(index + 1, diferencia, centrosdif);
		} else {
			return of(index + 1, remaining2, centros2);
		}
	}

	// Heurística
	public Double heuristic() {
		return remaining.isEmpty() ? 0.
				: IntStream.range(index, DatosEjercicio2.getnCursos())
						.filter(i -> !List2.intersection(remaining, DatosEjercicio2.getTematicasCurso(i)).isEmpty())
						.mapToDouble(i -> DatosEjercicio2.getPrecio(i)).min().orElse(100.);
	}
}
