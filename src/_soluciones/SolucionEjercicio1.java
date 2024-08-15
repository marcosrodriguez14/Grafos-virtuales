package _soluciones;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio1;
import ejercicio1.Ejercicio1Edge;
import ejercicio1.Ejercicio1Vertex;
import us.lsi.common.Map2;

public class SolucionEjercicio1 {
	
	
	public static SolucionEjercicio1 of_Range(List<Integer> value) {
		return new SolucionEjercicio1(value);
	}
	
	//Ahora en la PI5
	public static SolucionEjercicio1 of(GraphPath<Ejercicio1Vertex, Ejercicio1Edge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionEjercicio1 res = of_Range(ls);
		res.path = ls;
		return res;
	}
	
	
	

	private Double beneficio = 0.;
	private Map<String, Integer> soluciones = new TreeMap<>();
	
	
	private SolucionEjercicio1() {
		beneficio = 0.;
		soluciones = Map2.empty();
	
	}
	
	// Ahora en la PI5
	private List<Integer> path;
	
	private SolucionEjercicio1(List<Integer>ls) {
		beneficio =0.;
//		soluciones = new TreeMap<>();
		for (int i = 0; i<ls.size();i++) {
			if (ls.get(i)>0) {
				Integer kg= ls.get(i);
				Double b = (double) (DatosEjercicio1.getBeneficio(i)*kg);
				soluciones.put(String.format("P0%d ",i+1 ), kg);
				beneficio += b;
				
		}
		}
	}
	public static SolucionEjercicio1 empty() {
		return new SolucionEjercicio1();
	}
//	
//	public String toString() {
//		System.out.println("Variedades de cafe seleccionadads: ");
//		soluciones.entrySet().stream().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue()));
//		
//		return String.format("Beneficio: "+"%s", beneficio);
//	}
	
	
	// Ahora en la PI5
		@Override
		public String toString() {
			System.out.println("KG DE VARIEDADES SELECCIONADOS: ");
			soluciones.entrySet().stream().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue()));
			//System.out.println("Cursos elegidos: "+ soluciones.toString().replace("[", "{").replace("]", "}") );
			String res = String.format("%s Beneficio total: ", beneficio);
			return path==null? res:  String.format("%s\nPath de la solucion: %s", beneficio,path);
		}

		public int compareTo(SolucionEjercicio1 s) {
			return beneficio.compareTo(s.beneficio);
		}

}
