package pl.coderslab.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.model.Solution;
import pl.coderslab.util.DbUtil;
import pl.coderslab.util.Utils;

/**
 * Servlet implementation class SolutionDetails
 */
@WebServlet("/SolutionDetails")
public class SolutionDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolutionDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Writer writer = response.getWriter();
		
		String idString = request.getParameter("id");
		Solution[] solutions = (Solution[]) session.getAttribute("solutionsLimitedList");
		int id = 0;
		
		if (Utils.isNumeric(idString)) {
			id = Integer.parseInt(idString);
			
			if (Utils.solutionExists(solutions, id)) {
				try {
					Solution solution = Solution.loadById(DbUtil.getConn(), id);
					session.setAttribute("solution", solution);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/SolutionDetails.jsp").forward(request, response);
				
			} else {
				writer
				.append("Id doesn't exist!<br>")
				.append("<a href='" + request.getContextPath() 
				+ "' link='red'>Return to main site</a>");
			}
		} else {
			writer
			.append("Id must be a number!<br>")
			.append("<a href='" + request.getContextPath() 
			+ "' link='red'>Return to main site</a><br>");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		Writer writer = response.getWriter();
		
		
	}
	
}
