package com.surpass.vision.mapper;

import com.surpass.vision.domain.UserSpaceData;

public interface UserSpaceDataMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(UserSpaceData record);

    int insertSelective(UserSpaceData record);

    UserSpaceData selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserSpaceData record);

    int updateByPrimaryKey(UserSpaceData record);
}