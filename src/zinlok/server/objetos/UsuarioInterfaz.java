package zinlok.server.objetos;
// Implementa el usuario de un sistema
public interface UsuarioInterfaz {
	// Para obtener el nombre de usuario
	String getNombre();
	
	// Para añadir un objeto asociado al usuario
	void addObjeto(Objeto objeto);
	
	// Obtener un objeto por nombre
	Object getObjeto(String nombre);
}
