package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.VolumnBindings;

@Mapper
@Component
public interface VolumnBindingsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VolumnBindings record);

    int insertSelective(VolumnBindings record);

    VolumnBindings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VolumnBindings record);

    int updateByPrimaryKey(VolumnBindings record);
}