package dto;
public class UserDTO {

    private Integer idUser;
    private String name;
    private String username;
    private String password;
    private String email;
    private RoleDTO role;
    private String msgMail;


    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsgMail() {
        return msgMail;
    }

    public void setMsgMail(String msgMail) {
        this.msgMail = msgMail;
    }
}
