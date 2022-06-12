package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.RetrieveValid;

@Mapper
@Component
public interface RetrieveValidMapper {
    int deleteByPrimaryKey(String username);

    int insert(RetrieveValid record);

    int insertSelective(RetrieveValid record);

    RetrieveValid selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(RetrieveValid record);

    int updateByPrimaryKey(RetrieveValid record);
}