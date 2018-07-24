package cn.edu.zucc.IDao;

import cn.edu.zucc.bean.User;

public interface IUserDao {
    void adduser(User user) throws Exception;
    void modifyuser(User user) throws Exception;
    User readuser(String userid) throws Exception;
}
