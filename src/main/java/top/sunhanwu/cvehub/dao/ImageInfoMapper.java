package top.sunhanwu.cvehub.dao;

import top.sunhanwu.cvehub.model.ImageInfo;

public interface ImageInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImageInfo record);

    int insertSelective(ImageInfo record);

    ImageInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageInfo record);

    int updateByPrimaryKey(ImageInfo record);
}