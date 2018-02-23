package org.sc.oauth2.resource.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sc_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;
	private String email;
	private String dateOfBirth;
	private String placeOfBirth;

	public User() {
		super();
	}

	public User(String username, String email, String dateOfBirth, String placeOfBirth) {
		super();
		this.username = username;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.placeOfBirth = placeOfBirth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", dateOfBirth=" + dateOfBirth
				+ ", placeOfBirth=" + placeOfBirth + "]";
	}
}
