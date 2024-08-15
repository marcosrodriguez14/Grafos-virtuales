package ejercicio3;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.jgrapht.GraphPath;

import _datos.DatosEjercicio3;
import _soluciones.SolucionEjercicio3;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestEjercicio3B {

	public static <V, E> void toConsole(String type, GraphPath<V, E> path, Function<GraphPath<V, E>, ?> fSolution) {
		if (path != null)
			String2.toConsole("Solucion %s: %s", type, fSolution.apply(path));
		else
			String2.toConsole("%s no obtuvo solucion", type);

		String2.toConsole(String2.linea());
	}

	public static EGraph<Ejercicio3Vertex, Ejercicio3Edge> buildGraph(String fichero) {
		DatosEjercicio3.iniDatos(fichero);

		Ejercicio3Vertex vInicial = Ejercicio3Vertex.initial();

		EGraph<Ejercicio3Vertex, Ejercicio3Edge> graph = EGraph
				.virtual(vInicial, Ejercicio3Vertex.goal(), PathType.Sum, Type.Max)
				.heuristic(Ejercicio3Heuristic::heuristic).build();

		return graph;
	}

//	public static GraphPath<Ejercicio4Vertex, Ejercicio4Edge> getGreedySolution(String fichero) {
//
//		EGraph<Ejercicio4Vertex, Ejercicio4Edge> graph = buildGraph(fichero);
//
//		GreedyOnGraph<Ejercicio4Vertex, Ejercicio4Edge> gs = GreedyOnGraph.of(graph);
//		GraphPath<Ejercicio4Vertex, Ejercicio4Edge> gp = gs.path();
//   System.out.println("No tiene porque salir bien porque es la mejor solucion para ese vertice:");
//		toConsole("Greedy ", gp, SolucionEjercicio2::of);
//		
//		return gp;
//
//	}

	public static void getAStarSolution(String fichero) {

		EGraph<Ejercicio3Vertex, Ejercicio3Edge> graph = buildGraph(fichero);

		AStar<Ejercicio3Vertex, Ejercicio3Edge, SolucionEjercicio3> gs = AStar.of(graph);
		GraphPath<Ejercicio3Vertex, Ejercicio3Edge> gp = gs.search().get();

		toConsole("A*", gp, SolucionEjercicio3::of);
	
	}

	public static void getPDRSolution(String fichero) {

		EGraph<Ejercicio3Vertex, Ejercicio3Edge> graph = buildGraph(fichero);

		DPR<Ejercicio3Vertex, Ejercicio3Edge, SolucionEjercicio3> gs = DPR.of(graph);
		GraphPath<Ejercicio3Vertex, Ejercicio3Edge> gp = gs.search().get();

		toConsole("PDR", gp, SolucionEjercicio3::of);
	}
	
	public static void getBTSolution(String fichero) {

		EGraph<Ejercicio3Vertex, Ejercicio3Edge> graph = buildGraph(fichero);

		BT<Ejercicio3Vertex, Ejercicio3Edge, SolucionEjercicio3> gs = BT.of(graph);
		GraphPath<Ejercicio3Vertex, Ejercicio3Edge> gp = gs.search().get();

		toConsole("BT", gp, SolucionEjercicio3::of);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		List.of(1,2,3).forEach(num_test -> {
			String input_arg = String.format("ficherosEjercicios/Ejercicio3DatosEntrada%d.txt", num_test);
		//	GraphPath<Ejercicio4Vertex, Ejercicio4Edge> greedyPath = getGreedySolution(input_arg);
		getAStarSolution(input_arg);
		getPDRSolution(input_arg);
		getBTSolution(input_arg);
		});

	}

}
