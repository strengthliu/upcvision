package com.surpass.vision.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.surpass.vision.domain.UserInfo;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
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