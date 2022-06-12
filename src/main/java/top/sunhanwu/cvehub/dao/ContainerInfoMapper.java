package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.ContainerInfo;

@Mapper
@Component
public interface ContainerInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContainerInfo record);

    int insertSelective(ContainerInfo record);

    ContainerInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContainerInfo record);

    int updateByPrimaryKey(ContainerInfo record);
}