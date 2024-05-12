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
import model.Usuario;

/*
 * TIP: Hay que haer un Dao por cada entidad.
 * Patron Singelton aun mejor.
 * Yo quiero que cuando se instancie este objeto (DaoNave),
 * quiero que el constructor de esta clase se conecte.
 *
 * TAD - DAO
 * Para insertar en BD clase DAO (las clases DAO tienen persistencia) y para listar clases TAD (un arrayList por ejemplo).
 * Pero para no rizar mucho el rizo estamos usando DAO para todo, si estuviese el DAO muy sobrecargado ya lo separaríamos.
 */

public class DaoNave {
	
	public static Connection con = null; // patron Singleton
	// Connection es el que está haciendo la magia, que ya está hecha en DBConexion.
	// Recordar, el private oculta, pero el static visualiza.
	private static DaoNave instance = null; // *para el Singelton
	
	public DaoNave() throws SQLException { // este método es el constructor de esta clase.
		
		this.con = DBConexion.getConexion();
		
		// Cuando se cree esta clase DaoNave, se va a conectar nada más empezar.
	}

	/** SINGELTON
	 * Este método es el que utilizo para aplicar el patron SINGELTON.
	 * Hacer un método estático* (lo llamo igual que la clase) para ser llamado,
	 * que me va a devolver un objeto de la misma clase en la que estoy.
	 * @return
	 * @throws SQLException
	 */
	public static DaoNave getInstance() throws SQLException { 
		
		if (instance == null) { // Comprobar que no sea nulo.
			instance = new DaoNave();
		}
		
		return instance;
		/*
		 * Objeto DaoNave que llamo desde cualquier sitio.
		 * En Nave por ejemplo en el método insertar lo he llamado (patron Singelton).
		 */		
	}
	
	public void insertar(Nave n) throws SQLException {
		
		String sql = "INSERT INTO naves (nombre, clase, matricula, descripcion, estado, foto) VALUES (?,?,?,?,?,?)"; // secuencia sql
		PreparedStatement ps = con.prepareStatement(sql);
		/*
		 * No tengo que conectarme, yo ya tengo una conexion activa ('con'), 
		 * y le metemos la query que queremos utilizar (sql).
		 */
		
		// Cargar los datos:
		ps.setString(1, n.getNombre());
		ps.setString(2, n.getClase());
		ps.setString(3, n.getMatricula());
		ps.setString(4, n.getDescripcion());
		ps.setString(5, n.getEstado());
		ps.setString(6, n.getFoto());
		
		// prepareStatement listo para lanzarlo.
		
		@SuppressWarnings("unused")
		int filas = ps.executeUpdate();
		
		ps.close();
		/*
		executeUpdate porque quiero enviar, si es recibir seria executeQuery.
		No hace falta cerrar la conexion, porque es un objeto,
		cuando deje de funcionar el recolector basura de java se lo cargará. Solo cerrar el ps.
		*/
		
	}
	/*
	 * Un método con prepareStatement y me conecto con el atributo 'con' que ya tenemos elaborado.
	 * Ejecutarla y guardararla en un objeto de tipo ResultSet, ResultSet es el que utiliza mysql para almacenar esa coleccion.
	 * Y le indico que me ejecute el 'ps' pero dado que lo que quiero es recibir datos, no enviar, el que
	 * usaré es el executeQuery, no el executeUpdate.
	 */
	public ArrayList<Nave> listar() throws SQLException{
		
		// sin filtro, se podría usar ("...WHERE clase=?" por ejemplo), en este caso habría que modificar el while etc.
		String sql = "SELECT * FROM naves";
		
		PreparedStatement ps = con.prepareStatement(sql); 
		// Otra manera -> PreparedStatement ps = con.prepareStatement("SELECT * FROM naves")
		
		ResultSet res = ps.executeQuery(); // aquí ya tengo una lista
		
		ArrayList<Nave> naves = null; 		
		/*
		 * ArrayList<Nave> naves = new ArrayList<Nave>() esto funcionaría pero no es buena practica.
		 * Inicializar primero la coleccion como buena práctica.
		 * 
		 * Distribución en BD:
		 * null [] [] [] [] [] [] null
		 * Por lo tanto con el res.next salto el null y muevo el puntero a ese entero.
		 * El bucle while hará un next por cada caja, recorrerá hasta encontrar el null final y parar.	
		*/		
		while(res.next()) {
			
			// Si el ArryList 'result' no está inicializado (es null), inicializarlo, y sino no hacerlo.
			
			if(naves == null) { // este if se eliminaría si lo hiciéramos de la manera rápida pero "mala" práctica.
				
				naves = new ArrayList<Nave>(); 
				
			}
			/*
			 * En cada fila del ArrayList instancio un objeto de tipo Nave.
			 * Elegir un constructor (de clase Modelo) que, esta vez si, contenta el id.
			 */
			
			naves.add(new Nave(res.getInt("id"), res.getString("nombre"), res.getString("clase"), res.getString("matricula"), res.getString("descripcion"), res.getString("estado"),res.getString("foto")));
			/*
			 * ó -> naves.add(new Nave(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6),res.getString(7)));
			 * y con asterisco * en la query sql ó en el "SELECT idnaves, nombre, etcetc, FROM naves".
			 * Libre elección, solo tener cuidado al poner el orden numérico.			
			*/
		}
		
		return naves;
		
	}
	
	/*
	// Método para devolver una sola nave o usuario, etc. El servlet solo me devolvería uno.
	public Nave listarConFiltro(String matricula) throws SQLException {
		
		String sql = "SELECT * FROM naves WHERE matricula=?"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, matricula);
		ResultSet res = ps.executeQuery();
		
		ArrayList<Nave> ls = null;
		
		res.next();										
		Nave n = new Nave(res.getInt("id"), res.getString("nombre"), res.getString("clase"), res.getString("matricula"), res.getString("descripcion"), res.getString("estado"),res.getString("foto"));	
		
		return n;		
		
	}
	*/
	
	
	/* IMPORTANTE
	 * Vincular al proyecto mediante el Build Path el archhivo GSON (es la libreria GSON)
	 * que tengo en mi carpeta JAVA que cree.
	 * Convertir en formato JSON.
	 * Esto es para convertir todos los datos que le damos a un arhivo json básicamente. Siempre es igual.	
	*/
	public String listarJson() throws SQLException {
		
		String json = "";
		Gson gson = new Gson();
		/*
		 * Genero un  String con cadena vacía. Preparo el atributo.
		 * Creo un objeto gson. Gracias a la libreria Gson que importamos que está en el Build Path.
		 */
		
		/*
		 * Mediante este método toJson, tengo un String con todos los datos de mi base de datos en formato JSON.
		 * Dentro de ese String json, me metas lo que me devuelva el objeto gson, con el método toJson,
		 * que le voy a dar el método listar que es el que tiene todos los datos.
		 * 
		 * Con el objeto gson que acabo de crear, dentro de json quiero que me guardes lo que me genere
		 * el objeto gson con el método toJson, y que me devuelvas lo del método this.listar.
		*/
		json = gson.toJson(this.listar());
		
		return json; // 
		/*
		 * Retorno el archivo json
		 * Ahora mi modelo ya es capaz de conectarse y hacer las cosas en JSON.
		 */
		
	}
	
	
	

}
