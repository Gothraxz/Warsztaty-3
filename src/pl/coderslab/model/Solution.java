package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pl.coderslab.util.DbUtil;

public class Solution {

	public static void main(String[] args) {

	}
	
	private int id;
	private String created;
	private String updated;
	private String description;
	private Exercise exercise;
	private User user;
	private DateFormat dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public Solution(String description, Exercise exercise, User user) {
		this.description = description;
		this.exercise = exercise;
		this.user = user;
	}
	
	public Solution() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public DateFormat getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateFormat dateTime) {
		this.dateTime = dateTime;
	}
}
