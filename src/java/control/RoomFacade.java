/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bupt.Record;
import bupt.Room;
import bupt.util.JsfUtil;
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
public class RoomFacade extends AbstractFacade<Room> {

    @PersistenceContext(unitName = "HotelManageSystemPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoomFacade() {
        super(Room.class);
    }
    //public List<String> findRoomNo(Room r){
      //  Query query = em.createNamedQuery("Room.findNoByType");
      //  query.setParameter("type",r.getType());
       // return query.getResultList();
    //}
    
    public List<Room> findRoomByNo(String s){
        Query query = em.createNamedQuery("Room.findByRNo");
        query.setParameter("rNo",s);
        return query.getResultList();
    }
    public List<Room> findRoomNo(Room r){
        Query query = em.createNamedQuery("Room.findByType");
        query.setParameter("type",r.getType());
        return query.getResultList();
    }
}
