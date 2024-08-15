package ejemplos.ejemplo1.manual;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import _datos.DatosMulticonjunto;
import us.lsi.common.List2;

public record MulticonjuntoProblem(Integer index, Integer remaining) {

	public static Integer contador = 0;

	public static Integer getContador() {
		return contador;
	}

	// Constructor/factoria
	public static MulticonjuntoProblem of(Integer i, Integer rest) {
		contador += 1;
		return new MulticonjuntoProblem(i, rest);
	}

	// Problema inicial
	public static MulticonjuntoProblem initial() {
		return of(0, DatosMulticonjunto.getSuma());
	}

	// Alternativas
	public List<Integer> actions() {
		// si i == m
		List<Integer> alternativas = List2.empty();
		// i<m
		if (index < DatosMulticonjunto.getNumElementos()) {
			Integer elemento = DatosMulticonjunto.getElemento(index);
			Integer opciones = remaining / elemento;
			// i== m-1
			if (index == DatosMulticonjunto.getNumElementos() - 1) {
				// asignando multiplicidad
				if (remaining % elemento == 0) {
					alternativas = List2.of(remaining / elemento);
				}
				// no cojo ningun elemento
				else {
					alternativas = List2.of(0);
				}
			} else {
				alternativas = List2.rangeList(0, opciones + 1);
				// hacer la busqueda mas rapida
				Collections.reverse(alternativas);
			}
		}

		return alternativas;

	}

	// vecinos
	public MulticonjuntoProblem neighbor(Integer a) {
		return of(index + 1, remaining - a * DatosMulticonjunto.getElemento(index));
	}

	// Heurística
	// Optimista: si todavía falta para alcanzar la suma, el mayor de los que queda
	// puede alcanzarla
	public Double heuristic() {
		Double res = 0.;
		if (remaining > 0) {
			Integer max = IntStream.range(index, DatosMulticonjunto.getNumElementos())
					.map(elemento -> DatosMulticonjunto.getElemento(index)).filter(elemento -> elemento < remaining)
					.max().orElse(0);

			if (max > 0) {
				Integer r = remaining / max;
				res += remaining % max == 0 ? r : r + 1;
			} else {
				res += 100;
			}
		} else if (remaining < 0) {
			res += 100;
		}
		return res;
	}

}
