package top.sunhanwu.cvehub.bean.docker.requests;

import java.util.HashMap;
import java.util.List;

public class ContainerSetting {

    /**
     * 端口映射
     */
    private List<HashMap<String, String>> ports;

    /**
     * 磁盘映射
     */
    private List<HashMap<String, String>> volumns;

    /**
     * 环境变量
     */
    private List<HashMap<String, String>> envs;

    private List<HashMap<String, String>> others;

    public List<HashMap<String, String>> getPorts() {
        return ports;
    }

    public void setPorts(List<HashMap<String, String>> ports) {
        this.ports = ports;
    }

    public List<HashMap<String, String>> getVolumns() {
        return volumns;
    }

    public void setVolumns(List<HashMap<String, String>> volumns) {
        this.volumns = volumns;
    }

    public List<HashMap<String, String>> getEnvs() {
        return envs;
    }

    public void setEnvs(List<HashMap<String, String>> envs) {
        this.envs = envs;
    }

    public List<HashMap<String, String>> getOthers() {
        return others;
    }

    public void setOthers(List<HashMap<String, String>> others) {
        this.others = others;
    }
}
