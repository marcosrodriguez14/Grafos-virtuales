package ejercicio2;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio2;
import _soluciones.SolucionEjercicio2;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestEjercicio2B {

	public static <V, E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if (path != null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);

		String2.toConsole(String2.linea());
	}

	public static EGraph<Ejercicio2Vertex, Ejercicio2Edge> buildGraph(String fichero) {
		DatosEjercicio2.iniDatos(fichero);

		Ejercicio2Vertex vInicial = Ejercicio2Vertex.initial();

		EGraph<Ejercicio2Vertex, Ejercicio2Edge> graph = EGraph
				.virtual(vInicial, Ejercicio2Vertex.goal(), PathType.Sum, Type.Min)
				.greedyEdge(Ejercicio2Vertex::greedyEdge).heuristic(Ejercicio2Heuristic::heuristic).build();

		return graph;
	}

	public static GraphPath<Ejercicio2Vertex, Ejercicio2Edge> getGreedySolution(String fichero) {

		EGraph<Ejercicio2Vertex, Ejercicio2Edge> graph = buildGraph(fichero);

		GreedyOnGraph<Ejercicio2Vertex, Ejercicio2Edge> gs = GreedyOnGraph.of(graph);
		GraphPath<Ejercicio2Vertex, Ejercicio2Edge> gp = gs.path();
   System.out.println("No tiene porque salir bien porque es la mejor solucion para ese vertice:");
		toConsole("Greedy ", gp, SolucionEjercicio2::of);
		
		return gp;

	}

	public static void getAStarSolution(String fichero,GraphPath<Ejercicio2Vertex, Ejercicio2Edge> greedyPath) {

		EGraph<Ejercicio2Vertex, Ejercicio2Edge> graph = buildGraph(fichero);

		AStar<Ejercicio2Vertex, Ejercicio2Edge, SolucionEjercicio2> gs = AStar.of(graph);
		GraphPath<Ejercicio2Vertex, Ejercicio2Edge> gp = gs.search().get();

		toConsole("A*", gp, SolucionEjercicio2::of);
	
	}

	public static void getPDRSolution(String fichero,GraphPath<Ejercicio2Vertex, Ejercicio2Edge> greedyPath) {

		EGraph<Ejercicio2Vertex, Ejercicio2Edge> graph = buildGraph(fichero);

		DPR<Ejercicio2Vertex, Ejercicio2Edge, SolucionEjercicio2> gs = DPR.of(graph);
		GraphPath<Ejercicio2Vertex, Ejercicio2Edge> gp = gs.search().get();

		toConsole("PDR", gp, SolucionEjercicio2::of);

	}
	
	public static void getBTSolution(String fichero,GraphPath<Ejercicio2Vertex, Ejercicio2Edge> greedyPath) {

		EGraph<Ejercicio2Vertex, Ejercicio2Edge> graph = buildGraph(fichero);

		BT<Ejercicio2Vertex, Ejercicio2Edge, SolucionEjercicio2> gs = BT.of(graph);
		GraphPath<Ejercicio2Vertex, Ejercicio2Edge> gp = gs.search().get();

		toConsole("BT", gp, SolucionEjercicio2::of);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		List.of(1,2,3).forEach(num_test -> {
			String input_arg = String.format("ficherosEjercicios/Ejercicio2DatosEntrada%d.txt", num_test);
			GraphPath<Ejercicio2Vertex, Ejercicio2Edge> greedyPath = getGreedySolution(input_arg);
			getAStarSolution(input_arg,greedyPath);
			getPDRSolution(input_arg,greedyPath);
			getBTSolution(input_arg,greedyPath);
		});

	}

}
