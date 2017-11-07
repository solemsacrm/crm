package com.solemsa.jsf.controllerBeans.SEG;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.SEG.daoSEG_Cotizaciones;
import com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones;
import com.solemsa.jsf.data.ListValues;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import java.util.List;

@ManagedBean()
@ViewScoped
public class CotizacionesCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoSEG_Cotizaciones dao;
    private SEG_Cotizaciones model;
    private SEG_Cotizaciones model2;
    private int rowID;
    private boolean cambios=false;
    private boolean nm,ne,re,st,de,co,pr,ic,fe,ti;
    ////////Portal Attributes////////
    private ValueLists clientes;
            
    public CotizacionesCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoSEG_Cotizaciones(logInBean.getFactory());
            java.util.Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id="formu:idHidden";
            id = params.get(id);
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null || id.isEmpty())?"0":id;
            fetchSEG_Cotizaciones(Integer.parseInt(id));
            clientes = new ValueLists();
            logInBean.setHideHeaderRecordControls(true);
        }
        System.out.println("@PostConstruct METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public SEG_Cotizaciones getModel() {
        return model;
    }

    public void setModel(SEG_Cotizaciones model) {
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
    
    public void fetchSEG_Cotizaciones(int id)
    {
        model=dao.getSEG_Cotizacion(id);
        model2=dao.getSEG_Cotizacion(id);
        if(id<1)
        {
            //model.setEstatus("Acero");
            nm=ne=de=re=st=co=pr=ic=fe=ti=cambios=true;
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
        clientes.setLength(clientes.getList().size());
        System.out.println("clientes.legnth: "+clientes.getLength());
        clientes.setChanges(false);
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveSEG_Cotizaciones(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
    }
    
    public void saveSEG_Cotizaciones(String usu)
    {
        System.out.println("saveSEG_Cotizaciones METHOD REACHED");
        System.out.println("Current ID: "+model.getId_SEG_Cotizacion());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_SEG_Cotizacion()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            model2=dao.newSEG_Cotizacion(model);
        }
        else
        {
            System.out.println("SEG_Cotizaciones "+model.getId_SEG_Cotizacion()+" MODIFY ABOUT TO START");
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifySEG_Cotizacion(model);
                System.out.println("MODEL 2 SUCCESSFULLY MODIFIED");
                long search=model.getCliente().getId_Clientes();
                String txt=null;
                for(ListValues t:clientes.getList())
                    if(t.getId()==search)
                    {
                        txt=t.getLabel();
                        break;
                    }
                model.getCliente().setDescripcion(txt);
                clientes = new ValueLists();
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
            }
        }
        nm=ne=de=re=st=co=pr=ic=fe=ti=cambios=false;
    }
    
    public String deleteSEG_Cotizaciones()
    {
        if(model.getId_SEG_Cotizacion()>0)
        {
            model2.setCliente(null);
            dao.deleteSEG_Cotizacion(model2);
        }
        return "cotizaciones";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "NM":nm=!model.getNombre().equals(model2.getNombre());break;
            case "TI":ti=!model.getTipo().equals(model2.getTipo());break;
            case "DE":de=!model.getDescripcion().equals(model2.getDescripcion());break;
            case "FE":fe=!model.getFechaString().equals(model2.getFechaString());break;
            case "RE":re=model.getRenta()!=model2.getRenta();break;
            case "NE":ne=model.getNoElementos()!=model2.getNoElementos();break;
            case "IC":ic=model.getCliente().getId_Clientes()!=model2.getCliente().getId_Clientes();break;
            case "ST":st=model.getEstatus()!=model2.getEstatus();break;
            case "PR":pr=model.getPrecio()!=model2.getPrecio();break;
            case "CO":co=model.getCosto()!=model2.getCosto();break;
        }
        cambios=nm||ne||re||st||de||co||pr||ic||fe||ti;
        System.out.println("Cambios: "+cambios);
    }
    
}