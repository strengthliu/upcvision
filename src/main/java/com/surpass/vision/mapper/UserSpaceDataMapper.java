package com.surpass.vision.mapper;

import com.surpass.vision.domain.UserSpaceData;
import com.surpass.vision.domain.UserSpaceDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserSpaceDataMapper {
    long countByExample(UserSpaceDataExample example);

    int deleteByExample(UserSpaceDataExample example);

    int deleteByPrimaryKey(Double uid);

    int insert(UserSpaceData record);

    int insertSelective(UserSpaceData record);

    List<UserSpaceData> selectByExample(UserSpaceDataExample example);

    UserSpaceData selectByPrimaryKey(Double uid);

    int updateByExampleSelective(@Param("record") UserSpaceData record, @Param("example") UserSpaceDataExample example);

    int updateByExample(@Param("record") UserSpaceData record, @Param("example") UserSpaceDataExample example);

    int updateByPrimaryKeySelective(UserSpaceData record);

    int updateByPrimaryKey(UserSpaceData record);
}