package zinlok.server.servicios.arduino;

// Para la comunicación con la placa
public interface ArduinoInterfaz {
	
	/* 	Para activar la bandera de control de la placa
	 * 	-> Recibe: nada
	 * 	-> Devuelve: se ha podido o no
	 */
	public Boolean subeBandera();
	
	/*	Para desactivar la bandera de control de la placa
	 * 	-> Recibe: nada
	 * 	-> Devuelve: se ha podido o no
	 */
	public Boolean bajaBandera();
	
	/*	Manda una orden a la placa, un número entero
	 * 	-> Recibe: comando, número entero
	 * 	-> Devuelve: la orden se ha podido mandar
	 * 
	 */
	public Boolean escribe(int orden);
}
