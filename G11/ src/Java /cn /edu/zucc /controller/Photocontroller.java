package cn.edu.zucc.controller;

import cn.edu.zucc.IDao.IPhotoDao;
import cn.edu.zucc.IDao.IPhotoalbumDao;
import cn.edu.zucc.IDao.IUserDao;
import cn.edu.zucc.bean.Photo;
import cn.edu.zucc.bean.Photoalbum;
import cn.edu.zucc.bean.User;
import cn.edu.zucc.oss.OSSClientUtil;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;


import java.io.File;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


@Controller
public class Photocontroller {
    @Resource(name = "photoDao")
    private IPhotoDao iPhotoDao;

    @Resource(name = "userDao")
    private IUserDao iUserDao;

    @Resource(name = "photoalbumDao")
    private IPhotoalbumDao iphotoalbumDao;


    @RequestMapping("/listphoto")

    private String listphoto(HttpServletRequest request, @RequestParam(value = "paid") int paid,
                             @RequestParam(value = "paname") String paname) throws Exception {

        request.setAttribute("objlist", this.iPhotoDao.loadallphoto(paid));
        request.getSession().setAttribute("paname", paname);
        request.setAttribute("paid", paid);

        Photoalbum photoalbum = this.iphotoalbumDao.readphotoalbum(paid);
        String url = photoalbum.getPaurl();
        request.setAttribute("url", url);

        request.setAttribute("allpa", this.iphotoalbumDao.loadallphotoalbum((String) request.getSession().getAttribute("userid")));


        return "photo_list";
    }


    @RequestMapping(value = "addphoto")
    @ResponseBody
    private String addphoto(@RequestParam("paid") int paid,
                            @RequestParam("file") MultipartFile file,
                            HttpServletRequest request) throws Exception {
//        转换成文件
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        String userid = (String) request.getSession().getAttribute("userid");

//        上传至阿里云
        ossClientUtil.uploadObject2OSS(ossClientUtil.getOSSClient(), f, "photo-g11", userid + "/");

        DecimalFormat df = new DecimalFormat("#.00");

//        新建相片
        Photo photo = new Photo();
        Photoalbum photoalbum = this.iphotoalbumDao.readphotoalbum(paid);


        Timestamp time = new Timestamp(System.currentTimeMillis());
        photo.setPhotoalbumByPaid(photoalbum);
        photo.setPhotocreatdate(time);
        String endpoint = ossClientUtil.getENDPOINT();
        String bucktname = ossClientUtil.getBacketName();
        photo.setPhotourl("http://" + bucktname + "." + endpoint + "/" + userid + "/" + f.getName());

        photoalbum.setPaurl("http://" + bucktname + "." + endpoint + "/" + userid + "/" + f.getName());
        this.iphotoalbumDao.modifyphotoalbum(photoalbum);

        photo.setExphoto(file.getOriginalFilename());
        photo.setSize(new Double(df.format(file.getSize() / 1024.0 / 1024.0)));

//        设置容量
        User user = this.iUserDao.readuser(userid);

        double usercapacity = user.getUsercapacity() + photo.getSize();
        user.setUsercapacity(usercapacity);
        this.iUserDao.modifyuser(user);

        request.getSession().setAttribute("usercapacity", df.format(user.getUsercapacity()));
        this.iPhotoDao.addphoto(photo);

        photoalbum.setCount(this.iphotoalbumDao.photocount(paid));

        this.iphotoalbumDao.modifyphotoalbum(photoalbum);

        return "true";

    }

    @RequestMapping("/deletephoto")
    @ResponseBody
    private String deletephoto(@RequestParam(value = "photoid") int photoid,
                               @RequestParam(value = "paid") int paid,
                               HttpServletRequest request) throws Exception {

        String userid = (String) request.getSession().getAttribute("userid");

        User user = this.iUserDao.readuser(userid);
        Photo photo = this.iPhotoDao.readphoto(photoid);
        double usercapacity = user.getUsercapacity() - photo.getSize();
        user.setUsercapacity(usercapacity);
        this.iUserDao.modifyuser(user);
        DecimalFormat df = new DecimalFormat("#.00");
        request.getSession().setAttribute("usercapacity", df.format(user.getUsercapacity()));

        this.iPhotoDao.deletephoto(photoid);


        Photoalbum photoalbum = this.iphotoalbumDao.readphotoalbum(paid);

        if (photoalbum.getPaurl().equals(photo.getPhotourl())) {
            photoalbum.setPaurl("https://photo-g11.oss-cn-hangzhou.aliyuncs.com/111111/pic-none.png");
        }

        photoalbum.setCount(this.iphotoalbumDao.photocount(paid));

        this.iphotoalbumDao.modifyphotoalbum(photoalbum);

        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }


