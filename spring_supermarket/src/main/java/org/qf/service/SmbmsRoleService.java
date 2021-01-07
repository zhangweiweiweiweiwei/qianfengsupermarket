package org.qf.service;
import org.qf.mapper.SmbmsRoleMapper;
import org.qf.pojo.SmbmsRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SmbmsRoleService {

    @Resource
    private SmbmsRoleMapper smbmsRoleMapper;

    public SmbmsRole queryById(long id){
        return smbmsRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有的角色
     * @return
     */
    public List<SmbmsRole> queryRole(){
        return smbmsRoleMapper.selectAll();
    }


}