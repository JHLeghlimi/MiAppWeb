package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Esto es siempre lo mismo, lo copiamos y listo.

// No olvidarse de añadir el conector mysql...jar en el build path y tambien copiarlo en la carpeta de apache!!

// Una clase para conectarnos y desconectarnos sin estar todo el rato manual y configurando todo.
// Un objeto DBConexion que llamaré cuando quiera para conectarme y desconectarme.

public class DBConexion { 
	// esto es ya un objeto, DBConexion que se va a conectar.
	
	// IMPORTATE --> debe ser 'static': un atributo estático se extiende fuera del objeto,
	// es común para todos los objetos instanciados del tipo.
	// es decir, un ejemplo, cambiando nave 1 cambia para nave 5 tambien.
	// y 'final' es que no se puede modificar ya, es constante.
	
	// Cuando desde cualeuir parte de mi programa instancie este objeto,
	// me va a devolver un objeto Connection (conexion), es decir,
	// me va a devolver una conexion a la BD, a la ruta url que le pongo.
	
	public static final String JDBC_URL = "jdbc:mysql://localhost:3306/flota";
	public static Connection instance = null; // objeto Connection
	
	public static Connection getConexion() throws SQLException {
		
		if (instance == null) { 
			
			// para evitar conectarse si ya está conectado, ya que daría error.
			// Es decicir, si instance == null es que ya me había conectado antes.
			
			// Si instance es igual a null, conectate y devuelveme la instancia.
			// Si instance no es igual a null, es que ya me habia conectado antes,
			// así que devuelveme sin mas la instancia.
			
		instance = DriverManager.getConnection(JDBC_URL, "root", "1234");
		
		// La magia está en que sea estática y en la sentencia de arriba.
		
		// Si  en vez del throws SQLException, queremos gestionar los errores.
		/* 		try {
			instance = DriverManager.getConnection(JDBC_URL, "root", "1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Aquí gestionar los errores.
		}
			// El control de errores se hace con el try catch. En este  caso al tener prisa arrojamos el throws y listo.
			// En caso de hacerlo con el try catch, hay que eliminar el throws SQLException.
		*/
		
		}
		return instance;
	}

}
