package ejercicio4;
import _datos.DatosEjercicio4;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejercicio4Edge(Ejercicio4Vertex source, Ejercicio4Vertex target,Integer action,
		Double weight)implements SimpleEdgeAction<Ejercicio4Vertex,Integer> {
	
	public static Ejercicio4Edge of(Ejercicio4Vertex s, Ejercicio4Vertex t, Integer a) {
		// El peso de la arista es restarle al beneficio la penalizacion segun al vertice que vamos
		return new Ejercicio4Edge(s, t, a, Double.valueOf( DatosEjercicio4.getBeneficio(t.cliente())- DatosEjercicio4.getPeso(s.cliente(),t.cliente())-s.kms()));
	}
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
