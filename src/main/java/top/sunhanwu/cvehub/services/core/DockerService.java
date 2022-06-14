package top.sunhanwu.cvehub.services.core;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.glassfish.jersey.apache.connector.ApacheHttpClientBuilderConfigurator;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import top.sunhanwu.cvehub.bean.docker.requests.ContainerSetting;
import top.sunhanwu.cvehub.bean.docker.requests.RunContainerRequest;
import top.sunhanwu.cvehub.bean.response.BaseResponse;
import top.sunhanwu.cvehub.dao.AccountInfoMapper;
import top.sunhanwu.cvehub.dao.ContainerInfoMapper;
import top.sunhanwu.cvehub.dao.ImageInfoMapper;
import top.sunhanwu.cvehub.model.AccountInfo;
import top.sunhanwu.cvehub.model.ContainerInfo;
import top.sunhanwu.cvehub.model.ImageInfo;
import top.sunhanwu.cvehub.utils.JwtUtil;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

@Component
public class DockerService {

    @Autowired
    private Environment env;

    @Autowired
    private ImageInfoMapper imageInfoMapper;

    @Autowired
    private ContainerInfoMapper containerInfoMapper;

    @Autowired
    private AccountInfoMapper accountInfoMapper;

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
//        for(int i=0;i<imageList.size();i++){
//            ImageInfo temImageInfo = null;
//            try{
//                temImageInfo = imageInfoMapper.selectByPrimaryKey(imageList.get(i).getId());
//            }
//            catch (Exception e){
//                    logger.error("DockerService: select imageInfo by hashId error, err msg: " + e.getMessage());
//            }
//            if (temImageInfo != null)
//                continue;
//            ImageInfo newImageInfo = new ImageInfo();
//            newImageInfo.setCreatetime(imageList.get(i).getCreated());
//            newImageInfo.setHashid(imageList.get(i).getId());
//            newImageInfo.setParentid(imageList.get(i).getParentId());
//            newImageInfo.setTags(imageList.get(i).getRepoTags()[0]);
//            newImageInfo.setSize(imageList.get(i).getSize());
//            newImageInfo.setVirtualsize(imageList.get(i).getVirtualSize());
//            newImageInfo.setShow(0);
//            try {
//                imageInfoMapper.insertSelective(newImageInfo);
//            }
//            catch (Exception e){
//                logger.error("DockerService: insert imageinfo into database error, err msg: " + e.getMessage());
//            }
//        }
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

