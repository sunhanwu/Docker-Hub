package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.Tags;

@Mapper
@Component
public interface TagsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);
}