package top.sunhanwu.cvehub.services.core;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DockerService {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<Image> listImages(){
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://" + env.getProperty("docker.host") + ":" + env.getProperty("docker.port")).build();

        List<Image> imageList = null;
        try {
            imageList = dockerClient.listImagesCmd().exec();
        }
        catch (Exception e){
            logger.error("DockerService: dockerClient listImagesCmd exec error, err msg: " + e.getMessage());
            return null;
        }

        return imageList;
    }

    public List<Container> listContainers(){
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://" + env.getProperty("docker.host") + ":" + env.getProperty("docker.port")).build();

        List<Container> containerList = null;
        try {
            containerList = dockerClient.listContainersCmd().exec();
        }
        catch (Exception e){
            logger.error("DockerService: dockerClient listContainers exec error, err msg: " + e.getMessage());
            return null;
        }

        return containerList;
    }
}
