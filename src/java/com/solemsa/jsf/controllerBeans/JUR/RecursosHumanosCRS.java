package com.solemsa.jsf.controllerBeans.JUR;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.JUR.daoJUR_RecursosHumanos;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class RecursosHumanosCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_RecursosHumanos dao;
    private List<JUR_RecursosHumanos> lista;
    private JUR_RecursosHumanos cliente;
    private String rowID;
            
    public RecursosHumanosCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<JUR_RecursosHumanos> getLista() {
        return lista;
    }

    public void setLista(List<JUR_RecursosHumanos> lista) {
        this.lista = lista;
    }

    public JUR_RecursosHumanos getREX_Cotizacion() {
        return cliente;
    }

    public void setREX_Cotizacion(JUR_RecursosHumanos cliente) {
        this.cliente = cliente;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getJUR_RecursosHumanosList();
    }
    
    public void fetchJUR_Caso(int id)
    {
        this.cliente=dao.getJUR_RecursoHumano(id);
    }
    
    public String goToDetail()
    { 
        if(!logInBean.getActive()[4].equals("active"))
            logInBean.setActiveModule(4,10,"recursoshumanos");
        return "recursoshumanosD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        JUR_RecursosHumanos[] del=new JUR_RecursosHumanos[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            JUR_RecursosHumanos t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_JUR_RecursoHumano()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleJUR_RecursosHumanos(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("ERROR REDIRECTING ON Delete: "+e);}
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct RecursosHumanosCRS METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
            {
                dao=new daoJUR_RecursosHumanos(logInBean.getFactory());
                fetchList();
            }
        }
        System.out.println("@PostConstruct RecursosHumanosCRS METHOD PERFORMED");
    }
    
}
