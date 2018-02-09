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
	
	public void setId(int id) {
		this.id = id;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public void setSolutionCreated(String solutionCreated) {
		this.solutionCreated = solutionCreated;
	}

	public void setSolutionUpdated(String solutionUpdated) {
		this.solutionUpdated = solutionUpdated;
	}

	public void setSolutionDescription(String solutionDescription) {
		this.solutionDescription = solutionDescription;
	}

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
}
