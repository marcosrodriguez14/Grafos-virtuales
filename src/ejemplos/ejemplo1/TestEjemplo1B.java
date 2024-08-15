package ejemplos.ejemplo1;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.jgrapht.GraphPath;

import _datos.DatosMulticonjunto;
import _soluciones.SolucionMulticonjunto;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestEjemplo1B {

	public static <V, E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if (path != null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);

		String2.toConsole(String2.linea());
	}

	public static EGraph<MulticonjuntoVertex, MulticonjuntoEdge> buildGraph(String fichero) {
		DatosMulticonjunto.iniDatos(fichero);

		MulticonjuntoVertex vInicial = MulticonjuntoVertex.initial();

		EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = EGraph.virtual(vInicial, MulticonjuntoVertex.goal(),
				PathType.Sum, us.lsi.graphs.virtual.EGraph.Type.Min).greedyEdge(MulticonjuntoVertex::greedyEdge)
				.heuristic(MulticonjuntoHeuristic::heuristic).build();
		return graph;
		
	}

	//Entrada el voraz
	public static GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> getGreedySolution(String fichero) {
		EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = buildGraph(fichero);
		
		GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> gs = GreedyOnGraph.of(graph);
		GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = gs.path();
		
		toConsole("Greedy", gp, SolucionMulticonjunto::of);
		return gp;
	}

	public static void getAStarSolution(String fichero, GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> greedyPath) {
		EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = buildGraph(fichero);
	
		AStar<MulticonjuntoVertex, MulticonjuntoEdge,SolucionMulticonjunto > gs = AStar.of(graph);
		
		GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = gs.search().get();
		
		toConsole("A*",gp, SolucionMulticonjunto::of);
	}
	
	public static void getPDRSolution(String fichero, GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> greedyPath) {
		EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = buildGraph(fichero);
	
		DPR<MulticonjuntoVertex, MulticonjuntoEdge,SolucionMulticonjunto > gs = DPR.of(graph, null, greedyPath.getWeight(),greedyPath, true );
		
		GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = gs.search().get();
		
		toConsole("PDR*",gp, SolucionMulticonjunto::of);
	}
	public static void getBTSolution(String fichero, GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> greedyPath) {
		EGraph<MulticonjuntoVertex, MulticonjuntoEdge> graph = buildGraph(fichero);
	
		BT<MulticonjuntoVertex, MulticonjuntoEdge,SolucionMulticonjunto > gs = BT.of(graph, null,greedyPath.getWeight(),greedyPath, true );
		
		GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> gp = gs.search().get();
		
		toConsole("BT*",gp, SolucionMulticonjunto::of);
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		List.of(1, 2).forEach(num_test -> {
			String input_arg = String.format("ficheros/Ejemplo1DatosEntrada%d.txt", num_test);
			GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> greedyPath = getGreedySolution(input_arg);
			getAStarSolution(input_arg, greedyPath);
			getPDRSolution(input_arg, greedyPath);
			getBTSolution(input_arg, greedyPath);
		});

	}

}