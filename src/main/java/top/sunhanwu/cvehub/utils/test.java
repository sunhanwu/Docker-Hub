package top.sunhanwu.cvehub.utils;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;


public class test {

    public static void main(String[] args) {
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://" + "127.0.0.1" + ":" + "2375").build();
        ExposedPort tcp8090 = ExposedPort.tcp(8090);
        Ports portbindings = new Ports();
        portbindings.bind(tcp8090, Ports.Binding.bindPort(8090));
        CreateContainerResponse container = dockerClient.createContainerCmd("halohub/halo:1.5.3")
                .withCmd("-itd")
                .withName("halo")
                .withExposedPorts(tcp8090)
//                .withPortBindings(portbindings)
                .exec();
    }
}
