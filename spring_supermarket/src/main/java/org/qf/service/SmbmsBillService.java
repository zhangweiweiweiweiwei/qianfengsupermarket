package org.qf.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsBillMapper;
import org.qf.pojo.SmbmsBill;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;


@Service
public class SmbmsBillService {
    @Resource
    private SmbmsBillMapper smbmsBillMapper;
    @Resource
    private SmbmsProviderService smbmsProviderService;


    /**
     * 查询所有订单信息并分页
     * @param start
     * @param queryProductName
     * @param queryProviderId
     * @param queryIsPayment
     * @return
     */
    public PageInfo<SmbmsBill> findbill(Integer start, String queryProductName, Long queryProviderId, Integer queryIsPayment){
        PageHelper.startPage(start,5);
        Example example =new Example(SmbmsBill.class);
        Example.Criteria criteria=example.createCriteria();
        if(!StringUtils.isEmpty(queryProductName)){
            criteria.andLike("productName","%"+queryProductName+"%");
        }
        if(queryProviderId!=null && queryProviderId!=0){
            criteria.andEqualTo("providerId",queryProviderId);
        }
        if(queryIsPayment!=null && queryIsPayment!=0){
            criteria.andEqualTo("isPayment",queryIsPayment);
        }
        List<SmbmsBill> smbmsBills = smbmsBillMapper.selectByExample(example);
        bindbill(smbmsBills);
        PageInfo<SmbmsBill> pageInfo=new PageInfo<SmbmsBill>(smbmsBills);
        return pageInfo;
    }
    public void bindbill(List<SmbmsBill> smbmsBill){
        for(SmbmsBill smbmsBill1:smbmsBill){
            smbmsBill1.setProviderName(smbmsProviderService.queryProviderById(smbmsBill1.getProviderId()).getProName());
            System.out.println("供应商名字"+smbmsProviderService.queryProviderById(smbmsBill1.getProviderId()));
        }
    }



    public void addbill(SmbmsBill smbmsBill){
        smbmsBillMapper.insertSelective(smbmsBill);
    }
    public int deletebill(long id){
        return smbmsBillMapper.deleteByPrimaryKey(id);
    }
    public void updatebill(SmbmsBill smbmsBill){
        smbmsBillMapper.updateByPrimaryKey(smbmsBill);
    }



}
