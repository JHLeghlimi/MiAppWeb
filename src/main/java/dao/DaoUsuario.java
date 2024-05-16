package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

import model.Usuario;

public class DaoUsuario {
	
	public static Connection con = null;
	
	private static DaoUsuario instance = null; // Singelton
	
	public DaoUsuario() throws SQLException {
		
		this.con = DBConexion.getConexion();
		
	}
	
	/**
	 * Método usado para aplicar el patron Singelton.
	 * @return
	 * @throws SQLException
	 */
	public static DaoUsuario getInstance() throws SQLException {
		
		if(instance == null) {
			instance = new DaoUsuario();
		}
		return instance;
		
	}
	
	/**
	 * Método de inserción en la BD del objeto usuario.
	 * @param u
	 * @throws SQLException
	 */
	public void insertar(Usuario u) throws SQLException {
		
		String sql = "INSERT INTO usuarios (nombre,email,tlf,permiso) VALUES (?,?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getNombre());
		ps.setString(2, u.getEmail());
		ps.setString(3, u.getTlf());
		ps.setInt(4, u.getPermiso());
		
		int filas = ps.executeUpdate();
		
		ps.close();
		
	}
	
	public void actualizar(Usuario u) throws SQLException {
		/*
		 * Siempre hacer copia de la BD porque si ejecutase la query sin el WHERE me cargaría la BD
		 * 
		 */
		
		String sql = "UPDATE usuarios SET nombre=?, email=?, tlf=?, permiso=? WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, u.getNombre());
		ps.setString(2, u.getEmail());
		ps.setString(3, u.getTlf());
		ps.setInt(4, u.getPermiso());
		ps.setInt(5, u.getId());
		
		int filas = ps.executeUpdate();
		
		ps.close();
		
	}
	
	public void borrar(int id) throws SQLException {
		
		String sql = "DELETE FROM usuarios WHERE id=? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);

		int filas = ps.executeUpdate();
		
		ps.close();
		
	}
	
	public Usuario obtenerPorID(int id) throws SQLException {
		
		String sql = "SELECT * FROM usuarios WHERE id=?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id); // 1 de primero y unico parametro que le mando.
		
		// Para actualizar un registro lo primero es pedir los datos de ese registro.
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		Usuario u = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
		
		return u;
	}
	
	/**
	 * Método listar con prepardesStatement y retorna un Array List
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Usuario> listar() throws SQLException {
		
		String sql = "SELECT * FROM usuarios"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Usuario> ls = null;
		
		while(rs.next()) {			
			if (ls == null) {				
				ls = new ArrayList<Usuario>();				
			}			
			ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));			
		}
		return ls;		
		// retorna el Array Lsta
	}
	
	/**
	 * Método listar que retorna los usuarios con el filtrado de tipo. Se ha sobrecargado la función, es un copia y pega del listar general.
	 * @param tipo
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Usuario> listar(int tipoUsuario) throws SQLException {
		
		String sql = "SELECT * FROM usuarios WHERE permiso=?"; 
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1,  tipoUsuario); // aquí el parametro tipo que lo añado a la sentencia
		ResultSet rs = ps.executeQuery();
		
		ArrayList<Usuario> ls = null;
		
		while(rs.next()) {			
			if (ls == null) {				
				ls = new ArrayList<Usuario>();				
			}			
			ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));			
		}
		return ls;		
		// PASO 4. Retorna un Array List con los elementos que han cumplido la condición WHERE de la BD
	}
	
	/**
	 * Método para convertir los datos en un archivo formato JSON.
	 * @return
	 * @throws SQLException
	 */
	public String listarJson() throws SQLException {
		
		String json = "";
		
		Gson gson = new Gson();
		
		json = gson.toJson(this.listar());
		
		return json;
		
	}
	
	/**
	 * Se ha sobrecargado la función, esto es copiando y pegando pero metiendole el int.
	 * @param tipoUsuario
	 * @return
	 * @throws SQLException
	 */
	public String listarJson(int tipoUsuario) throws SQLException { // esto es llamado desde el servlet GestionUsuarios
		
		String json = "";
		
		Gson gson = new Gson();
		
		json = gson.toJson(this.listar(tipoUsuario)); // PASO 3. LLama al método listar(tipoUsuario) de arriba
		
		return json;
		// PASO 5. retorna el json al servlet y el servlet lo envía al out.print
	}


}
