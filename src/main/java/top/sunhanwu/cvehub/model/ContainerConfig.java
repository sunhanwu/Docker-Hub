package top.sunhanwu.cvehub.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class ContainerConfig {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}