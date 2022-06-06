package top.sunhanwu.cvehub.model;

public class ImageInfo {
    private Long id;

    private Long createtime;

    private String hashid;

    private String parentid;

    private String tags;

    private Long size;

    private Long virtualsize;

    private Integer IsShow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public String getHashid() {
        return hashid;
    }

    public void setHashid(String hashid) {
        this.hashid = hashid == null ? null : hashid.trim();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getVirtualsize() {
        return virtualsize;
    }

    public void setVirtualsize(Long virtualsize) {
        this.virtualsize = virtualsize;
    }

    public Integer getShow() {
        return IsShow;
    }

    public void setShow(Integer show) {
        this.IsShow = show;
    }
}