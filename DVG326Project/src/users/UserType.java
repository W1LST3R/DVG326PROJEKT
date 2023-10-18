package users;

public interface UserType {
	public void setUsername(String username);
	public String getUsername();
	public void setPassword(String password);
	public String getPassword(String masterkey);
}
