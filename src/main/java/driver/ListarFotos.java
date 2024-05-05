package driver;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Nave;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

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
		
		// RECORDAR: request es entrada, response es salida.
		
		try {
			// Voy a crear un objeto PrintWriter que me va a servir para enviar cosas.
			PrintWriter out = response.getWriter();
			
			DaoNave dao = new DaoNave(); // Llamada a DaoNave
			
			String resultados = dao.listarJson(); // Aquí ya tengo un String con todos los datos.
			
			System.out.println(resultados); // Deberían aparecer todos los datos.
			
			out.print(resultados);
			
			// Esto hará que el back responda correctamente.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
