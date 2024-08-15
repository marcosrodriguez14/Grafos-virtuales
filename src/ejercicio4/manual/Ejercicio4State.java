package ejercicio4.manual;

import java.util.List;

import _datos.DatosEjercicio4;
import _soluciones.SolucionEjercicio4;
import us.lsi.common.List2;

public class Ejercicio4State {

	Ejercicio4Problem actual;
	Double acumulado;
	List<Integer> acciones;
	List<Ejercicio4Problem> anteriores;

	// Constructor
	private Ejercicio4State(Ejercicio4Problem p, Double a, List<Integer> ls1, List<Ejercicio4Problem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	// Factoria
	public static Ejercicio4State of(Ejercicio4Problem p, Double a, List<Integer> ls1,
			List<Ejercicio4Problem> ls2) {
		return new Ejercicio4State(p, a, ls1, ls2);
	}

	// Estado inicial
	public static Ejercicio4State initial() {
		Ejercicio4Problem pi = Ejercicio4Problem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}

	public void forward(Integer a) {
		acumulado += DatosEjercicio4.getBeneficio(a)-DatosEjercicio4.getPeso(actual.cliente(),a);
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
	}

	public void back() {
		int last = acciones.size() - 1;
		var prob_anterior = anteriores.get(last);

		acumulado -= DatosEjercicio4.getPeso(prob_anterior.cliente(),acciones.get(last));
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_anterior;
	}

	public List<Integer> alternativas() {
		return actual.actions();
	}

	public Double cota(Integer a) {
		Double peso = a > 0 ? DatosEjercicio4.getPeso(actual.cliente(),a) : 0.;
		return acumulado + peso + actual.neighbor(a).heuristic();

	}

	public Boolean esSolucion() {
		return actual.pendientes().isEmpty() && actual.cliente() == 0;
	}

	public Boolean esTerminal() {
		return actual.pendientes().isEmpty() && actual.cliente() == 0 ;
	}

	public SolucionEjercicio4 getSolucion() {
		return SolucionEjercicio4.of_Range(acciones);
	}

	@Override
	public String toString() {
		return "[Actual = " + actual.toString() + ", Acumulado = " + acumulado.toString() + ", Acciones = "
				+ acciones.toString() + ", Anteriores = " + anteriores.toString() + "]";
	}

}
