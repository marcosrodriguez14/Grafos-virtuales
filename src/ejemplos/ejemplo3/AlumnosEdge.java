package ejemplos.ejemplo3;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record AlumnosEdge(AlumnosVertex source, AlumnosVertex target, Integer action, Double weight) 
               implements SimpleEdgeAction<AlumnosVertex,Integer> {

	public static AlumnosEdge of(AlumnosVertex v1, AlumnosVertex v2, Integer a) {	
		// TODO La arista debe tener peso 
		return null;
	}

}


