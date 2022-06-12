package top.sunhanwu.cvehub.model;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class AccountInfoKey {
    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}