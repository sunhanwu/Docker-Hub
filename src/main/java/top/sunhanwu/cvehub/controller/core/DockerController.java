package top.sunhanwu.cvehub.controller.core;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import top.sunhanwu.cvehub.bean.docker.response.ListDockerContaionersResponse;
import top.sunhanwu.cvehub.bean.docker.response.ListDockerImageResponse;
import top.sunhanwu.cvehub.bean.response.ListImageInfoResponse;
import top.sunhanwu.cvehub.bean.docker.requests.addContainerRequest;
import top.sunhanwu.cvehub.model.ImageInfo;
import top.sunhanwu.cvehub.services.core.DockerService;
import top.sunhanwu.cvehub.services.core.ImageService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/docker")
public class DockerController {

    @Autowired
    private DockerService dockerService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * listImages: 查看当前所有镜像信息
     */
    @GetMapping("/listImages")
    public ListDockerImageResponse showDockerInfos(){
        ListDockerImageResponse listDockerImageResponse = new ListDockerImageResponse();
        listDockerImageResponse.setHost(env.getProperty("docker.host"));

        List<Image> imageList = dockerService.listImages();

        if(imageList == null){
            listDockerImageResponse.setMsg("Internal Error");
            listDockerImageResponse.setCode(500);
            return listDockerImageResponse;
        }

        listDockerImageResponse.setMsg("success");
        listDockerImageResponse.setCode(200);

        listDockerImageResponse.setDockerImageInfoList(imageList);

        return listDockerImageResponse;
    }

    /**
     * listContainer: 查看当前所有容器信息
     */
    @GetMapping("/listContainers")
    public ListDockerContaionersResponse listContainers(){
        ListDockerContaionersResponse listDockerContaionersResponse = new ListDockerContaionersResponse();

        List<Container> containerList = null;
        containerList = dockerService.listContainers();

        if(containerList == null){
            listDockerContaionersResponse.setMgs("Internal Error");
            listDockerContaionersResponse.setCode(500);
            return listDockerContaionersResponse;
        }

        listDockerContaionersResponse.setContainerList(containerList);
        listDockerContaionersResponse.setCode(200);
        listDockerContaionersResponse.setMgs("success");

        return listDockerContaionersResponse;
    }

    /**
     * listSupplyImages
     */
    @GetMapping("/listSupplyImages")
    public ListImageInfoResponse listSupplyImages(@RequestParam int start, @RequestParam int num){
        ListImageInfoResponse listImageInfoResponse = new ListImageInfoResponse();
        List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
        if(start < 0 || num <= 0){
            logger.error("ListSupplyImages params error, start = " + start + " num= " + num);
            listImageInfoResponse.setMsg("param error");
            listImageInfoResponse.setImageInfos(imageInfos);
            return listImageInfoResponse;
        }
        imageInfos = imageService.listImageInfos(start, num);
        listImageInfoResponse.setMsg("success");
        listImageInfoResponse.setImageInfos(imageInfos);
        return listImageInfoResponse;
    }

    /**
     * addSupplyImage
     */
    @PostMapping("/addSupplyImage")
    public void addSupplyImage(@RequestBody ImageInfo imageInfo){
    }

    /**
     * Create Container for someone
     */
    @GetMapping("/createContainer")
    public void createContainer(@RequestBody addContainerRequest containerRequest ){
        return;
    }
}
