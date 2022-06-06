package top.sunhanwu.cvehub.bean.docker.requests;

import java.util.List;
import java.util.Map;

public class addSupplyImageRequest {
    private String name;

    private String tag;

    private List<Map<Integer, Integer>> ports;

    private List<Map<String, String>> volumns;
}
