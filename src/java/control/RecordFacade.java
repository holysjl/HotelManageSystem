/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Record;
import bupt.Room;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author holysjl
 */
@Stateless
public class RecordFacade extends AbstractFacade<Record> {

    @PersistenceContext(unitName = "HotelManageSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecordFacade() {
        super(Record.class);
    }
    public List<Record> findRecord(Record r){
        Query query = em.createNamedQuery("Record.findRecord");
        query.setParameter("roomRNo",r.getRoomRNo());
        query.setParameter("startDate",r.getStartDate());
        return query.getResultList();
    }
    public List<Room> getAvailableRoom(String type){
        Date d = new Date();
        Query query = em.createNamedQuery("Record.findAvailableRoom");
        query.setParameter("type", type);
        query.setParameter("date", d);
        return query.getResultList();
    }
    public List<Room> findAllRoom(String type){
        Query query = em.createNamedQuery("Record.findAllRoom");
        query.setParameter("type", type);
        return query.getResultList();
    }
    
    public List<Record> findAllSelected(){
        Date d = new Date();
        Query query = em.createNamedQuery("Record.findAllSelected");
        query.setParameter("date", d);
        return query.getResultList();
    }
    public List<Record> findByRoomNo(String rNo){
        Date d = new Date();
        Query query = em.createNamedQuery("Record.findByRoomNo");
        query.setParameter("rNo", rNo);
        query.setParameter("date", d);
        return query.getResultList();
    
    }
    public List<Integer> findCash(String rNo){
        Date d = new Date();
        Query query = em.createNamedQuery("Record.findCash");
        query.setParameter("rNo", rNo);
        query.setParameter("date", d);
        return query.getResultList();
    
    }
        public List<Integer> findFee(String rNo){
        Date d = new Date();
        Query query = em.createNamedQuery("Record.findFee");
        query.setParameter("rNo", rNo);
        query.setParameter("date", d);
        return query.getResultList();
    
    }
    
    
}
