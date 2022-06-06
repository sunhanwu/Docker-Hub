package top.sunhanwu.cvehub.services.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.dao.ImageInfoMapper;
import top.sunhanwu.cvehub.model.ImageInfo;

import java.util.List;

@Component
public class ImageService {
    @Autowired
    private ImageInfoMapper imageInfoMapper;


    public List<ImageInfo> listImageInfos(int index_left, int num){
        List<ImageInfo> result;
        result = imageInfoMapper.listAllImageInfos(index_left, num);
        return result;
    }

    public int addImageInfo(ImageInfo imageInfo){

        return 200;
    }
}
