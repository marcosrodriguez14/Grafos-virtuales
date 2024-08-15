package ejercicio4.manual;

import java.util.Set;

import _soluciones.SolucionEjercicio4;

import us.lsi.common.Set2;

public class Ejercicio4BT {

	private static Double mejorValor;
	private static Ejercicio4State estado;
	private static Set<SolucionEjercicio4> soluciones;
	
	public static void search() {
		soluciones = Set2.newTreeSet();
		mejorValor = Double.MAX_VALUE; 
		estado = Ejercicio4State.initial();
		bt_search();
	}

	private static void bt_search() {
		if (estado.esSolucion()) {
			Double valorObtenido = estado.acumulado;
			if (valorObtenido < mejorValor) {  
				mejorValor = valorObtenido;
				soluciones.add(estado.getSolucion());
			}
		} else if(!estado.esTerminal()){
			for (Integer a: estado.alternativas()) {
				if (estado.cota(a) <= mejorValor) {  
					estado.forward(a);
					bt_search();
					estado.back();
				}
			}
		}
	}

	public static Set<SolucionEjercicio4> getSoluciones() {
		return soluciones;
	}
}
