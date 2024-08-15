package ejercicio3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import _datos.DatosEjercicio3;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record Ejercicio3Vertex(Integer index,Integer investigador,Integer trabajo,List<Integer>dias,List<List<Integer>>distribucion)
		implements VirtualVertex<Ejercicio3Vertex, Ejercicio3Edge, Integer> {

	public static Ejercicio3Vertex of(Integer index,Integer investigador,Integer trabajo,List<Integer>dias,List<List<Integer>>distribucion) {
		return new Ejercicio3Vertex( index, investigador, trabajo,dias,distribucion);
	}

	public static Ejercicio3Vertex initial() {
	
		
	
		return of(0,0,0,DatosEjercicio3.getDiasDisponibles(),DatosEjercicio3.getListReparto());

	}
	//Es un vertice final si llega al numero de investigadores * trabajos
	public static Predicate<Ejercicio3Vertex> goal(){
		return v ->  v.index == DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos();
	} 
	//Es solucion si la lista de distribuciones y dias son mayores o iguales a 0 es decir
	//ninguna de las dos es negativa
	public static Predicate<Ejercicio3Vertex> goalHasSolution() {
	    return v -> v.distribucion.stream()
	            .flatMap(List::stream)
	            .allMatch(e -> e >= 0) && v.dias().stream()
	            .allMatch(e -> e >= 0);
	}


	@Override
	  public List<Integer> actions() {
        List<Integer> alternativas = new ArrayList<>();
        //Si el indice es menor que el numero de investigadores * trabajos
        if (index < DatosEjercicio3.getnInvestigadores() * DatosEjercicio3.getmTrabajos()) {
            Integer investigador = index / DatosEjercicio3.getmTrabajos();
            Integer trabajo = index % DatosEjercicio3.getmTrabajos();
            Integer especialidad = DatosEjercicio3.getEspecialidad(investigador);
            Integer diasNecesarios = DatosEjercicio3.diasNecesarios(trabajo, especialidad);
            //Si la capacidad es mas pequeña que los dias necesarios significa que el investigador
            //estaria haciendo mas trabajo del que tiene por eso su alternativa es no hacerlo
            if(DatosEjercicio3.getCapacidad(investigador)- diasNecesarios<0) {
            	alternativas= List2.of(0);
            	//En el caso de que si pueda
            }else {
            	//Calculamos que es mas pequeño si los dias que necesita o la capacidad pues es lo maximo
            	//que se podrá hacer con ese investigador en ese trabajo
            List<Integer> min = List2.of(diasNecesarios,DatosEjercicio3.getCapacidad(investigador));
            Integer alternativasMinimas = Collections.min(min);
            alternativas = List2.rangeList(0, alternativasMinimas +1); 
            }
            //Si hemos llegado al vertice final no tenemos alternativas
       }else {
        	alternativas = List2.empty();
        }
        Collections.reverse(alternativas);
         return alternativas;
    }

	
	@Override
	  public Ejercicio3Vertex neighbor(Integer a) {
		
		//El index2 nos permite calcular los investigadores para el siguiente vertice
		//El index3 nos permite hacer los cambios en la lista dias y distribucion porque 
		//si lo hiciesemos con index2 estariamos modificando la lista respeto al siguiente vertice
		
 		Integer index2 = index;
		Integer index3 = index2+1;
        Integer investigador2 = investigador;
        Integer trabajo2 = trabajo;
        List<Integer> dias2 = List2.copy(dias);
        
        Integer investigador3 = investigador;
        Integer trabajo3 = trabajo;
        List<List<Integer>>distribucion2 = List2.copy(distribucion);
        Integer especialidad = DatosEjercicio3.getEspecialidad(investigador);
        
        //Calculamos el investigador y trabjo para el siguiente indice
        
         investigador2 = index3 / DatosEjercicio3.getmTrabajos();
         trabajo2= index3 % DatosEjercicio3.getmTrabajos();
        
     
   
        //Modificacion de dias
         
        Integer resta = dias2.get(investigador3) - a;
        dias2.set(investigador3, resta);
       
        
    	//Modificacion de distribucion 
        
        //tenemos que hacer la lista mutable porque por defecto la List<List<Integer>> no podemos hacerle
        // un set a la lista de dentro
        	List<List<Integer>> distribucion2Mutable = distribucion2.stream()
      	    .map(ArrayList::new) // Crear una nueva lista mutable para cada sublista
      	    .collect(Collectors.toList()); // Convertir el stream a una lista mutable
        	List<Integer> distribucionTrabajoMutable = new ArrayList<>(distribucion2Mutable.get(trabajo3));
        	//Obtenemos el trabajo y la especialidad y le restamos la accion
        	Integer resta2 = distribucion2Mutable.get(trabajo3).get(especialidad) - a;
        	distribucionTrabajoMutable.set(especialidad, resta2);
        	distribucion2Mutable.set(trabajo3, distribucionTrabajoMutable);
        	
        	
          return of( index2 +1, investigador2, trabajo2, dias2, distribucion2Mutable);
    }
	
	@Override
	public Ejercicio3Edge edge(Integer a) {
		return Ejercicio3Edge.of(this, neighbor(a), a);
	}
}
