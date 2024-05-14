package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
