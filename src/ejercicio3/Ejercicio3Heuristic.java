package ejercicio3;

import java.util.function.Predicate;

import _datos.DatosEjercicio3;

public class Ejercicio3Heuristic {

	public static Double heuristic(Ejercicio3Vertex v1, Predicate<Ejercicio3Vertex> goal, Ejercicio3Vertex v2) {
		// calidades totales
		Double calidades = 0.; 
		// mientras index < n * m
		if (v1.index() <= DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos()) {
			//Recorremos la lista de distribuciones del vertice en el que estamos
				for (int i = 0; i < v1.distribucion().size(); i++) {
					//Si alguno de sus dias es negativo y la lista dias alguno es negativo entonces es un mal camino
					if (v1.distribucion().get(i).stream().anyMatch(e -> e < 0)&&v1.dias().stream().anyMatch(d -> d < 0)) {
						calidades = calidades - 10000;
						//Si son positivos es un mejor camino asi que no le restamos
					} else {
						//Si todos son 0 en la distribucion significa que se cumple el trabajo asi que añadimos su calidad
						if (v1.distribucion().get(i).stream().allMatch(e -> e.equals(0))) {
							calidades = calidades + DatosEjercicio3.getCalidadTrabajo(i);

						}
					}
				}
			} 
		return calidades;
	}
}