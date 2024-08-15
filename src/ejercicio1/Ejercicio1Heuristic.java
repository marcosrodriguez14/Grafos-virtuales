package ejercicio1;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import _datos.DatosEjercicio1;

public class Ejercicio1Heuristic {

		public static Double heuristic(Ejercicio1Vertex v1, Predicate<Ejercicio1Vertex> goal, Ejercicio1Vertex v2) {
			Double res = 0.;
			//Desde el vertice en el que estemos hasta el ultimo el el mayor beneficio que podremos obtener es el que podemos calcular con la cantidad que nos queda
			if (v1.index() < DatosEjercicio1.getmVariedades()) {
				res = IntStream.range(0, DatosEjercicio1.getmVariedades()).boxed()
						.mapToDouble(i -> DatosEjercicio1.getBeneficio(i) * DatosEjercicio1.getKgMaxVariedadGrafo(i, v1.remaining()))
								.max().orElse(-1000.);
			}
			return res;
		}
}
