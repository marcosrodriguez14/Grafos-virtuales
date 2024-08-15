package ejercicio1.manual;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import _datos.DatosEjercicio1;
import us.lsi.common.List2;

public record Ejercicio1Problem(Integer index, List<Integer> remaining) {

	public static Integer contador = 0;

	public static Integer getContador() {
		return contador;
	}

	// Constructor/factoria
	public static Ejercicio1Problem of(Integer i, List<Integer> rest) {
		contador += 1;
		return new Ejercicio1Problem(i, rest);
	}

	// Problema inicial
	public static Ejercicio1Problem initial() {
		return of(0, DatosEjercicio1.getLsTipos());

	}

	// Alternativas
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

	// vecinos
	public Ejercicio1Problem neighbor(Integer a) {

		List<Integer> remaining2 = List2.copy(remaining);
		Set<String> tipos = DatosEjercicio1.getcafesDeVariedad(index);
		Set<Integer> indiceTipo = DatosEjercicio1.setIndicesTipos(tipos);

		for (Integer i : indiceTipo) {
			Double porcentaje = DatosEjercicio1.getPorcentaje(i, index);
			Integer mult = (int) (porcentaje * a);
			remaining2.set(i, remaining2.get(i) - mult);
		}
		return of(index + 1, remaining2);
	}

	// Heurística
	public Double heuristic() {
		Double res = 0.;
		res = IntStream.range(0, DatosEjercicio1.getmVariedades()).boxed()
				.mapToDouble(i -> DatosEjercicio1.getBeneficio(i) * DatosEjercicio1.getKgMaxVariedadGrafo(i, remaining))
				.max().orElse(-1000.);
		return res;
	}

}
