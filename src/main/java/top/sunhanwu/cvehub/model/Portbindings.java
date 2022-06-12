package top.sunhanwu.cvehub.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class Portbindings {
    private Long id;

    private Integer innerport;

    private Integer outerport;

    private Long containerid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInnerport() {
        return innerport;
    }

    public void setInnerport(Integer innerport) {
        this.innerport = innerport;
    }

    public Integer getOuterport() {
        return outerport;
    }

    public void setOuterport(Integer outerport) {
        this.outerport = outerport;
    }

    public Long getContainerid() {
        return containerid;
    }

    public void setContainerid(Long containerid) {
        this.containerid = containerid;
    }
}