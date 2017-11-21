package com.solemsa.jsf.controllerBeans.AOU;

import com.solemsa.jsf.controllerBeans.*;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.daoClientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.AOU.OUT_Proyectos;
import com.solemsa.jsf.DAOs.AOU.daoProyectos;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class ProyectosCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoProyectos dao;
    private List<OUT_Proyectos> lista;
    private OUT_Proyectos proyecto;
    private String rowID;
            
    public ProyectosCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<OUT_Proyectos> getLista() {
        return lista;
    }

    public void setLista(List<OUT_Proyectos> lista) {
        this.lista = lista;
    }

    public OUT_Proyectos getProyecto() {
        return proyecto;
    }

    public void setProyecto(OUT_Proyectos proyecto) {
        this.proyecto = proyecto;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getOUT_ProyectosList();
    }
    
    public String goToDetail()
    { 
        return "proyectosD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        OUT_Proyectos[] del=new OUT_Proyectos[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            OUT_Proyectos t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_OUT_Proyecto()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleProyectos(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("ERROR REDIRECTING ON Delete: "+e);}
    }
    
    public int fromVentas(char p)
    {
        return p=='6'?1:0;
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct Clientes METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
            {
                dao=new daoProyectos(logInBean.getFactory());
                fetchList();
            }
        }
        System.out.println("@PostConstruct Clientes METHOD PERFORMED");
    }
    
}
