package org.qf.web;

import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsProviderMapper;
import org.qf.pojo.SmbmsProvider;
import org.qf.pojo.SmbmsUser;
import org.qf.service.SmbmsProviderService;
import org.qf.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsProviderController {
    @Resource
    private SmbmsProviderService smbmsProviderService;
    @Resource
    private SmbmsProviderMapper smbmsProviderMapper;

    //查询并分页
    @RequestMapping("/providerList")
    public String providerList(Model model,String queryProCode,String queryProName,@RequestParam(defaultValue = "1") Integer pageIndex){
        System.out.println(queryProCode);
        PageInfo<SmbmsProvider> pageInfo = smbmsProviderService.finduser(queryProCode,queryProName,pageIndex);
        System.out.println(pageInfo);

        model.addAttribute("pageInfo",pageInfo);

        return "jsp/providerlist";
    }



    @RequestMapping("/queryprovider")
    @ResponseBody
    public List<SmbmsProvider> queryprovider(){
        return smbmsProviderService.queryallprovider();
    }




    /**
     * 供应商信息详情查看
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewProvider")
    public String viewProvider( Long id,Model model){
        SmbmsProvider providerview = smbmsProviderService.providerview(id);
        model.addAttribute("provider",providerview);
        return "jsp/providerview";
    }


    /**
     * 修改供应商
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/providerModify")
    public String modifyprovider( Long id,Model model){
        SmbmsProvider modifyprovider = smbmsProviderService.modifyprovider(id);
        model.addAttribute("provider",modifyprovider);
        return "jsp/providermodify";
    }
    @RequestMapping("/updateProvider")
    public String updateProvider(@RequestParam(name = "id") Long id,SmbmsProvider smbmsProvider){
        SmbmsProvider provider = smbmsProviderMapper.selectByPrimaryKey(id);
        provider.setProCode(smbmsProvider.getProCode());
        provider.setProName(smbmsProvider.getProName());
        provider.setProContact(smbmsProvider.getProContact());
        provider.setProPhone(smbmsProvider.getProPhone());
        provider.setProAddress(smbmsProvider.getProAddress());
        provider.setProFax(smbmsProvider.getProFax());
        provider.setProDesc(smbmsProvider.getProDesc());
        smbmsProviderService.updateprovider(provider);
        return "redirect:/providerList";
    }


    /**
     * 添加供应商
     * @param smbmsProvider
     * @param session
     * @return
     */
    @RequestMapping("/addProvider")
    public String addProvider(SmbmsProvider smbmsProvider, HttpSession session){
        System.out.println("----------------------");
        SmbmsUser s=(SmbmsUser)session.getAttribute("user");
        smbmsProvider.setCreationDate(new Date());
        smbmsProvider.setCreatedBy(s.getId());
        int i =smbmsProviderService.addProvider(smbmsProvider);
        System.out.println(i);
        if (i>0){
            return "redirect:/providerList";
        }
        return "jsp/provideradd";
    }


    /**
     * 删除供应商
     * @param id
     * @return
     */
    @RequestMapping("/deleteProvider")
    @ResponseBody
    public JsonResult deleteProvider(Long id){
        int i =smbmsProviderService.deleteprovider(id);
        if(1!=0){
            return new JsonResult().message("删除成功").success(true);
        }
        return new JsonResult().message("删除失败").success(false);
    }

    @RequestMapping("/showProvider")
    @ResponseBody
    public List<SmbmsProvider> showProviderName(){
        List<SmbmsProvider> smbmsProviders = smbmsProviderService.queryProvider();
        return smbmsProviders;
    }

































/*    @RequestMapping("/queryByCode")
    @ResponseBody
    public JsonResult queryUserCode(String usercode){
        SmbmsUser smbmsUser = smbmsUserService.queryUserByCode(usercode);
        if(smbmsUser!=null){
            return  new JsonResult().message("已存在，不可用").success(true);
        }
        else {
            return new JsonResult().message("该账号可以使用").success(false);
        }
    }*/
//    @RequestMapping("deleteprovider")
//    @ResponseBody
//    public  JsonResult deleteprovider(int proid){
//        int i = smbmsProviderService.deleteprovider(proid);
//        if(i>0){
//            return  new JsonResult().message("删除成功").delResult("true");
//        }
//        else {
//            return  new JsonResult().message("删除失败").delResult("false");
//        }
//    }


}
