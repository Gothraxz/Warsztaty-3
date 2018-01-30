package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import pl.coderslab.util.DbUtil;

public class Exercise {

	public static void main(String[] args) {

	}
	
	private int id;
	private String title;
	private String description;
	// zmienne na potrzeby pobrania rozwiązań
	private int solutionId;
	private String solutionCreated;
	private String solutionUpdated;
	private String solutionDescription;
	
	public Exercise(String title) {
		this.title = title;
		this.description = "N/A";
	}
	
	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public Exercise() {};
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	// gettery na potrzeby pobrania rozwiązań
	public int getSolutionId() {
		return solutionId;
	}

	public String getSolutionCreated() {
		return solutionCreated;
	}

	public String getSolutionUpdated() {
		return solutionUpdated;
	}

	public String getSolutionDescription() {
		return solutionDescription;
	}

	public static void createTable() {
		String query = "CREATE TABLE Exercise(\n" + 
				"	id INT AUTO_INCREMENT,\n" + 
				"    title VARCHAR(255) NOT NULL,\n" + 
				"    description TEXT,\n" + 
				"    PRIMARY KEY(id)\n" + 
				");";
		
		try (Connection conn = DbUtil.getConn();
				PreparedStatement stm = conn.prepareStatement(query)) {
			stm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Nie można utworzyć tabeli Exercise");
		}
	}
	
	public void saveToDB() {
		if	(this.id == 0) {
			String sql = "INSERT INTO Exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns)) {
				preparedStatement.setString(1, this.title);
				preparedStatement.setString(2, this.description);
				preparedStatement.executeUpdate();
				
				try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
					if (rs.next())	{
						this.id	= rs.getInt(1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "UPDATE Exercise SET title = ?, description = ? where id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setString(1, this.title);
				preparedStatement.setString(2, this.description);
				preparedStatement.setInt(3, this.id);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Exercise loadById(int id) {
		String sql = "SELECT * FROM Exercise WHERE id = ?";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Exercise loadedExercise = new Exercise();
					loadedExercise.id = resultSet.getInt("id");
					loadedExercise.title = resultSet.getString("title");
					loadedExercise.description = resultSet.getString("description");
					return loadedExercise;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public Exercise[] loadAllExercises() {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM Exercise";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Exercise loadedExercise = new Exercise();
					loadedExercise.id = resultSet.getInt("id");
					loadedExercise.title = resultSet.getString("title");
					loadedExercise.description = resultSet.getString("description");
					exercises.add(loadedExercise);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Exercise[] eArray = new Exercise[exercises.size()]; 
		eArray = exercises.toArray(eArray);
		return eArray;
	}
	
	public void delete() {
		if (this.id != 0) {
			String sql = "DELETE FROM Exercise WHERE id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setInt(1, this.id);
				preparedStatement.executeUpdate();
				this.id = 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static public Solution[] loadAllByUserId(int id) {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM Solution WHERE users_id = ?";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Solution loadedSolutions = new Solution();
					loadedSolutions.setId(resultSet.getInt("id"));
					loadedSolutions.setCreated(resultSet.getString("created"));
					loadedSolutions.setUpdated(resultSet.getString("updated"));
					loadedSolutions.setDescription(resultSet.getString("description"));
					loadedSolutions.setExercise(Exercise.loadById(resultSet.getInt("exercise_id")));
					loadedSolutions.setUser(User.loadById(resultSet.getInt("users_id")));
					solutions.add(loadedSolutions);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Solution[] sArray = new Solution[solutions.size()]; 
		sArray = solutions.toArray(sArray);
		return sArray;
	}
	
}
