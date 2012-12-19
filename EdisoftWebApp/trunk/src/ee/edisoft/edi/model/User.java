package ee.edisoft.edi.model;

import java.io.Serializable;

/**
 * User is a JavaBean class, that models
 * a user in the business domain.
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String salt;
	
	public User() {
		
	}

	public User(String firstName, String lastName,
			String email, String password, String salt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.salt = salt;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		
		if (id != other.id) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName +
				", email=" + email + ", getFirstName()=" + getFirstName() +
				", getLastName()=" + getLastName() + ", getEmail()=" + getEmail() + "]";
	}

}
