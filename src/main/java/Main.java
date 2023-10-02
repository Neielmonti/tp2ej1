import java.io.File;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
public class Main {
	private static Scanner entrada = new Scanner(System.in);
	public static void main(String[] args) {
		File archivo= new File("db4o.db");
		if (!archivo.exists()) {
			new File("db4o.db");
		}
		ObjectContainer bd=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"db4o.db");
		menu(bd);
	}
	public static void clearConsole() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	public static void menu(ObjectContainer bd) {
		clearConsole();
		System.out.println("""
  	---------------[Menu Principal]----------------
  			
		1) ABM Clientes
		2) AMB Facturas
		3) Realizar consultas
		0) Salir
			
		Ingrese una opcion: """);
		String opcion = entrada.nextLine();
		switch (opcion) {
			case "1" -> menuABMCliente(bd);
			case "2" -> menuABMFactura(bd);
			case "3" -> menuConsultas(bd);
			case "0" -> {
				bd.close();
				entrada.close();
				clearConsole();
				System.out.println("Nos vemos!");
				System.exit(0);
			}
			default -> menu(bd);
		}
	}
	public static void menuABMFactura (ObjectContainer bd) {
		clearConsole();
		System.out.println("""
		---------------[Menu ABM de FACTURA]----------------

		1) Listar facturas
		2) Alta
		3) Baja
		4) Modificacion
		0) Volver al Menu Principal
		Ingrese una opcion:""");
		
		String opcion = entrada.nextLine();
		switch (opcion) {
		case "1":
			listarFacturas(bd);
			menuABMFactura(bd);
			break;
		case "2":
			altaFactura(bd);
			menuABMFactura(bd);
			break;
		case "3":
			bajaFactura(bd);
			menuABMFactura(bd);
			break;
		case "4":
			modificarFactura(bd);
			menuABMFactura(bd);
			break;
		case "0" : menu(bd);
		default: menuABMFactura(bd);
		}
	}
	public static void menuABMCliente(ObjectContainer bd) {
		clearConsole();
		System.out.println("""
			---------------[Menu ABM de CLIENTE]----------------

			1) Listar clientes
			2) Alta
			3) Baja
			4) Modificacion
			0) Volver al Menu Principal
			
			Ingrese una opcion:""");
		String opcion = entrada.nextLine();
		switch (opcion) {
		case "1":
			listarCliente(bd);
			menuABMCliente(bd);
			break;
		case "2":
			altaCliente(bd);
			menuABMCliente(bd);
			break;
		case "3":
			bajaCliente(bd);
			menuABMCliente(bd);
			break;
		case "4":
			modificarCliente(bd);
			menuABMCliente(bd);
			break;
		case "0" : menu(bd);
		default: menuABMCliente(bd);
		}
	}
	public static void menuConsultas (ObjectContainer db) {
		clearConsole();
		System.out.println("""
   			-------------[Menu de consultas]----------------
   			
			1) Listar las facturas de un cliente
			2) Seleccionar las facturas con un importe especifico
			0) Volver al Menu Principal
			
			Ingrese una opcion:""");
		String opcion = entrada.nextLine();
		switch (opcion) {
			case "1" -> {
				listarFacturasCliente(db);
				menuConsultas(db);
			}
			case "2" -> {
				seleccionImporteFactura(db);
				menuConsultas(db);
			}
			case "0" -> menu(db);
			default -> menuConsultas(db);
		}
	}
	public static void listarCliente (ObjectContainer db) {
		clearConsole();
    	System.out.println("-------------[Clientes]--------------");
    	CLIENTE proto = new CLIENTE();
        ObjectSet<CLIENTE> result = db.queryByExample(proto);
		if (result.size() == 0) {
			System.out.println("No hay clientes");
			System.out.println("Presione ENTER para continuar");
			entrada.nextLine();
			return;
		}
        while(result.hasNext()) {
	        System.out.println(result.next());
	        System.out.println();
	    }
        System.out.println("----------------------------------------");
        System.out.println();
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
    }
	public static void listarFacturas (ObjectContainer db) {
		clearConsole();
    	System.out.println("-------------[Facturas]--------------");
    	FACTURA proto=new FACTURA();
        ObjectSet<FACTURA> result = db.queryByExample(proto);
		if (result.size() == 0) {
			System.out.println("No hay facturas");
			System.out.println("Presione ENTER para continuar");
			entrada.nextLine();
			return;
		}
        while(result.hasNext()) {
	        System.out.println(result.next());
	        System.out.println();
	    }
        System.out.println("----------------------------------------");
        System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
    }
	// ABM CLIENTE
	public static void altaCliente(ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese el Id del Cliente");
		String opcion = entrada.nextLine();
		int numero = Integer.parseInt(opcion);
		ObjectSet<CLIENTE> resul = db.queryByExample(new CLIENTE(numero));
		
		if (resul.size()>0 ){
			System.out.println("Id existente");
		}else{
			System.out.println("Ingrese el nombre");
			opcion = entrada.nextLine();
			CLIENTE cliente = new CLIENTE(numero,opcion);
			db.store(cliente); 
			}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void bajaCliente(ObjectContainer db){
		clearConsole();
		System.out.println("Ingrese el Id a eliminar");
		String opcion = entrada.nextLine();
		int numero = Integer.parseInt(opcion);
		ObjectSet<CLIENTE> result = db.queryByExample(new CLIENTE(numero));
		
		if (result.size()>0) {
			CLIENTE cliente = result.next();
			db.delete(cliente);
			System.out.println("Cliente eliminado");
		}else {
				System.out.println("Cliente inexistente");
			  }
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void modificarCliente (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese el Id a Modificar");
		String opcion = entrada.nextLine();
		int numero = Integer.parseInt(opcion);
		ObjectSet<CLIENTE> result = db.queryByExample(new CLIENTE(numero));
		
		if (result.size()>0) {
		CLIENTE cliente = result.next();
		System.out.println("Ingrese el nuevo nombre");
		opcion = entrada.nextLine();
		cliente.setDescripcion(opcion);
		db.store(cliente);
		}else {
			System.out.println("Id inexistente");
		}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	// ABM FACTURA
	public static void altaFactura (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese el Id de la Factura");
		String opcion = entrada.nextLine();
		int numeroFac = Integer.parseInt(opcion);
		System.out.println("Ingrese el Id del CLiente");
		opcion = entrada.nextLine();
		
		int numeroId = Integer.parseInt(opcion);
		ObjectSet<CLIENTE> resul = db.queryByExample(new CLIENTE(numeroId));
		if (resul.size()>0) {
			CLIENTE cliente = resul.next();
			resul = db.queryByExample(new FACTURA(numeroFac));
			if (resul.size()==0) {
				System.out.println("Ingrese el monto de la factura");
				opcion = entrada.nextLine();
				if (Float.parseFloat(opcion) < 0) {
					System.out.println("Monto invalido");
					return;
				}
				FACTURA factura = new FACTURA(numeroFac);
				factura.setCliente(cliente);
				factura.setImporte(Float.parseFloat(opcion));
				db.store(factura);
				System.out.println("Factura creada correctamente");
			}else {
				System.out.println("Factura existente");
				}
		}else {
			System.out.println("Cliente inexistente");
		}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void bajaFactura (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese Id Factura");
		String opcion = entrada.nextLine();
		int numero = Integer.parseInt(opcion);
		ObjectSet<FACTURA> result = db.queryByExample(new FACTURA(numero));
		if (result.size()>0) {
			FACTURA factura = result.next();
			System.out.println("Factura eliminada");
			db.delete(factura);
		}else {
				System.out.println("Factura inexistente");
			  }
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void modificarFactura (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese el Id de la Factura");
		String opcion = entrada.nextLine();
		int numeroFac = Integer.parseInt(opcion);
		ObjectSet<FACTURA> result = db.queryByExample(new FACTURA(numeroFac));
		if (result.size()>0) {
			//existe la factura
			FACTURA factura = result.next();
			System.out.println("Ingrese Monto Factura");
			opcion = entrada.nextLine();
			float monto = Float.parseFloat(opcion);
			if (monto>0) {
				factura.setImporte(monto);
				db.store(factura);
			}else {
				System.out.println("Monto invalido");
			}
		}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}

	//CONSULTAS
	public static void listarFacturasCliente (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese Id del Cliente a listar sus Facturas");
		String opcion = entrada.nextLine();
		ObjectSet<CLIENTE> result = db.queryByExample(new CLIENTE(Integer.parseInt(opcion)));
		if (result.size()>0) {
			  CLIENTE cliente = result.next();
			  Query qry = db.query();
			  qry.constrain(FACTURA.class);
			  qry.descend("cliente").constrain(cliente);
			  result = qry.execute();
			  listar(result);  
		}else {
			System.out.println("Cliente inexistente");
		}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void seleccionImporteFactura (ObjectContainer db) {
		clearConsole();
		System.out.println("Ingrese Importe, Mostara las facturas con el Importe Ingresado");
		
		String opcion = entrada.nextLine();
		float numero = Float.parseFloat(opcion);
		if (numero >= 0) {
		Query qry = db.query();
		qry.constrain(FACTURA.class);
		qry.descend("importe").constrain(numero);
		ObjectSet<FACTURA> result= qry.execute();
		listar(result);
		} else {
			System.out.println("Dato incorrecto");
		}
		System.out.println("Presione ENTER para continuar");
		entrada.nextLine();
	}
	public static void listar (ObjectSet<?> result) {
		clearConsole();
		 while(result.hasNext()) {
		        System.out.println(result.next());
		    }
	        System.out.println("");
	        System.out.println("______________________");
	        System.out.println("");
	}
}