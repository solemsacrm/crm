package com.solemsa.jsf.controllerBeans.JUR;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import java.util.List;

@ManagedBean()
@ViewScoped
public class CasosCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Casos dao;
    private JUR_Casos model;
    private JUR_Casos model2;
    private int rowID;
    private boolean cambios=false;
    private boolean nm,ne,ju,ea,de,co,ic,pc,ti;
    ////////Portal Attributes////////
    private String portalValues;
    private ValueLists clientes;
    private ValueLists RHs;
            
    public CasosCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct CasosCVS METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoJUR_Casos(logInBean.getFactory());
            java.util.Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id="formu:idHidden";
            id = params.get(id);
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null || id.isEmpty())?"0":id;
            fetchJUR_Casos(Integer.parseInt(id));
            clientes = new ValueLists();
            RHs = new ValueLists();
            logInBean.setHideHeaderRecordControls(true);
        }
        System.out.println("@PostConstruct CasosCVS METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public JUR_Casos getModel() {
        return model;
    }

    public void setModel(JUR_Casos model) {
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

    public String getPortalValues() {
        return portalValues;
    }

    public void setPortalValues(String portalValues) {
        this.portalValues = portalValues;
    }

    public ValueLists getClientes() {
        return clientes;
    }

    public void setClientes(ValueLists clientes) {
        this.clientes = clientes;
    }
    
    public ValueLists getRHs() {
        return RHs;
    }

    public void setRHs(ValueLists RHs) {
        this.RHs = RHs;
    }
    
    public void fetchJUR_Casos(int id)
    {
        model=dao.getJUR_Caso(id,logInBean.getUsuario().getJur(),false);
        model2=dao.getJUR_Caso(id,0,true);
        if(id<1)
        {
            model.setJuzgado("Civil");
            model.setPapelCliente("Demandante");
            nm=ne=de=ju=ea=co=ic=pc=ti=cambios=true;
        }
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
    
    public void loadRHsList()
    {
        System.out.println("loadRHsList METHOD REACHED");
        List temp=dao.loadRecursosHumanosList();
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[]tupla=(Object[])i.next();
            RHs.add((Long)tupla[0],(tupla[1]!=null?(tupla[1]+" "+tupla[2]):tupla[0].toString()));
        }
        System.out.println("RHs LOADED WITH: "+RHs.getLength()+" RECORDS");
        RHs.setChanges(false);
        System.out.println("loadRHsList METHOD REACHED");
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveJUR_Casos(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
    }
    
    public void saveJUR_Casos(String usu)
    {
        System.out.println("saveJUR_Casos METHOD REACHED");
        try
        {
            System.out.println("Current ID: "+model.getId_JUR_Caso());
            model.setZz_UsuarioModificacion(usu);
            if(model.getId_JUR_Caso()<1)
            {
                model.setZz_UsuarioCreacion(usu);
                model=dao.newJUR_Caso(model,logInBean.getUsuario().getJur());
            }
            else
            {
                System.out.println("JUR_Casos "+model.getId_JUR_Caso()+" MODIFY ABOUT TO START");
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model=dao.modifyJUR_Caso(model);
            }
            model2=dao.getJUR_Caso(model.getId_JUR_Caso(),0,true);
        }
        catch(Exception e)
        {
            System.out.println("saveJUR_Casos EXCEPTION: "+e);
            e.printStackTrace();
        }
        nm=ne=de=ju=ea=co=ic=pc=ti=cambios=false;
        System.out.println("saveJUR_Casos METHOD PERFORMED");
    }
    
    public String deleteJUR_Casos()
    {
        if(model.getId_JUR_Caso()>0)
        {
            model2.setCliente(null);
            dao.deleteJUR_Caso(model2);
        }
        return "casos";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "IC":ic=model.getCliente().getId_Clientes()!=model2.getCliente().getId_Clientes();break;
            case "NM":nm=!(model.getNombre()!=null?model.getNombre():"").equals((model2.getNombre()!=null?model2.getNombre():""));break;
            case "TI":ti=!(model.getTipo()!=null?model.getTipo():"").equals((model2.getTipo()!=null?model2.getTipo():""));break;
            case "DE":de=!(model.getDescripcion()!=null?model.getDescripcion():"").equals((model2.getDescripcion()!=null?model2.getDescripcion():""));break;
            case "PC":pc=!(model.getPapelCliente()!=null?model.getPapelCliente():"").equals((model2.getPapelCliente()!=null?model2.getPapelCliente():""));break;
            case "JU":ju=!(model.getJuzgado()!=null?model.getJuzgado():"").equals((model2.getJuzgado()!=null?model2.getJuzgado():""));break;
            case "NE":ne=!(model.getNoExpediente()!=null?model.getNoExpediente():"").equals((model2.getNoExpediente()!=null?model2.getNoExpediente():""));break;
            case "EA":ea=model.getEtapaActual()!=model2.getEtapaActual();break;
            case "CO":co=model.getCosto()!=model2.getCosto();break;
        }
        cambios=nm||ne||ju||ea||de||co||ic||pc||ti;
        System.out.println("Cambios: "+cambios);
    }
    
    public void commitPortal()
    {
        System.out.println("commitPortal METHOD REACHED:\n"+portalValues);
        String arr[]=dao.ca.commitPortal(portalValues);
        portalValues=""+dao.modifyJUR_Tareas(dao.setJUR_TareaRelationships(arr,model),arr,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        System.out.println("commitPortal METHOD PERFORMED");
    }
    
    public void deleteFromPortal()
    {
        System.out.println("deleteFromPortal METHOD REACHED");
        dao.deleteJUR_Tarea(dao.ca.parseStringLong(portalValues));
        portalValues="";
        System.out.println("deleteFromPortal METHOD PERFORMED");
    }
    
    public String getEstatusName()
    {
        switch(model.getEtapaActual())
        {
            case 0:return "Pendiente";
            case 1:return "En Proceso";
            case 2:return "Perdido";
            case 3:return "Ganado";
            default:return "";
        }
    }
    
    public String goToDetail()
    { 
        return "expedientesD?faces-redirect-true";
    }
    
}