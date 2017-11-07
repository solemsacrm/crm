package com.solemsa.jsf.controllerBeans.SEG;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.SEG.daoSEG_Cotizaciones;
import com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class CotizacionesCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoSEG_Cotizaciones dao;
    private List<SEG_Cotizaciones> lista;
    private SEG_Cotizaciones cliente;
    private String rowID;
            
    public CotizacionesCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<SEG_Cotizaciones> getLista() {
        return lista;
    }

    public void setLista(List<SEG_Cotizaciones> lista) {
        this.lista = lista;
    }

    public SEG_Cotizaciones getREX_Cotizacion() {
        return cliente;
    }

    public void setREX_Cotizacion(SEG_Cotizaciones cliente) {
        this.cliente = cliente;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getSEG_CotizacionesList();
    }
    
    public void fetchSEG_Cotizacion(int id)
    {
        this.cliente=dao.getSEG_Cotizacion(id);
    }
    
    public String goToDetail()
    { 
        if(!logInBean.getActive()[3].equals("active"))
            logInBean.setActiveModule(3,10,"cotizaciones");
        return "cotizacionesD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        SEG_Cotizaciones[] del=new SEG_Cotizaciones[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            SEG_Cotizaciones t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_SEG_Cotizacion()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleSEG_Cotizaciones(del);
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
                dao=new daoSEG_Cotizaciones(logInBean.getFactory());
            logInBean.setHideHeaderRecordControls(false);
            if(logInBean.getActive()[3].equals("active"))
                fetchList();
        }
    }
    
}
