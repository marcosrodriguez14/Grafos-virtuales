package ejercicio3.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import _datos.DatosEjercicio3;
import us.lsi.common.List2;

public record Ejercicio3Problem(Integer index, Integer investigador, Integer trabajo, List<Integer> dias,
		List<List<Integer>> distribucion) {

	// Factoria
	public static Ejercicio3Problem of(Integer index, Integer investigador, Integer trabajo, List<Integer> dias,
			List<List<Integer>> distribucion) {
		return new Ejercicio3Problem(index, investigador, trabajo, dias, distribucion);
	}

	// Problema inicial
	public static Ejercicio3Problem initial() {
		return of(0, 0, 0, DatosEjercicio3.getDiasDisponibles(), DatosEjercicio3.getListReparto());
	}

	// Alternativas
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<>();
		if (index < DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos()) {
			Integer investigador = index / DatosEjercicio3.getmTrabajos();
			Integer trabajo = index % DatosEjercicio3.getmTrabajos();
			Integer especialidad = DatosEjercicio3.getEspecialidad(investigador);
			Integer diasNecesarios = DatosEjercicio3.diasNecesarios(trabajo, especialidad);
			if (DatosEjercicio3.getCapacidad(investigador) - diasNecesarios < 0) {
				alternativas = List2.of(0);
			} else {
				List<Integer> min = List2.of(diasNecesarios, DatosEjercicio3.getCapacidad(investigador));
				Integer alternativasMinimas = Collections.min(min);
				alternativas = List2.rangeList(0, alternativasMinimas + 1);
			}
		} else {
			alternativas = List2.empty();
		}
		Collections.reverse(alternativas);
		return alternativas;
	}

	// Vecino
	public Ejercicio3Problem neighbor(Integer a) {

		Integer index2 = index;
		Integer index3 = index2 + 1;
		Integer investigador2 = investigador;
		Integer trabajo2 = trabajo;
		List<Integer> dias2 = List2.copy(dias);

		Integer investigador3 = investigador;
		Integer trabajo3 = trabajo;
		List<List<Integer>> distribucion2 = List2.copy(distribucion);
		Integer especialidad = DatosEjercicio3.getEspecialidad(investigador);

		investigador2 = index3 / DatosEjercicio3.getmTrabajos();
		trabajo2 = index3 % DatosEjercicio3.getmTrabajos();

		// Modificacion de dias

		Integer resta = dias2.get(investigador3) - a;
		dias2.set(investigador3, resta);

		// Modificacion de distribucion
		List<List<Integer>> distribucion2Mutable = distribucion2.stream().map(ArrayList::new) // Crear una nueva lista
																								// mutable para cada
																								// sublista
				.collect(Collectors.toList()); // Convertir el stream a una lista mutable
		List<Integer> distribucionTrabajoMutable = new ArrayList<>(distribucion2Mutable.get(trabajo3));
		Integer resta2 = distribucion2Mutable.get(trabajo3).get(especialidad) - a;
		distribucionTrabajoMutable.set(especialidad, resta2);
		distribucion2Mutable.set(trabajo3, distribucionTrabajoMutable);

		return of(index2 + 1, investigador2, trabajo2, dias2, distribucion2Mutable);
	}

	public Double heuristic() {

		Double calidades = 0.;
		// mientras index < n * m
		if (this.index() <= DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos()) {
			// comprobar que la lista dias es positiva

			// recorre la lista de distribucion
			for (int i = 0; i < this.distribucion().size(); i++) {
				//
				if (this.distribucion().get(i).stream().anyMatch(e -> e < 0)
						&& this.dias().stream().anyMatch(d -> d < 0)) {
					calidades = calidades - 10000;
				} else {
					if (this.distribucion().get(i).stream().allMatch(e -> e.equals(0))) {
						calidades = calidades + DatosEjercicio3.getCalidadTrabajo(i);

					}
				}
			}
		}

		return calidades;
	}

}
