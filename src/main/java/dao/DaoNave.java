package dao;

import java.sql.Connection;

// Clases DAO (Data Access Object)
// Son las que cogen los datos y convertirlo en secuencias sql que mandará a la BD.
// Las siglas dao tienen que ver con la gestion de la base de datos.
// Todo lo que este en dao, tiene que ver con la base de datos.


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Nave;

// TIP: Hay que haer un Dao por cada entidad.
// Patron Singelton aun mejor.

// Yo quiero que cuando se instancie este objeto (DaoNave), 
// quiero que el constructor de esta clase se conecte.

public class DaoNave {
	
	public static Connection con = null; // patron Singleton, se explicará otro sábado.
	// Connection es el que está haciendo la magia, que ya esá hecha.
	// Recordar, el private oculta, pero el static visualiza.
	private static DaoNave instance = null; // para el Singelton
	
	public DaoNave() throws SQLException { // este método es el constructor de esta clase.
		
		this.con = DBConexion.getConexion();
		
		// Cuando se cree esta clase DaoNave, se va a conectar nada más empezar.
	}
	
	// SINGELTON:
	// Hacer un método estático (lo llamo igual que la clase) para ser llamado,
	// que me va a devolver un objeto de la misma clasde en la que estoy.

	/**
	 * Este método es el que utilizo para aplicar el patron Singelton.
	 * @return
	 * @throws SQLException
	 */
	public static DaoNave getInstance() throws SQLException {
		
		if (instance == null) { // Comprobar que no sea nulo.
			instance = new DaoNave();
		}
		
		return instance;
		
		
	}
	
	public void insertar(Nave n) throws SQLException {
		
		String sql = "INSERT INTO naves (nombre, clase, matricula, descripcion, estado, foto) VALUES (?,?,?,?,?,?)"; // secuencia sql
		PreparedStatement ps = con.prepareStatement(sql);
		// No tengo que conectarme, yo ya tengo una conexion activa ('con'), 
		// y le metemos la query que queremos utilizar (sql).
		
		// Cargar los datos:
		ps.setString(1, n.getNombre());
		ps.setString(2, n.getClase());
		ps.setString(3, n.getMatricula());
		ps.setString(4, n.getDescripcion());
		ps.setString(5, n.getEstado());
		ps.setString(6, n.getFoto());
		
		// prepareStatement listo para lanzarlo.
		
		int filas = ps.executeUpdate();
		ps.close();
		
		// executeUpdate porque quiero enviar, si es recibir seria executeQuery.
		// No hace falta cerrar la conexion, porque es un objeto,
		// cuando deje de funcionar el recolector basura de java se lo cargará. Solo cerrar el ps.
	}
	
	public ArrayList<Nave> listar() throws SQLException{
		
		String sql = "SELECT * FROM naves";
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet res = ps.executeQuery();
		
		ArrayList<Nave> naves = null;
		
		
		
		while(res.next()) {
			
			if(naves == null) {
				
				naves = new ArrayList<Nave>();
				
			}
			
			naves.add(new Nave(res.getInt("id"), res.getString("nombre"), res.getString("clase"), res.getString("matricula"), res.getString("descripcion"), res.getString("estado"),res.getString("foto")));
			
		}
		
		return naves;
		
		
		
	}
	
	// IMPORTANTE
	
	// Vincular al proyecto mediante el Build Path el archhivo GSON (es la libreria GSON) 
	// que tengo en mi carpeta JAVA que cree.
	
	// Convertir en formato JSON.
	
	public String listarJson() throws SQLException {
		
		String txtJSON = "";
		
		Gson gson = new Gson(); // libreria gson
		
		// Mediante este método toJson, tengo un String con todos los datos de mi base de datos
		// en formato JSON.
		txtJSON = gson.toJson(this.listar());
		
		return txtJSON;
		
		// Ahora mi modelo ya es capaz de conectarse y hacer las cosas en JSON.
		
	}
	
	
	

}
