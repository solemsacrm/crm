package com.solemsa.jsf.controllerBeans.rex;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.rex.daoREX_Obras;
import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class ObrasCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_Obras dao;
    private List<REX_Obras> lista;
    private REX_Obras cliente;
    private String rowID;
            
    public ObrasCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<REX_Obras> getLista() {
        return lista;
    }

    public void setLista(List<REX_Obras> lista) {
        this.lista = lista;
    }

    public REX_Obras getREX_Obra() {
        return cliente;
    }

    public void setREX_Obra(REX_Obras cliente) {
        this.cliente = cliente;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getREX_ObrasList();
    }
    
    public String goToDetail()
    { 
        return "obrasD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        REX_Obras[] del=new REX_Obras[c];
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            REX_Obras t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                if(t.getId_REX_Obra()==n)
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleREX_Obras(del);
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
                dao=new daoREX_Obras(logInBean.getFactory());
            logInBean.setHideHeaderRecordControls(false);
            fetchList();
        }
    }
    
}
