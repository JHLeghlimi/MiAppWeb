package driver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.DaoUsuario;


/**
 * Servlet implementation class GestionUsuarios
 */
@WebServlet(name = "GestionUsuarios", urlPatterns = "/GestionUsuarios")
public class GestionUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionUsuarios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub				
		
		// Usando un mismo servlet para listar un formulario entero o solo por ID, de ahí las dos opciones.
		// Se podría usar Switch.
		
		PrintWriter out = response.getWriter();
		
		int opcion = Integer.parseInt(request.getParameter("op")); // del &op= en el html
		// int id = Integer.parseInt(request.getParameter("id")); 
		// cuidado con donde colocarlo, sino alguna de las opciones no recibirá el id. Se podría oprimizar para no ponerlo 3 veces en las 3 opciones.
		
		if(opcion == 2) { 
			// proceso logica edicion
			// esto hará una cosa y el else otra.
			int id = Integer.parseInt(request.getParameter("id")); 
			Usuario u = new Usuario(); 
			// Un objeto Usuario con el constructor vacío.
			try {
				u.obtenerPorID(id);
				out.print(u.dameJson());
				
				System.out.println(u.dameJson());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else if(opcion == 1) {
			DaoUsuario usuarios;
			try {
				usuarios = new DaoUsuario();
				out.print(usuarios.listarJson());
				
				// out.print(DaoUsuario.getInstance().listarJson());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else if(opcion == 3) {
			
			try {
				int id = Integer.parseInt(request.getParameter("id"));
				
				// Se podría usar singelton? Si, investigar
				
				DaoUsuario usuarios = new DaoUsuario(); //esto es lo mismo que lo de la opcion 1 pero en una sola línea
				usuarios.borrar(id);
				out.print(usuarios.listarJson());
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // esto es lo mismo que lo de la opcion 1 pero en una sola línea				
		}else if(opcion == 4) { 
			
			int tipoUsuario = Integer.parseInt(request.getParameter("tipoUsuario")); // El name que le hemos dado en el <select name= tipoUsuario>
			System.out.println("Entro en filtro por tipo" + tipoUsuario); // esto es un truco para comprobar si entra aquí
			
			try {
				DaoUsuario usuarios = new DaoUsuario(); //  PASO 2. llamada a DaoUsuario
				out.print(usuarios.listarJson(tipoUsuario)); // PASO 6. Recibimos el json del DaoUsuario y desde aquí vamos al listarUsuarios.html
				
				// Se podría usar singelton? Si, investigar
				// DaoUsuario.getInstance().listarJson(tipoUsuario);
				// out.print(tipoUsuario);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
				
		
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// getParameter siempre devuelve String, si queremos numero tenemos que parsear.
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String tlf = request.getParameter("tlf");
		int permiso = Integer.parseInt(request.getParameter("permiso"));
		String id = request.getParameter("id");
		
		Usuario u = new Usuario(nombre, email, tlf, permiso);
		
		try {
			
			if (id == "") {
		
				DaoUsuario.getInstance().insertar(u);
				
				//DaoUsuario dau = new DaoUsuario();
				//dao.insertar(u);
				
				//u.insertar();

			} else {
				
				int idInt = Integer.parseInt(id); // pasame a a Integer el numero que has recibido
				u.setId(idInt);
				u.actualizar();
				// u.actualizar(Integer.parseInt(id)); manera abreviada de las 3 lineas de arriba
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Para que al insertar con altaUsuario, el servlet no se quede muerto.
		 * Lo va a insertar y me redirige a la página de la lista.
		 * Así el usuario ve lo insertado en tiempo real.
		 * Básicamente es decirle, vete a este sitio.
		 */
		response.sendRedirect("listarUsuarios.html");

	}

}
