package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

public class ExerciseDao extends Exercise {

	Exercise ex = new Exercise();

	private int id = ex.getId();
	private String title = ex.getTitle();
	private String description = ex.getDescription();
	
	private int solutionId = ex.getSolutionId();
	private String solutionCreated = ex.getSolutionCreated();
	private String solutionUpdated = ex.getSolutionUpdated();
	private String solutionDescription = ex.getSolutionDescription();


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

	public void saveToDB(Exercise ex) {
		if	(ex.getId() == 0) {
			String sql = "INSERT INTO Exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns)) {
				preparedStatement.setString(1, ex.getTitle());
				preparedStatement.setString(2, ex.getDescription());
				preparedStatement.executeUpdate();

				try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
					if (rs.next())	{
						ex.setId(rs.getInt(1));
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
				preparedStatement.setString(1, ex.getTitle());
				preparedStatement.setString(2, ex.getDescription());
				preparedStatement.setInt(3, ex.getId());
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
					loadedExercise.setId(resultSet.getInt("id"));
					loadedExercise.setTitle(resultSet.getString("title"));
					loadedExercise.setDescription(resultSet.getString("description"));
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
					loadedExercise.setId(resultSet.getInt("id"));
					loadedExercise.setTitle(resultSet.getString("title"));
					loadedExercise.setDescription(resultSet.getString("description"));
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
					loadedSolutions.setExercise(ExerciseDao.loadById(resultSet.getInt("exercise_id")));
					loadedSolutions.setUser(UserDao.loadById(resultSet.getInt("users_id")));
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
