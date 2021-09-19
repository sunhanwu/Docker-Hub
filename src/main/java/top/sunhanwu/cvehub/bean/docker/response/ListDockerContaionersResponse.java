package top.sunhanwu.cvehub.bean.docker.response;

import com.github.dockerjava.api.model.Container;

import java.util.List;

public class ListDockerContaionersResponse {

    private String mgs;

    private Integer code;

    private List<Container> containerList;

    public String getMgs() {
        return mgs;
    }

    public void setMgs(String mgs) {
        this.mgs = mgs;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(List<Container> containerList) {
        this.containerList = containerList;
    }

    @Override
    public String toString() {
        return "ListDockerContaionersResponse{" +
                "mgs='" + mgs + '\'' +
                ", code=" + code +
                ", containerList=" + containerList +
                '}';
    }
}
