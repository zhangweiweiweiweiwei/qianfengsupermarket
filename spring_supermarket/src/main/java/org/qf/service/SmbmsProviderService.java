package org.qf.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsProviderMapper;
import org.qf.pojo.SmbmsProvider;
import org.qf.pojo.SmbmsRole;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SmbmsProviderService {
    @Resource
    private SmbmsProviderMapper smbmsProviderMapper;


    /**
     * 查询所有供应商并分页
     * @param queryProName
     * @param queryProCode
     * @param pageNum
     * @return
     */
   public PageInfo<SmbmsProvider> finduser(String queryProCode,String queryProName,Integer pageNum){
       PageHelper.startPage(pageNum,5);
       Example example=new Example(SmbmsProvider.class);
       Example.Criteria criteria=example.createCriteria();
       if(!StringUtils.isEmpty(queryProCode)){
           criteria.andLike("proCode","%"+queryProCode+"%");
       }
       if(!StringUtils.isEmpty(queryProName)){
           criteria.andLike("proName","%"+queryProName+"%");
       }

       List<SmbmsProvider> providers = smbmsProviderMapper.selectByExample(example);
       PageInfo<SmbmsProvider> pageInfo =new PageInfo<SmbmsProvider>(providers);
       return  pageInfo;
   }


   public SmbmsProvider queryProviderById(Long id){
       SmbmsProvider smbmsProvider = smbmsProviderMapper.selectByPrimaryKey(id);
       return smbmsProvider;
   }

   //供应商下拉框
   public List<SmbmsProvider> queryProvider(){
       List<SmbmsProvider> providers = smbmsProviderMapper.selectAll();
       return providers;
   }

   public int addProvider(SmbmsProvider smbmsProvider){
       return smbmsProviderMapper.insertSelective(smbmsProvider);
   }
   public SmbmsProvider providerview(Long id){
       SmbmsProvider provider = smbmsProviderMapper.selectByPrimaryKey(id);
       return provider;
   }
   public SmbmsProvider modifyprovider(Long id){
       SmbmsProvider provider = smbmsProviderMapper.selectByPrimaryKey(id);
       return provider;
   }
   public void updateprovider(SmbmsProvider smbmsProvider){
       smbmsProviderMapper.updateByPrimaryKey(smbmsProvider);
   }
   public  int deleteprovider(long id){
       int i = smbmsProviderMapper.deleteByPrimaryKey(id);
       System.out.println("成功删除"+i);
       return i;
   }
   public List<SmbmsProvider> queryallprovider(){
       List<SmbmsProvider> providers = smbmsProviderMapper.selectAll();
       return providers;
   }


}
