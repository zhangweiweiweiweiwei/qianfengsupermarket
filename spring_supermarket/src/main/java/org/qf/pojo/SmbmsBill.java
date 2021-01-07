package org.qf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "smbms_bill")
public class SmbmsBill {
    @Id @KeySql(useGeneratedKeys = true) //id 主键自增
    private Long id;
    @Column(name = "billCode")
    private String billCode;
    @Column(name = "productName")
    private String productName;
    @Column(name = "productDesc")
    private String productDesc;
    @Column(name = "productUnit")
    private String productUnit;
    @Column(name = "productCount")
    private BigDecimal productCount;
    @Column(name = "totalPrice")
    private BigDecimal totalPrice;
    @Column(name = "isPayment")
    private Integer isPayment;
    @Column(name = "createdBy")
    private long createdBy;
    @Column(name = "creationDate")
    private Date creationDate;
    @Column(name = "modifyBy")
    private long modifyBy;
    @Column(name = "modifyDate")
    private Date modifyDate;
    @Column(name = "providerId")
    private Long providerId;

    @Transient
    private String providerName;

    @Override
    public String toString() {
        return "SmbmsBill{" +
                "id=" + id +
                ", billcode='" + billCode + '\'' +
                ", productname='" + productName + '\'' +
                ", productdesc='" + productDesc + '\'' +
                ", productunit='" + productUnit + '\'' +
                ", productcount=" + productCount +
                ", totalprice=" + totalPrice +
                ", ispayment=" + isPayment +
                ", createdBy=" + createdBy +
                ", creationdate=" + creationDate +
                ", modifyBy=" + modifyBy +
                ", modifyDate=" + modifyDate +
                ", providerid=" + providerId +
                ", providerName='" + providerName + '\'' +
                '}';
    }


}