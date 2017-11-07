package com.solemsa.jsf.controllerBeans.JUR;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Tareas;
import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class TareasCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Tareas dao;
    private List<JUR_Tareas> lista;
    private JUR_Tareas tarea;
    private String rowID;
            
    public TareasCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<JUR_Tareas> getLista() {
        return lista;
    }

    public void setLista(List<JUR_Tareas> lista) {
        this.lista = lista;
    }

    public JUR_Tareas getJUR_Tarea() {
        return tarea;
    }

    public void setJUR_Tarea(JUR_Tareas tarea) {
        this.tarea = tarea;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getJUR_TareasList(logInBean.getUsuario());
    }
    
    public void fetchJUR_Tarea(int id)
    {
        this.tarea=dao.getJUR_Tarea(id,false);
    }
    
    public String goToDetail()
    { 
        if(!logInBean.getActive()[5].equals("active"))
            logInBean.setActiveModule(3,10,"tareas");
        return "tareasD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        JUR_Tareas[] del=new JUR_Tareas[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            JUR_Tareas t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_JUR_Tarea()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleJUR_Tareas(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("ERROR REDIRECTING ON Delete: "+e);}
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct TareasCRS METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
            {
                dao=new daoJUR_Tareas(logInBean.getFactory());
                logInBean.setHideHeaderRecordControls(false);
                fetchList();
            }
        }
        System.out.println("@PostConstruct TareasCRS METHOD PERFORMED");
    }
    
}
