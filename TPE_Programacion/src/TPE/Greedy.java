package TPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * Estrategia Greedy:
 *
 * Funcion de seleccion:
 * Ordenamos el conjunto C por peso de forma descendente porque el objetivo
 * es minimizar el peso sin asignar, al procesar primero los paquetes mas
 * pesados nos aseguramos de que si algun paquete no encuentra lugar en
 * ningun camion, sea el de menor peso posible, reduciendo asi el peso total 
 * no asignado. La seleccion simplemente toma el primer
 * elemento de C y lo remueve, de esta forma a medida que avanza el
 * algoritmo los candidatos restantes son cada vez mas livianos.
 *
 * El algoritmo recorre el conjunto C mientras haya candidatos. Por cada
 * paquete seleccionado verifica si es factible asignarlo a algun camion,
 * es decir que no supere la capacidad maxima y que si contiene alimentos
 * el camion sea refrigerado. Si existe mas de un camion que cumpla se elige
 * el que tenga menor capacidad restante , evitando desperdiciar espacio en 
 * camiones grandes. Si ningun camion lo permite
 * el paquete queda sin asignar incrementando el peso total no asignado.
 * La solucion finaliza cuando C queda vacio, es decir cuando se proceso
 * cada paquete exactamente una vez.
 *
 *Complegidad Computacional:
 *
 *1° Al comienzo de la funcion cargamos todos los camiones a la solucion,
 *	 O(c) donde c es la cantidad de camiones, luego ordenamos los paquetes
 *   con un .sort(), lo cual tiene una complegidad de O(p log p) donde p
 *   es la cantidad de paquetes.
 *
 *2° Luego, al hacer un while por la cantidad de paquetes, en este caso es
 *   O(p) donde p son la cantidad de paquetes. Ademas, dentro de este
 *   while, iteramos por cada camion para buscar el mejor a la hora de
 *   asignarle el paquete y guardarnos el indice, esto agrega un O(c) por
 *   cada vuelta del while, dando como resultado O(p x c).
 *
 * En conclusion, la complegidad computacional es de: O(c) + O(p log p) + O(p x c)
 */


public class Greedy {

	public Solucion greedy(List<Paquete> paquetes, List<Camion> camiones) {

	    Solucion solucion = new Solucion();

	    
	    Map<Camion, List<Paquete>> S = new HashMap<>();
	    double[] cargaActual = new double[camiones.size()];
	    for (int i = 0; i < camiones.size(); i++) {
	        S.put(camiones.get(i), new ArrayList<>());
	    }

	   
	    List<Paquete> C = new ArrayList<>(paquetes);
	  
	    //Ordeno los paquetes por peso de manera decendente
	    C.sort((a, b) -> Double.compare(b.getPeso(), a.getPeso()));

	    double pesoNoAsignado = 0;

	    
	    while (!C.isEmpty()) {

	        
	        Paquete x = C.remove(0);
	        solucion.incrementarCandidatos();

	        
	        int indiceMejorCamion = -1;
	        double menorCapacidadRestante = Double.MAX_VALUE;

	        for (int i = 0; i < camiones.size(); i++) {
	            Camion camion = camiones.get(i);
	            double capacidadRestante = camion.getCapacidadMaxima() - cargaActual[i];

	            if (puedoAsignar(x, camion, cargaActual[i])) {
	                
	            	if (capacidadRestante < menorCapacidadRestante) {
	                	
	                    menorCapacidadRestante = capacidadRestante;
	                    indiceMejorCamion = i;
	                }
	            }
	        }

	        
	        if (indiceMejorCamion != -1) {
	            Camion mejorCamion = camiones.get(indiceMejorCamion);
	            S.get(mejorCamion).add(x);
	            cargaActual[indiceMejorCamion] += x.getPeso();
	        } else {
	            
	            pesoNoAsignado += x.getPeso();
	        }
	    }

	    solucion.setAsignacion(S);
	    solucion.setPesoNoAsignado(pesoNoAsignado);
	    return solucion;
	}

	private boolean puedoAsignar(Paquete paquete, Camion camion, double cargaActual) {
	    if (cargaActual + paquete.getPeso() > camion.getCapacidadMaxima()) {
	        return false;
	    }
	    if (paquete.isContieneAlimentos() && !camion.isRefrigerado()) {
	        return false;
	    }
	    return true;
	}
}
