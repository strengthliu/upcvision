package com.surpass.vision.mapper;

import com.surpass.vision.domain.DepartmentInfo;
import com.surpass.vision.domain.DepartmentInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface DepartmentInfoMapper {
    long countByExample(DepartmentInfoExample example);

    int deleteByExample(DepartmentInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentInfo record);

    int insertSelective(DepartmentInfo record);

    List<DepartmentInfo> selectByExample(DepartmentInfoExample example);

    DepartmentInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DepartmentInfo record, @Param("example") DepartmentInfoExample example);

    int updateByExample(@Param("record") DepartmentInfo record, @Param("example") DepartmentInfoExample example);

    int updateByPrimaryKeySelective(DepartmentInfo record);

    int updateByPrimaryKey(DepartmentInfo record);
    
    //*********************************************************
    
    @Select("select distinct * from t_department")
    @ResultMap("BaseResultMap")
    List<DepartmentInfo> selectAllDepartment();

    @Select("select max(id) from t_department")
    @ResultMap("BaseResultMap")
    Integer selectMaxId();


}