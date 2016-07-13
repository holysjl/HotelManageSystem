package bupt;

import bupt.util.JsfUtil;
import bupt.util.PaginationHelper;
import control.RecordFacade;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("recordController")
@SessionScoped
public class RecordController implements Serializable {


    private Record current;
    private List<Record> items = null;
    private List<Record> records;
    private String type;
    private List<Room> rooms;
    private String roomNo;
    private List<Record> checkrecords;
    private List<Integer> checkcash;
    private List<Integer> checkfee;
    private Room testroom;
    @EJB
    private control.RecordFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public RecordController() {
    }

    public Record getSelected() {
        if (current == null) {
            current = new Record();
            selectedItemIndex = -1;
            current.setRecordNo(1L);
        }
        return current;
    }
    public Room getTestroom(){
        return testroom;
    }
    public void setTestroom(Room r){
        testroom = r;
    }
    public void testRoom() throws ParseException{
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = new Date();
            Calendar date = Calendar.getInstance();
	    date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 100);
	    Date endDate = dft.parse(dft.format(date.getTime()));
            current.setCashPledge(0);
            current.setPaidFee(0);
            current.setStartDate(endDate);
            current.setEndDate(endDate);
            try {
                getFacade().create(current);
                findRecord();
                findAvailableRoom();
                //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("添加房间成功"));
                //return "roomManage";
            } catch (Exception e) {
                //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("添加房间失败"));
                //return null;
            }
            
    }
    
    public String endProgram(){
        current = new Record();
        selectedItemIndex = -1;
        return "Home";
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getRoomNo(){
        return roomNo;
    }
    public void setRoomNo(String roomNo){
        this.roomNo = roomNo;
    }
    public List<Record> getCheckrecords(){
        return checkrecords;
    }
    public List<Integer> getCheckcash(){
        return checkcash;
    }
    public List<Integer> getCheckfee(){
        return checkfee;
    }
    
    public void searchRecord(){
        checkrecords = ejbFacade.findByRoomNo(roomNo);
        checkfee = ejbFacade.findFee(roomNo);
        checkcash = ejbFacade.findCash(roomNo);
    }
    
    public List<Room> getRooms(){
        return rooms;
    }
    public void setRooms(List<Room> rooms){
        this.rooms = rooms;
    }
    
  
    
    public void findAvailableRoom(){
        rooms=ejbFacade.findAllRoom(type);
        rooms.removeAll(ejbFacade.getAvailableRoom(type));
    }

    public void findRecord(){
        records= ejbFacade.findRecord(current);
    }
    public List<Record> getRecords(){
        //records= ejbFacade.findRecord(current);
        return records;
    }
    
    private RecordFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }


    public void create() {
     
        try {
            //records= ejbFacade.findRecord(current);
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = new Date();
            Calendar date = Calendar.getInstance();
	    date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
	    Date endDate1 = dft.parse(dft.format(date.getTime()));
            date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) + 1);
	    Date endDate2 = dft.parse(dft.format(date.getTime()));
            
            if (current.getStartDate().after(endDate1) && current.getStartDate().before(endDate2) && current.getEndDate().after(current.getStartDate())){
                getFacade().create(current);
                findRecord();
                findAvailableRoom();
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecordCreated"));
            }
            else {
                Exception e = new Exception();
                throw e;
            }
            //return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            //return null;
        }
        
       
    }

    public String update() {
        try {
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = new Date();
            Calendar date = Calendar.getInstance();
	    date.setTime(beginDate);
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
	    Date endDate = dft.parse(dft.format(date.getTime()));
            Record checkrecord = checkrecords.get(0);
            checkrecord.setEndDate(endDate);
            
            getFacade().edit(checkrecord);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecordUpdated"));
            return "Home";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));

            return "Home";
        }
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecordDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public List<Record> getItems() {
        items = ejbFacade.findAllSelected();
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Record getRecord(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Record.class)
    public static class RecordControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RecordController controller = (RecordController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "recordController");
            return controller.getRecord(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Record) {
                Record o = (Record) object;
                return getStringKey(o.getRecordNo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Record.class.getName());
            }
        }

    }

}
