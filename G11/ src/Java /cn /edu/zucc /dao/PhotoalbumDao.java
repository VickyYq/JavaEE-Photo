package cn.edu.zucc.dao;

import cn.edu.zucc.IDao.IPhotoalbumDao;
import cn.edu.zucc.bean.Photoalbum;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

public class PhotoalbumDao implements IPhotoalbumDao{

    @Resource(name = "sessionFactory")

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public synchronized void addphotoalbum (Photoalbum photoalbum) throws Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(photoalbum);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void deletephotoalbum (int paid) throws Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Photoalbum photoalbum = this.load(paid);
        session.delete(photoalbum);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public  synchronized void modifyphotoalbum (Photoalbum photoalbum) throws Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(photoalbum);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Photoalbum load(int paid) {
        Session session = sessionFactory.openSession();
        Photoalbum photoalbum =session.load(Photoalbum.class,paid);
        session.close();
        return photoalbum;
    }

    @Override
    public  synchronized List loadallphotoalbum (String userid) throws Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql="from Photoalbum where userid = :userid";
        Query query = session.createQuery(hql);
        query.setParameter("userid",userid);
        List<Photoalbum> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public synchronized Photoalbum readphotoalbum (int paid) throws Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql="from Photoalbum where paid = :paid";
        Query query = session.createQuery(hql);
        query.setParameter("paid",paid);
        Photoalbum photoalbum = (Photoalbum) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return photoalbum;
    }

    @Override
    public synchronized int photocount(int paid) throws  Exception{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql="select count(*) from Photo where paid = :paid";
        Query query = session.createQuery(hql);
        query.setParameter("paid",paid);
        int count = Integer.valueOf(query.uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return count;
    }

}
