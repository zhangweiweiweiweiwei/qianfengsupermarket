package org.qf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色实体类 :封装 @Data（lombok） 包含了get和set方法
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "smbms_role")
public class SmbmsRole implements Serializable {
    @Id @KeySql(useGeneratedKeys = true) //id 主键自增
    private Long id;
    @Column(name = "roleCode")
    private String roleCode;
    @Column(name = "roleName")
    private String roleName;
    @Column(name = "createdBy")
    private Long createdBy;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "modifyBy")
    private Long modifyBy;
    @Column(name = "modifyDate")
    private Date modifyDate;
}