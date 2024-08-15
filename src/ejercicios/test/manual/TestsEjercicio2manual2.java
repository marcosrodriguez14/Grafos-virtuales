package ejercicios.test.manual;

import java.util.List;

import _datos.DatosEjercicio2;
import _utils.TestsPI5;
import ejercicio2.manual.Ejercicio2PDR;
import us.lsi.common.String2;

public class TestsEjercicio2manual2 {

	public static void main(String[] args) {
		List.of(1,2,3).forEach(num_test -> {
			DatosEjercicio2.iniDatos("ficherosEjercicios/Ejercicio2DatosEntrada"+num_test+".txt");
			String2.toConsole("Solucion obtenida: %s\n", Ejercicio2PDR.search());
			TestsPI5.line("*");
		});
	}
	
}
