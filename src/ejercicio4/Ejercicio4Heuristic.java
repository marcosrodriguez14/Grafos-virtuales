package ejercicio4;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosEjercicio4;
public class Ejercicio4Heuristic {
	

	public static Double heuristic(Ejercicio4Vertex v1, Predicate<Ejercicio4Vertex> goal, Ejercicio4Vertex v2) {
		//La heuristica es coge el beneficio maximo que nos queda menos la penalizacion
		Set<Integer> vertices = DatosEjercicio4.getSetClientes();
		Double sumBenef = vertices.stream().mapToDouble(x -> DatosEjercicio4.getBeneficio(x)).sum();
		Double res = sumBenef - v1.visitados().stream().mapToDouble(x -> DatosEjercicio4.getBeneficio(x)).sum();
		return res;
	}
}

