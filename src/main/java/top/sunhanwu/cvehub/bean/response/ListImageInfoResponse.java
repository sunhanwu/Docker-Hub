package top.sunhanwu.cvehub.bean.response;

import top.sunhanwu.cvehub.model.ImageInfo;

import java.util.List;

public class ListImageInfoResponse {
    private String msg;

    private List<ImageInfo> imageInfos;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ImageInfo> getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(List<ImageInfo> imageInfos) {
        this.imageInfos = imageInfos;
    }
}
