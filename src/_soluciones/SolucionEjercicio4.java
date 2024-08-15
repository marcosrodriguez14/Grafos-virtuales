package _soluciones;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio4;
import _utils.Cliente;
import ejercicio4.Ejercicio4Edge;
import ejercicio4.Ejercicio4Vertex;

public class SolucionEjercicio4 implements Comparable<SolucionEjercicio4>{

	public static SolucionEjercicio4 of_Range(List<Integer> value) {
		return new SolucionEjercicio4(value);
	}
	
	// Ahora en la PI5
			public static SolucionEjercicio4 of(GraphPath<Ejercicio4Vertex, Ejercicio4Edge> path) {
				List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
				SolucionEjercicio4 res = of_Range(ls);
				res.path = ls;
				return res;
			}

	private Double kms;
	private Double beneficio;
	private List<Cliente> clientes;
	
	// Ahora en la PI5
		private List<Integer> path;

	private SolucionEjercicio4() {
		kms = 0.;
		beneficio = 0.;
		clientes = new ArrayList<>();
		//Añadimos el vertice 0 
		clientes.add(DatosEjercicio4.getCliente(0));
	}

	private SolucionEjercicio4(List<Integer> value) {
		kms = 0.;
		beneficio = 0.;
		clientes = new ArrayList<>();
		//Añadimos el vertice 0
		clientes.add(DatosEjercicio4.getCliente(0));
		for (int i = 0; i < value.size(); i++) {
			clientes.add(DatosEjercicio4.getCliente(value.get(i)));
			//si el vertice es 0 y existe una arista del 0 al vertice sumamos la distancia y la restamos al beneficio
			if (i == 0) {
				if (DatosEjercicio4.existeArista(0, value.get(i))) {
					kms += DatosEjercicio4.getPeso(0, value.get(i));
					beneficio += DatosEjercicio4.getBeneficio(value.get(i)) - kms;
				}
			} else {
				if (DatosEjercicio4.existeArista(value.get(i - 1), value.get(i))) {
					kms += DatosEjercicio4.getPeso(value.get(i - 1), value.get(i));
					beneficio += DatosEjercicio4.getBeneficio(value.get(i)) - kms;
				}
			}
		}
	}

	public static SolucionEjercicio4 empty() {
		return new SolucionEjercicio4();
	}

//	public String toString() {
//		List<Integer> vertices = clientes.stream().map(c -> c.id()).toList();
//		return "Camino desde 0 hasta 0:\n" + vertices + "\nKms: " + kms + "\nBeneficio: " + beneficio;
//	}
	
	// Ahora en la PI5
		@Override
		public String toString() {
			
			List<Integer> vertices = clientes.stream().map(c -> c.id()).toList();
			System.out.println("Camino de vertices elegidos: "+ vertices.toString().replace("[", "{").replace("]", "}") );
			String res = String.format("%s Beneficio total: ", beneficio);
			return path==null? res:  String.format("%s\nPath de la solucion: %s", beneficio,vertices);
		}

		public int compareTo(SolucionEjercicio4 s) {
			return beneficio.compareTo(s.beneficio);
		}
}
