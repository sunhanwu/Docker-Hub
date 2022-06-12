package top.sunhanwu.cvehub.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

public class Tags {
    private Long id;

    private Long imageid;

    private String tag;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}