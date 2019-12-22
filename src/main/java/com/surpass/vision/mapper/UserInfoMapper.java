package com.surpass.vision.mapper;

import com.surpass.vision.domain.UserInfo;
import com.surpass.vision.domain.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Double id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(Double id);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    
    // userinfomapper
    
    @Select("select * from t_user where name=#{name} and password=#{password}")
    @ResultMap("BaseResultMap")
    UserInfo selectByNamePassword(String name,String password);

    @Select("select * from t_user where name=#{name}")
    @ResultMap("BaseResultMap")
    List<UserInfo> selectByName(String name);

    @Select("select distinct * from t_user")
    @ResultMap("BaseResultMap")
    List<UserInfo> selectAdminUserInfo();


}