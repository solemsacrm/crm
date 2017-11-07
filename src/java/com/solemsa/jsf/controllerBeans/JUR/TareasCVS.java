package com.solemsa.jsf.controllerBeans.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Tareas;
import com.solemsa.hibernate.entities.JUR.JUR_Tareas;
import com.solemsa.jsf.data.ListValues;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Iterator;
import java.util.List;

@ManagedBean()
@ViewScoped
public class TareasCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Tareas dao;
    private JUR_Tareas model;
    private JUR_Tareas model2;
    private int rowID;
    private boolean ca,et,as,de,fe,fa,no,es,rh,cambios;
    ////////Portal Attributes////////
    private String portalValues;
    private ValueLists casos,etapas,RHs;
            
    public TareasCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoJUR_Tareas(logInBean.getFactory());
            java.util.Map<String,String> params=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String id="formu:idHidden";
            id = params.get(id);
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null || id.isEmpty())?"0":id;
            fetchJUR_Tareas(Integer.parseInt(id));
            casos = new ValueLists();
            RHs = new ValueLists();
            etapas = new ValueLists();
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

    public JUR_Tareas getModel() {
        return model;
    }

    public void setModel(JUR_Tareas model) {
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

    public ValueLists getCasos() {
        return casos;
    }

    public void setCasos(ValueLists casos) {
        this.casos = casos;
    }

    public ValueLists getEtapas() {
        return etapas;
    }

    public void setEtapas(ValueLists etapas) {
        this.etapas = etapas;
    }
    
    public ValueLists getRHs() {
        return RHs;
    }

    public void setRHs(ValueLists RHs) {
        this.RHs = RHs;
    }
    
    public boolean skipEtapasListLoad() {
        return ca&&model.getId_JUR_Tarea()>0;
    }
    
    public String getRHnombre()
    {
        if(model.getJUR_RecursoHumano()!=null)
        {
            String n=model.getJUR_RecursoHumano().getNombre(),
            a=model.getJUR_RecursoHumano().getApellidos();
            return (n!=null?(n.trim()):"")+" "+(a!=null?(a.trim()):"");
        }
        else return null;
    }
    
    public void fetchJUR_Tareas(int id)
    {
        model=dao.getJUR_Tarea(id,false);
        model2=dao.getJUR_Tarea(id,true);
        if(id<1)
        {
            ca=et=as=de=fe=fa=no=es=rh=cambios=true;
        }
        else 
        {
            dao.setJUR_TareaRelaciones(model);
            dao.setJUR_TareaRelaciones(model2);
            ca=et=as=de=fe=fa=no=es=rh=cambios=false;
        }
    }
    
    public void loadCasosList()
    {
        System.out.println("loadCasosList METHOD REACHED");
        List temp=dao.loadJUR_CasosList();
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[]tupla=(Object[])i.next();
            casos.add((Long)tupla[0],(tupla[1]!=null?tupla[1]:tupla[0]).toString());
        }
        System.out.println("loadCasosList LOADED WITH: "+casos.getLength()+" RECORDS");
        casos.setChanges(false);
        System.out.println("loadCasosList METHOD PERFORMED");
    }
    
    public void loadEtapasList()
    {
        System.out.println("loadEtapasList METHOD REACHED");
        if(model.getJUR_Caso().getId_JUR_Caso()>0)
        {
            etapas=new ValueLists();
            List<JUR_Etapas> temp=dao.loadJUR_EtapasList(model.getJUR_Caso());
            Iterator i=temp.iterator();
            JUR_Etapas tupla=null;
            while(i.hasNext())
            {
                tupla=(JUR_Etapas)i.next();
                etapas.add(tupla.getId_JUR_Etapa(),(tupla.getNombre()!=null?tupla.getNombre():tupla.getId_JUR_Etapa()+""));
            }
            if(tupla!=null)
            {
                model.setJUR_Caso(tupla.getJUR_Caso());
                model.setJUR_Etapa(new JUR_Etapas());
            }
            System.out.println("loadEtapasList LOADED WITH: "+etapas.getLength()+" RECORDS");
            etapas.setChanges(false);
        }
        System.out.println("loadEtapasList METHOD PERFORMED");
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
        saveJUR_Tareas(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
    }
    
    public void saveJUR_Tareas(String usu)
    {
        System.out.println("saveJUR_Tareas METHOD REACHED");
        System.out.println("Current ID: "+model.getId_JUR_Tarea());
        model.setZz_UsuarioModificacion(usu);
        //if(model.getJuzgado().equals("Penal"))
        //    model.setPapelCliente("Denunciante");
        if(!ca)
        {
            if(model.getJUR_Etapa().getId_JUR_Etapa()<1)
                if(model2.getJUR_Etapa().getId_JUR_Etapa()>0)
                    model.setJUR_Etapa(model2.getJUR_Etapa());
        }
        if(model.getId_JUR_Tarea()<1)
            dao.newJUR_Tarea(model,usu,logInBean.getUsuario().isGerente());
        else dao.modifyJUR_Tarea(model,usu,logInBean.getUsuario().isGerente());
        model=dao.getJUR_Tarea(model.getId_JUR_Tarea(),true);
        model2=dao.getJUR_Tarea(model.getId_JUR_Tarea(),true);
        dao.setJUR_TareaRelaciones(model);
        dao.setJUR_TareaRelaciones(model2);
        //if(casos.getLength()<1)
        {
            casos=new ValueLists();
            etapas=new ValueLists();
            //loadCasosList();
        }
        //if(RHs.getLength()<1)
        {
            RHs=new ValueLists();
            //loadRHsList();
        }
        ca=et=as=de=fe=fa=no=es=rh=cambios=false;
    }
    
    public String deleteJUR_Tareas()
    {
        if(model.getId_JUR_Tarea()>0&&logInBean.getUsuario().isGerente())
        {
            model2.setJUR_Caso(null);
            model2.setJUR_Etapa(null);
            model2.setJUR_RecursoHumano(null);
            dao.deleteJUR_Tarea(model2);
        }
        return "tareas";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        switch(s)
        {
            case "DE":de=!(model.getDescripcion()!=null?model.getDescripcion():"").equals(model2.getDescripcion()!=null?model2.getDescripcion():"");break;
            case "NO":no=!(model.getNotas()!=null?model.getNotas():"").equals(model2.getNotas()!=null?model2.getNotas():"");break;
            case "FA":fa=!model.getFechaAsignacionString().equals(model2.getFechaAsignacionString());break;
            case "FE":fe=!model.getFechaAsignacionString().equals(model2.getFechaEntregaString());break;
            case "AS":as=!(model.getAsunto()!=null?model.getAsunto():"").equals(model2.getAsunto()!=null?model2.getAsunto():"");break;
            case "CA":ca=model.getJUR_Caso().getId_JUR_Caso()!=model2.getJUR_Caso().getId_JUR_Caso();onJUR_CasoChangeActions();break;
            case "ET":et=model.getJUR_Etapa().getId_JUR_Etapa()!=model2.getJUR_Etapa().getId_JUR_Etapa();break;
            case "RH":rh=model.getJUR_RecursoHumano().getId_JUR_RecursoHumano()!=model2.getJUR_RecursoHumano().getId_JUR_RecursoHumano();break;
            case "ES":es=model.isFinalizado()!=model2.isFinalizado();break;
        }
        cambios=fa||fe||as||ca||et||de||no||es||rh;
        System.out.println("Cambios: "+cambios);
    }
    
    private void onJUR_CasoChangeActions()
    {
        if(ca)
            model.setJUR_Etapa(new JUR_Etapas());
        else
        {
            model.setJUR_Etapa(model2.getJUR_Etapa());
            et=false;
        }
        etapas=new ValueLists();
        dao.loadJUR_CasoCliente(model);
    }
    
    private void setValueListsAfterSave()
    {
        System.out.println("setValueListsAfterSave METHOD REACHED");
        long search=model.getJUR_Caso().getId_JUR_Caso();
        String txt=null;
        for(ListValues t:casos.getList())
            if(t.getId()==search)
            {
                txt=t.getLabel();
                break;
            }
        model.getJUR_Caso().setNombre(txt);
        casos = new ValueLists();
        search=model.getJUR_Etapa().getId_JUR_Etapa();
        txt=null;
        for(ListValues t:etapas.getList())
            if(t.getId()==search)
            {
                txt=t.getLabel();
                break;
            }
        model.getJUR_Etapa().setNombre(txt);
        RHs = new ValueLists();
        search=model.getJUR_RecursoHumano().getId_JUR_RecursoHumano();
        txt=null;
        for(ListValues t:RHs.getList())
            if(t.getId()==search)
            {
                txt=t.getLabel();
                break;
            }
        model.getJUR_RecursoHumano().setNombre(txt);
        model.getJUR_RecursoHumano().setApellidos("");
        RHs = new ValueLists();
        System.out.println("setValueListsAfterSave METHOD PERFORMED");
    }
    
}