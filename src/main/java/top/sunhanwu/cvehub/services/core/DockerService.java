package top.sunhanwu.cvehub.services.core;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.glassfish.jersey.apache.connector.ApacheHttpClientBuilderConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import top.sunhanwu.cvehub.bean.response.BaseResponse;
import top.sunhanwu.cvehub.dao.ImageInfoMapper;
import top.sunhanwu.cvehub.model.ImageInfo;

import java.net.http.HttpClient;
import java.util.List;

@Component
public class DockerService {

    @Autowired
    private Environment env;

    @Autowired
    private ImageInfoMapper imageInfoMapper;

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

        /**
         * add iamge infos to database
         */
        for(int i=0;i<imageList.size();i++){
            ImageInfo temImageInfo = null;
            try{
                temImageInfo = imageInfoMapper.selectByHashId(imageList.get(i).getId());
            }
            catch (Exception e){
                    logger.error("DockerService: select imageInfo by hashId error, err msg: " + e.getMessage());
            }
            if (temImageInfo != null)
                continue;
            ImageInfo newImageInfo = new ImageInfo();
            newImageInfo.setCreatetime(imageList.get(i).getCreated());
            newImageInfo.setHashid(imageList.get(i).getId());
            newImageInfo.setParentid(imageList.get(i).getParentId());
            newImageInfo.setTags(imageList.get(i).getRepoTags()[0]);
            newImageInfo.setSize(imageList.get(i).getSize());
            newImageInfo.setVirtualsize(imageList.get(i).getVirtualSize());
            newImageInfo.setShow(0);
            try {
                imageInfoMapper.insertSelective(newImageInfo);
            }
            catch (Exception e){
                logger.error("DockerService: insert imageinfo into database error, err msg: " + e.getMessage());
            }
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

    public int addContainer(String cmd){
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://" + env.getProperty("docker.host") + ":" + env.getProperty("docker.port")).build();
        try{
            dockerClient.createContainerCmd(cmd).exec();
        }
        catch (Exception e){
            logger.error("DockerService: createContainerCmd exec error, msg: " + e.getMessage());
            return 500;
        }
        return 200;
    }

    public String PullImage(String imageName){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://" + env.getProperty("docker.host") + ":" + env.getProperty("docker.port"))
                .withDockerTlsVerify(false)
                .withDockerConfig(env.getProperty("docker.DockerConfigPath"))
                .withRegistryUsername(env.getProperty("docker.RegistryUsername"))
                .withRegistryPassword(env.getProperty("docker.RegistryPassword"))
                .withRegistryEmail(env.getProperty("docker.RegistryEmail"))
                .withRegistryUrl(env.getProperty("docker.RegistryUrl"))
                .build();
        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
        try{
            PullImageCmd req = dockerClient.pullImageCmd(imageName);
            PullImageResultCallback res = new PullImageResultCallback();
            res = req.withTag("latest").exec(res);
            res.awaitSuccess();
        }
        catch (Exception e){
            logger.error("DockerService: pullImageCmd exec error, msg: " + e.getMessage());
            return "error";
        }
        return "success";
    }
}
