package top.sunhanwu.cvehub.bean.docker.requests;

public class RunContainerRequest {
    private int imageId;

    private String name;

    private String imageName;

    private String imageTag;

    private ContainerSetting containerSetting;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public ContainerSetting getContainerSetting() {
        return containerSetting;
    }

    public void setContainerSetting(ContainerSetting containerSetting) {
        this.containerSetting = containerSetting;
    }
}
