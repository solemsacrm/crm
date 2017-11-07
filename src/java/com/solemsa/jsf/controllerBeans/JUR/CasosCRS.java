package com.solemsa.jsf.controllerBeans.JUR;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class CasosCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Casos dao;
    private List<JUR_Casos> lista;
    private JUR_Casos cliente;
    private String rowID;
            
    public CasosCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<JUR_Casos> getLista() {
        return lista;
    }

    public void setLista(List<JUR_Casos> lista) {
        this.lista = lista;
    }

    public JUR_Casos getREX_Cotizacion() {
        return cliente;
    }

    public void setREX_Cotizacion(JUR_Casos cliente) {
        this.cliente = cliente;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getJUR_CasosList();
    }
    
    public void fetchJUR_Caso(int id)
    {
        this.cliente=dao.getJUR_Caso(id,logInBean.getUsuario().getJur(),false);
    }
    
    public String goToDetail()
    { 
        if(!logInBean.getActive()[3].equals("active"))
            logInBean.setActiveModule(3,10,"casos");
        return "casosD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        JUR_Casos[] del=new JUR_Casos[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            JUR_Casos t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_JUR_Caso()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleJUR_Casos(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("ERROR REDIRECTING ON Delete: "+e);}
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct CasosCRS METHOD REACHED with: "+(dao!=null));
        if(logInBean.checkSession())
        {
            if(dao==null)
            {
                dao=new daoJUR_Casos(logInBean.getFactory());
                logInBean.setHideHeaderRecordControls(false);
                if(logInBean.getActive()[3].equals("active"))
                    fetchList();
            }
        }
        System.out.println("@PostConstruct CasosCRS METHOD PERFORMED");
    }
    
}
