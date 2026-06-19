package TPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {

	/*
	 * Estrategia Backtracking:
	 * 
	 * Para cada paquete se evaluan dos opciones: asignarlo a un camion o
	 * dejarlo sin asignar. Para asignarlo se verifica mediante puedoAsignar()
	 * que no supere la capacidad maxima del camion y que si contiene alimentos
	 * el camion sea refrigerado. Si se puede asignar, se carga el paquete y
	 * se avanza al siguiente. Si no se puede asignar a ningun camion, el paquete
	 * queda sin asignar y se avanza al siguiente igualmente.
	 * Una vez procesados todos los paquetes se guarda la solucion si el peso
	 * sin asignar es menor al de la mejor solucion conocida hasta el momento.
	 * Se aplica una poda: si el peso sin asignar acumulado ya supera o iguala
	 * al de la mejor solucion conocida, se abandona esa rama sin seguir explorando. 
	 *
	 *
	 *Complegidad computacional:
	 *
	 *1° En el metodo publico tenemos un O(c) donde c es la cantidad de camiones que voy 
	 *	 a iterar para cargarlos en la tabla HASH
	 *
	 *2° En el metodo privado tenemos O(n) a la hora de guardar una solucion tenemos que 
	 * 	 copiar la asignacionActual y cargarla en mejor solucion
	 * 
	 * 3° Luego en el momento de cargar los paquetes a los camiones, iteramos por cada camion
	 * 	 O(n) donde n son la cantidad de camiones 
	 * 
	 * En conclucion esto el ciclo back va a ser llamado mas de una vez por eso
	 * podriamos decir que la complegidad en este caso es de O(n x n^2)
	 *
	 *
	 */


	public Solucion backtracking(List<Paquete> paquetes, List<Camion> camiones) {

		Solucion mejorSolucion = new Solucion();

		
		mejorSolucion.setPesoNoAsignado(Double.MAX_VALUE);

		
		Map<Camion, List<Paquete>> asignacionActual = new HashMap<>();
		double[] cargaActual = new double[camiones.size()]; 


		for (Camion c : camiones) {
			asignacionActual.put(c, new ArrayList<>());
		}

		back(paquetes, camiones, 0, asignacionActual, cargaActual,
				0.0, mejorSolucion);

		return mejorSolucion;
	}

	private void back(List<Paquete> paquetes, List<Camion> camiones, int indicePaquete, 
			Map<Camion, List<Paquete>> asignacionActual,
			double[] cargaActual,double pesoSinAsignarActual,Solucion mejorSolucion) {


		mejorSolucion.incrementarEstados(); 

		
		if (indicePaquete == paquetes.size()) {
			if (pesoSinAsignarActual < mejorSolucion.getPesoNoAsignado()) {
				
				mejorSolucion.setPesoNoAsignado(pesoSinAsignarActual);
				mejorSolucion.setAsignacion(copiarAsignacion(asignacionActual));
			}
			return;
		}

		// PODA: si ya acumule mas peso sin asignar que la mejor solucion conocida,
		// no tiene sentido seguir por esta rama
		if (pesoSinAsignarActual >= mejorSolucion.getPesoNoAsignado()) {
			return;
		}


		// obtenemos el primer paquete de la lista
		// iteramos por cada camion y evaluamos si podemos asignar el paquete a ese camion
		// sino incrementamos el indice para en otro llamado evaluar el siguente paquete

		Paquete paquete = paquetes.get(indicePaquete);


		for (int i = 0; i < camiones.size(); i++) {
			Camion camion = camiones.get(i);

			if (puedoAsignar(paquete, camion, cargaActual[i])) {

				asignacionActual.get(camion).add(paquete);
				cargaActual[i] += paquete.getPeso();

				

				back(paquetes, camiones, indicePaquete + 1,
						asignacionActual, cargaActual,
						pesoSinAsignarActual, mejorSolucion);

				

				asignacionActual.get(camion).remove(paquete);
				cargaActual[i] -= paquete.getPeso();
			}

		}

		// en caso de no asignar el paquete , llamo a back incrementando el indice
		back(paquetes, camiones, indicePaquete + 1,
				asignacionActual, cargaActual,
				pesoSinAsignarActual + paquete.getPeso(), mejorSolucion);


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



	private Map<Camion, List<Paquete>> copiarAsignacion(Map<Camion, List<Paquete>> asignacionActual) {
		Map<Camion, List<Paquete>> copia = new HashMap<>();
		for (Map.Entry<Camion, List<Paquete>> entry : asignacionActual.entrySet()) {
			copia.put(entry.getKey(), new ArrayList<>(entry.getValue()));
		}
		return copia;
	}



}


