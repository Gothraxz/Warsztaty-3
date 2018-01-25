package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;

import pl.coderslab.model.Group;
import pl.coderslab.util.Connect;
import pl.coderslab.util.DbUtil;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try (Connection conn = DbUtil.getConn()){
			
			Group group = Group.loadById(conn, 1);
			System.out.println(group.getName());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
