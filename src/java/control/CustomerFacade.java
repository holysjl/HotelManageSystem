/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Booking;
import bupt.Customer;
import bupt.Record;
import java.text.SimpleDateFormat;
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

    
    
     public List<Customer> searchByName1(String name) {
        Query q = em.createNamedQuery("Customer.findByCName", Customer.class);
        q.setParameter("cName", name);
        return q.getResultList();
    }

    public List<Record> searchByName2(String name) {
        Query q = em.createNamedQuery("Customer.findName2", Customer.class);
        q.setParameter("cName", name);
        return q.getResultList();
    }

    public List<Customer> searchBySDate1(Date sdate) {
        Query q = em.createNamedQuery("Customer.findSDate1", Customer.class);
        q.setParameter("startDate", sdate);
        return q.getResultList();
    }

    public List<Record> searchBySDate2(Date sdate) {
        Query q = em.createNamedQuery("Customer.findSDate2", Customer.class);
        q.setParameter("startDate", sdate);
        return q.getResultList();
    }

    public List<Customer> searchByEDate1(Date edate) {
        Query q = em.createNamedQuery("Customer.findEDate1", Customer.class);
        q.setParameter("endDate", edate);
        return q.getResultList();
    }

    public List<Record> searchByEDate2(Date edate) {
        Query q = em.createNamedQuery("Customer.findEDate2", Customer.class);
        q.setParameter("endDate", edate);
        return q.getResultList();
    }

    public List<Customer> searchByNameSDate1(String cName, Date sdate) {
        Query q = em.createNamedQuery("Customer.findNameSDate1", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("startDate", sdate);
        return q.getResultList();
    }

    public List<Record> searchByNameSDate2(String cName, Date sdate) {
        Query q = em.createNamedQuery("Customer.findNameSDate2", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("startDate", sdate);
        return q.getResultList();
    }

    public List<Customer> searchByNameEDate1(String cName, Date edate) {
        Query q = em.createNamedQuery("Customer.findNameEDate1", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("endDate", edate);
        return q.getResultList();
    }

    public List<Record> searchByNameEDate2(String cName, Date edate) {
        Query q = em.createNamedQuery("Customer.findNameEDate2", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("endDate", edate);
        return q.getResultList();
    }

    public List<Customer> searchBySEDate1(Date sDate, Date eDate) {
        Query q = em.createNamedQuery("Customer.findSEDate1", Customer.class);
        q.setParameter("startDate", sDate);
        q.setParameter("endDate", eDate);
        return q.getResultList();
    }

    public List<Record> searchBySEDate2(Date sDate, Date eDate) {
        Query q = em.createNamedQuery("Customer.findSEDate2", Customer.class);
        q.setParameter("startDate", sDate);
        q.setParameter("endDate", eDate);
        return q.getResultList();
    }

    public List<Customer> searchByNameSEDate1(String cName, Date sDate, Date eDate) {
        Query q = em.createNamedQuery("Customer.findNameSEDate1", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("startDate", sDate);
        q.setParameter("endDate", eDate);
        return q.getResultList();
    }

    public List<Record> searchByNameSEDate2(String cName, Date sDate, Date eDate) {
        Query q = em.createNamedQuery("Customer.findNameSEDate2", Customer.class);
        q.setParameter("cName", cName);
        q.setParameter("startDate", sDate);
        q.setParameter("endDate", eDate);
        return q.getResultList();
    }
    
}
