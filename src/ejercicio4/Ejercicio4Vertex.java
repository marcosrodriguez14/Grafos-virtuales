package ejercicio4;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import _datos.DatosEjercicio4;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio4Vertex(Integer cliente, Set<Integer> pendientes,List<Integer>visitados,Double kms)
		implements VirtualVertex<Ejercicio4Vertex, Ejercicio4Edge, Integer> {

	public static Ejercicio4Vertex of(Integer cliente, Set<Integer> pendientes,List<Integer>visitados,Double kms) {
		return new Ejercicio4Vertex(cliente,pendientes,visitados,kms);
	}

	public static Ejercicio4Vertex initial() {
		//Vertice inicial formado por el cliente 0 , un conjunto con todos los clientes que hay que visitar y una lista de
		//visitados con 0 pues es el vertice inicial y 0 kms pues aun no hemos recorrido ninguno.
		return of(0, DatosEjercicio4.getSetClientes(),List2.of(0),0.);

	}
	//Un vertice final es aquel en el que hayamos visitado todos los clientes y su vertice final sea 0
	public static Predicate<Ejercicio4Vertex> goal(){
		return v ->  v.pendientes.isEmpty() && v.cliente == 0;
	}
	//Igualmente tiene solucion si se cumplen estas dos cosas
	public static Predicate<Ejercicio4Vertex> goalHasSolution() {
		return v -> v.pendientes.isEmpty() && v.cliente == 0;}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		//Si la lista de pendientes no esta vacia las alternativas son ir a los vertices que tengan aristas con el que estamos
		if(!pendientes.isEmpty()) {
			for(Integer p:pendientes) {
				if(DatosEjercicio4.existeArista(cliente, p)) {
					alternativas.add(p);
				}
			}
			//si esta vacia nuestra unica opcion es ir al vertice 0 si existe una arista
		}else {
			if(DatosEjercicio4.existeArista(cliente, 0)){
				alternativas = List2.of(0);
			}// si no existe arista no tenemos alternativas
			else {
				alternativas = List2.of();
			}
		}
		return alternativas;
	}

	
	@Override
	public Ejercicio4Vertex neighbor(Integer a) {
	  //Copiamos para no modificar las originales
		Set<Integer> pendientes2 = Set2.copy(pendientes);
		List<Integer> visitados2 = List2.copy(visitados);
		
		Double kms2= kms+0.;
		//Si pendientes contiene 0 se lo tenemos que quitar pues ya lo hemos visitado en el vertice 0
		//Tenemos que añadirlo a visitados 
		if(pendientes2.contains(0)) {
			visitados2.add(0);
			pendientes2.remove(0);
		}
		//Eliminamos la alternativa de pendientes porque estamos visitando ese vertice
		//La añaidmos en visitados y sumamos los kms
		pendientes2.remove(a);
		visitados2.add(a);
		kms2+=DatosEjercicio4.getPeso(cliente, a) ;
		return of(a,pendientes2,visitados2,kms2);
	}
	
	@Override
	public Ejercicio4Edge edge(Integer a) {
		return Ejercicio4Edge.of(this, neighbor(a), a);
	}


}
