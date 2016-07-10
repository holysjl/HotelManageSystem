/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Record;
import bupt.Room;
import bupt.util.JsfUtil;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    
    public void addNew(Record record){
        em.persist(record);
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
    
}
