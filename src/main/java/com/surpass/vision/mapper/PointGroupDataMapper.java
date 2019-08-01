package com.surpass.vision.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.UserInfo;

public interface PointGroupDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PointGroupData record);

    int insertSelective(PointGroupData record);

    PointGroupData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PointGroupData record);

    int updateByPrimaryKey(PointGroupData record);

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