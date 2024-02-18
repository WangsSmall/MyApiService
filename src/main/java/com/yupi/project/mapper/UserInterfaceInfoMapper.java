package com.yupi.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.yuapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 75654
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-02-06 14:48:38
* @Entity com.yupi.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    // -- 获取接口调用次数的统计信息，并按照调用总次数降序排列，最后取前三个接口作为结果
    // select interfaceInfoId, sum(totalNum) as totalNum
    // from user_interface_info
    // group by interfaceInfoId
    // order by totalNum desc
    // limit 3;
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




