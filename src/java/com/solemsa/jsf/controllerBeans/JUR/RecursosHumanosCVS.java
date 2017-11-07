package com.solemsa.jsf.controllerBeans.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.JUR.daoJUR_RecursosHumanos;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.List;

@ManagedBean()
@ViewScoped
public class RecursosHumanosCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_RecursosHumanos dao;
    private JUR_RecursosHumanos model;
    private JUR_RecursosHumanos model2;
    private int rowID;
    private boolean nm,ap,pu,in,ob,cambios=false;
    ////////Portal Attributes////////
    private String portalValues;
    private List<JUR_Casos> Casos;
    private ValueLists Etapas;
    private int CasosLength;
            
    public RecursosHumanosCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoJUR_RecursosHumanos(logInBean.getFactory());
            java.util.Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id="formu:idHidden";
            id = params.get(id);
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null || id.isEmpty())?"0":id;
            fetchJUR_RecursosHumanos(Integer.parseInt(id));
            Casos=dao.ca.newArrayList();
            Etapas=new ValueLists();
            logInBean.setHideHeaderRecordControls(false);
        }
        System.out.println("@PostConstruct METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public JUR_RecursosHumanos getModel() {
        return model;
    }

    public void setModel(JUR_RecursosHumanos model) {
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

    public List<JUR_Casos> getCasos() {
        return Casos;
    }

    public void setCasos(List<JUR_Casos> Casos) {
        this.Casos = Casos;
    }

    public ValueLists getEtapas() {
        return Etapas;
    }

    public void setEtapas(ValueLists Etapas) {
        this.Etapas = Etapas;
    }

    public int getCasosLength() {
        return CasosLength;
    }

    public void setCasosLength(int CasosLength) {
        this.CasosLength = CasosLength;
    }
    
    public void loadCasosList()
    {
        System.out.println("loadCasosList METHOD REACHED");
        if(CasosLength<1)
        {
            List<JUR_Casos> l=dao.loadJUR_CasosList();
            int n=l.size();
            for(int i=0;i<n;i++)
                Casos.add(dao.cloneCaso(l.get(i)));
            CasosLength=Casos.size();
        }
        System.out.println("loadCasosList METHOD PERFORMED");
    }
    
    public void fetchJUR_RecursosHumanos(int id)
    {
        model=dao.getJUR_RecursoHumano(id);
        model2=dao.getJUR_RecursoHumano(id);
        if(id<1)
            nm=ap=pu=in=ob=cambios=true;
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveJUR_RecursosHumanos(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
    }
    
    public void saveJUR_RecursosHumanos(String usu)
    {
        System.out.println("saveJUR_RecursosHumanos METHOD REACHED");
        System.out.println("Current ID: "+model.getId_JUR_RecursoHumano());
        model.setZz_UsuarioModificacion(usu);
        if(model.getId_JUR_RecursoHumano()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            model2=dao.newJUR_RecursoHumano(model);
        }
        else
        {
            System.out.println("JUR_RecursosHumanos "+model.getId_JUR_RecursoHumano()+" MODIFY ABOUT TO START");
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifyJUR_RecursoHumano(model);
                System.out.println("MODEL 2 SUCCESSFULLY MODIFIED");
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
            }
        }
        nm=ap=pu=in=ob=cambios=false;
    }
    
    public String deleteJUR_RecursosHumanos()
    {
        if(model.getId_JUR_RecursoHumano()>0)
            dao.deleteJUR_RecursoHumano(model2);
        return "casos";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "NM":nm=!model.getNombre().equals(model2.getNombre());break;
            case "AP":ap=!model.getApellidos().equals(model2.getApellidos());break;
            case "PU":pu=!model.getPuesto().equals(model2.getPuesto());break;
            case "IN":pu=model.isInterno()!=model2.isInterno();break;
            case "OB":pu=!model.getObservaciones().equals(model2.getObservaciones());break;
        }
        cambios=nm||ap||pu||in||ob;
        System.out.println("Cambios: "+cambios);
    }
    
    public void deleteFromPortal()
    {
        System.out.println("deleteFromPortal METHOD REACHED with: "+portalValues);
        dao.deleteJUR_Tarea(dao.ca.parseStringLong(portalValues));
        portalValues="";
        System.out.println("deleteFromPortal METHOD PERFORMED");
    }
    
    public void commitPortal()
    {
        System.out.println("commitPortal METHOD REACHED with: "+portalValues);
        String[] arr=dao.ca.commitPortal(portalValues);
        portalValues=""+dao.modifyJUR_Tareas(dao.setJUR_TareaRelationships(arr,model,Casos,Etapas),arr,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        System.out.println("commitPortal METHOD PERFORMED");
    }
    
    public void loadEtapasForPortal()
    {
        System.out.println("loadEtapasForPortal METHOD REACHED with: "+portalValues+","+CasosLength);
        if(CasosLength<1)
            loadCasosList();
        int id=Integer.parseInt(portalValues),i;
        JUR_Casos tmp=null;
        for(i=0;i<CasosLength;i++)
        {
            tmp=Casos.get(i);
            if(tmp.getId_JUR_Caso()==id)
                break;
        }
        System.out.println("tmp: "+tmp);
        if(i<CasosLength)
        {
            List<JUR_Etapas>l=dao.loadEtapasByCaso(tmp.getId_JUR_Caso());
            int n=l.size();
            Etapas=new ValueLists();
            for(i=0;i<n;i++)
            {
                JUR_Etapas node=l.get(i);
                Etapas.add(node.getId_JUR_Etapa(),node.getNombre());
            }
        }
        System.out.println("loadEtapasForPortal METHOD PERFORMED");
    }
}