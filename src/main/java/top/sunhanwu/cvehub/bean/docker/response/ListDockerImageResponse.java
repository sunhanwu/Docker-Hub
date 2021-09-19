package top.sunhanwu.cvehub.bean.docker.response;

import com.github.dockerjava.api.model.Image;

import java.util.List;

public class ListDockerImageResponse {

    /**
     * docker宿主机ip
     */
    private String host;

    private List<Image> dockerImageInfoList;

    private String msg;

    private Integer code;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<Image> getDockerImageInfoList() {
        return dockerImageInfoList;
    }

    public void setDockerImageInfoList(List<Image> dockerImageInfoList) {
        this.dockerImageInfoList = dockerImageInfoList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DockerLsResponse{" +
                "host='" + host + '\'' +
                ", dockerStatusInfoList=" + dockerImageInfoList +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
