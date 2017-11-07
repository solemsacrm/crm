package com.solemsa.jsf.controllerBeans.rex;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.rex.daoREX_ODCs;
import com.solemsa.hibernate.entities.REX.REX_ODCs;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class OdcsCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_ODCs dao;
    private List<REX_ODCs> lista;
    //private REX_ODCs cliente;
    private String rowID;
            
    public OdcsCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<REX_ODCs> getLista() {
        return lista;
    }

    public void setLista(List<REX_ODCs> lista) {
        this.lista = lista;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getREX_ODCsList();
    }
    
    public String goToDetail()
    { 
        return "odcD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        REX_ODCs[] del=new REX_ODCs[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            REX_ODCs t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_REX_OrdenDeCompra()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleREX_ODCs(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("ERROR REDIRECTING ON Delete: "+e);}
    }
    
    @PostConstruct  
    public void initDAO(){
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoREX_ODCs(logInBean.getFactory());
            logInBean.setHideHeaderRecordControls(false);
            fetchList();
        }
    }
    
}
