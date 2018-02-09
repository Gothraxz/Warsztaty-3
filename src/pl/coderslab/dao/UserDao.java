package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Group;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

public class UserDao extends User{
	
	User us = new User();
	private int id = us.getId();
	private String username = us.getUsername();
	private String password = us.getPassword();
	private String email = us.getEmail();
	private Group group = us.getGroup();


	public static void createTable() {
		String query = "CREATE TABLE Users(\n" + 
				"	id BIGINT AUTO_INCREMENT,\n" + 
				"    username VARCHAR(255),\n" + 
				"    email VARCHAR(255) UNIQUE,\n" + 
				"    password VARCHAR(245),\n" + 
				"    group_id INT NOT NULL,\n" + 
				"    PRIMARY KEY(id),\n" + 
				"    FOREIGN KEY(group_id) REFERENCES Groups(id)\n" + 
				");";
		
		try (Connection conn = DbUtil.getConn();
				PreparedStatement stm = conn.prepareStatement(query)){
			stm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Nie można utworzyć tabeli Users.");
		}
	}
	
	public void saveToDB(User us) {
		if	(us.getId() == 0) {
			String sql = "INSERT INTO Users(username, email, password, group_id) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns)) {
				preparedStatement.setString(1, us.getUsername());
				preparedStatement.setString(2, us.getEmail());
				preparedStatement.setString(3, us.getPassword());
				preparedStatement.setInt(4, us.getGroup().getId());
				preparedStatement.executeUpdate();
				try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
					if (rs.next())	{
						us.setId(rs.getInt(1));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "UPDATE Users SET username = ?, email = ?, password = ?, group_id = ? where id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setString(1, us.getUsername());
				preparedStatement.setString(2, us.getEmail());
				preparedStatement.setString(3, us.getPassword());
				preparedStatement.setInt(4, us.getGroup().getId());
				preparedStatement.setInt(5, us.getId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static User loadById(int id) {
		String sql = "SELECT * FROM Users WHERE id = ?";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					User loadedUser = new User();
					loadedUser.setId(resultSet.getInt("id"));
					loadedUser.setUsername(resultSet.getString("username"));
					loadedUser.setEmail(resultSet.getString("email"));
					loadedUser.setPassword(resultSet.getString("password"));
					loadedUser.setGroup(GroupDao.loadById(resultSet.getInt("group_id")));
					return loadedUser;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public User[] loadAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM Users";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					User loadedUser = new User();
					loadedUser.setId(resultSet.getInt("id"));
					loadedUser.setUsername(resultSet.getString("username"));
					loadedUser.setPassword(resultSet.getString("password"));
					loadedUser.setEmail(resultSet.getString("email"));
					loadedUser.setGroup(GroupDao.loadById(resultSet.getInt("group_id")));
					users.add(loadedUser);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User[] uArray = new User[users.size()]; 
		uArray = users.toArray(uArray);
		return uArray;
	}
	
	public void delete() {
		if (this.id != 0) {
			String sql = "DELETE FROM Users WHERE id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setInt(1, this.id);
				preparedStatement.executeUpdate();
				this.id = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static User[] loadAllByGrupId(int id) {
		ArrayList<User> solution = new	ArrayList<>();
		String sql = "SELECT * FROM Users WHERE group_id=?;";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					User loadedUser = new User();
					loadedUser = UserDao.loadById(resultSet.getInt("id"));
					solution.add(loadedUser);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		User[] uArray = new User[solution.size()];	
		uArray = solution.toArray(uArray);
		return uArray;
	}
}
