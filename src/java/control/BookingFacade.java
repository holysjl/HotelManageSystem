/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Booking;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author holysjl
 */
@Stateless
public class BookingFacade extends AbstractFacade<Booking> {

    @PersistenceContext(unitName = "HotelManageSystemPU")
    private EntityManager em;
    
    private Date today = new Date();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookingFacade() {
        super(Booking.class);
    }
    public List<Booking> findBooking(){
        Date date= new Date();
        TypedQuery<Booking> query = em.createNamedQuery("Booking.findAll", Booking.class);
        query.setParameter("date", date);
        return query.getResultList();
    }
    
    
    public List<Booking> searchByName(String name){
        Query q = em.createNamedQuery("Booking.findByCName",Booking.class);
        q.setParameter("cName", name);
        q.setParameter("today", today);
        return q.getResultList();
    }
    
    public List<Booking> searchByTel(String tel){
        Query q = em.createNamedQuery("Booking.findByTel",Booking.class);
        q.setParameter("tel", tel);
        q.setParameter("today", today);
        return q.getResultList();
    }
    
    public List<Booking> searchByNameTel(String name, String tel){
        Query q = em.createNamedQuery("Booking.findByNameTel",Booking.class);
        q.setParameter("cName", name);
        q.setParameter("tel", tel);
        q.setParameter("today", today);
        return q.getResultList();
    }
}
