package ejercicio4.manual;

import java.util.List;
import java.util.Set;

import _datos.DatosEjercicio4;
import us.lsi.common.List2;
import us.lsi.common.Set2;

public record Ejercicio4Problem(Integer cliente, Set<Integer> pendientes, List<Integer> visitados, Double kms) {

	// Factoria
	public static Ejercicio4Problem of(Integer cliente, Set<Integer> pendientes, List<Integer> visitados, Double kms) {
		return new Ejercicio4Problem(cliente, pendientes, visitados, kms);
	}

	// Problema inicial
	public static Ejercicio4Problem initial() {
		return of(0, DatosEjercicio4.getSetClientes(), List2.of(0), 0.);
	}

	// Alternativas
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (!pendientes.isEmpty()) {
			for (Integer p : pendientes) {
				if (DatosEjercicio4.existeArista(cliente, p)) {
					alternativas.add(p);
				}
			}
		} else {
			if (DatosEjercicio4.existeArista(cliente, 0)) {
				alternativas = List2.of(0);
			} else {
				alternativas = List2.of();
			}
		}
		return alternativas;
	}

	// Vecino
	public Ejercicio4Problem neighbor(Integer a) {

		Set<Integer> pendientes2 = Set2.copy(pendientes);
		List<Integer> visitados2 = List2.copy(visitados);
		Double kms2 = kms + 0.;
		if (pendientes2.contains(0)) {
			visitados2.add(0);
			pendientes2.remove(0);
		}
		pendientes2.remove(a);
		visitados2.add(a);
		kms2 += DatosEjercicio4.getPeso(cliente, a);
		return of(a, pendientes2, visitados2, kms2);
	}

	// Heuristica

	public Double heuristic() {
		Double res = 0.;
		if (this.cliente()!=0) {
			Set<Integer> vertices = this.pendientes();
			Double sumBenef = vertices.stream().mapToDouble(x -> DatosEjercicio4.getBeneficio(x)).sum();
			res= sumBenef;
		}else {
			res =-10000.;
		}
		return res;
	}

}
