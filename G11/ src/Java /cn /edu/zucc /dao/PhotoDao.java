package cn.edu.zucc.dao;

import cn.edu.zucc.IDao.IPhotoDao;
import cn.edu.zucc.bean.Photo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.annotation.Resource;
import java.util.List;

public class PhotoDao implements IPhotoDao {
    @Resource(name = "sessionFactory")

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public synchronized void addphoto(Photo photo) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(photo);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void deletephoto(int photoid) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Photo photo = this.load(photoid);
        session.delete(photo);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Photo load(int photoid) {
        Session session = sessionFactory.openSession();
        Photo photo = session.load(Photo.class, photoid);
        session.close();
        return photo;
    }


    @Override
    public void modifyphotodetail(Photo photo) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(photo);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized List<Photo>  loadallphoto(int paid) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from Photo where paid = :paid";
        Query query = session.createQuery(hql);
        query.setParameter("paid", paid);
        List<Photo> list = query.list();
        session.getTransaction().commit();
        session.close();
        return list;
    }

    @Override
    public synchronized Photo readphoto(int photoid) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "from Photo where photoid = :photoid";
        Query query = session.createQuery(hql);
        query.setParameter("photoid", photoid);
        Photo photo = (Photo) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return photo;
    }

}
