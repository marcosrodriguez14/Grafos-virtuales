package ejercicio3.manual;

import java.util.List;

import _datos.DatosEjercicio3;
import _soluciones.SolucionEjercicio3;
import us.lsi.common.List2;

public class Ejercicio3State {

	Ejercicio3Problem actual;
	Double acumulado;
	List<Integer> acciones;
	List<Ejercicio3Problem> anteriores;

	// Constructor
	private Ejercicio3State(Ejercicio3Problem p, Double a, List<Integer> ls1, List<Ejercicio3Problem> ls2) {
		actual = p;
		acumulado = a;
		acciones = ls1;
		anteriores = ls2;
	}

	// Factoria
	public static Ejercicio3State of(Ejercicio3Problem p, Double a, List<Integer> ls1,
			List<Ejercicio3Problem> ls2) {
		return new Ejercicio3State(p, a, ls1, ls2);
	}

	// Estado inicial
	public static Ejercicio3State initial() {
		Ejercicio3Problem pi = Ejercicio3Problem.initial();
		return of(pi, 0., List2.empty(), List2.empty());
	}

	public void forward(Integer a) {
	
		acumulado +=  !actual.distribucion().get(actual.trabajo()).equals(actual.neighbor(a).distribucion().get(actual.neighbor(a).trabajo()))
				&& actual.neighbor(a).distribucion().stream().anyMatch(x -> x.stream().allMatch(e->e == 0))?DatosEjercicio3.getCalidadTrabajo(actual.trabajo())+0.:0.;
		acciones.add(a);
		anteriores.add(actual);
		actual = actual.neighbor(a);
		System.out.println(actual);
	}

	public void back() {
		int last = acciones.size() - 1;
		var prob_anterior = anteriores.get(last);

		acumulado -= !prob_anterior.distribucion().get(prob_anterior.trabajo()).equals(prob_anterior.neighbor(last).distribucion().get(prob_anterior.neighbor(last).trabajo()))
				&& actual.neighbor(last).distribucion().stream().anyMatch(x -> x.stream().allMatch(e->e == 0))?DatosEjercicio3.getCalidadTrabajo(prob_anterior.trabajo())+0.:0.;
		acciones.remove(last);
		anteriores.remove(last);
		actual = prob_anterior;
	}

	public List<Integer> alternativas() {
		return actual.actions();
	}

	public Double cota(Integer a) {
		Double peso = a > 0 ? DatosEjercicio3.getCalidadTrabajo(actual.trabajo()):0.;
		return acumulado + peso + actual.neighbor(a).heuristic();

	}

	public Boolean esSolucion() {
		return actual.index() == DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos();
	}

	public Boolean esTerminal() {
		return actual.distribucion().stream()
	            .flatMap(List::stream)
	            .allMatch(e -> e >= 0) && actual.dias().stream()
	            .allMatch(e -> e >= 0); 
	}

	public SolucionEjercicio3 getSolucion() {
		return SolucionEjercicio3.of_Range(acciones);
	}

	@Override
	public String toString() {
		return "[Actual = " + actual.toString() + ", Acumulado = " + acumulado.toString() + ", Acciones = "
				+ acciones.toString() + ", Anteriores = " + anteriores.toString() + "]";
	}

}
