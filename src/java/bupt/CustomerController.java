package bupt;

import bupt.util.JsfUtil;
import bupt.util.PaginationHelper;
import control.CustomerFacade;

import java.io.Serializable;
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

@Named("customerController")
@SessionScoped
public class CustomerController implements Serializable {

    private Customer current;
    private List<Customer> items;
    private Record tempRecordNo;
    private String cname;
    private Date sdate;
    private Date edate;

    private List<Customer> items1 = null;
    private List<Record> items2 = null;
    @EJB
    private control.CustomerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CustomerController() {
    }

    public Customer getSelected() {
        if (current == null) {
            current = new Customer();
            selectedItemIndex = -1;
        }
        return current;
    }
    public Record getTempRecordNo(){
        return tempRecordNo;
    }
    public void setTempRecordNo(Record s){
        this.tempRecordNo=s;
    }
    public String getCname() {
        return cname;
    }

    public void setCname(String cName) {
        this.cname = cName;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date eDate) {
        this.edate = eDate;
    }

    public List<Customer> getItems1() {
        return items1;
    }

    public List<Record> getItems2() {
        return items2;
    }

    public void showQueryResults(ActionEvent ae) {

        boolean isNull1 = cname.isEmpty();
        boolean isNull2 = sdate.before(new Date());
        boolean isNull3 = edate.before(new Date());

        if (!isNull1 && isNull2 && isNull3) {
            items1 = ejbFacade.searchByName1(cname);
            items2 = ejbFacade.searchByName2(cname);
        }
        if (isNull1 && !isNull2 && isNull3) {
            items1 = ejbFacade.searchBySDate1(sdate);
            items2 = ejbFacade.searchBySDate2(sdate);
        }
        if (isNull1 && isNull2 && !isNull3) {
            items1 = ejbFacade.searchByEDate1(edate);
            items2 = ejbFacade.searchByEDate2(edate);
        }
        if (!isNull1 && !isNull2 && isNull3) {
            items1 = ejbFacade.searchByNameSDate1(cname, sdate);
            items2 = ejbFacade.searchByNameSDate2(cname, sdate);
        }
        if (!isNull1 && isNull2 && !isNull3) {
            items1 = ejbFacade.searchByNameEDate1(cname, edate);
            items2 = ejbFacade.searchByNameEDate2(cname, edate);
        }
        if (isNull1 && !isNull2 && !isNull3) {
            items1 = ejbFacade.searchBySEDate1(sdate, edate);
            items2 = ejbFacade.searchBySEDate2(sdate, edate);
        }
        if (!isNull1 && !isNull2 && !isNull3) {
            items1 = ejbFacade.searchByNameSEDate1(cname, sdate, edate);
            items2 = ejbFacade.searchByNameSEDate2(cname, sdate, edate);
        }

    }
    
    
    
    
    
    
    
    private CustomerFacade getFacade() {
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
        current = new Customer();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomerCreated"));
             items = ejbFacade.findByRecordNo(tempRecordNo);
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
        
    }


    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomerUpdated"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CustomerDeleted"));
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

    public List<Customer> getItems() {
        items = ejbFacade.findByRecordNo(tempRecordNo);
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

    public Customer getCustomer(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Customer.class)
    public static class CustomerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomerController controller = (CustomerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "customerController");
            return controller.getCustomer(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Customer) {
                Customer o = (Customer) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Customer.class.getName());
            }
        }

    }

}
