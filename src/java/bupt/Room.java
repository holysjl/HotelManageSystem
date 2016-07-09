/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author holysjl
 */
@Entity
@Table(name = "room")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRNo", query = "SELECT r FROM Room r WHERE r.rNo = :rNo"),
    @NamedQuery(name = "Room.findByType", query = "SELECT r FROM Room r WHERE r.type = :type"),
    @NamedQuery(name = "Room.findByPrice", query = "SELECT r FROM Room r WHERE r.price = :price")})
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "RNo")
    private String rNo;
    @Size(max = 10)
    @Column(name = "Type")
    private String type;
    @Column(name = "Price")
    private Integer price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomRNo")
    private Collection<Record> recordCollection;

    public Room() {
    }

    public Room(String rNo) {
        this.rNo = rNo;
    }

    public String getRNo() {
        return rNo;
    }

    public void setRNo(String rNo) {
        this.rNo = rNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<Record> getRecordCollection() {
        return recordCollection;
    }

    public void setRecordCollection(Collection<Record> recordCollection) {
        this.recordCollection = recordCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rNo != null ? rNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.rNo == null && other.rNo != null) || (this.rNo != null && !this.rNo.equals(other.rNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bupt.Room[ rNo=" + rNo + " ]";
    }
    
}
