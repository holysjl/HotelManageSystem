/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author holysjl
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id AND c.recordRecordNo.recordNo= :recordNo"),
    @NamedQuery(name = "Customer.findBySex", query = "SELECT c FROM Customer c WHERE c.sex = :sex"),
    @NamedQuery(name = "Customer.findByTel", query = "SELECT c FROM Customer c WHERE c.tel = :tel"),
    @NamedQuery(name = "Customer.findByRecord", query = "SELECT c FROM Customer c WHERE c.recordRecordNo = :recordRecordNo"),
    @NamedQuery(name = "Customer.findByCName", query = "SELECT c FROM Customer c WHERE c.cName = :cName"),
    @NamedQuery(name = "Customer.findSDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate"),
    @NamedQuery(name = "Customer.findEDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE s.startDate<=:endDate"),
    @NamedQuery(name = "Customer.findNameSDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE p.cName=:cName AND s.endDate>= :startDate"),
    @NamedQuery(name = "Customer.findNameEDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE p.cName =:cName AND s.startDate<=:endDate"),
    @NamedQuery(name = "Customer.findSEDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate AND s.startDate<=:endDate"),
    @NamedQuery(name = "Customer.findNameSEDate1", query = "SELECT DISTINCT p FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate AND s.startDate<=:endDate AND p.cName=:cName"),
    @NamedQuery(name = "Customer.findName2",query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE p.cName=:cName"),
    @NamedQuery(name = "Customer.findSDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate"),
    @NamedQuery(name = "Customer.findEDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.startDate<=:endDate"),
    @NamedQuery(name = "Customer.findNameSDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate AND p.cName=:cName"),
    @NamedQuery(name = "Customer.findNameEDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.startDate<=:endDate AND p.cName=:cName"),
    @NamedQuery(name = "Customer.findSEDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate AND s.startDate<=:endDate"),
    @NamedQuery(name = "Customer.findNameSEDate2", query = "SELECT DISTINCT s FROM Customer p JOIN p.recordRecordNo s WHERE s.endDate>= :startDate AND s.startDate<=:endDate AND p.cName=:cName")})
public class Customer implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 18)
    @Column(name = "ID")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CName")
    private String cName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Sex")
    private String sex;
    @Size(max = 15)
    @Column(name = "Tel")
    private String tel;
    @JoinColumn(name = "record_RecordNo", referencedColumnName = "RecordNo")
    @ManyToOne(optional = false)
    private Record recordRecordNo;

    public Customer() {
    }

    public Customer(String id) {
        this.id = id;
    }

    public Customer(String id, String cName, String sex) {
        this.id = id;
        this.cName = cName;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Record getRecordRecordNo() {
        return recordRecordNo;
    }

    public void setRecordRecordNo(Record recordRecordNo) {
        this.recordRecordNo = recordRecordNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.Customer[ id=" + id + " ]";
    }


}
