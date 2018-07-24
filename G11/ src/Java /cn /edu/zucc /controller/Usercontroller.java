package cn.edu.zucc.controller;

import cn.edu.zucc.IDao.IPhotoalbumDao;
import cn.edu.zucc.IDao.IUserDao;
import cn.edu.zucc.bean.User;
import cn.edu.zucc.email.SendEmail;
import cn.edu.zucc.oss.OSSClientUtil;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class Usercontroller {

    @Resource(name = "userDao")
    private IUserDao iUserDao;

    @Resource(name = "photoalbumDao")
    private IPhotoalbumDao iPhotoalbumDao;

    @RequestMapping("/adduserresult")
    private String addUserResult(@RequestParam(value = "userid") String userid,
                                 @RequestParam(value = "username") String username,
                                 @RequestParam(value = "userpwd") String userpwd,
                                @RequestParam(value = "email") String email ) throws Exception {

        User user = new User();
        user.setUserid(userid);
        user.setUsername(username);
        user.setUserpwd(userpwd);
        user.setEmail(email);
        user.setUsercapacity(0);
        this.iUserDao.adduser(user);
        OSSClientUtil ossClientUtil = new OSSClientUtil();
        ossClientUtil.createFolder(ossClientUtil.getOSSClient(), "photo-g11", userid + "/");

        return "login";
    }

    @RequestMapping("/signup")
    @ResponseBody
    private String signup(@RequestParam(value = "userid") String userid) throws Exception {

        User user = this.iUserDao.readuser(userid);
        JSONObject json = new JSONObject();

        if (user != null) {
            json.put("result", 1);
        }

        return json.toString();
    }

    @RequestMapping("/email")
    @ResponseBody
    private String email(@RequestParam(value = "userid") String userid,
                          @RequestParam(value = "email", required = false) String email) throws Exception {

        User user = this.iUserDao.readuser(userid);
        JSONObject json = new JSONObject();

        if (user == null) {
            json.put("result", 1);
        }else {
            if (!user.getEmail().equals(email)) {
                json.put("result", 2);
            }
        }

        return json.toString();
    }

    @RequestMapping("/logincheck")
    @ResponseBody
    private String logincheck(HttpServletRequest request,
                              @RequestParam(value = "userid") String userid,
                              @RequestParam(value = "userpwd") String userpwd) throws Exception {

        User user = this.iUserDao.readuser(userid);
        JSONObject json = new JSONObject();

        if (user == null) {
            json.put("result", 1);
        } else {
            String password = user.getUserpwd();
            if (!password.equals(userpwd)) {
                json.put("result", 2);
            }
            request.getSession().setAttribute("obj",user);
            request.getSession().setAttribute("userid", userid);
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("usercapacity", user.getUsercapacity());

            request.getSession().setAttribute("photoalbum",this.iPhotoalbumDao.loadallphotoalbum(userid));
        }
        return json.toString();
    }

    @RequestMapping("/modifyuser")
    private String modifyuser(HttpServletRequest request) throws Exception {
        User user = this.iUserDao.readuser((String) request.getSession().getAttribute("userid"));

        request.setAttribute("obj", user);
        return "user_mdf";
    }

    @RequestMapping("/modifyuserresult")
    private String modifyuserresult(@RequestParam(value = "userid") String userid,
                                    @RequestParam(value = "userpwd") String userpwd) throws Exception {

        User user = this.iUserDao.readuser(userid);

        user.setUserpwd(userpwd);
        this.iUserDao.modifyuser(user);

        return "login";
    }

    @RequestMapping("/modifyusername")
    @ResponseBody
    private String modifyusername(HttpServletRequest request, @RequestParam(value = "userid") String userid,
                                  @RequestParam(value = "username") String username) throws Exception {

        User user = this.iUserDao.readuser(userid);
        user.setUsername(username);
        this.iUserDao.modifyuser(user);
        request.getSession().setAttribute("username", username);
        JSONObject json = new JSONObject();
        json.put("result", 1);

        return json.toString();
    }

    @RequestMapping("/sendemail")
    @ResponseBody
    private String sendemail(@RequestParam(value = "userid") String userid,
                             @RequestParam(value = "email")String email,
                             @RequestParam(value = "schema")String scheme,
                             @RequestParam(value = "servername")String servername,
                             @RequestParam(value = "serverport")String serverport){
        SendEmail sendEmail = new SendEmail();
        String p = scheme+"://"+servername+":"+serverport;
        String vc = sendEmail.sendemail(p,userid,email);
        JSONObject json = new JSONObject();
        json.put("result",vc);
        return json.toString();
    }

    @RequestMapping("/modifyuser1")
    private String modifyuser1(HttpServletRequest request) throws Exception {
        User user = this.iUserDao.readuser((String) request.getSession().getAttribute("userid"));

        request.setAttribute("obj", user);
        return "forget_pwdmdf";
    }
}
