package org.qf.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;

import static javax.print.attribute.standard.MediaPrintableArea.MM;

@Data                   //get  set 设置
@AllArgsConstructor     //有参构造
@NoArgsConstructor      //无参构造

@Table(name = "smbms_user")
public class SmbmsUser {

    @Id@KeySql(useGeneratedKeys = true) //id 主键自增
    private Long id;
    @Column(name = "userCode")
    private String userCode;
    @Column(name = "userName")
    private String userName;
    @Column(name = "userPassword")
    private String userPassword;
    @Column(name = "gender")
    private Integer gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;
    @Column(name = "userRole")
    private Integer userRole;
    @Column(name = "createdBy")
    private Long createdBy;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "modifyBy")
    private Long modifyBy;
    @Column(name = "modifyDate")
    private Date modifyDate;


    @Transient
    private String roleName;

    @Transient
    private Integer age;

    public Integer getAge(){
        //计算年龄
        Calendar newDate = Calendar.getInstance();

        Calendar birth =Calendar.getInstance();

        birth.setTime(birthday);

        return  newDate.get(Calendar.YEAR)-birth.get(Calendar.YEAR);

    }





}
