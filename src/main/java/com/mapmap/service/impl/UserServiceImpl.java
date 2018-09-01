package com.mapmap.service.impl;

import com.mapmap.common.ServerResponse;
import com.mapmap.dao.UserMapper;
import com.mapmap.pojo.User;
import com.mapmap.service.IUserService;
import com.mapmap.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String phone) {
        int resultCount=userMapper.checkUsername(username);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在。");
        }
        //to-do 密码登陆MD5

        User user=userMapper.selectLogin(username,phone);

        if(user==null){
            return ServerResponse.createByErrorMessage("密码错误。");
        }

        user.setUserPhone(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功。",user);
    }

    public ServerResponse<String> register(User user){
        int resultCount=userMapper.checkUsername(user.getUserName());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("用户名已存在。");
        }
        resultCount=userMapper.checkEmail(user.getUserPhone());//getEmail
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("Email已存在。");
        }
        //user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setUserPhone(MD5Util.MD5EncodeUtf8(user.getUserPhone()));
        resultCount=userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("注册失败。");
        }
        return ServerResponse
    }

}
