package zinlok.server.objetos;

import java.util.ArrayList;

public class Usuario implements UsuarioInterfaz{
	// Nombre de usuario
	String nombre = "";
	
	// Objetos asociados
	ArrayList<Objeto> listaObjetos;
	
	public Usuario(String nombre){
		this.nombre = nombre;
		this.listaObjetos = new ArrayList<Objeto>();
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void addObjeto(Objeto objeto){
		this.listaObjetos.add(objeto);
	}
	
	public Objeto getObjeto(String nombre){
		int i=0;
		Boolean bandera = false;
		Objeto objeto = null;
		for (i=0; i<this.listaObjetos.size() && bandera==false;i++){
			if(this.listaObjetos.get(i).getNombre().equals(nombre)){
				bandera=true;
				objeto=listaObjetos.get(i);
			}
		}
		
		return objeto;
	}
}
