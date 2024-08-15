package ejercicios.test.manual;

import java.util.List;

import _datos.DatosEjercicio3;
import _utils.TestsPI5;
import ejercicio3.manual.Ejercicio3BT;
import us.lsi.common.String2;
public class TestEjercicio3manual2 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			DatosEjercicio3.iniDatos("ficherosEjercicios/Ejercicio3DatosEntrada"+num_test+".txt");
			Ejercicio3BT.search();
			Ejercicio3BT.getSoluciones().forEach(s -> String2.toConsole("Solucion obtenida: %s\n", s));
			System.out.println(Ejercicio3BT.getSoluciones());
			TestsPI5.line("*");
		});
	}
	
}
