package zinlok.server.cliente;

import java.net.*;

/* Recibe el socket otorgado por SirveCliente */
public interface ClienteInterfaz {
	/* Método de ejecución principal del hilo.
	 * -> Recibe: nada
	 * -> Devuelve: nada
	 */
	public void run (Socket miSocket);
	
}
