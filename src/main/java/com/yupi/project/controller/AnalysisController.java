package com.yupi.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.yupi.project.common.BaseResponse;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.common.ResultUtils;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.UserInterfaceInfoMapper;
import com.yupi.project.model.vo.InterfaceInfoVO;
import com.yupi.project.service.InterfaceInfoService;
import com.yupi.yuapicommon.model.entity.InterfaceInfo;
import com.yupi.yuapicommon.model.entity.UserInterfaceInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 接口解析
 *
 * @author 75654
 * @date 2024/2/9 20:37
 */
@RequestMapping("analysis")
public class AnalysisController {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 获取调用次数最多的接口信息列表。
     * 通过用户接口信息表查询调用次数最多的接口ID，再关联查询接口详细信息。
     *
     * @return 接口信息列表，包含调用次数最多的接口信息
     */
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        // 1.通过 user_interface_info 获取接口调用次数
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        // 1.1 根据 id 分组
        // {1=[User [id=1, name=zhangsan, age=33]], 2=[User [id=2, name=lisi, age=44]], 3=[User [id=3, name=wangwu, age=55]]}
        Map<Long, List<UserInterfaceInfo>> idToUserInterfaceInfoMap = userInterfaceInfoList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        // 2.根据接口 id 访问 interface_info 获取完整接口信息，并将调用次数合并
        QueryWrapper<InterfaceInfo> qw = new QueryWrapper<>();
        qw.in("id", idToUserInterfaceInfoMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(qw);
        // 2.1 判空
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 3.整合结果集
        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
            // 将 interfaceInfo 数据复制到 interfaceInfoVO 中
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            // 根据 interfaceInfo 的 id 获取在 map 中的统计次数
            // get(0) 说明: 元素下只有索引 0 处
            Integer totalNum = idToUserInterfaceInfoMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            // 补全参数
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        // 4.返回
        return ResultUtils.success(interfaceInfoVOList);
    }
}
