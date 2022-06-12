package top.sunhanwu.cvehub.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class Arch {
    private Long id;

    private Long imageid;

    private String arch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageid() {
        return imageid;
    }

    public void setImageid(Long imageid) {
        this.imageid = imageid;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch == null ? null : arch.trim();
    }
}