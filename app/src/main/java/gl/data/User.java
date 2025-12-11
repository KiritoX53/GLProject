package gl.data;

public class User {
    private String username;
    private String password;
    private Data data;

    public User(String username, String password, Data data) {
        this.username = username;
        this.password = password;
        this.data = data;
    }

    public User(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPassword() {
        return password;
    }
    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}