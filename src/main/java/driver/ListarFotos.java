package driver;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


import dao.DaoNave;

/**
 * Servlet implementation class ListarFotos
 */
public class ListarFotos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListarFotos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		 * RECORDAR: request -> entrada, response -> salida.
		 * Cuando reciba una llamada por Get, llame  a la BD genere el json y me lo devuelva.
		 * 
		 * Generar un objeto String con respuesta json que me metas lo que te devuelve el metodo DaoNave
		 * pero en este caso llamo a listarJson.
		 */
		try {
			/*
			 * Voy a crear un objeto PrintWriter que me va a servir para enviar cosas.
			 * PRINTWRITER CON SINGELTON Y SIN SINGELTON
			 */
			
			// CON SINGELTON
			String respuestaJSON;
			respuestaJSON = DaoNave.getInstance().listarJson();
			System.out.println(respuestaJSON); 
			// Impresion por consola para comprobar que funciona antes de mandarlo a cliente por json.
			
			PrintWriter out = response.getWriter();
			
			out.print(respuestaJSON);
			/*
			 * De esta manera ya el cliente recibe los datos por web, siguiente paso es que esos datos 
			 * los reciba Javascript y los procese.
			 */
			
			// OTRA MANERA DE PRINTWRITER
			// 
			 
			/* SIN SINGELTON:

			PrintWriter out = response.getWriter();
			
			DaoNave dao = new DaoNave(); // Llamada a DaoNave
			
			String respuestaJSON = dao.listarJson(); // Aquí ya tengo un String con todos los datos.
			
			System.out.println(respuestaJSON); // Deberían aparecer todos los datos.
			
			out.print(respuestaJSON);
			
			*/

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		/* Para la prueba con listado.html del video de vimeo.
		try {
			
			ArrayList<Nave> listaEnObjetos = DaoNave.getInstance().listar(); // Con singelton.
			
				for (Nave a : listaEnObjetos) {
					
					System.out.println(a.toString());
					
				}
			
			// Esto hará que el back responda correctamente.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*
		try {
			
			// CON SINGELTON
			ArrayList<Nave> listadoNaves = DaoNave.getInstance().listar(); 
			
			for (Nave a : listadoNaves) {
				
				System.out.println(a.toString());
				
			}
			
			/* SIN SINGELTON
			DaoNave lista;
			
			PrintWriter out = response.getWriter();
			lista = new DaoNave();
			ArrayList<Nave> listadoNaves = lista.listar();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}
