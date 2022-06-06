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
import top.sunhanwu.cvehub.dao.ImageInfoMapper;
import top.sunhanwu.cvehub.model.ImageInfo;

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
}
