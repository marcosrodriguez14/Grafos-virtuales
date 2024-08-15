package ejemplos.ejemplo2.manual;

import java.util.List;

import _datos.DatosSubconjuntos;
import _soluciones.SolucionSubconjuntos;
import us.lsi.common.List2;

public class SubconjuntosState {

	SubconjuntosProblem actual;
	Double acumulado;
	List<Integer> acciones;
	List<SubconjuntosProblem> anteriores;

	// Constructor
	private SubconjuntosState(SubconjuntosProblem p, Double a, List<Integer> ls1, List<SubconjuntosProblem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	// Factoria
	public static SubconjuntosState of(SubconjuntosProblem p, Double a, List<Integer> ls1,
			List<SubconjuntosProblem> ls2) {
		return new SubconjuntosState(p, a, ls1, ls2);
	}

	// Estado inicial
	public static SubconjuntosState initial() {
		SubconjuntosProblem pi = SubconjuntosProblem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}

	public void forward(Integer a) {
		acumulado += a * DatosSubconjuntos.getPeso(actual.index());
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		int last = acciones.size() - 1;
		var prob_anterior = anteriores.get(last);

		acumulado -= acciones.get(last) * DatosSubconjuntos.getPeso(prob_anterior.index());
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_anterior;
	}

	public List<Integer> alternativas() {
		return actual.actions();
	}

	public Double cota(Integer a) {
		Double peso = a > 0 ? DatosSubconjuntos.getPeso(actual.index()) : 0.;
		return acumulado + peso + actual.neighbor(a).heuristic();

	}

	public Boolean esSolucion() {
		return actual.remaining().isEmpty();
	}

	public Boolean esTerminal() {
		return actual.index() == DatosSubconjuntos.getNumSubconjuntos();
	}

	public SolucionSubconjuntos getSolucion() {
		return SolucionSubconjuntos.of(acciones);
	}

	@Override
	public String toString() {
		return "[Actual = " + actual.toString() + ", Acumulado = " + acumulado.toString() + ", Acciones = "
				+ acciones.toString() + ", Anteriores = " + anteriores.toString() + "]";
	}

}
