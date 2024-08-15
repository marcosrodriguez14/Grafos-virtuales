package _datos;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class DatosEjercicio3 {
	private static List<Investigador> investigadores;
	private static List<Trabajo> trabajos;

	public record Investigador(String nombre, Integer capacidad, Integer especialidad) {

		public static Investigador of(String nombre, Integer capacidad, Integer especialidad) {
			return new Investigador(nombre, capacidad, especialidad);
		}
	}

	public record Trabajo(String trabajo, Integer calidad, Map<Integer, Integer> reparto) {
		public static Trabajo of(String trabajo, Integer calidad, Map<Integer, Integer> reparto) {
			return new Trabajo(trabajo, calidad, reparto);
		}
	}

	public static void iniDatos(String fichero) {
		investigadores = List2.empty();
		trabajos = List2.empty();

		List<String> lineas = Files2.linesFromFile(fichero);
		for (String linea : lineas) {
			if (linea.startsWith("INV")) {
				String[] str = linea.split(";");
				String[] Inv_Capacidad = str[0].split(":");
				String nombre = Inv_Capacidad[0].trim();
				Integer capacidad = Integer.valueOf(Inv_Capacidad[1].replace("capacidad=", "").trim());
				Integer especialidad = Integer.valueOf(str[1].replace("especialidad=", "").trim());

				investigadores.add(Investigador.of(nombre, capacidad, especialidad));
			} else if (linea.startsWith("T0")) {
				Map<Integer, Integer> reparto = Map2.empty();

				String[] str = linea.split(";");
				String[] nombreCalidad = str[0].split("->");
				String nombre = nombreCalidad[0];
				Integer calidad = Integer.valueOf(nombreCalidad[1].replace("calidad=", "").trim());

				String repartoStr[] = str[1].replace("reparto=", "").trim().replace("(", "").replace(")", "").trim()
						.split(",");
				List<String> repartoStrList = Arrays.asList(repartoStr);
				for (String cv : repartoStrList) {
					String[] claveValor = cv.split(":");
					reparto.put(Integer.valueOf(claveValor[0].trim()), Integer.valueOf(claveValor[1].trim()));
				}
				trabajos.add(Trabajo.of(nombre, calidad, reparto));
			}
		}
	}

	// Metodos de entrada

	public static Integer getnInvestigadores() {
		return investigadores.size();
	}

	public static Integer geteEspecialidades() {
		return investigadores.stream().map(x -> x.especialidad()).collect(Collectors.toSet()).size();
	}

	public static Integer getmTrabajos() {
		return trabajos.size();
	}

	// devuelve 1 si el trabajador i tiene la especialidad k
	public static Integer tieneEspecialidad(Integer i, Integer k) {
		return investigadores.get(i).especialidad() == k ? 1 : 0;
	}

	// devuelve los dias disposibles del trabajador j
	// capacidad = dias disponibles
	public static Integer getCapacidad(Integer i) {
		return investigadores.get(i).capacidad();
	}

	// dias necesarios para el trabajo j de investigador con especialidad k
	public static Integer diasNecesarios(Integer j, Integer k) {
		return trabajos.get(j).reparto().get(k);
	}
	//Devuelve la calidad del trabajo j
	public static Integer getCalidadTrabajo(Integer j) {
		return trabajos.get(j).calidad();
	}
	//Devuelve la lista de investigadores
	public static List<Investigador> getInvestigadores() {
		return investigadores;
	}

	//Devuelve la lista de trabajos
	public static List<Trabajo> getTrabajos() {
		return trabajos;
	}
	//Obtiene la especialidad de un investigador
	public static Integer getEspecialidad(Integer i) {
		return investigadores.get(i).especialidad();
	}
	//Obtiene el trabajo i
	public static Trabajo getTrabajo (Integer i) {
		return trabajos.get(i);
	}
	//Obtiene la lista de dias que se necesitan hacer 
	public static List<List<Integer>> getListReparto() {
		return trabajos.stream().map(x -> x.reparto.values().stream().toList()).toList();
	}
	//Obtiene la lista de los dias disponibles de los investigadores
	public static List<Integer>getDiasDisponibles(){
		return investigadores.stream().map(x-> x.capacidad()).toList();
	}
	

	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficherosEjercicios/Ejercicio3DatosEntrada1.txt");
System.out.println(getListReparto());
System.out.println(getDiasDisponibles());
	}
}
