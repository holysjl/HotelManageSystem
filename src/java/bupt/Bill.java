/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author holysjl
 */
@Entity
@Table(name = "bill")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b"),
    @NamedQuery(name = "Bill.findByRecordRecordNo", query = "SELECT b FROM Bill b WHERE b.recordRecordNo = :recordRecordNo"),
    @NamedQuery(name = "Bill.findByExtraFee", query = "SELECT b FROM Bill b WHERE b.extraFee = :extraFee"),
    @NamedQuery(name = "Bill.findByTotalFee", query = "SELECT b FROM Bill b WHERE b.totalFee = :totalFee")})
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_RecordNo")
    private Long recordRecordNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Extra_Fee")
    private int extraFee;
    @Column(name = "Total_Fee")
    private Integer totalFee;
    @JoinColumn(name = "record_RecordNo", referencedColumnName = "RecordNo", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Record record;

    public Bill() {
    }

    public Bill(Long recordRecordNo) {
        this.recordRecordNo = recordRecordNo;
    }

    public Bill(Long recordRecordNo, int extraFee) {
        this.recordRecordNo = recordRecordNo;
        this.extraFee = extraFee;
    }

    public Long getRecordRecordNo() {
        return recordRecordNo;
    }

    public void setRecordRecordNo(Long recordRecordNo) {
        this.recordRecordNo = recordRecordNo;
    }

    public int getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(int extraFee) {
        this.extraFee = extraFee;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordRecordNo != null ? recordRecordNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.recordRecordNo == null && other.recordRecordNo != null) || (this.recordRecordNo != null && !this.recordRecordNo.equals(other.recordRecordNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.Bill[ recordRecordNo=" + recordRecordNo + " ]";
    }
    
}
