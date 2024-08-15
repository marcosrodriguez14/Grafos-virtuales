package ejercicio2;

import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosEjercicio2;
import us.lsi.common.List2;

public class Ejercicio2Heuristic {
	//Si hemos cubierto todas las tematicas devolvemos 0 si no nos quedamos con el minimo que nos quede por cubrir
	public static Double heuristic(Ejercicio2Vertex v1, Predicate<Ejercicio2Vertex> goal, Ejercicio2Vertex v2) {
		return v1.remaining().isEmpty()? 0.: 
			IntStream.range(v1.index(), DatosEjercicio2.getnCursos())
			.filter(i -> !List2.intersection(v1.remaining(), 
					DatosEjercicio2.getTematicasCurso(i)).isEmpty())
			.mapToDouble(i -> DatosEjercicio2.getPrecio(i)).min().orElse(100.);
	}	

}

