package _soluciones;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio3;
import _datos.DatosEjercicio3.Investigador;
import ejercicio3.Ejercicio3Edge;
import ejercicio3.Ejercicio3Vertex;
import us.lsi.common.List2;

public class SolucionEjercicio3 implements Comparable<SolucionEjercicio3> {
	private static List<List<Integer>> lastVert= List2.empty();
	public static SolucionEjercicio3 of_Range(List<Integer> value) {
		return new SolucionEjercicio3(value);
	}

	// Ahora en la PI5
				public static SolucionEjercicio3 of(GraphPath<Ejercicio3Vertex, Ejercicio3Edge> path) {
					List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
					List<List<Integer>>lastvert = path.getEndVertex().distribucion();
					lastVert = lastvert;
					SolucionEjercicio3 res = of_Range(ls);
					res.path = ls;
					return res;
				}

	private Integer calidad= 0;
	private List<Investigador> investigadores=DatosEjercicio3.getInvestigadores();
	private List<List<Integer>> dias =new ArrayList<>();
	private SortedMap<String, List<Integer>> solucion= new TreeMap<>();
	
	// Ahora en la PI5
	private List<Integer> path;
	private SolucionEjercicio3() {
	}
	
	private SolucionEjercicio3(List<Integer> ls) {
		Integer nIvestigadores = DatosEjercicio3.getnInvestigadores();
		Integer nTrabajos = DatosEjercicio3.getmTrabajos();
		Integer nEspecialidades = DatosEjercicio3.geteEspecialidades();
		
		dias = new ArrayList<>();
		investigadores =DatosEjercicio3.getInvestigadores();
		solucion = new TreeMap<>();
		calidad = 0;
	
		//creamos una lista para cada invstigador
		for (int i = 0; i < nIvestigadores; i++) {
			dias.add(new ArrayList<>());
		}
		
		for (int j = 0; j < nTrabajos; j++) {
			Integer IndiceIni = nIvestigadores * j;
			Integer IndiceFinal = IndiceIni + nIvestigadores;
			List<Integer> trabajos = ls.subList(IndiceIni, IndiceFinal);
			
			// Añadimos a la lista del investigador los dias
			for (int i = 0; i < nIvestigadores; i++) {
				dias.get(i).add(trabajos.get(i));
				solucion.put(investigadores.get(i).nombre(), dias.get(i));
			}
			
			Boolean cumpleDias = true;
			for (int k = 0; k < nEspecialidades; k++) {
				Integer suma = 0;
				for (int i = 0; i < nIvestigadores; i++) {
					suma += trabajos.get(i) * DatosEjercicio3.tieneEspecialidad(i, k);
				}
				if (suma < DatosEjercicio3.diasNecesarios(j, k)) {
					cumpleDias = false;
					k = nEspecialidades;
				}
			}
			
			// Si se realiza el trabajo, se suma su calidad
			if (cumpleDias) {
				calidad += DatosEjercicio3.getCalidadTrabajo(j);
			}
			calidad=0;
			for(int i = 0; i< lastVert.size();i++) {
				if(lastVert.get(i).stream().allMatch(e -> e ==0)) {
					calidad = calidad + DatosEjercicio3.getCalidadTrabajo(i);
				}
				
			}
			
		}

	}
	public static SolucionEjercicio3 empty() {
		return new SolucionEjercicio3();
	}

	// Ahora en la PI5
			@Override
			public String toString() {
				System.out.println("Reparto obtenido (dias trabajados por cada investigador en cada trabajo):");
				solucion.entrySet().stream().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue()));
				String res = String.format("Calidad total de los trabajos: %s ", calidad);
				return res;		}

			public int compareTo(SolucionEjercicio3 s) {
				return calidad.compareTo(s.calidad);
			}
}
