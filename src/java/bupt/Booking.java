/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author holysjl
 */
@Entity
@Table(name = "booking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingNo", query = "SELECT b FROM Booking b WHERE b.bookingNo = :bookingNo"),
    @NamedQuery(name = "Booking.findByType", query = "SELECT b FROM Booking b WHERE b.type = :type"),
    @NamedQuery(name = "Booking.findByTel", query = "SELECT b FROM Booking b WHERE b.tel = :tel"),
    @NamedQuery(name = "Booking.findByCName", query = "SELECT b FROM Booking b WHERE b.cName = :cName"),
    @NamedQuery(name = "Booking.findByDateFrom", query = "SELECT b FROM Booking b WHERE b.dateFrom = :dateFrom"),
    @NamedQuery(name = "Booking.findByDateTo", query = "SELECT b FROM Booking b WHERE b.dateTo = :dateTo")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BookingNo")
    private Long bookingNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Tel")
    private String tel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CName")
    private String cName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date_From")
    @Temporal(TemporalType.DATE)
    private Date dateFrom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date_To")
    @Temporal(TemporalType.DATE)
    private Date dateTo;

    public Booking() {
    }

    public Booking(Long bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Booking(Long bookingNo, String type, String tel, String cName, Date dateFrom, Date dateTo) {
        this.bookingNo = bookingNo;
        this.type = type;
        this.tel = tel;
        this.cName = cName;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Long getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(Long bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingNo != null ? bookingNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingNo == null && other.bookingNo != null) || (this.bookingNo != null && !this.bookingNo.equals(other.bookingNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.Booking[ bookingNo=" + bookingNo + " ]";
    }
    
}
