package ejercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import _datos.DatosEjercicio2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio2Vertex(Integer index, Set<Integer> remaining, Set<Integer> centers)
		implements VirtualVertex<Ejercicio2Vertex, Ejercicio2Edge, Integer> {

	public static Ejercicio2Vertex of(Integer i, Set<Integer> rest, Set<Integer> centers) {
		return new Ejercicio2Vertex(i, rest, centers);
	}
	//vertice inicial empezamos por el curso 0 , las tematicas las tenemos todas por cubrir y los centros que hemos seleccionados estan vacios.
	public static Ejercicio2Vertex initial() {
		return of(0, Set2.copy(DatosEjercicio2.getTematicas()), Set2.empty());
	}
	//Cuando llegemos al último curso hemos terminado
	public static Predicate<Ejercicio2Vertex> goal() {
		return v -> v.index() == DatosEjercicio2.getnCursos() ;
	}
	//Tiene solucion si se han cubierto todas las tematicas
	public static Predicate<Ejercicio2Vertex> goalHasSolution() {
		return v -> v.remaining().isEmpty();
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<Integer>();

		//Si no hemos recorrido todos los cursos y tenemos todas las tematicas la alternativa es no coger los siguientes.
		if (index < DatosEjercicio2.getnCursos()) {
			if (remaining().isEmpty()) {
				alternativas = List2.of(0);
			} else {
				//Si no hemos cogido todas las tematicas y nos encontramos en el ultimo vertice
				Set<Integer> rest = Set2.difference(remaining(), DatosEjercicio2.getTematicasCurso(index));
				if (index == DatosEjercicio2.getnCursos() - 1) {
					//Si en los centros tenemos el centro del indice o el numero de centros es mayor que el que se puede coger 
					if (centers().contains(DatosEjercicio2.getCentroCurso(index)) || centers.size() < DatosEjercicio2.getmaxCentros()) {
						alternativas = rest.isEmpty() ? List2.of(1) : List2.empty();
					}
					//Si las que nos quedan por coger son iguales que las que tenemos no la cogemos
				} else if (rest.equals(remaining)) {
					alternativas = List2.of(0);
				} else {
					//si no son iguales o la cogemos o no pero no podemos pasarnos de los centros
					if (centers().contains(DatosEjercicio2.getCentroCurso(index)) || centers.size() < DatosEjercicio2.getmaxCentros()) {
						alternativas = List2.of(0, 1);
					} else {
						alternativas = List2.of(0);
					}
				}
			}
		}
		return alternativas;
	}

	public Ejercicio2Vertex neighbor(Integer a) {
		//Copiamos las listas para no modificarlas
		Set<Integer> remaining2 = Set2.copy(remaining);
		Set<Integer> centros2 = Set2.copy(centers);
		//Si hemos cogido la arista restamos las tematicas que hemos cogido y añadimos el centro
		if (a == 1) {
			Set<Integer> diferencia = Set2.difference(remaining2, DatosEjercicio2.getTematicasCurso(index));
			centros2.add(DatosEjercicio2.getCentroCurso(index));
			Set<Integer> centrosdif = Set2.copy(centros2);
			return of(index + 1, diferencia, centrosdif);
		// Si no hemos cogido el centro no tenemos que modificar nada
		} else {
			return of(index + 1, remaining2, centros2);
		}
	}
	@Override
	public Ejercicio2Edge edge(Integer a) {
		return Ejercicio2Edge.of(this, neighbor(a), a);
	}

	public Ejercicio2Edge greedyEdge() {
		//Si lo centros contienen o el numero de centros es mas pequeño que los centros que podemos coger tenemos dos opciones
		Set<Integer> rest = Set2.difference(remaining, Set2.of(DatosEjercicio2.getTematicasCurso(index)));
		if (centers().contains(DatosEjercicio2.getCentroCurso(index)) || centers.size() < DatosEjercicio2.getmaxCentros()) {
			return rest.equals(remaining)?edge(0):edge(1);
		//Si se pasa del numero de centro no lo podemos coger
		}else {return edge(0);
		}
	}

	@Override
	public String toString() {
		String res = "\n" + "indice : " + index.toString() + " remaining :" + remaining.toString();
		return res;
	}

}