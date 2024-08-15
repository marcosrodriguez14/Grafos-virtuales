package ejemplos.ejemplo1;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import _datos.DatosMulticonjunto;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record MulticonjuntoVertex(Integer index, Integer remaining)
		implements VirtualVertex<MulticonjuntoVertex, MulticonjuntoEdge, Integer> {

	public static MulticonjuntoVertex of(Integer i, Integer rest) {
		return new MulticonjuntoVertex(i, rest);
	}

	//  Consulte las clases GraphsPI5 y TestPI5

	public static MulticonjuntoVertex initial() {
		return of(0, DatosMulticonjunto.getSuma());

	}

	public static Predicate<MulticonjuntoVertex> goal() {
		return v -> v.index() == DatosMulticonjunto.getNumElementos();
	}

	public static Predicate<MulticonjuntoVertex> goalHasSolution() {
		return v -> v.remaining == 0;
	}

	@Override
	public List<Integer> actions() {
		// Alternativas de un vertice

		List<Integer> alternativas = List2.empty();

		if (index < DatosMulticonjunto.getNumElementos()) {
			Integer elemento = DatosMulticonjunto.getElemento(index);
			Integer opciones = remaining / elemento;
			
			if (index == DatosMulticonjunto.getNumElementos() - 1) {
				if (remaining % elemento == 0) {
					alternativas = List2.of(remaining / elemento);
				} else {
					alternativas = List2.of(0);
				}
			} else {
				// Range List es cerrado por eso es opciones + 1
				alternativas = List2.rangeList(0, opciones + 1);
			}
		}
		return alternativas;
	}

	@Override
	public MulticonjuntoVertex neighbor(Integer a) {
		//  Vertice siguiente al actual segun la alternativa a
		return of(index + 1, remaining - a * DatosMulticonjunto.getElemento(index));
	}

	@Override
	public MulticonjuntoEdge edge(Integer a) {
		return MulticonjuntoEdge.of(this, neighbor(a), a);
	}

	// Se explica en practicas.
	public MulticonjuntoEdge greedyEdge() {
		return existeMayorMejor() ? edge(0) : edge(remaining / DatosMulticonjunto.getElemento(index));
	}

	private Boolean existeMayorMejor() {
		Integer max = IntStream.range(index + 1, DatosMulticonjunto.getNumElementos())
				.map(i -> DatosMulticonjunto.getElemento(i)).filter(e -> remaining % e == 0).max().orElse(0);
		return max > DatosMulticonjunto.getElemento(index);
	}

	@Override
	public String toString() {
		return String.format("%d; %d", index, remaining);
	}

}
