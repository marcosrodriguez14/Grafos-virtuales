 package ejercicio3;
import _datos.DatosEjercicio3;
import us.lsi.graphs.virtual.SimpleEdgeAction;

public record Ejercicio3Edge(Ejercicio3Vertex source, Ejercicio3Vertex target,Integer action,
		Double weight)implements SimpleEdgeAction<Ejercicio3Vertex,Integer> {
	
	public static Ejercicio3Edge of(Ejercicio3Vertex s, Ejercicio3Vertex t, Integer a) {

		//Si la lista de distribuciones no es igual a la del siguiente vertice
		// y la del siguiente todos sus elementos son 0 cogemos la calidad del trabajo que se ha hecho.
		if( !s.distribucion().get(s.trabajo()).equals(t.distribucion().get(s.trabajo()))
				&& t.distribucion().stream().anyMatch(x -> x.stream().allMatch(e->e == 0))) {
			Double calidad = DatosEjercicio3.getCalidadTrabajo(s.trabajo())+0.;
			return new Ejercicio3Edge(s, t, a,calidad);	
		}
		// si no significa que ese trabajo o ya se ha completado o no se ha hecho entonce su arista
		// tendrá peso 0.
		else {
			return new Ejercicio3Edge(s, t, a,0.);	
		}
		
		
	}
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}