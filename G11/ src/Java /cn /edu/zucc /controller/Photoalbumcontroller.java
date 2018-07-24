package cn.edu.zucc.controller;

import cn.edu.zucc.IDao.IPhotoDao;
import cn.edu.zucc.IDao.IPhotoalbumDao;

import cn.edu.zucc.IDao.IUserDao;
import cn.edu.zucc.bean.Photo;
import cn.edu.zucc.bean.Photoalbum;
import cn.edu.zucc.bean.User;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;


@Controller
public class Photoalbumcontroller {

    @Resource(name = "photoalbumDao")
    private IPhotoalbumDao iPhotoalbumDao;

    @Resource(name = "userDao")
    private IUserDao iUserDao;

    @Resource(name = "photoDao")
    private IPhotoDao iPhotoDao;

    @RequestMapping("/listpa")
    private String list(HttpServletRequest request) throws Exception {
        String userid = (String) request.getSession().getAttribute("userid");
        request.setAttribute("objlist", this.iPhotoalbumDao.loadallphotoalbum(userid));
        request.setAttribute("username", request.getSession().getAttribute("username"));
        double usercapacity = new Double(request.getSession().getAttribute("usercapacity").toString());
        request.getSession().setAttribute("usercapacity", usercapacity);

        return "index";
    }

    @RequestMapping("/addphotoalbum")
    @ResponseBody
    private String addphotoalbum(@RequestParam(value = "userid") String userid,
                                 @RequestParam(value = "paname") String paname,
                                 @RequestParam(value = "padetail") String padetail,
                                 @RequestParam(value = "palimit") String palimit,
                                 HttpServletRequest request) throws Exception {
        Photoalbum photoalbum = new Photoalbum();
        photoalbum.setPaname(paname);
        photoalbum.setPadetail(padetail);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        photoalbum.setPacreatedate(time);
        photoalbum.setPalimit(palimit);
        User user = new User();
        user.setUserid(userid);
        photoalbum.setUserByUserid(user);
        photoalbum.setPaurl("https://photo-g11.oss-cn-hangzhou.aliyuncs.com/111111/pic-none.png");

        this.iPhotoalbumDao.addphotoalbum(photoalbum);

        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/deletephotoalbum")
    @ResponseBody
    private String deletephotoalbum(HttpServletRequest request) throws Exception {

        int paid = Integer.valueOf(request.getParameter("paid"));
        String userid = (String) request.getSession().getAttribute("userid");

        List<Photo> photos = this.iPhotoDao.loadallphoto(paid);
        for(int i = 0 ; i<photos.size();i++){
            this.iPhotoDao.deletephoto(photos.get(i).getPhotoid());
            User user = this.iUserDao.readuser(userid);
            double usercapacity = user.getUsercapacity() - photos.get(i).getSize();
            user.setUsercapacity(usercapacity);
            this.iUserDao.modifyuser(user);
            DecimalFormat df = new DecimalFormat("#.00");
            request.getSession().setAttribute("usercapacity", df.format(user.getUsercapacity()));
        }

//        this.iTitlepageDao.deleteTitlepage(paid);
        this.iPhotoalbumDao.deletephotoalbum(paid);


        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/modifyphotoalbum")
    @ResponseBody
    private String modifyphotoalbum(@RequestParam(value = "paid") int paid,
                                    @RequestParam(value = "paname") String paname,
                                    @RequestParam(value = "padetail") String padetail,
                                    @RequestParam(value = "palimit") String palimit) throws Exception {
        Photoalbum photoalbum = this.iPhotoalbumDao.readphotoalbum(paid);

        photoalbum.setPaid(paid);
        photoalbum.setPaname(paname);
        photoalbum.setPadetail(padetail);
        photoalbum.setPalimit(palimit);


        this.iPhotoalbumDao.modifyphotoalbum(photoalbum);

        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }


}
