package com.solemsa.jsf.controllerBeans.rex;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.rex.daoREX_ODCs;
import com.solemsa.hibernate.entities.REX.REX_ODCs;
import com.solemsa.jsf.DAOs.rex.daoREX_ODCdetalle;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean()
@ViewScoped
public class OdcsCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_ODCs dao;
    private REX_ODCs model;
    private REX_ODCs model2;
    private int rowID;
    private boolean cambios=false;
    boolean ti,mo,fx,fe,es,nt,pv,ob;
    ////////Portal Attributes////////
    private String portalValues;
    private ValueLists obras,proveedores,materiales;
            
    public OdcsCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct METHOD REACHED");
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoREX_ODCs(logInBean.getFactory());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            id=(id==null)?"0":id;
            System.out.println("POSTCONSTRUCT ID: "+id);
            fetchREX_ODC(Integer.parseInt(id));
            obras = new ValueLists();
            proveedores = new ValueLists();
            materiales = new ValueLists();
            logInBean.setHideHeaderRecordControls(true);
        }
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public REX_ODCs getModel() {
        return model;
    }

    public void setModel(REX_ODCs model) {
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

    public ValueLists getObras() {
        return obras;
    }

    public void setObras(ValueLists obras) {
        this.obras = obras;
    }

    public ValueLists getProveedores() {
        return proveedores;
    }

    public void setProveedores(ValueLists proveedores) {
        this.proveedores = proveedores;
    }

    public ValueLists getMateriales() {
        return materiales;
    }

    public void setMateriales(ValueLists materiales) {
        this.materiales = materiales;
    }
    
    public void fetchREX_ODC(int id)
    {
        System.out.println("fetchREX_ODC METHOD REACHED");
        model=dao.getREX_ODC(id);
        model2=dao.getREX_ODC(id);
        if(id<1)
        {
            model.setTipo("Acero");
            model.setMoneda("USD");
            model.setEstatus(1);
            model2.setEstatus(1);
            ti=mo=fx=fe=es=nt=pv=ob=cambios=true;
        }
        else
        {
            
        }
        System.out.println("fetchREX_ODC METHOD SUCCESSFUL");
    }
    
    public void loadObrasList()
    {
        System.out.println("loadObrasList METHOD REACHED");
        List temp=dao.loadREX_ObrasList();
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[] tupla = (Object[]) i.next();
            obras.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString());
        }
        obras.setLength(obras.getList().size());
        System.out.println("obras.legnth: "+obras.getLength());
        obras.setChanges(false);
    }
    
    public void loadProveedoresList()
    {
        System.out.println("loadProveedoresList METHOD REACHED");
        List temp=dao.loadREX_ProveedoresList(model.getTipo());
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[] tupla = (Object[]) i.next();
            proveedores.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString());
        }
        proveedores.setLength(proveedores.getList().size());
        System.out.println("proveedores.legnth: "+proveedores.getLength());
        proveedores.setChanges(false);
    }
    
    public void loadMaterialesList()
    {
        System.out.println("loadMaterialesList METHOD REACHED");
        List temp=dao.loadREX_MaterialesList(model.getREX_Proveedor());
        int length=0;
        if(temp!=null)
            if(temp.size()>0)
                length=((Object[])temp.get(0)).length-1;
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[] tupla = (Object[]) i.next();
            materiales.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString()+",["+tupla[2]+"]_2,["+tupla[3]+"]_3D");
        }
        materiales.setLength(materiales.getList().size());
        System.out.println("materiales.legnth: "+materiales.getLength());
        materiales.setChanges(false);
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveREX_ODC(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
    }
    
    public String parseDates(String d,String c){
        System.out.println("DATE: "+d);
        String[]f=d.split(c);
        c=c.equals("-")?"/":"-";
        return f[2]+c+f[1]+c+f[0];
    }
    
    public void saveREX_ODC(String usu)
    {
        System.out.println("saveREX_Material METHOD REACHED");
        System.out.println("Current ID: "+model.getId_REX_OrdenDeCompra());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_REX_OrdenDeCompra()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            model2=dao.newREX_ODC(model);
            System.out.println("NEW ID: "+model.getId_REX_OrdenDeCompra());
        }
        else
        {
            System.out.println("REX_Materiales "+model.getId_REX_OrdenDeCompra()+" MODIFY ABOUT TO START");
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifyREX_ODC(model);
                System.out.println("MODEL 2 SUCCESSFULLY MODIFIED");
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
            }
        }
        //Will reset the VL's for it's ajax re-rendering
        if(model.getREX_Obra().getId_REX_Obra()<1)
            obras=new ValueLists();
        if(model.getREX_Proveedor().getId_REX_Proveedor()<1)
            proveedores=new ValueLists();
        ti=mo=fx=fe=es=nt=pv=ob=cambios=false;
    }
    
    public void commitPortal()
    {
        System.out.println("commitPortal METHOD REACHED with: "+portalValues);
        CommonActions ca=new CommonActions();
        String[] arr=ca.commitPortal(portalValues),
        arr2={arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre()};
        arr=null;
        daoREX_ODCdetalle dro=new daoREX_ODCdetalle(logInBean.getFactory());
        if(arr2[1].isEmpty())//id
        {
            System.out.println("OFERTA ID IS EMPTY");
            portalValues=dro.newREX_ODCdetalle(model,arr2);
        }
        else
        {
            System.out.println("OFERTA ID IS NOT EMPTY: "+arr2[2]);
            dro.modifyREX_ODCdetalle(model,arr2);
            portalValues=arr2[1];
        }
    }
    
    public void deleteFromPortal()
    {
        daoREX_ODCdetalle dro=new daoREX_ODCdetalle(logInBean.getFactory());
        System.out.println("PORTAL VALUES: "+portalValues);
        dro.deleteREX_ODCdetalle(model,Long.parseLong(portalValues),logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        portalValues="";
    }
    
    public String deleteREX_Proveedor()
    {
        if(model.getId_REX_OrdenDeCompra()>0)
        {
            dao.deleteREX_ODC(model2);
        }
        return "odcs";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "OB":ob=model.getREX_Obra().getId_REX_Obra()!=model2.getREX_Obra().getId_REX_Obra();System.out.println(model.getREX_Obra().getId_REX_Obra()+","+model2.getREX_Obra().getId_REX_Obra());break;
            case "PV":pv=model.getREX_Proveedor().getId_REX_Proveedor()!=model2.getREX_Proveedor().getId_REX_Proveedor();System.out.println(model.getREX_Proveedor().getId_REX_Proveedor()+","+model2.getREX_Proveedor().getId_REX_Proveedor());break;
            case "TI":ti=!model.getTipo().equals(model2.getTipo()==null?"":model2.getTipo());if(ti)model.setREX_Proveedor(new REX_Proveedores());System.out.println(model.getTipo()+","+model2.getTipo());break;
            case "MO":mo=!model.getMoneda().equals(model2.getMoneda()==null?"":model2.getMoneda());System.out.println(model.getMoneda()+","+model2.getMoneda());break;
            case "FX":fx=!model.getFechaExpedicionString().equals(model2.getFechaExpedicionString()==null?"":model2.getFechaExpedicionString());System.out.println(model.getFechaExpedicionString()+","+model2.getFechaExpedicionString());break;
            case "FE":fe=!model.getFechaEntregaString().equals(model2.getFechaEntregaString()==null?"":model2.getFechaEntregaString());System.out.println(model.getFechaEntregaString()+","+model2.getFechaEntregaString());break;
            case "ES":es=model.getEstatus()!=model2.getEstatus();System.out.println(model.getEstatus()+","+model2.getEstatus());break;
            case "NT":nt=!model.getNotas().equals(model2.getNotas()==null?"":model2.getFechaExpedicionString());System.out.println(model.getNotas()+","+model2.getNotas());break;
        }
        cambios=ti||mo||fx||fe||es||nt||pv||ob;
        System.out.println("Cambios: "+cambios);
    }
    
    public void resetProveedor()
    {
        System.out.println("proveedores VALUE LIST ABOUT TO BE RESETTED");
        resetPortal();
        proveedores=new ValueLists();
        System.out.println("proveedores VALUE LIST SUCCESSFULLY RESETTED: "+proveedores.getLength());
    }
    
    public void resetPortal()
    {
        System.out.println("materiales VALUE LIST ABOUT TO BE RESETTED");
        dao.deleteAllFromODC(model);
        materiales=new ValueLists();
        System.out.println("materiales VALUE LIST SUCCESSFULLY RESETTED: "+materiales.getLength());
        //ti=mo=fx=fe=es=nt=pv=ob=cambios=false;
    }
    
    public void downloadODC()
    {
        System.out.println("downloadODC METHOD REACHED");
        new CommonActions().exportReport(model.getFolio(),FacesContext.getCurrentInstance().getExternalContext().getRealPath("/")+"/resources/reports/report1.jasper",model.getREX_ODCdetalle());
        System.out.println("downloadODC METHOD SUCCESSFUL");
    }
    
    public int return0(){
        return 0;
    }
    
}