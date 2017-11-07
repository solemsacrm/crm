package com.solemsa.jsf.controllerBeans.rex;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.rex.daoREX_Materiales;
import com.solemsa.hibernate.entities.REX.REX_Materiales;
import com.solemsa.hibernate.entities.REX.REX_Oferta;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.DAOs.rex.daoREX_Oferta;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import java.util.List;

@ManagedBean()
@ViewScoped
public class MaterialesCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_Materiales dao;
    private REX_Materiales model;
    private REX_Materiales model2;
    private int rowID;
    private boolean cambios=false;
    private boolean ti,nm,nt,un;
    ////////Portal Attributes////////
    private ValueLists proveedores;
    private String portalValues;
            
    public MaterialesCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoREX_Materiales(logInBean.getFactory());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            fetchREX_Material(Integer.parseInt(id));
            proveedores = new ValueLists();
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

    public REX_Materiales getModel() {
        return model;
    }

    public void setModel(REX_Materiales model) {
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

    public ValueLists getProveedores() {
        return proveedores;
    }

    public void setProveedores(ValueLists proveedores) {
        this.proveedores = proveedores;
    }

    public String getPortalValues() {
        return portalValues;
    }

    public void setPortalValues(String portalValues) {
        this.portalValues = portalValues;
    }
    
    public void fetchREX_Material(int id)
    {
        model=dao.getREX_Material(id);
        model2=dao.getREX_Material(id);
        if(id<1)
        {
            model.setTipo("Acero");
            nm=ti=nt=un=cambios=true;
        }
    }
    
    public void loadProveedoresList(AjaxBehaviorEvent event)
    {
        System.out.println("loadClientesList METHOD REACHED");
        List temp=dao.loadProveedoresList(model2.getTipo());
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
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveREX_Material(logInBean.getUsuario().getUsername());
    }
    
    public String parseDates(String d,String c){
        System.out.println("DATE: "+d);
        String[]f=d.split(c);
        c=c.equals("-")?"/":"-";
        return f[2]+c+f[1]+c+f[0];
    }
    
    public void saveREX_Material(String usu)
    {
        System.out.println("saveREX_Material METHOD REACHED");
        System.out.println("Current ID: "+model.getId_REX_Material());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_REX_Material()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            dao.newREX_Material(model);
        }
        else
        {
            System.out.println("REX_Materiales "+model.getId_REX_Material()+" MODIFY ABOUT TO START");
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifyREX_Material(model);
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
        nm=ti=nt=un=cambios=false;
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
            ro.setREX_Proveedor(new REX_Proveedores());
            ro.getREX_Proveedor().setId_REX_Proveedor(Long.parseLong(arr[2]));
            ro.setREX_Material(model);
            ro.setPrecio(arr[4].isEmpty()?0:Float.parseFloat(arr[4]));
            ro.setMoneda(arr[5]);
            String u=logInBean.getUsuario().getUsername();
            ro.setZz_UsuarioCreacion(u);
            ro.setZz_UsuarioModificacion(u);
            portalValues=dro.newREX_Oferta(ro);
        }
        else
        {
            System.out.println("OFERTA ID IS NOT EMPTY: "+arr[2]);
            REX_Oferta ro=dro.getREX_Oferta(Long.parseLong(arr[1]));
            if(ro.getREX_Proveedor()==null)
            {
                System.out.println("OFERTA PROVEEDOR WAS NULL");
                ro.setREX_Proveedor(new REX_Proveedores());
            }
            ro.getREX_Proveedor().setId_REX_Proveedor(Long.parseLong(arr[2]));
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
    
    public String deleteREX_Material()
    {
        if(model.getId_REX_Material()>0)
        {
            //model2.set_Cliente(null);
            dao.deleteREX_Material(model2);
        }
        return "materiales";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "NM":nm=!model.getNombre().equals(model2.getNombre());break;
            case "TI":ti=!model.getTipo().equals(model2.getTipo());break;
            case "UN":un=!model.getUnidad().equals(model2.getUnidad());break;
            case "NT":nt=!model.getNotas().equals(model2.getNotas());break;
        }
        cambios=ti||nm||nt||un;
        System.out.println("Cambios: "+cambios);
    }
    
}