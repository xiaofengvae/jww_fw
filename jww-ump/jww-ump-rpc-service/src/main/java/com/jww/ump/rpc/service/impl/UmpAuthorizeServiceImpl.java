package com.jww.ump.rpc.service.impl;

import com.jww.ump.dao.mapper.UmpAuthorizeMapper;
import com.jww.ump.rpc.api.UmpAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限业务处理实现类
 *
 * @author wanyong
 * @create 2017-11-29
 **/
@Service("umpAuthorizeService")
public class UmpAuthorizeServiceImpl implements UmpAuthorizeService {

    @Autowired
    private UmpAuthorizeMapper umpAuthorizeMapper;

    @Override
    public List<String> queryPermissionByUserId(Long userId) {
        return umpAuthorizeMapper.selectPermissionByUserId(userId);
    }
}
