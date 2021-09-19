package top.sunhanwu.cvehub.controller.core;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.sunhanwu.cvehub.bean.docker.response.ListDockerContaionersResponse;
import top.sunhanwu.cvehub.bean.docker.response.ListDockerImageResponse;
import top.sunhanwu.cvehub.services.core.DockerService;

import java.util.List;

@RestController
@RequestMapping("/docker")
public class DockerController {

    @Autowired
    private DockerService dockerService;

    @Autowired
    private Environment env;

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

}
