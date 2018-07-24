package cn.edu.zucc.IDao;

import cn.edu.zucc.bean.Photoalbum;

import java.sql.Timestamp;
import java.util.List;

public interface IPhotoalbumDao {
    void addphotoalbum (Photoalbum photoalbum) throws Exception;
    void deletephotoalbum (int paid) throws Exception;
    void modifyphotoalbum (Photoalbum photoalbum) throws Exception;
    List loadallphotoalbum (String userid) throws Exception;
    Photoalbum load (int paid) throws Exception;
    Photoalbum readphotoalbum (int paid) throws Exception;
    int photocount(int paid) throws  Exception;
}
