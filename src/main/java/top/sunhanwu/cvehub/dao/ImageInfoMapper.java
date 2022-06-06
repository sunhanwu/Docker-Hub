package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.ImageInfo;

import java.util.List;

@Component
@Mapper
public interface ImageInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImageInfo record);

    int insertSelective(ImageInfo record);

    ImageInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageInfo record);

    int updateByPrimaryKey(ImageInfo record);

    List<ImageInfo> listAllImageInfos(int index_left, int num);

    ImageInfo selectByHashId(String hashId);
}