    private boolean isImagePulled(String imageName, String tag){
        /*
         * check whether the image is pulled.
         */
        List<Image> imageList = this.listImages();
        for(int i=0; i<imageList.size(); i++){
            for(int j=0; j<imageList.get(i).getRepoTags().length; j++){
                if(imageList.get(i).getRepoTags()[j].equals(imageName + ":" + tag)){
                    // image is pulled
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isContainerExist(String containerName){
        /**
         * check whether the container is exist.
         */
        List<Container> containerList = this.listContainers();
        for(int i=0; i<containerList.size(); i++){
            for(int j=0; j<containerList.get(i).getNames().length; j++){
                // todo: need to check the container name, the "/"
                if(containerList.get(i).getNames()[j].equals("/" + containerName)){
                    // container is exist
                    return true;
                }
            }
        }
        return false;
    }

    public String PullImage(String imageName, String tag){
        if(this.isImagePulled(imageName, tag)){
            return "image is pulled";
        }
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
            res = req.withTag(tag).exec(res);
            res.awaitSuccess();
        }
        catch (Exception e){
            logger.error("DockerService: pullImageCmd exec error, msg: " + e.getMessage());
            return "error";
        }
        return "success";
    }

    public String runContainer(RunContainerRequest runContainerRequest, String userId, String token){
        /**
         * request param validate
         */
        if(runContainerRequest.getName() == null){
            return "container name is null";
        }
        if(runContainerRequest.getImageName() == null){
            return "image name is null";
        }
        if(runContainerRequest.getImageTag() == null){
            return "image tag is null";
        }

        /**
         * Get Userinfo and check the auth
         */
        String username = JwtUtil.getUserNameByToken(token);
        AccountInfo accountInfo = accountInfoMapper.selectByPrimaryKey(username);
        if(accountInfo == null){
            return "user not exist";
        }
        if(accountInfo.getId() != Long.parseLong(userId)){
            return "token or userid error";
        }

        /**
         * get image info by imageId and check image info
         */
        ImageInfo imageInfo = imageInfoMapper.selectByPrimaryKey(runContainerRequest.getImageId());
        if(imageInfo == null){
            return "image not exist";
        }
        if(!imageInfo.getName().equals(runContainerRequest.getImageName())){
            return "image name error";
        }

        /**
         * if image is not pulled, pull image
         */
        if(!this.isImagePulled(runContainerRequest.getImageName(), runContainerRequest.getImageTag())){
            String result = this.PullImage(runContainerRequest.getImageName(), runContainerRequest.getImageTag());
            if(!result.equals("success")){
                return result;
            }
        }

        /**
         * check whether the container is exist.
         * containers under the same user cannot have the same name
         */
        List<ContainerInfo> containerInfos = containerInfoMapper.selectByName(runContainerRequest.getName() + "_" + userId);
        if(containerInfos.size() > 0){
            return "container name is exist";
        }
        /**
         * if container is exist, return; use doker
         */
        if(this.isContainerExist(runContainerRequest.getName() + "_" + userId)){
            return "container is exist";
        }else{
            runContainerRequest.setName(runContainerRequest.getName() + "_" + userId);
        }

        /**
         *
         * config docker client
         */
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

        /**
         * create container
         */
        try{
            CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(runContainerRequest.getImageName() + ":" + runContainerRequest.getImageTag()).withName(runContainerRequest.getName());
            ContainerSetting containerSetting = runContainerRequest.getContainerSetting();
            /**
             * add portbindings
             */
            if(containerSetting.getPorts().size() > 0){
                for(String inner: containerSetting.getPorts().get(0).keySet()){
                    if(Integer.parseInt(inner) > 65535){
                        return "inner port error";
                    }
                    int innerPort = Integer.parseInt(inner);
                    if(Integer.parseInt(containerSetting.getPorts().get(0).get(inner)) > 65536){
                        return "inner port error";
                    }
                    int outerPort = Integer.parseInt(containerSetting.getPorts().get(0).get(inner));
                    ExposedPort tcpPort = ExposedPort.tcp(innerPort);
                    Ports portBingings = new Ports();
                    portBingings.bind(tcpPort, Ports.Binding.bindPort(outerPort));
                    createContainerCmd = createContainerCmd.withExposedPorts(tcpPort).withHostConfig(new HostConfig().withPortBindings(portBingings));
                }
            }
            /**
             * add volumn bindings
             */
            if(containerSetting.getVolumns().size() > 0){
                for(String inner: containerSetting.getVolumns().get(0).keySet()){
                    String outer = containerSetting.getVolumns().get(0).get(inner);
                    Volume volume = new Volume(outer);
                    Bind bind = new Bind(inner, volume);
                    //todo: need to test which is inner and outer
                    createContainerCmd = createContainerCmd.withHostConfig(new HostConfig().withBinds(bind));
                }
            }

            /**
             * add env settings
             */
            List<String> envs = new ArrayList<String>();
            if(containerSetting.getEnvs().size() > 0){
                for(String inner: containerSetting.getEnvs().get(0).keySet()){
                    String outer = containerSetting.getEnvs().get(0).get(inner);
                    envs.add(inner + "=" + outer);
                }
                createContainerCmd = createContainerCmd.withEnv(envs);
            }

            /**
             * add other commands
             */
            List<String> cmds = new ArrayList<String>();
            if(containerSetting.getOthers().size() > 0){
                for(String inner: containerSetting.getOthers().get(0).keySet()){
                    String outer = containerSetting.getOthers().get(0).get(inner);
                    if(outer.equals("")){
                        cmds.add(inner);
                    }else{
                        cmds.add(inner + " " + outer);
                    }
                }
                createContainerCmd = createContainerCmd.withCmd(cmds);
            }

            /**
             * start container by default
             */
            createContainerCmd = createContainerCmd.withTty(true);
            String containerId = createContainerCmd.exec().getId();
            dockerClient.startContainerCmd(containerId).exec();

            /**
             * save data to db
             */
            ContainerInfo containerInfo = new ContainerInfo();
            containerInfo.setName(runContainerRequest.getName());
            containerInfo.setUserid(Long.parseLong(userId));
            containerInfo.setImageid(runContainerRequest.getImageId());
            containerInfo.setContainerId(containerId);
            try {
                containerInfoMapper.insert(containerInfo);
            }
            catch (Exception e){
                logger.error("DockerService: runContainer insert error, msg: " + e.getMessage());
                /**
                 * if insert error, delete container
                 */
                dockerClient.removeContainerCmd(containerId).withForce(true).withRemoveVolumes(true).exec();
                return "insert error";
            }
        }
        catch (Exception e){
            logger.error("DockerService: create container error, msg: " + e.getMessage());
            return "error";
        }
        return "success";
    }

}
