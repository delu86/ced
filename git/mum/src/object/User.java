package object;

import java.sql.Timestamp;

public class User {

	private String user;
	private String profile;
	private Timestamp lastAccess;
	
	public User(String user, String profile,Timestamp lastAccess) {
		this.user=user;
		this.profile=profile;
		this.lastAccess=lastAccess;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Timestamp getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Timestamp lastAccess) {
		this.lastAccess = lastAccess;
	}

}
