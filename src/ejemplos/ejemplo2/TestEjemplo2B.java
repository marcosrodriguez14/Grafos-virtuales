package ejemplos.ejemplo2;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.jgrapht.GraphPath;

import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestEjemplo2B {

	public static <V, E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if (path != null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);

		String2.toConsole(String2.linea());
	}

	public static EGraph<Ejemplo2Vertex, Ejemplo2Edge> buildGraph(String fichero) {
		DatosSubconjuntos.iniDatos(fichero);

		Ejemplo2Vertex vInicial = Ejemplo2Vertex.initial();

		EGraph<Ejemplo2Vertex, Ejemplo2Edge> graph = EGraph
				.virtual(vInicial, Ejemplo2Vertex.goal(), PathType.Sum, Type.Min)
				.greedyEdge(Ejemplo2Vertex::greedyEdge).heuristic(SubconjuntosHeuristic::heuristic).build();

		return graph;
	}

	public static GraphPath<Ejemplo2Vertex, Ejemplo2Edge> getGreedySolution(String fichero) {

		EGraph<Ejemplo2Vertex, Ejemplo2Edge> graph = buildGraph(fichero);

		GreedyOnGraph<Ejemplo2Vertex, Ejemplo2Edge> gs = GreedyOnGraph.of(graph);
		GraphPath<Ejemplo2Vertex, Ejemplo2Edge> gp = gs.path();

		toConsole("Greedy", gp, SolucionSubconjuntos::of);

		return gp;

	}

	public static void getAStarSolution(String fichero, GraphPath<Ejemplo2Vertex, Ejemplo2Edge> greedyPath) {

		EGraph<Ejemplo2Vertex, Ejemplo2Edge> graph = buildGraph(fichero);

		AStar<Ejemplo2Vertex, Ejemplo2Edge, SolucionSubconjuntos> gs = AStar.of(graph, null,
				greedyPath.getWeight(), greedyPath);
		GraphPath<Ejemplo2Vertex, Ejemplo2Edge> gp = gs.search().get();

		toConsole("A*", gp, SolucionSubconjuntos::of);
	}

	public static void getPDRSolution(String fichero, GraphPath<Ejemplo2Vertex, Ejemplo2Edge> greedyPath) {

		EGraph<Ejemplo2Vertex, Ejemplo2Edge> graph = buildGraph(fichero);

		DPR<Ejemplo2Vertex, Ejemplo2Edge, SolucionSubconjuntos> gs = DPR.of(graph, null, greedyPath.getWeight(),
				greedyPath, true);
		GraphPath<Ejemplo2Vertex, Ejemplo2Edge> gp = gs.search().get();

		toConsole("PDR", gp, SolucionSubconjuntos::of);
	}
	
	public static void getBTSolution(String fichero, GraphPath<Ejemplo2Vertex, Ejemplo2Edge> greedyPath) {

		EGraph<Ejemplo2Vertex, Ejemplo2Edge> graph = buildGraph(fichero);

		BT<Ejemplo2Vertex, Ejemplo2Edge, SolucionSubconjuntos> gs = BT.of(graph, null, greedyPath.getWeight(),
				greedyPath, true);
		GraphPath<Ejemplo2Vertex, Ejemplo2Edge> gp = gs.search().get();

		toConsole("BT", gp, SolucionSubconjuntos::of);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		List.of(1, 2).forEach(num_test -> {
			String input_arg = String.format("ficheros/Ejemplo2DatosEntrada%d.txt", num_test);
			GraphPath<Ejemplo2Vertex, Ejemplo2Edge> greedyPath = getGreedySolution(input_arg);
			getAStarSolution(input_arg, greedyPath);
			getPDRSolution(input_arg, greedyPath);
			getBTSolution(input_arg, greedyPath);
		});

	}

}
