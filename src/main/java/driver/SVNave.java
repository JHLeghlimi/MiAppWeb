package driver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Nave;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

/**
 * Servlet implementation class SVNave
 */
@WebServlet(name = "SVNave", urlPatterns = "/SVNave")
@MultipartConfig
public class SVNave extends HttpServlet { // IMPORTANTE el 'extends' HttpServlet
	private static final long serialVersionUID = 1L;
       
	private String pathFiles = "C:\\Users\\Usuario\\eclipse-workspace\\MiAppWeb\\src\\main\\webapp\\fotosSubidas";
	private File uploads = new File(pathFiles);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SVNave() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Esto siempre es lo mismo, es algo robótico.
		// Lo que se pone entre comillas es el name que he puesto en html, si no es asi no me lo va a recepcionar.
		
		String nombre = request.getParameter("nombre");
		String clase = request.getParameter("clase");
		String matricula = request.getParameter("matricula");
		String descripcion = request.getParameter("descripcion");
		String estado = request.getParameter("estado");
		
		
		/* Para subir una foto
		 * - Decidir en que carpeta voy a guardar las fotos.
		 * - Recuperar el nombre del archivo que quiero subir.
		 * - Recuperar la ruta temporal donde el servidor guardó el archivo.
		 * - Decidir el nombre que va a tener el archivo(hacer objeto FILE).
		 * - Transmitir los datos del archivo.
		 * - Guardar el archivo.
		 */
		
		
		/*
		 *  ORIGEN -> DESTINO
		 *  LA info que debo obtener: ruta - datos - nombreArchivo
		 */
		
		//     Buffer (el camino o path)
		// [F] -------> [F]
		
		// Obtengo los datos de la foto del formulario (los datos del archivo, no la foto en sí).
		Part part = request.getPart("foto"); // datos binarios de la foto.
		
		// Obtengo la ruta/nombre del archivo (en caso ideal, el nombre hay que tratarlo para que no sea repetido, etc).
		Path path = Paths.get(part.getSubmittedFileName()); // esto me da el nombre de archivo original.
		String fileName = path.getFileName().toString();
		
		// Preparo el camino para enviar datos. Preparr el camino, el buffer.
		InputStream input = part.getInputStream();
		
		// Creo el contenedor.
		File file = new File (uploads, fileName);
		
		try {
			// Copio los datos del archivo dentro del contenedor utilizando el buffer creado input.
			Files.copy(input, file.toPath());
			
		} catch (Exception e) { // Try catch: si no lo ha copiado dame un mensaje de por qué. Gestion de errores.
			// TODO: handle exception
			
			// Lo siguiente es opcional, queda pro.
			System.out.println("Error al copiar archivo"); 
			// Mensaje en web al usuario:
			PrintWriter respuesta = response.getWriter();
			respuesta.print("<h4> Se ha producido un error, contacte con el administrador</h4>");
		} 
		
		// El siguiente paso es hacer el objeto nave con el nombre, la clase, la matrícula, etc.
		// Elijo el constructor que quiero usar:
		
		Nave n1 = new Nave(nombre, clase, matricula, descripcion, estado, fileName);
		System.out.println(n1.toString());
		
		// Aquí ya tengo que gestionar los errores, no puedo lanzar el throws.
		
		// Importante: aquí ya inserto.
		try {   
			n1.insertar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error en base de datos");
			
			// Quiero que este usuario una vez aquí se almacene en la BD.
		} 
		
		// int anosServicio = request.getParameter("anosServicio") esto no funcionaría al ser int --> lo convertimos en -->
		// int anosServicio = Integer.parseInt(request.getParameter("anosServicio"));
		// Hemos convertido el integer en un String con el parseInt, porque los datos viajan en un String aunque me pongan un numero.
		// Se llama parsearlo.
	}
	

}
