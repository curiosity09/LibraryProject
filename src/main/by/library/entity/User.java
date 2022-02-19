package main.by.library.entity;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String role;
    private UserData userData;
    private boolean isBanned;
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_LIBRARIAN = "librarian";
    public static final String ROLE_USER = "user";

    public User(int id, String username, String password, String role, UserData userData, boolean isBanned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.userData = userData;
        this.isBanned = isBanned;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String username;
        private String password;
        private String role;
        private UserData userData;
        private boolean isBanned;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder userData(UserData userData) {
            this.userData = userData;
            return this;
        }

        public Builder isBanned(boolean isBanned) {
            this.isBanned = isBanned;
            return this;
        }

        public User build(){
            return new User(id,username,password,role,userData,isBanned);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isBanned != user.isBanned) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != null ? !role.equals(user.role) : user.role != null) return false;
        return userData != null ? userData.equals(user.userData) : user.userData == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (userData != null ? userData.hashCode() : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", userData=" + userData +
                ", isBanned=" + isBanned +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
