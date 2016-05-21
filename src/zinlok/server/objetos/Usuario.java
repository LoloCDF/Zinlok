package zinlok.server.objetos;

public class Usuario implements UsuarioInterfaz{
	// Nombre de usuario
	String nombre = "";
	
	// Objetos asociados
	Objeto[] listaObjetos;
	
	public Usuario(String nombre){
		this.nombre = nombre;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void addObjeto(Objeto objeto){
		this.listaObjetos[this.listaObjetos.length]=objeto;
	}
	
	public Objeto getObjeto(String nombre){
		int i=0;
		Boolean bandera = false;
		Objeto objeto = null;
		for (i=0; i<this.listaObjetos.length && bandera==false;i++){
			if(this.listaObjetos[i].getNombre().equals(nombre)){
				bandera=true;
				objeto=listaObjetos[i];
			}
		}
		
		return objeto;
	}
}
