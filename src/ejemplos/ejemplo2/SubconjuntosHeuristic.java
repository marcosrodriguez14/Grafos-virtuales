package ejemplos.ejemplo2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosSubconjuntos;
import us.lsi.common.List2;

public class SubconjuntosHeuristic {
	
	// Se explica en practicas.
	public static Double heuristic(Ejemplo2Vertex v1, Predicate<Ejemplo2Vertex> goal, Ejemplo2Vertex v2) {
		return v1.remaining().isEmpty()? 0.: 
			IntStream.range(v1.index(), DatosSubconjuntos.getNumSubconjuntos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosSubconjuntos.getElementos(i)).isEmpty())
			.mapToDouble(i -> DatosSubconjuntos.getPeso(i)).min().orElse(100.);
	}	

}

