package org.qf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="smbms_provider")
public class SmbmsProvider {
    //id 主键自增
    @Id@KeySql(useGeneratedKeys = true)
    private Long id;
    @Column(name = "proCode")
    private String proCode;
    @Column(name = "proName")
    private String proName;
    @Column(name = "proDesc")
    private String proDesc;
    @Column(name = "proContact")
    private String proContact;
    @Column(name = "proPhone")
    private String proPhone;
    @Column(name = "proAddress")
    private String proAddress;
    @Column(name = "proFax")
    private String proFax;
    @Column(name = "createdBy")
    private Long createdBy;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "modifyDate")
    private Date modifyDate;
    @Column(name = "modifyBy")
    private long modifyBy;


}