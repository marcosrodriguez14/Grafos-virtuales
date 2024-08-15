package ejemplos.ejemplo3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosAlumnos;
import us.lsi.graphs.virtual.VirtualVertex;

// Uso el segundo modelo
public record AlumnosVertex(Integer index, List<Integer> remaining) 
     implements VirtualVertex<AlumnosVertex,AlumnosEdge,Integer> {

	public static AlumnosVertex of(Integer i, List<Integer> rest) {
		return new AlumnosVertex(i, rest);
	}
	
	@Override
	public List<Integer> actions() {
		// TODO Alternativas de un vertice 
		return null;
	}

	@Override
	public AlumnosVertex neighbor(Integer a) {
		// TODO Vertice siguiente al actual segun la alternativa a 
		return null;
	}

	@Override
	public AlumnosEdge edge(Integer a) {
		return AlumnosEdge.of(this,this.neighbor(a),a);
	}
	
	// Se explica en practicas.
	public AlumnosEdge greedyEdge() {
		Comparator<Integer> cmp = Comparator.comparing(j -> DatosAlumnos.getAfinidad(index, j));
		
		Integer a = IntStream.range(0, DatosAlumnos.getNumGrupos())
		.filter(j -> DatosAlumnos.getAfinidad(index, j)>0 && remaining.get(j)>0)
		.boxed().max(cmp).orElse(0);
		
		return edge(a);
	}
}
