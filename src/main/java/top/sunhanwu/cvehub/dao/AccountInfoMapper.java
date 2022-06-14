package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.AccountInfo;

import java.util.List;

@Mapper
@Component
public interface AccountInfoMapper {
    int deleteByPrimaryKey(String username);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(String username);

    AccountInfo selectById(String id);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);
}