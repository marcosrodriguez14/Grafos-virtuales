package _soluciones;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio2;
import ejercicio2.Ejercicio2Edge;
import ejercicio2.Ejercicio2Vertex;
import us.lsi.common.Set2;

public class SolucionEjercicio2 {
	
	
	public static SolucionEjercicio2 of_Range(List<Integer> value) {
		return new SolucionEjercicio2(value);
	}
	
	// Ahora en la PI5
		public static SolucionEjercicio2 of(GraphPath<Ejercicio2Vertex, Ejercicio2Edge> path) {
			List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
			SolucionEjercicio2 res = of_Range(ls);
			res.path = ls;
			return res;
		}

	private Double coste;
	private Set<String> soluciones;
	
	
	// Ahora en la PI5
	private List<Integer> path;
	
	private SolucionEjercicio2() {
		coste = 0.;
		soluciones = Set2.empty();
	}
	private SolucionEjercicio2(List<Integer>ls) {
		coste =0.;
		soluciones = new TreeSet<>();
		for (int i = 0; i<ls.size();i++) {
			if (ls.get(i)>0) {
				Double c = DatosEjercicio2.getPrecio(i);
				soluciones.add(String.format("S%d", i));
				coste += c;
			}
		}
		
	}
	public static SolucionEjercicio2 empty() {
		return new SolucionEjercicio2();
	}

	// Ahora en la PI5
	@Override
	public String toString() {
		System.out.println("Cursos seleccionados: ");
		//soluciones.entrySet().stream().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue()));
		System.out.println("Cursos elegidos: "+ soluciones.toString().replace("[", "{").replace("]", "}") );
		String res = String.format("%s Coste total: ", coste);
		return path==null? res:  String.format("%s\nPath de la solucion: %s", coste,path);
	}

	public int compareTo(SolucionEjercicio2 s) {
		return coste.compareTo(s.coste);
	}
	
	
	
	
	
	
	
	
	
	
}
