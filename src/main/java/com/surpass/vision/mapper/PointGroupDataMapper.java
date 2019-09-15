package com.surpass.vision.mapper;

import com.surpass.vision.appCfg.GlobalConsts;
import com.surpass.vision.domain.FileList;
import com.surpass.vision.domain.PointGroupData;
import com.surpass.vision.domain.PointGroupDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update({ "update t_pointGroup set owner = #{owner},creater = #{creater},shared = #{shared},points = #{points} ,otherRule2 = #{otherRule2},otherRule1 = #{otherRule1} where name = #{name} and type='"+GlobalConsts.Type_graph_+"\'" })
	int updateByName(@Param("owner") String owner,@Param("creater") String creater,@Param("shared") String shared,@Param("points") String points,@Param("otherRule1") String otherRule1,@Param("otherRule2") String otherRule2,@Param("name") String name);

    @Select("select distinct * from t_pointGroup where name=#{name} and type='"+GlobalConsts.Type_graph_+"\'")
    @ResultMap("BaseResultMap")
	PointGroupData selectByName(@Param("name") String name);

    
}