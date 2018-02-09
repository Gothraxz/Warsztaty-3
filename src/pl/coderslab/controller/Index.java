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

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

/**
 * Servlet implementation class Index
 */
@WebServlet("/")
//przetestować /* oraz puste string
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
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
		
		int numberSolutions = Integer.parseInt(request.getServletContext()
				.getInitParameter("number-solutions"));
		
		Solution[] solutionsLimitedList = SolutionDao.loadAllSolutions(numberSolutions);
		
		
		session.setAttribute("solutionsLimitedList", solutionsLimitedList);
		request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
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
	
	private User[] getUsers() {
		
		if (UserDao.loadAllUsers() != null) {
			return UserDao.loadAllUsers();
		}
		return new User[0];
	}
	
	private Solution[] getSolutions(int i) {
		
		if (SolutionDao.loadAllSolutions(i) != null) {
			return SolutionDao.loadAllSolutions(i);
		}
		return new Solution[0];
	}

}