    @RequestMapping("/modifyphoto")
    @ResponseBody
    private String modifyphoto(@RequestParam(value = "photoid") int photoid,
                               @RequestParam(value = "exphoto") String exphoto,
                               @RequestParam(value = "photodetail") String photodeatil) throws Exception {
        Photo photo = this.iPhotoDao.readphoto(photoid);
        photo.setExphoto(exphoto);
        photo.setPhotodetail(photodeatil);

        this.iPhotoDao.modifyphotodetail(photo);
        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/movephoto")
    @ResponseBody
    private String movephoto(@RequestParam(value = "photoid") int photoid,
                             @RequestParam(value = "paid") int paid) throws Exception {
        Photo photo = this.iPhotoDao.readphoto(photoid);
        Photoalbum photoalbum = new Photoalbum();
        photoalbum.setPaid(paid);
        photo.setPhotoalbumByPaid(photoalbum);
        this.iPhotoDao.modifyphotodetail(photo);
        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/setting")
    @ResponseBody
    private String setting(@RequestParam(value = "photoid") int photoid,
                           @RequestParam(value = "paid") int paid) throws Exception {
        Photo photo = this.iPhotoDao.readphoto(photoid);
        String url = photo.getPhotourl();

        Photoalbum photoalbum = this.iphotoalbumDao.readphotoalbum(paid);
        photoalbum.setPaurl(url);

        this.iphotoalbumDao.modifyphotoalbum(photoalbum);

        JSONObject json = new JSONObject();

        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/allmove")
    @ResponseBody
    private String allmove(@RequestParam(value = "allphotoid") String arry_photoid,
                           @RequestParam(value = "paid") int paid) throws Exception {


        List<String> result = Arrays.asList(arry_photoid.split(","));
        for (int i = 0; i < result.size(); i++) {
            Photo photo = this.iPhotoDao.readphoto(Integer.valueOf(result.get(i)));
            Photoalbum photoalbum = new Photoalbum();
            photoalbum.setPaid(paid);
            photo.setPhotoalbumByPaid(photoalbum);
            this.iPhotoDao.modifyphotodetail(photo);
        }
        JSONObject json = new JSONObject();
        json.put("result", 1);
        return json.toString();
    }

    @RequestMapping("/alldelete")
    @ResponseBody
    private String alldelete(@RequestParam(value = "allphotoid") String arry_photoid,
                             @RequestParam(value = "paid") int paid,
                             HttpServletRequest request) throws Exception {

        String userid = (String) request.getSession().getAttribute("userid");

        User user = this.iUserDao.readuser(userid);

        List<String> result = Arrays.asList(arry_photoid.split(","));
        for (int i = 0; i < result.size(); i++) {
            Photo photo = this.iPhotoDao.readphoto(Integer.valueOf(result.get(i)));
            double usercapacity = user.getUsercapacity() - photo.getSize();
            user.setUsercapacity(usercapacity);
            this.iUserDao.modifyuser(user);
            DecimalFormat df = new DecimalFormat("#.00");
            request.getSession().setAttribute("usercapacity", df.format(user.getUsercapacity()));

            this.iPhotoDao.deletephoto(Integer.valueOf(result.get(i)));
            Photoalbum photoalbum = this.iphotoalbumDao.readphotoalbum(paid);

            if (photoalbum.getPaurl().equals(photo.getPhotourl())) {
                photoalbum.setPaurl("https://photo-g11.oss-cn-hangzhou.aliyuncs.com/111111/pic-none.png");
            }

            photoalbum.setCount(this.iphotoalbumDao.photocount(paid));

            this.iphotoalbumDao.modifyphotoalbum(photoalbum);

        }


        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/allmodify")
    @ResponseBody
    private String modifyphoto(@RequestParam(value = "allphotoid") String arry_photoid,
                               @RequestParam(value = "exphoto") String exphoto,
                               @RequestParam(value = "photodetail") String photodeatil) throws Exception {

        List<String> result = Arrays.asList(arry_photoid.split(","));
        for (int i = 0; i < result.size(); i++) {
            Photo photo = this.iPhotoDao.readphoto(Integer.valueOf(result.get(i)));

            photo.setExphoto(exphoto);
            photo.setPhotodetail(photodeatil);
            this.iPhotoDao.modifyphotodetail(photo);

        }

        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/movesharephoto")
    @ResponseBody
    private String movesharephoto(@RequestParam(value = "photoid") int photoid,
                                  @RequestParam(value = "paid") int paid,
                                  HttpServletRequest request) throws Exception {
        Photo photo = this.iPhotoDao.readphoto(photoid);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Photo photo1 = new Photo();
        photo1.setSize(photo.getSize());
        photo1.setPhotodetail(photo.getPhotodetail());
        photo1.setExphoto(photo.getExphoto());
        photo1.setPhotourl(photo.getPhotourl());
        photo1.setPhotocreatdate(time);

        Photoalbum photoalbum =  this.iphotoalbumDao.readphotoalbum(paid);
        photoalbum.setPaid(paid);
        photo1.setPhotoalbumByPaid(photoalbum);

        this.iPhotoDao.addphoto(photo1);

        User user = this.iUserDao.readuser((String) request.getSession().getAttribute("userid"));

        double usercapacity = user.getUsercapacity() + photo1.getSize();

        user.setUsercapacity(usercapacity);
        this.iUserDao.modifyuser(user);

        DecimalFormat df = new DecimalFormat("#.00");
        request.getSession().setAttribute("usercapacity", df.format(user.getUsercapacity()));


        photoalbum.setCount(this.iphotoalbumDao.photocount(paid));

        this.iphotoalbumDao.modifyphotoalbum(photoalbum);



        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/downloadphoto")
    @ResponseBody
    private String downloadphoto(@RequestParam("photoid") int photoid) throws Exception{
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        Photo photo = this.iPhotoDao.readphoto(photoid);
        String exphoto = photo.getExphoto();
        String url = photo.getPhotourl();
        String key = url.substring(46);
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        String downloadFilePath = com.getPath()+"/Desktop/"+exphoto;
        System.out.println(downloadFilePath);
        ossClientUtil.downloadFile(key,downloadFilePath);
        return "true";
    }
}

