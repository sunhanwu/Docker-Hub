package top.sunhanwu.cvehub.bean.docker.requests;

public class RunContainerRequest {
    private String name;

    private String imageName;

    private String imageTag;

    private ContainerSetting containerSetting;
}
