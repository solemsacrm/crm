package com.solemsa.jsf.controllerBeans.AOU;

import java.io.Serializable;
import java.util.List;
import java.sql.Date;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.daoClientes;
import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.Correos;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.AOU.OUT_Proyectos;
import com.solemsa.hibernate.entities.Telefonos;
import com.solemsa.jsf.DAOs.AOU.daoProyectos;
import com.solemsa.jsf.DAOs.daoCorreos;
import com.solemsa.jsf.DAOs.daoTelefonos;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;

@ManagedBean()
@ViewScoped
public class OUT_ProyectosCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoProyectos dao;
    private OUT_Proyectos model,model2;
    private List<OUT_Proyectos> lista;
    private int rowID;
    private ValueLists clientes;
    private boolean cambios=false;
    boolean nm,de,es,lo,ic;
    private int[] servicios2={};

            
    public OUT_ProyectosCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoProyectos(logInBean.getFactory());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            System.out.println("IT WAS CONVERTED BITHC!!!");
            clientes = new ValueLists();
            rowID=(int)Long.parseLong(id);
            fetchProyecto(rowID);
        }
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public OUT_Proyectos getModel() {
        return model;
    }

    public void setModel(OUT_Proyectos model) {
        this.model = model;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public boolean isCambios() {
        return cambios;
    }

    public void setCambios(boolean cambios) {
        this.cambios = cambios;
    }
    
    public ValueLists getClientes() {
        return clientes;
    }

    public void setClientes(ValueLists clientes) {
        this.clientes = clientes;
    }
    
    public void fetchList(){
        this.lista=dao.getOUT_ProyectosList();
    }
    
    public void fetchProyecto(long id)
    {
        this.model=dao.getProyecto(id,false);
        this.model2=dao.getProyecto(id,false);
        if(id<1)
        {
            //model.setZz_UsuarioCreacion(logInBean.getUsuario().getNombre());
            model.setEstatus("Activo");
            nm=ic=de=es=cambios=true;
            //model2.setEstatus("Activo");
 
        }
    }
    
    public void updateFormServicios()
    {
        //serviciosSetter();
        saveProyecto(logInBean.getUsuario().getNombre());
    }
    
     public void saveProyecto(String usu)
    {
        System.out.println("saveJUR_Casos METHOD REACHED");
        try
        {
            System.out.println("Current ID: "+model.getId_OUT_Proyecto());
            model.setZz_UsuarioModificacion(usu);
            if(model.getId_OUT_Proyecto()<1)
            {
                model.setZz_UsuarioCreacion(usu);
                model=dao.newProyecto(model);
            }
            else
            {
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model=dao.modifyProyecto(model);
            }
            model2=dao.getProyecto(model.getId_OUT_Proyecto(),true);
        }
        catch(Exception e)
        {
            System.out.println("saveJUR_Casos EXCEPTION: "+e);
            e.printStackTrace();
        }
        nm=ic=de=es=cambios=false;
        System.out.println("saveJUR_Casos METHOD PERFORMED");
    }
    
    public String deleteProyecto()
    {
        if(model.getId_OUT_Proyecto()>0)
        {           
            dao.deleteProyecto(model2);
        }
        return "proyectos";
    }
    
    public void loadClientesList(AjaxBehaviorEvent event)
    {
        System.out.println("loadClientesList METHOD REACHED");
        List temp=dao.loadClienteesList();
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[]tupla=(Object[])i.next();
            clientes.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString());
        }
        System.out.println("clientes LOADED WITH: "+clientes.getLength()+" RECORDS");
        clientes.setChanges(false);
        System.out.println("loadClientesList METHOD PERFORMED");
    }
     
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "NM":nm=!(model.getNombre()!=null?model.getNombre():"").equals((model2.getNombre()!=null?model2.getNombre():""));break;
            case "IC":ic= model.getCliente().getId_Clientes()!=model2.getCliente().getId_Clientes();break;
            case "DE":de=!(model.getDescripcion()!=null?model.getDescripcion():"").equals((model2.getDescripcion()!=null?model2.getDescripcion():""));break;
            case "ES":es=!(model.getEstatus()!=null?model.getEstatus():"").equals((model2.getEstatus()!=null?model2.getEstatus():""));break;
        }
        cambios=nm||es||de||ic;
        System.out.println("Cambios: "+cambios);
    }
}