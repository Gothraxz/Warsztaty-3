package pl.coderslab.panel;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Group;
import pl.coderslab.model.User;

/**
 * Servlet implementation class ExerciseAdmin
 */
@WebServlet("/ExerciseAdmin")
public class ExerciseAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExerciseAdmin() {
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
		
		Exercise[] exercises = ExerciseDao.loadAllExercises();

		if (exercises == null ) {
			writer
			.append("There aren't any exercises!<br>")
			.append("<a href='" + request.getContextPath() 
			+ "' link='red'>Return to main site</a>");
		} else {
			session.setAttribute("exercises", exercises);
			request.getRequestDispatcher("/WEB-INF/panel/ExerciseList.jsp")
			.forward(request, response);
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
		
		String exerciseId = (String) session.getAttribute("exerciseId");
		String exerciseTitle = request.getParameter("exerciseTitle");
		String exerciseDescription = request.getParameter("exerciseDescription");
		
		int id = Integer.parseInt(exerciseId);
		
		Exercise exercise;
		ExerciseDao ed = new ExerciseDao();
		if (id == 0) {
			exercise = new Exercise(exerciseTitle, exerciseDescription);
			exercise.setTitle(exerciseTitle);
			exercise.setDescription(exerciseDescription);
			ed.saveToDB(exercise);
			doGet(request, response);
		} else {
			exercise = ExerciseDao.loadById(id);
			
			if (exerciseTitle != null && exerciseTitle != "") {
				exercise.setTitle(exerciseTitle);		
			}
			
			if (exerciseDescription != null && exerciseDescription != "") {
				exercise.setDescription(exerciseDescription);
			}
		
			ed.saveToDB(exercise);
			doGet(request, response);
		}

	}

}
