package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.Portbindings;

@Mapper
@Component
public interface PortbindingsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Portbindings record);

    int insertSelective(Portbindings record);

    Portbindings selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Portbindings record);

    int updateByPrimaryKey(Portbindings record);
}