package com.solemsa.jsf.controllerBeans.JUR;

import com.solemsa.hibernate.entities.JUR.JUR_NotificacionAlcance;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.jsf.DAOs.JUR.daoJUR_Landing;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.List;

@ManagedBean()
@ViewScoped
public class LandingCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoJUR_Landing dao;
    private List<JUR_Notificaciones> JUR_Notificaciones;
    private int JUR_NotificacionesLength;
    private String auxValues;
    private JUR_NotificacionAlcance JUR_Alcance;
    
            
    public LandingCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct LandingCVS METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoJUR_Landing(logInBean.getFactory());
            logInBean.setHideHeaderRecordControls(true);
            //fetchJUR_Notificaciones();
        }
        System.out.println("@PostConstruct LandingCVS METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<JUR_Notificaciones> getJUR_Notificaciones() {
        return JUR_Notificaciones;
    }

    public void setJUR_Notificaciones(List<JUR_Notificaciones> JUR_Notificaciones) {
        this.JUR_Notificaciones = JUR_Notificaciones;
    }

    public int getJUR_NotificacionesLength() {
        return JUR_NotificacionesLength;
    }

    public void setJUR_NotificacionesLength(int JUR_NotificacionesLength) {
        this.JUR_NotificacionesLength = JUR_NotificacionesLength;
    }

    public String getAuxValues() {
        return auxValues;
    }

    public void setAuxValues(String auxValues) {
        this.auxValues = auxValues;
    }

    public JUR_NotificacionAlcance getJUR_Alcance() {
        return JUR_Alcance;
    }

    public void setJUR_Alcance(JUR_NotificacionAlcance JUR_Alcance) {
        this.JUR_Alcance = JUR_Alcance;
    }

    public void fetchJUR_Notificaciones()
    {
        System.out.println("ABOUT TO TRY AND FETCH JUR_Notificaciones: "+(dao!=null));
        JUR_Notificaciones=dao.getJUR_NotificacionesList(logInBean.getUsuario().getJur(),logInBean.getUsuario().isGerente());
        JUR_NotificacionesLength=JUR_Notificaciones.size();
        System.out.println("FETCHED "+JUR_NotificacionesLength+" JUR_Notificaciones");
    }
    
    public boolean getPrioridadByReferenc(JUR_Notificaciones n)
    {
        System.out.println("getFechaEntregaTarea METHOD REACHED");
        if(!n.isPrioridad())
            if(n.getJUR_Tarea()!=null)
            {
                if(n.getJUR_Tarea().getFechaEntrega()!=null)
                {
                    boolean f=dao.ca.stringToDate(dao.ca.parseDate(dao.ca.substractNaturalDaysToDate(n.getJUR_Tarea().getFechaEntrega(),5),"-"),"/").compareTo(dao.ca.getCurrentDate())<1;
                    if(f)
                        dao.changePrioridad(n,f);
                    return f;
                }
                else return false;
            }
        return n.isPrioridad();
    }
    
    public boolean checkIfHasAlcance(JUR_Notificaciones noti)
    {
        long id=logInBean.getUsuario().getJur();
        boolean f=false;
        if(noti.getJUR_Alcance()!=null)
        {
            int n=noti.getJUR_AlcanceLength()==null?0:noti.getJUR_AlcanceLength();
            for(int i=0;i<n&&!f;i++)
            {
                JUR_Alcance=noti.getJUR_Alcance().get(i);
                System.out.println(JUR_Alcance.getNotas()+"");
                f=JUR_Alcance.getId_JUR_NotificacionAlcance()==id;
                if(f) break;
                JUR_Alcance=null;
            }
        }
        return f;
    }
    
    public String getFechaEntregaTarea(JUR_Notificaciones n)
    {
        System.out.println("getFechaEntregaTarea METHOD REACHED");
        if(n.getJUR_Tarea()!=null)
        {
            if(n.getJUR_Tarea().getFechaEntregaString()==null&&n.getJUR_Tarea().getFechaEntrega()!=null)
            {
                n.getJUR_Tarea().setFechaEntregaString(dao.ca.parseDate(n.getJUR_Tarea().getFechaEntrega()+"","-"));
                return "Entrega: "+n.getJUR_Tarea().getFechaEntregaString();
            }
            else return "Entrega: No especificada";
        }
        return "";
    }
    
    public String goToTarea()
    {
        logInBean.setActiveModule(5,10,"tareas");
        return "tareasD?faces-redirect-true";
    }
    
    public void hideRow()
    {
        System.out.println("hideRow METHOD REACHED");
        dao.hideNotification(JUR_Notificaciones,auxValues);
        auxValues="";
        System.out.println("hideRow METHOD PERFORMED");
    }
    
}