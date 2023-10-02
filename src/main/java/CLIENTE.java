public class CLIENTE {
	private int id;
	private String descripcion;
	
	public CLIENTE(int num, String nombre) {
		this.id = num; 
		this.descripcion = nombre;
	}
	
	public CLIENTE(int num) {
		this.id = num; 
		this.descripcion = null;
	} 
	
	public CLIENTE() {
		id = 0;
		descripcion = null;
	}

	public void setId (int id) {
		this.id= id;
	}
	
	public void setDescripcion (String descr) {
		this.descripcion= descr;
	}
	
	public int getId() {return this.id;}
	
	public String getDescripcion () {return this.descripcion;}
	
	public String toString() {
		return "Id: " + this.id +"\n" +"  Descripcion: "+ this.descripcion;
	} 
	
}

