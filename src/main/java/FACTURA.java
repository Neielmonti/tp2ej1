public class FACTURA {
	private int nro;
	private CLIENTE cliente;
	private float importe;
	
	
	public FACTURA(int numero) {
		this.nro = numero;
		this.importe = 0;
		this.cliente = null;
	}

	public FACTURA() {
		nro = 0;
		cliente = null;
		importe = 0;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}
	
	public void setCliente (CLIENTE cliente) {
		this.cliente=cliente;
	}
	
	public void setImporte (float importe) {
		this.importe = importe;
	}
	
	public int getNro () {return nro;}
	
	public CLIENTE getCliente () {return cliente;}
	
	public float getImporte () {return importe;}
	
	public String toString() {
		return "NRO: " + nro + "\n" + " Importe: "+ importe+ "\n" +" Cliente :"+"\n" + cliente;
	}	
}

