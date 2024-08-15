package ejercicio1;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import org.jgrapht.GraphPath;
import _datos.DatosEjercicio1;
import _soluciones.SolucionEjercicio1;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.path.EGraphPath.PathType;

public class TestEjercicio1B {

	public static <V, E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if (path != null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);

		String2.toConsole(String2.linea());
	}

	public static EGraph<Ejercicio1Vertex, Ejercicio1Edge> buildGraph(String fichero) {
		DatosEjercicio1.iniDatos(fichero);

		Ejercicio1Vertex vInicial = Ejercicio1Vertex.initial();

		EGraph<Ejercicio1Vertex, Ejercicio1Edge> graph = EGraph
				.virtual(vInicial, Ejercicio1Vertex.goal(), PathType.Sum, us.lsi.graphs.virtual.EGraph.Type.Max)
				.heuristic(Ejercicio1Heuristic::heuristic).build();

		return graph;
	}

	public static GraphPath<Ejercicio1Vertex, Ejercicio1Edge> getGreedySolution(String fichero) {

		EGraph<Ejercicio1Vertex, Ejercicio1Edge> graph = buildGraph(fichero);

		GreedyOnGraph<Ejercicio1Vertex, Ejercicio1Edge> gs = GreedyOnGraph.of(graph);
		GraphPath<Ejercicio1Vertex, Ejercicio1Edge> gp = gs.path();

		toConsole("Greedy", gp, SolucionEjercicio1::of);

		return gp;

	}

	public static void getAStarSolution(String fichero) {

		EGraph<Ejercicio1Vertex, Ejercicio1Edge> graph = buildGraph(fichero);

		AStar<Ejercicio1Vertex, Ejercicio1Edge, SolucionEjercicio1> gs = AStar.of(graph);
		GraphPath<Ejercicio1Vertex, Ejercicio1Edge> gp = gs.search().get();

		toConsole("A*", gp, SolucionEjercicio1::of);
	}

	public static void getPDRSolution(String fichero) {

		EGraph<Ejercicio1Vertex, Ejercicio1Edge> graph = buildGraph(fichero);

		DPR<Ejercicio1Vertex, Ejercicio1Edge, SolucionEjercicio1> gs = DPR.of(graph);
		GraphPath<Ejercicio1Vertex, Ejercicio1Edge> gp = gs.search().get();

		toConsole("PDR", gp, SolucionEjercicio1::of);
	}

	public static void getBTSolution(String fichero) {

		EGraph<Ejercicio1Vertex, Ejercicio1Edge> graph = buildGraph(fichero);

		BT<Ejercicio1Vertex, Ejercicio1Edge, SolucionEjercicio1> gs = BT.of(graph);
		GraphPath<Ejercicio1Vertex, Ejercicio1Edge> gp = gs.search().get();

		toConsole("BT", gp, SolucionEjercicio1::of);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		List.of(1, 2, 3).forEach(num_test -> {
			String input_arg = String.format("ficherosEjercicios/Ejercicio1DatosEntrada%d.txt", num_test);
			GraphPath<Ejercicio1Vertex, Ejercicio1Edge> greedyPath = getGreedySolution(input_arg);
			getAStarSolution(input_arg);
			getPDRSolution(input_arg);
			getBTSolution(input_arg);
		});
	}

}
