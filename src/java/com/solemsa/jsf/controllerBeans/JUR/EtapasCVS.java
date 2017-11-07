package com.solemsa.jsf.controllerBeans.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_EtapaDetalle;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.List;

@ManagedBean()
@ViewScoped
public class EtapasCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Etapas dao;
    private List<JUR_Etapas>modelList,modelList2;
    private JUR_Casos JUR_Caso;
    private int rowID;
    private boolean[] cambios,nm,de;
    ////////Portal Attributes////////
    private String portalValues;
    ////////PopOver Attributes///////
    private JUR_Notificaciones popOver;
            
    public EtapasCVS(){
        
    }
    
    public EtapasCVS(JUR_Casos JUR_Caso){
        this.JUR_Caso=JUR_Caso;
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct EtapasCVS METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoJUR_Etapas(logInBean.getFactory());
            String id;
            System.out.println("JUR_Caso: "+JUR_Caso);
            if(JUR_Caso==null)
            {
                java.util.Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                id="formu:idHidden";
                id=params.get(id);
                if(id==null)
                    id=JUR_Caso.getId_JUR_Caso()+"";
                System.out.println("POSTCONSTRUCT ID: "+id);
                fetchJUR_Etapas((id==null || id.isEmpty())?0:Long.parseLong(id));
            }
        }
        System.out.println("@PostConstruct EtapasCVS METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<JUR_Etapas> getModelList() {
        return modelList;
    }

    public void setModelList(List<JUR_Etapas> modelList) {
        this.modelList = modelList;
    }

    public JUR_Casos getJUR_Caso() {
        return JUR_Caso;
    }

    public void setJUR_Caso(JUR_Casos JUR_Caso) {
        this.JUR_Caso = JUR_Caso;
    }

    public JUR_Notificaciones getPopOver() {
        return popOver;
    }

    public void setPopOver(JUR_Notificaciones popOver) {
        this.popOver = popOver;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public String getPortalValues() {
        return portalValues;
    }

    public void setPortalValues(String portalValues) {
        this.portalValues = portalValues;
    }

    public boolean[] getCambios() {
        return cambios;
    }

    public void setCambios(boolean[] cambios) {
        this.cambios = cambios;
    }
    
    public void initializeBooleans()
    {
        cambios=new boolean[JUR_Caso.getJUR_EtapasLength()];
        System.out.println("CAMBIOS.length: "+cambios.length);
        nm=new boolean[cambios.length];
        de=new boolean[cambios.length];
        if(JUR_Caso.getJUR_EtapasLength()<2 && modelList.get(0).getId_JUR_Etapa()<1)
        {
            setBooleansTo(cambios,true);
            setBooleansTo(nm,true);
            setBooleansTo(de,true);
        }
        else
        {
            setBooleansTo(cambios,false);
            setBooleansTo(nm,false);
            setBooleansTo(de,false);
        }
    }
    
    public void fetchJUR_Etapas(long id)
    {
        System.out.println("fetchJUR_Etapas METHOD REACHED WITH: "+id);
        JUR_Caso=dao.loadJUR_Caso(id);
        modelList=JUR_Caso.getJUR_Etapas();
        if(modelList==null)
            modelList=dao.getJUR_EtapasList(JUR_Caso);
        if(JUR_Caso.getJUR_EtapasLength()>0)
        {
            initializeBooleans();
            modelList2=cloneLista();
        }
        //else
        //    addAccordionNode();
    }
    
    public void updateFormServicios(int vsIndex)
    {
        System.out.println("updateFormServicios METHOD REACHED: "+vsIndex);
        //this.vsIndex=Integer.parseInt(e.getComponent().getAttributes().get("data-index").toString());
        saveJUR_Etapas(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre(),vsIndex);
        setBooleansTo(nm,false,vsIndex);
        setBooleansTo(de,false,vsIndex);
        setBooleansTo(cambios,false,vsIndex);
    }
    
    public void saveJUR_Etapas(String usu,int vsIndex)
    {
        JUR_Etapas model=modelList.get(vsIndex),model2=null;
        System.out.println("saveJUR_Etapas METHOD REACHED WITH: "+vsIndex);
        System.out.println("Current ID: "+model.getId_JUR_Etapa());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_JUR_Etapa()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            model2=dao.newJUR_Etapa(model);
        }
        else
        {
            System.out.println("JUR_Etapas "+model.getId_JUR_Etapa()+" MODIFY ABOUT TO START");
            try{
                model2=dao.modifyJUR_Etapa(model);
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
                e.printStackTrace();
            }
        }
        modelList2.set(vsIndex,model2);
        setBooleansTo(nm,false,vsIndex);
        setBooleansTo(de,false,vsIndex);
        setBooleansTo(cambios,false,vsIndex);
    }
    
    public void checkForChanges(AjaxBehaviorEvent e)
    {
        String s=e.getComponent().getId();
        int vsIndex=Integer.parseInt(e.getComponent().getAttributes().get("data-index").toString());
        System.out.println("AJAX EVENT COMPONENT ID: "+s+"["+vsIndex+"]");
        JUR_Etapas model=modelList.get(vsIndex),model2=modelList2.get(vsIndex);
        if(model.getId_JUR_Etapa()>0)
        {
            switch(s)
            {
                case "NM":nm[vsIndex]=!model.getNombre().equals(model2.getNombre());System.out.println("1)"+model.getNombre()+"\n2)"+model2.getNombre());break;
                case "DE":de[vsIndex]=!model.getDescripcion().equals(model2.getDescripcion());break;
            }
            cambios[vsIndex]=nm[vsIndex]||de[vsIndex];
        }
        System.out.println("Cambios: "+cambios[vsIndex]);
    }
    
    private List<JUR_Etapas> cloneLista()
    {
        List<JUR_Etapas> l=dao.ca.newArrayList();
        int n=JUR_Caso.getJUR_EtapasLength();
        for (int i=0; i<n;i++)
            l.add(dao.cloneModel(modelList.get(i)));
        return l;
    }
    
    public void addAccordionNode()
    {
        System.out.println("addAccordionNode METHOD REACHED: "+JUR_Caso);
        try
        {
            JUR_Etapas tmp=new JUR_Etapas();
            tmp.setJUR_Caso(JUR_Caso);
            dao.setDefaultData(tmp,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
            JUR_Caso.getJUR_Etapas().add(tmp);
            int n=JUR_Caso.getJUR_Etapas().size();
            System.out.println("\tn: "+n);
            JUR_Caso.setJUR_EtapasLength(n);
            if(modelList2==null)
            {
                modelList2=cloneLista();
                initializeBooleans();
            }
            else
            {
                modelList2.add(dao.cloneModel(tmp));
                boolean[] nmt=nm.clone(),det=de.clone(),cat=cambios.clone();
                nm=new boolean[n];
                de=new boolean[n];
                cambios=new boolean[n];
                nm[--n]=true;
                de[n]=true;
                cambios[n]=true;
                for(int i=0;i<n;i++)
                {
                    nm[i]=nmt[i];
                    de[i]=det[i];
                    cambios[i]=cat[i];
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("addAccordionNode EXCEPTION: "+e);
            e.printStackTrace();
        }
        System.out.println("addAccordionNode METHOD SUCCESSFUL");
    }
    
    public void rmvAccordionNode(int index)
    {
        System.out.println("rmvAccordionNode METHOD REACHED");
        try{
            JUR_Etapas tmp=modelList.get(index);
            if(tmp.getId_JUR_Etapa()>0)
                dao.deleteJUR_Etapa(tmp);
            modelList.remove(tmp);
            modelList2.remove(index);
            boolean nmt[]=nm.clone(),det[]=de.clone(),cat[]=cambios.clone();
            int n=nmt.length;
            if(n>1)n--;
            nm=new boolean[n];
            de=new boolean[n];
            cambios=new boolean[n];
            for(int i=0;i<n;i++)
                if(i>index)
                {
                    nm[i]=nmt[i+1];
                    de[i]=det[i+1];
                    cambios[i]=cat[i+1];
                }
                else
                {
                    nm[i]=nmt[i];
                    de[i]=det[i];
                    cambios[i]=cat[i];
                }
            JUR_Caso.setJUR_EtapasLength(JUR_Caso.getJUR_EtapasLength()-1);
        }
        catch(Exception e)
        {
            System.out.println("rmvAccordionNode EXCEPTION: "+e);
        }
        System.out.println("rmvAccordionNode METHOD SUCCESSFUL");
    }
    
    public void deleteFromPortal(int vsIndex)
    {
        System.out.println("portalValues: "+portalValues);
        JUR_Etapas model=modelList.get(vsIndex);
        String usu=logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre();
        System.out.println("model.getJUR_EtapaDetalleLength(): "+model.getJUR_EtapaDetalleLength());
        JUR_EtapaDetalle tmp=dao.getDetalleFromList(portalValues,model.getJUR_EtapaDetalle(),model.getJUR_EtapaDetalleLength());
        tmp.setJUR_Etapa(model);
        dao.deleteJUR_EtapaDetalle(tmp,usu);
        model.setJUR_EtapaDetalleLength(model.getJUR_EtapaDetalleLength()-1);
    }
    
    public void commitPortal(int vsIndex)
    {
        System.out.println("commitPortal METHOD REACHED with: "+portalValues);
        JUR_Etapas model=modelList.get(vsIndex);
        String[] arr=dao.ca.commitPortal(portalValues);
        String usu=logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre();
        JUR_EtapaDetalle tmp=null;
        if(arr[1].isEmpty())//id
        {
            System.out.println("JUR_EtapaDetalle ID IS EMPTY");
            tmp=dao.newJUR_EtapaDetalle(model,arr,usu);
            modelList2.set(vsIndex,model);
            portalValues=tmp.getId_JUR_EtapaDetalle()+"";
        }
        else
        {
            System.out.println("JUR_EtapaDetalle ID IS NOT EMPTY: "+arr[1]);
            long id=dao.ca.parseStringLong(arr[1]);
            if(id>0)
            {
                tmp=dao.getDetalleFromList(id,model.getJUR_EtapaDetalle(),model.getJUR_EtapaDetalleLength());
                dao.modifyJUR_EtapaDetalle(tmp,arr,usu);
                portalValues=arr[1];
            }
            else
            {
                tmp=dao.newJUR_EtapaDetalle(model,arr,usu);
                //tmp=dao.setDetalleFromList(dao.ca.parseStringInt(arr[0]),tmp,model.getJUR_EtapaDetalle(),model.getJUR_EtapaDetalleLength());
                portalValues=""+tmp.getId_JUR_EtapaDetalle();
            }
        }
    }
    
    public void setBooleansTo(boolean[] arr,boolean p)
    {
        for(int i=0;i<arr.length;i++)
            arr[i]=p;
    }
    
    public void setBooleansTo(boolean[] arr,boolean p,int i)
    {
            arr[i]=p;
    }
    
    public void loadModelsOnClick(AjaxBehaviorEvent e)
    {
        System.out.println("loadModelsOnClick METHOD REACHED");
        int vsIndex=Integer.parseInt(e.getComponent().getAttributes().get("data-index").toString());
        System.out.println("model TO BE RETRIEVED: "+vsIndex);
        JUR_Etapas model=modelList.get(vsIndex);
        //model2=dao.cloneModel(model);
        dao.loadJUR_EtapaDetalle(model);
        System.out.println("loadModelsOnClick METHOD PERFORMED");
    }
    
    public void loadPopoverContent()
    {
        System.out.println("loadPopoverContent METHOD REACHED");
        //String[] vsIndex=e.getComponent().getAttributes().get("data-index").toString().split("_");
        String[] vsIndex=portalValues.split("_");
        popOver=dao.loadJUR_NotificacionModelFromList(modelList,Integer.parseInt(vsIndex[0]),Integer.parseInt(vsIndex[1])-1);
        System.out.println("popOver.getJUR_AlcanceLength(): "+popOver.getJUR_AlcanceLength());
        portalValues="["+vsIndex[1]+"],";
        vsIndex[0]=popOver.getFechaInicioString();
        vsIndex[1]=popOver.getFechaFinString();
        portalValues+="["+(vsIndex[0]==null?"":vsIndex[0])+"],";
        portalValues+="["+(vsIndex[1]==null?"":vsIndex[1])+"],";
        vsIndex[0]=popOver.isPrioridad()+"";
        vsIndex[1]=popOver.getDescripcion();
        portalValues+="["+(vsIndex[0]==null?"":vsIndex[0])+"],";
        portalValues+="["+(vsIndex[1]==null?"":vsIndex[1])+"]";
        System.out.println("portalValues: "+portalValues);
        System.out.println("loadPopoverContent METHOD PERFORMED");
    }
    
    public boolean detalleIsLoaded(int vsIndex)
    {
        return modelList.get(vsIndex).getJUR_EtapaDetalleLength()>0;
    }
    
    public void setPopoverFieldValue()
    {
        System.out.println("setPopoverFieldValue MEHTOD REACHED\nThis Method is only for the values set in the view to take place in the bean\nsetPopoverFieldValue MEHTOD PERFORMED");
    }
    
    public void inPopoverActions(boolean delete)
    {
        System.out.println("inPopoverActions METHOD REACHED WITH: "+delete);
        if(delete)
        {
            dao.deleteNotificacion(popOver,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        }
        else
        {
            popOver.setJUR_Caso(JUR_Caso);
            dao.saveNotificacion(popOver,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        }
        System.out.println("inPopoverActions METHOD PERFORMED");
    }
    
    public String goToCaso()
    {
        System.out.println("goToCaso METHOD REACHED");
        logInBean.setHideHeaderRecordControls(!logInBean.getUsuario().isGerente());
        System.out.println("goToCaso METHOD PERFORMED");
        return "casosD?faces-redirect-true";
    }
    
}