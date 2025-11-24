package model;

/**
 * Admin account.
 */
public class Admin extends Account {
    public Admin(String username, String password, String name) { super(username,password,name); }
    @Override public String getRole() { return "ADMIN"; }
}
