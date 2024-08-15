package ejercicios.test.manual;

import java.util.List;
import _datos.DatosEjercicio1;
import _utils.TestsPI5;
import ejercicio1.manual.Ejercicio1PDR;
import us.lsi.common.String2;

public class TestsEjercicio1manual {

	public static void main(String[] args) {
		List.of(1, 2, 3).forEach(num_test -> {
			DatosEjercicio1.iniDatos("ficherosEjercicios/Ejercicio1DatosEntrada" + num_test + ".txt");
			String2.toConsole("Solucion obtenida: %s\n", Ejercicio1PDR.search());
			TestsPI5.line("*");
		});
	}

}
