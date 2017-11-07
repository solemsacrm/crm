package com.solemsa.jsf.controllerBeans.rex;

import com.solemsa.hibernate.entities.REX.REX_Oferta;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.rex.daoREX_Proveedores;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.DAOs.rex.daoREX_Oferta;
import com.solemsa.hibernate.entities.REX.REX_Materiales;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean()
@ViewScoped
public class ProveedoresCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_Proveedores dao;
    private REX_Proveedores model;
    private REX_Proveedores model2;
    private int rowID;
    private boolean cambios=false;
    boolean nm,ti,rs,d1,d2,ci,es,pa,nt,cp;
    ////////Portal Attributes////////
    private String portalValues;
    private ValueLists materiales;
            
    public ProveedoresCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct METHOD REACHED");
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoREX_Proveedores(logInBean.getFactory());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            fetchREX_Proveedor(Integer.parseInt(id));
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

    public REX_Proveedores getModel() {
        return model;
    }

    public void setModel(REX_Proveedores model) {
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

    public ValueLists getMateriales() {
        return materiales;
    }

    public void setMateriales(ValueLists cliNomList) {
        this.materiales = cliNomList;
    }
    
    public void fetchREX_Proveedor(int id)
    {
        model=dao.getREX_Proveedor(id);
        model2=dao.getREX_Proveedor(id);
        if(id<1)
        {
            model.setTipo("Acero");
            model.setPais("México");
            nm=ti=rs=d1=d2=ci=es=pa=nt=cp=cambios=true;
        }
    }
    
    public void loadMaterialesList()
    {
        System.out.println("loadClientesList METHOD REACHED");
        List temp=dao.loadMaterialesList(model2.getTipo());
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[] tupla = (Object[]) i.next();
            materiales.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString());
        }
        materiales.setLength(materiales.getList().size());
        System.out.println("clientes.legnth: "+materiales.getLength());
        materiales.setChanges(false);
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveREX_Proveedor(logInBean.getUsuario().getUsername());
    }
    
    public String parseDates(String d,String c){
        System.out.println("DATE: "+d);
        String[]f=d.split(c);
        c=c.equals("-")?"/":"-";
        return f[2]+c+f[1]+c+f[0];
    }
    
    public void saveREX_Proveedor(String usu)
    {
        System.out.println("saveREX_Material METHOD REACHED");
        System.out.println("Current ID: "+model.getId_REX_Proveedor());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_REX_Proveedor()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            dao.newREX_Proveedor(model);
        }
        else
        {
            System.out.println("REX_Materiales "+model.getId_REX_Proveedor()+" MODIFY ABOUT TO START");
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifyREX_Proveedor(model);
                System.out.println("MODEL 2 SUCCESSFULLY MODIFIED");
                /*long search=model.get_Cliente().getId_Clientes();
                String txt=null;
                for(ListValues t:clientes.getList())
                    if(t.getId()==search)
                    {
                        txt=t.getLabel();
                        break;
                    }
                model.get_Cliente().setNombre(txt);
                clientes = new ValueLists();*/
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
            }
        }
        nm=ti=rs=d1=d2=ci=es=pa=nt=cp=cambios=false;
    }
    
    public void commitPortal()
    {
        System.out.println("commitPortal METHOD REACHED with: "+portalValues);
        String[] arr=new CommonActions().commitPortal(portalValues);
        int n=arr.length;
        daoREX_Oferta dro=new daoREX_Oferta(logInBean.getFactory());
        if(arr[1].isEmpty())//id
        {
            System.out.println("OFERTA ID IS EMPTY");
            REX_Oferta ro=new REX_Oferta();
            ro.setREX_Material(new REX_Materiales());
            ro.getREX_Material().setId_REX_Material(Long.parseLong(arr[2]));
            ro.setREX_Proveedor(model);
            ro.setPrecio(arr[3].isEmpty()?0:Float.parseFloat(arr[4]));
            ro.setMoneda(arr[4]);
            String u=logInBean.getUsuario().getUsername();
            ro.setZz_UsuarioCreacion(u);
            ro.setZz_UsuarioModificacion(u);
            portalValues=dro.newREX_Oferta(ro);
        }
        else
        {
            System.out.println("OFERTA ID IS NOT EMPTY: "+arr[2]);
            REX_Oferta ro=dro.getREX_Oferta(Long.parseLong(arr[1]));
            if(ro.getREX_Material()==null)
            {
                System.out.println("OFERTA PROVEEDOR WAS NULL");
                ro.setREX_Material(new REX_Materiales());
            }
            ro.getREX_Material().setId_REX_Material(Long.parseLong(arr[2]));
            ro.setPrecio(arr[4].isEmpty()?0:Float.parseFloat(arr[4]));
            ro.setMoneda(arr[5]);
            ro.setZz_UsuarioModificacion(logInBean.getUsuario().getUsername());
            dro.modifyREX_Oferta(ro);
            portalValues=arr[1];
        }
    }
    
    public void deleteFromPortal()
    {
        daoREX_Oferta dro=new daoREX_Oferta(logInBean.getFactory());
        dro.deleteREX_Oferta(dro.getREX_Oferta(Long.parseLong(portalValues)));
    }
    
    public String deleteREX_Proveedor()
    {
        if(model.getId_REX_Proveedor()>0)
        {
            //model2.set_Cliente(null);
            dao.deleteREX_Proveedor(model2);
        }
        return "proveedores";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "NM":nm=!model.getNombre().equals(model2.getNombre());break;
            case "TI":ti=!model.getTipo().equals(model2.getTipo());break;
            case "RS":rs=!model.getRazonSocial().equals(model2.getRazonSocial());break;
            case "D1":d1=!model.getDireccion1().equals(model2.getDireccion1());break;
            case "D2":d2=!model.getDireccion2().equals(model2.getDireccion2());break;
            case "CI":ci=!model.getCiudad().equals(model2.getCiudad());break;
            case "ES":es=!model.getEstado().equals(model2.getEstado());break;
            case "PA":pa=!model.getPais().equals(model2.getPais());break;
            case "CP":nt=!model.getCodigoPostal().equals(model2.getCodigoPostal());break;
            case "NT":nt=!model.getNotas().equals(model2.getNotas());break;
        }
        cambios=nm||ti||rs||d1||d2||ci||es||pa||nt||cp;
        System.out.println("Cambios: "+cambios);
    }
    
    public int return0(){
        return 0;
    }
    
}