package org.qf.web;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsBillMapper;
import org.qf.mapper.SmbmsProviderMapper;
import org.qf.pojo.SmbmsBill;
import org.qf.pojo.SmbmsProvider;
import org.qf.pojo.SmbmsRole;
import org.qf.service.SmbmsBillService;
import org.qf.service.SmbmsProviderService;
import org.qf.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsBillController {

    @Resource
    private SmbmsBillService smbmsBillService;
    @Resource
    private SmbmsProviderService smbmsProviderService;
    @Resource
    private SmbmsProviderMapper smbmsProviderMapper;
    @Resource
    private SmbmsBillMapper smbmsBillMapper;

    //查询订单并分页
    @RequestMapping("/billList")
    public String billList(Model model, @RequestParam(defaultValue = "1") Integer pageIndex ,String queryProductName,Long queryProviderId,Integer queryIsPayment){
        PageInfo<SmbmsBill> pageInfo = smbmsBillService.findbill(pageIndex,queryProductName,queryProviderId,queryIsPayment);

        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("queryProductName",queryProductName);
        model.addAttribute("providerList",smbmsProviderService.queryProvider());
        model.addAttribute("queryIsPayment",queryIsPayment);
        return "jsp/billlist";
    }



    /**
     * 订单信息详情查询
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewBill")
    public String billview(Long id,Model model){
        SmbmsBill bill = smbmsBillMapper.selectByPrimaryKey(id);
        SmbmsProvider smbmsProvider = smbmsProviderMapper.selectByPrimaryKey(bill.getProviderId());
        bill.setProviderName(smbmsProvider.getProName());
        model.addAttribute("bill",bill);
        return "jsp/billview";
    }


    /**
     * 订单信息修改
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/billModify")
    public String billmodify(Long id, Model model){
        SmbmsBill bill = smbmsBillMapper.selectByPrimaryKey(id);
        SmbmsProvider smbmsProvider = smbmsProviderMapper.selectByPrimaryKey(bill.getProviderId());
       // bill.setProvidername(smbmsProvider.getProname());
        System.out.println(smbmsProvider.getProName());
        model.addAttribute("bill",bill);
        return "jsp/billmodify";
    }


    /**
     * 订单更新
     * @param id
     * @param bill
     * @return
     */
    @RequestMapping("/updateBill")
    public  String modifysuccess(int id,SmbmsBill bill){
        SmbmsBill smbmsBill = smbmsBillMapper.selectByPrimaryKey(id);
        smbmsBill.setBillCode(bill.getBillCode());
        smbmsBill.setProductName(bill.getProductName());
        smbmsBill.setProductUnit(bill.getProductUnit());
        smbmsBill.setProductCount(bill.getProductCount());
        smbmsBill.setTotalPrice(bill.getTotalPrice());
        smbmsBill.setProviderId(bill.getProviderId());
        System.out.println("账单状态:"+bill.getIsPayment());
        smbmsBill.setIsPayment(bill.getIsPayment());
        smbmsBillService.updatebill(smbmsBill);
        return "redirect:/billList";
    }


    /**
     * 订单添加
     * @param smbmsBill
     * @return
     */
    @RequestMapping("addBill")
    public String addbill(SmbmsBill smbmsBill){
        smbmsBill.setCreationDate(new Date());
        smbmsBillService.addbill(smbmsBill);
        return "redirect:/billList";
    }




    /**
     * 订单删除
     * @param id
     * @return
     */
    @RequestMapping("/deleteBill")
    @ResponseBody
    public JsonResult deleteBill(int id){
        int i = smbmsBillService.deletebill(id);
        if(i>0){
            return  new JsonResult().message("删除成功").success(true);
        }
        else {
            return  new JsonResult().message("删除失败").success(false);
        }
    }


}
