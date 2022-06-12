package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.ContainerConfig;

@Mapper
@Component
public interface ContainerConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContainerConfig record);

    int insertSelective(ContainerConfig record);
}