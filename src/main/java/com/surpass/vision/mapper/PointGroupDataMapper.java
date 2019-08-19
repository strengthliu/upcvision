package com.surpass.vision.mapper;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.PointGroupDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface PointGroupDataMapper {
    long countByExample(PointGroupDataExample example);

    int deleteByExample(PointGroupDataExample example);

    int deleteByPrimaryKey(Double id);

    int insert(PointGroupData record);

    int insertSelective(PointGroupData record);

    List<PointGroupData> selectByExample(PointGroupDataExample example);

    PointGroupData selectByPrimaryKey(Double id);

    int updateByExampleSelective(@Param("record") PointGroupData record, @Param("example") PointGroupDataExample example);

    int updateByExample(@Param("record") PointGroupData record, @Param("example") PointGroupDataExample example);

    int updateByPrimaryKeySelective(PointGroupData record);

    int updateByPrimaryKey(PointGroupData record);
    
    // pointGroupMapper

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_xygraph_+"'")
    @ResultMap("BaseResultMap")
    List<PointGroupData> getAdminXYGraph();

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_realtimedata_+"'")
    @ResultMap("BaseResultMap")
	List<PointGroupData> getAdminRealTimeData();

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_alertdata_+"'")
    @ResultMap("BaseResultMap")
	List<PointGroupData> getAdminAlertData();

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_historydata_+"'")
    @ResultMap("BaseResultMap")
	List<PointGroupData> getAdminHistoryData();

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_linealertdata_+"'")
    @ResultMap("BaseResultMap")
	List<PointGroupData> getAdminLineAlertData();

    @Select("select distinct * from t_pointGroup where type='"+GlobalConsts.Type_graph_+"'")
    @ResultMap("BaseResultMap")
	List<PointGroupData> getAdminGraphData();

    
}