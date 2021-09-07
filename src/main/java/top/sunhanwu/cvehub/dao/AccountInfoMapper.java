package top.sunhanwu.cvehub.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import top.sunhanwu.cvehub.model.AccountInfo;

@Component
@Mapper
public interface AccountInfoMapper {
    int deleteByPrimaryKey(String username);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);

}