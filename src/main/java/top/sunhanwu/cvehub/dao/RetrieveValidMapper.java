package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.RetrieveValid;

@Component
@Mapper
public interface RetrieveValidMapper {

    int insert(RetrieveValid retrieveValid);

    RetrieveValid selectByPrimaryKey(String username);

    int updateByPrimaryKey(RetrieveValid retrieveValid);
}
