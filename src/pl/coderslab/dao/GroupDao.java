package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.model.Group;
import pl.coderslab.util.DbUtil;

public class GroupDao extends Group{

	Group gr = new Group();

	private int id = gr.getId();
	private String name = gr.getName();
	

	public static void createTable() {
		String query = "CREATE TABLE Groups(\n" + 
				"	id INT AUTO_INCREMENT,\n" + 
				"    name VARCHAR(255) NOT NULL,\n" + 
				"    PRIMARY KEY(id)\n" + 
				");";
		
		try (Connection conn = DbUtil.getConn();
				PreparedStatement stm = conn.prepareStatement(query)) {
			stm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Nie można utworzyć tabeli Groups");
		}
	}
	
	public void saveToDB(Group gr) {
		if	(gr.getId() == 0) {
			String sql = "INSERT INTO Groups(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns)) {
				preparedStatement.setString(1, gr.getName());
				preparedStatement.executeUpdate();
				
				try (ResultSet rs = preparedStatement.getGeneratedKeys();) {
					if (rs.next())	{
						gr.setId(rs.getInt(1));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "UPDATE Groups SET name = ? where id = ?";
			try (Connection conn = DbUtil.getConn();
					PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.setString(1, gr.getName());
				preparedStatement.setInt(2, gr.getId());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Group loadById(int id) {
		String sql = "SELECT * FROM Groups WHERE id = ?";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					Group loadedGroup = new Group();
					loadedGroup.setId(resultSet.getInt("id"));
					loadedGroup.setName(resultSet.getString("name"));
					return loadedGroup;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static public Group[] loadAllGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		String sql = "SELECT * FROM Groups";
		try (Connection conn = DbUtil.getConn();
				PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Group loadedGroup = new Group();
					loadedGroup.setId(resultSet.getInt("id"));
					loadedGroup.setName(resultSet.getString("name"));
					groups.add(loadedGroup);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Group[] gArray = new Group[groups.size()]; 
		gArray = groups.toArray(gArray);
		return gArray;
	}
	
	public void delete() {
		if (this.id != 0) {
			String sql = "DELETE FROM Groups WHERE id = ?";
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
	

	
}
