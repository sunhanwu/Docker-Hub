package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.Arch;

@Mapper
@Component
public interface ArchMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Arch record);

    int insertSelective(Arch record);

    Arch selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Arch record);

    int updateByPrimaryKey(Arch record);
}