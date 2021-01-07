package org.qf.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsUserMapper;
import org.qf.pojo.SmbmsUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class SmbmsUserService {
    @Resource
    private SmbmsUserMapper smbmsUserMapper;

    @Resource
    private SmbmsRoleService smbmsRoleService;

    /**
     * 用户登录
     *
     * @param userCode
     * @param userPassword
     * @return
     */

    public SmbmsUser loginUser(String userCode, String userPassword) {
        SmbmsUser smbmsUser = new SmbmsUser();
        smbmsUser.setUserCode(userCode);
        smbmsUser.setUserPassword(userPassword);
        return smbmsUserMapper.selectOne(smbmsUser);
    }


    /**
     * 查询所有用户信息并分页
     *
     * @param userName
     * @param userRole
     * @param pageNum
     * @return
     */
    public PageInfo<SmbmsUser> queryList(String userName, Long userRole, Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        Example example = new Example(SmbmsUser.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(userName)) {
            criteria.andLike("userName", "%" + userName + "%");
        }
        if (userRole != null && userRole != 0) {
            criteria.andEqualTo("userRole", userRole);
        }
        List<SmbmsUser> smbmsUsers = smbmsUserMapper.selectByExample(example);
        bindUser(smbmsUsers);
        PageInfo<SmbmsUser> pageInfo = new PageInfo<SmbmsUser>(smbmsUsers);
        return pageInfo;
    }

    public void bindUser(List<SmbmsUser> smbmsUsers) {
        for (SmbmsUser smbmsUser : smbmsUsers) {
            smbmsUser.setRoleName(smbmsRoleService.queryById(smbmsUser.getUserRole()).getRoleName());
        }
    }


    /**
     * 查看用户表信息
     *
     * @param id
     * @return
     */
    public SmbmsUser queryById(long id) {
        SmbmsUser smbmsUser = smbmsUserMapper.selectByPrimaryKey(id);
        bindRole2(smbmsUser);
        return smbmsUser;

    }

    private void bindRole2(SmbmsUser smbmsUser) {
        smbmsUser.setRoleName(smbmsRoleService.queryById(smbmsUser.getUserRole()).getRoleName());
    }


    /**
     * 修改用户信息
     *
     * @param
     * @return
     */

    public SmbmsUser showUser(Long id) {
        SmbmsUser smbmsUser = smbmsUserMapper.selectByPrimaryKey(id);
        return smbmsUser;
    }

    public int updateUser(SmbmsUser smbmsUser) {
        return smbmsUserMapper.updateByPrimaryKeySelective(smbmsUser);
    }


    //添加用户
    public int addUser(SmbmsUser smbmsUser){
        return smbmsUserMapper.insertSelective(smbmsUser);
    }

    //通过用户编码 查询用户信息
    @Transactional
    public SmbmsUser queryByUserCode(String userCode){
        SmbmsUser smbmsUser =new SmbmsUser();
        smbmsUser.setUserCode(userCode);
        return smbmsUserMapper.selectOne(smbmsUser);
    }

    //删除用户

    public int deleteUser(Long id){
        return smbmsUserMapper.deleteByPrimaryKey(id);
    }



    /*修改用户密码模块*/
    public int updatePassword(SmbmsUser smbmsUser){
        return smbmsUserMapper.updateByPrimaryKey(smbmsUser);
    }




}