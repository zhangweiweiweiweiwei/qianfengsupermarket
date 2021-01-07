package org.qf.web;
import com.github.pagehelper.PageInfo;
import org.qf.pojo.SmbmsRole;
import org.qf.pojo.SmbmsUser;
import org.qf.service.SmbmsRoleService;
import org.qf.service.SmbmsUserService;
import org.qf.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsUserController {
    @Resource
    private SmbmsUserService smbmsUserService;
    @Resource
    private SmbmsRoleService smbmsRoleService;

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/login")  //login文件
    public String login(String userCode, String userPassword, Model model, HttpSession session){
        SmbmsUser smbmsUser = smbmsUserService.loginUser(userCode, userPassword);
        if(smbmsUser!=null){
            System.out.println(smbmsUser);
           session.setAttribute("user",smbmsUser);
           return  "redirect:jsp/frame.jsp";
//            return "jsp/frame";
        }
        return "index";
    }

    /**
     * 首部退出及退出系统
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        //1.session销毁
        session.removeAttribute("user");
        return "redirect:/login.jsp";
    }

    /**
     * 查询并分页  userlist文件
     * @param model
     * @param queryname
     * @param queryUserRole
     * @param pageIndex
     * @return
     */
    @RequestMapping("/userList")
    public String queryList(Model model, String queryname,Long queryUserRole,@RequestParam(defaultValue = "1") Integer  pageIndex){
        PageInfo<SmbmsUser> pageInfo =smbmsUserService.queryList(queryname,queryUserRole,pageIndex);
        List<SmbmsRole> smbmsRoles = smbmsRoleService.queryRole();
        model.addAttribute("queryname",queryname);
        model.addAttribute("queryUserRole",queryUserRole);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("roleList",smbmsRoles);
        return "jsp/userlist";
    }


    /**
     *用户信息详情查看
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/viewUser")
    public  String viewUser(Long id,Model model){
        SmbmsUser smbmsUser =smbmsUserService.queryById(id);
        model.addAttribute(smbmsUser);
        return "jsp/userview";

    }

    /**
     * 修改查询
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/userModify")
    public String showUser(Long id,Model model){
        SmbmsUser smbmsUser = smbmsUserService.showUser(id);
        model.addAttribute("user",smbmsUser);
        return "jsp/usermodify";
    }

    /**
     * 更新用户信息
     * @param smbmsUser
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(SmbmsUser smbmsUser){
        int i = smbmsUserService.updateUser(smbmsUser);
        if(i>0){
            return "redirect:userList";
        }
        return "jsp/usermodify";
    }


    /**
     * 显示角色（ajax）
     * @return
     */
    @RequestMapping("/showRole")
    @ResponseBody
    public List<SmbmsRole> showRoleName(){
        List<SmbmsRole> smbmsRoles = smbmsRoleService.queryRole();
        return smbmsRoles;
    }


    /**
     * 添加用户信息
     * @param smbmsUser
     * @param session
     * @return
     */
    @RequestMapping("/addUser")
    public String addUser(SmbmsUser smbmsUser,HttpSession session){
        SmbmsUser user = (SmbmsUser) session.getAttribute("user");
        smbmsUser.setCreatedBy(user.getId());
        smbmsUser.setCreationDate(new Date());

        int i = smbmsUserService.addUser(smbmsUser);
        if (i>0){
            return "redirect:/userList";
        }
        return "jsp/useradd";

    }

    /**
     * 检查用户是否已存在
     * @param userCode
     * @return
     */
    @RequestMapping("/queryByUserCode")
    @ResponseBody
    public JsonResult selectUserByCode(String userCode){
        SmbmsUser smbmsUser =smbmsUserService.queryByUserCode(userCode);
        if(smbmsUser!=null){
            return new JsonResult().message("用户不可用").success(true);
        }
        return new JsonResult().message("可用").success(false);
    }


    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public JsonResult deleteUser(Long id){
        int i =smbmsUserService.deleteUser(id);
        if(1!=0){
            return new JsonResult().message("删除成功").success(true);
        }
        return new JsonResult().message("删除失败").success(false);
    }


    /*修改密码模块*/
    @RequestMapping("/newPassword")
    public String  updatePassword(String oldpassword,String newpassword,String renewpassword,HttpSession session){
        System.out.println("111111111111111111111");
        SmbmsUser user = (SmbmsUser)session.getAttribute("user");
        user.setUserPassword(newpassword);
       int i = smbmsUserService.updatePassword(user);
       if(i!=0){
           return "login";
       }
        return "jsp/pwdmodify";
    }


    @RequestMapping("/selectpwd")
    @ResponseBody
    public JsonResult selectpassword(String oldpassword,HttpSession session){
        SmbmsUser user = (SmbmsUser)session.getAttribute("user");
        System.out.println(user);
        String u= user.getUserPassword();
        System.out.println("用户的密码"+u);
        if(u.equals(oldpassword)){
            System.out.println("-----------------");
            return new JsonResult().message("密码正确").success(true);
        }
        else {
            return new JsonResult().message("密码错误").success(false);
        }
    }


}