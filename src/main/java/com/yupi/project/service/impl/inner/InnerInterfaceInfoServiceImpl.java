package com.yupi.project.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yupi.project.common.ErrorCode;
import com.yupi.project.exception.BusinessException;
import com.yupi.project.mapper.InterfaceInfoMapper;
import com.yupi.yuapicommon.model.entity.InterfaceInfo;
import com.yupi.yuapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author 75654
 * @date 2024/2/9 18:51
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInvokeInterfaceInfo(String url, String method) {
        // 1.判空
        if (StringUtils.isAnyBlank(url,method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 2.构造条件
        QueryWrapper<InterfaceInfo> interfaceInfoQueryWrapper = new QueryWrapper<>();
        interfaceInfoQueryWrapper.eq("url", url);
        interfaceInfoQueryWrapper.eq("method", method);
        // 3.结果返回
        return interfaceInfoMapper.selectOne(interfaceInfoQueryWrapper);
    }
}
