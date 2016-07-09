/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author holysjl
 */
@Entity
@Table(name = "record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Record.findAll", query = "SELECT r FROM Record r"),
    @NamedQuery(name = "Record.findByRecordNo", query = "SELECT r FROM Record r WHERE r.recordNo = :recordNo"),
    @NamedQuery(name = "Record.findByStartDate", query = "SELECT r FROM Record r WHERE r.startDate = :startDate"),
    @NamedQuery(name = "Record.findByEndDate", query = "SELECT r FROM Record r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "Record.findByPaidFee", query = "SELECT r FROM Record r WHERE r.paidFee = :paidFee"),
    @NamedQuery(name = "Record.findByCashPledge", query = "SELECT r FROM Record r WHERE r.cashPledge = :cashPledge"),
})
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RecordNo")
    private Long recordNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Start_Date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "End_Date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Paid_Fee")
    private int paidFee;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cash_Pledge")
    private int cashPledge;
    @JoinColumn(name = "Room_RNo", referencedColumnName = "RNo")
    @ManyToOne(optional = false)
    private Room roomRNo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "record")
    private Bill bill;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordRecordNo")
    private Collection<Customer> customerCollection;

    public Record() {
    }

    public Record(Long recordNo) {
        this.recordNo = recordNo;
    }

    public Record(Long recordNo, Date startDate, Date endDate, int paidFee, int cashPledge) {
        this.recordNo = recordNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paidFee = paidFee;
        this.cashPledge = cashPledge;
    }

    public Long getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(Long recordNo) {
        this.recordNo = recordNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(int paidFee) {
        this.paidFee = paidFee;
    }

    public int getCashPledge() {
        return cashPledge;
    }

    public void setCashPledge(int cashPledge) {
        this.cashPledge = cashPledge;
    }

    public Room getRoomRNo() {
        return roomRNo;
    }

    public void setRoomRNo(Room roomRNo) {
        this.roomRNo = roomRNo;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

     

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordNo != null ? recordNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Record)) {
            return false;
        }
        Record other = (Record) object;
        if ((this.recordNo == null && other.recordNo != null) || (this.recordNo != null && !this.recordNo.equals(other.recordNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.Record[ recordNo=" + recordNo + " ]";
    }
    
}
