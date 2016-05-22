package zinlok.server.cliente;

import zinlok.server.objetos.Usuario;

/* Recibe el socket otorgado por SirveCliente */
public interface ClienteInterfaz {
	/* Método de ejecución principal del hilo.
	 * -> Recibe: nada
	 * -> Devuelve: nada
	 */
	public void run ();
	
	public Boolean puedoIniciar(String nombre, int tipo);
	public Usuario getUsuario();
	
}
