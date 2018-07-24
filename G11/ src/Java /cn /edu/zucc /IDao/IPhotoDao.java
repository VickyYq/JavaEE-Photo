package cn.edu.zucc.IDao;

import cn.edu.zucc.bean.Photo;

import java.util.List;

public interface IPhotoDao {
    void addphoto(Photo photo) throws Exception;
    void deletephoto(int photoid) throws Exception;
    void modifyphotodetail(Photo photo)throws Exception;
    List loadallphoto(int paid) throws Exception;
    Photo load (int paid) throws Exception;
    Photo readphoto(int photoid);
}
