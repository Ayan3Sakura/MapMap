package com.mapmap.service;

import com.mapmap.common.ServerResponse;
import com.mapmap.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String phone);

}
