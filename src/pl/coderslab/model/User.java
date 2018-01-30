package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import pl.coderslab.util.DbUtil;

public class User {

	public static void main(String[] args) {
		
	}
	
	private int id;
	private String username;
	private String password;
	private String email;
	private Group group;
	
	public User (String username, String email, Group group, String password) {
		this.username = username;
		this.email = email;
		this.group = group;
		setPassword(password);
	}
	
	public User() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	
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
	
	public void saveToDB() {
		if	(this.id == 0) {
			String sql = "INSERT INTO Users(username, email, password, group_id) VALUES (?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns)) {
				preparedStatement.setString(1, this.username);
				preparedStatement.setString(2, this.email);
				preparedStatement.setString(3, this.password);
				preparedStatement.setInt(4, this.group.getId());
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
			String sql = "UPDATE Users SET username = ?, email = ?, password = ?, group_id = ? where id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setString(1, this.username);
				preparedStatement.setString(2, this.email);
				preparedStatement.setString(3, this.password);
				preparedStatement.setInt(4, this.group.getId());
				preparedStatement.setInt(5, this.id);
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
					loadedUser.id = resultSet.getInt("id");
					loadedUser.username = resultSet.getString("username");
					loadedUser.email = resultSet.getString("email");
					loadedUser.password = resultSet.getString("password");
					loadedUser.group = Group.loadById(resultSet.getInt("group_id"));
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
					loadedUser.id = resultSet.getInt("id");
					loadedUser.username = resultSet.getString("username");
					loadedUser.password = resultSet.getString("password");
					loadedUser.email = resultSet.getString("email");
					loadedUser.group = Group.loadById(resultSet.getInt("group_id"));
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
					loadedUser = User.loadById(resultSet.getInt("id"));
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
