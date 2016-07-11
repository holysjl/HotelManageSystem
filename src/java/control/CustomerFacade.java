/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Booking;
import bupt.Customer;
import bupt.Record;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author holysjl
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "HotelManageSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    public List<Customer> findCustomer(){
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
        return query.getResultList();
    }
    public List<Customer> findByRecordNo(Record recordNo){
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findByRecord", Customer.class);
        query.setParameter("recordRecordNo",recordNo);
        return query.getResultList();
    }
    
}
