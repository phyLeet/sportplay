package com.xyzdock.sportplay.controller;

import com.alibaba.fastjson.JSON;
import com.xyzdock.sportplay.bean.QueryInfo;
import com.xyzdock.sportplay.bean.User;
import com.xyzdock.sportplay.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/allUser")
    public String getUserList(QueryInfo queryInfo) {
        int numbers = userDao.getUserCounts("%" + queryInfo.getQuery() + "%");
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<User> users = userDao.getAllUser("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", users);
        String resString = JSON.toJSONString(res);
        return resString;
    }

    @RequestMapping("/userState")
    public String updateUserState(@RequestParam("id") Integer id,
                                  @RequestParam("state") Boolean state) {
        int i = userDao.updateState(id, state);
        String str = i > 0 ? "success" : "error";
        return str;
    }
}
