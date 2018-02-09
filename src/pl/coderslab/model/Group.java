package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pl.coderslab.util.DbUtil;

public class Group {

	public static void main(String[] args) {
		
	}
	
	private int id;
	private String name;
	
	public Group(String name) {
		this.name = name;
	}
	
	public Group() {};
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
