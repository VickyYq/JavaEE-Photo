package cn.edu.zucc.dao;

import cn.edu.zucc.IDao.IUserDao;
import cn.edu.zucc.bean.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.annotation.Resource;

public class UserDao implements IUserDao {
    @Resource(name = "sessionFactory")

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public synchronized void adduser(User user) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user1 = session.get(User.class, user.getUserid());
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void modifyuser(User user) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized User readuser(String userid) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from User where userid = :userid";
        Query query = session.createQuery(hql);
        query.setParameter("userid", userid);
        User user = (User) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

}
