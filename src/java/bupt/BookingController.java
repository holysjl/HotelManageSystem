package bupt;

import bupt.util.JsfUtil;
import bupt.util.PaginationHelper;
import control.BookingFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("bookingController")
@SessionScoped
public class BookingController implements Serializable {

    private Booking current;
    //private DataModel items = null;
    private List<Booking> items;
    
    private String name;
    private String tel;
    private boolean isNull1;
    private boolean isNull2;
    private boolean isSearch=false;
    
    @EJB
    private control.BookingFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Booking> bookingList = new ArrayList<>();

    public BookingController() {
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    
     public boolean isIsNull1() {
         isNull1 = name.isEmpty();
        return isNull1;
    }

    public void setIsNull1(boolean isNull1) {
        this.isNull1 = isNull1;
    }

    public boolean isIsNull2() {
        isNull2 = tel.isEmpty();
        return isNull2;
    }

    public void setIsNull2(boolean isNull2) {
        this.isNull2 = isNull2;
    }
    
    public void showBookingResults(ActionEvent ae){
        
        isNull1 = name.isEmpty();
        isNull2 = tel.isEmpty();
        
        if(!isNull1 && !isNull2)
            items = ejbFacade.searchByNameTel(name, tel);
        if(!isNull1 && isNull2)
            items = ejbFacade.searchByName(name);
        if(isNull1 && !isNull2)
            items = ejbFacade.searchByTel(tel);
        if (isNull1 && isNull2)
            items = ejbFacade.findBooking();
        isSearch = true;
           
    }
    
    public List<Booking> getItems() {
        if (!isSearch){
            items = ejbFacade.findBooking();
        }
        //isSearch = false;
        return items;
    }

    
    
    public Booking getSelected() {
        if (current == null) {
            current = new Booking();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BookingFacade getFacade() {
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



    public String prepareCreate() {
        current = new Booking();
        selectedItemIndex = -1;
       return "Home.xhtml";
    }

    public String create() {
        try {
            if (!current.getDateTo().after(current.getDateFrom())){
                Exception e = new Exception();
                throw e;
            }
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BookingCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }


    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BookingUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BookingDeleted"));
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

    public Booking getBooking(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Booking.class)
    public static class BookingControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BookingController controller = (BookingController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bookingController");
            return controller.getBooking(getKey(value));
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
            if (object instanceof Booking) {
                Booking o = (Booking) object;
                return getStringKey(o.getBookingNo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Booking.class.getName());
            }
        }

    }

}
