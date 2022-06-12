package top.sunhanwu.cvehub.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class VolumnBindings {
    private Long id;

    private String innervolumn;

    private String outervolumn;

    private Long containerid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInnervolumn() {
        return innervolumn;
    }

    public void setInnervolumn(String innervolumn) {
        this.innervolumn = innervolumn == null ? null : innervolumn.trim();
    }

    public String getOutervolumn() {
        return outervolumn;
    }

    public void setOutervolumn(String outervolumn) {
        this.outervolumn = outervolumn == null ? null : outervolumn.trim();
    }

    public Long getContainerid() {
        return containerid;
    }

    public void setContainerid(Long containerid) {
        this.containerid = containerid;
    }
}