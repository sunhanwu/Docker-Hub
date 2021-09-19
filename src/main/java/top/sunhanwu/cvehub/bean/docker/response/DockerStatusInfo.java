package top.sunhanwu.cvehub.bean.docker.response;

public class DockerStatusInfo {

    private String containerId;

    private String image;

    private String command;

    private String created;

    private String status;

    private String ports;

    private String names;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "DockerStatusInfo{" +
                "containerId='" + containerId + '\'' +
                ", image='" + image + '\'' +
                ", command='" + command + '\'' +
                ", created='" + created + '\'' +
                ", status='" + status + '\'' +
                ", ports='" + ports + '\'' +
                ", names='" + names + '\'' +
                '}';
    }
}
