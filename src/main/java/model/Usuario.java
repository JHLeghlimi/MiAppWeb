package model;

import java.sql.SQLException;

import com.google.gson.Gson;

import dao.DaoUsuario;

public class Usuario {

	private int id;
	private String nombre;
	private String email;
	private String tlf;
	private int permiso; // importante el permiso
	
	// Se crean los constructores que vaya necesitando.
	
	/**
	 * Constructor vacío.
	 */
	public Usuario() {
		
	}
	
	/**
	 * Constructor para <strong>creación del objeto</strong> desde el formulario.
	 * @param nombre Atributo solo texto ....
	 * @param email Atributo solo texto ....
	 * @param tlf ...
	 * @param permiso
	 */
	public Usuario(String nombre, String email, String tlf, int permiso) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.tlf = tlf;
		this.permiso = permiso;
	}

	/**Constructor con todos los campos incluido el id.
	 * 
	 * @param id
	 * @param nombre
	 * @param email
	 * @param tlf
	 * @param permiso
	 */
	public Usuario(int id, String nombre, String email, String tlf, int permiso) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tlf = tlf;
		this.permiso = permiso;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTlf() {
		return tlf;
	}

	public void setTlf(String tlf) {
		this.tlf = tlf;
	}

	public int getPermiso() {
		return permiso;
	}

	public void setPermiso(int permiso) {
		this.permiso = permiso;
	}
	/**
	 * 
	 * @throws SQLException
	 */
	public void insertar() throws SQLException {
		
		DaoUsuario.getInstance().insertar(this);
		
		// DaoUsuario dao = new DaoUsuario ();
		// dao.insertar(this);
		
	}
	
	public void actualizar() throws SQLException {
		
		DaoUsuario.getInstance().actualizar(this);
		
		// DaoUsuario dao = new DaoUsuario();
		// dao.actualizar(this);
		
	}
	
	public void borrar(int id) throws SQLException {
		
		DaoUsuario.getInstance().borrar(id);
		
		// DaoUsuario dao = new DaoUsuario();
		// dao.borrar(id);
		
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void obtenerPorID(int id) throws SQLException {
		
		DaoUsuario dao = new DaoUsuario ();
		Usuario aux = dao.obtenerPorID(id); // Genero un objeto auxiliar (aux) y llamo al dao y guardo los datos en aux.
		
		this.setId(aux.getId());
		this.setNombre(aux.getNombre());
		this.setEmail(aux.getEmail());
		this.setTlf(aux.getTlf());
		this.setPermiso(aux.getPermiso());
		
		//DaoUsuario.getInstance().obtenerPorID(id);
	
	}	
	
		
	public String dameJson() {
		
		String json = "";
		
		Gson gson = new Gson();
		
		json = gson.toJson(this);
		
		return json;
	}
	
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", tlf=" + tlf + ", permiso=" + permiso
				+ "]";
	}
	
	
	
	
}
