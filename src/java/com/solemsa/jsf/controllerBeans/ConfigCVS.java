package com.solemsa.jsf.controllerBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.daoConfig;
import com.solemsa.hibernate.entities.Usuarios;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean()
@ViewScoped
public class ConfigCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoConfig dao;
    private Usuarios model;
    private Usuarios model2;
    private boolean cambios=false;
    private CommonActions ca;
    private String path,co3,co2,co1,areaText;
    private int success;
            
    public ConfigCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        templatePath();
        if (logInBean.checkSession(getAreaAbbreviation()))
        {
            if(dao==null)
                dao=new daoConfig(logInBean.getFactory());
            ca=new CommonActions();
            fetchUsuario();
            cambios=false;
            success=1;
        }
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public Usuarios getModel() {
        return model;
    }

    public void setModel(Usuarios model) {
        this.model = model;
    }
    
    public boolean isCambios() {
        return cambios;
    }

    public void setCambios(boolean cambios) {
        this.cambios = cambios;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCo1() {
        return co1;
    }

    public void setCo1(String co1) {
        this.co1 = co1;
    }
    
    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }
    
    public String getCo3() {
        return co3;
    }

    public void setCo3(String co3) {
        this.co3 = co3;
    }

    public String getAreaText() {
        return areaText;
    }

    public int getSuccess() {
        return success;
    }
    
    private void templatePath()
    {
        String a=null;
        switch(logInBean.getUsuario().getArea())
        {
            case 1: a="AOU";areaText="Área de Administración y Outsourcing";break;
            case 2: a="JUR";areaText="Área Legal";break;
            case 3: a="CNT";areaText="Área de Contabilidad";break;
            case 4: a="SEG";areaText="Área de Seguridad";break;
            case 5: a="REX";areaText="REX Irrigación";break;
            case 6: a="VEN";areaText="Área de Ventas";break;
            default: if(logInBean.getUsuario().isDirectivo())a="DVO";areaText="Directivo";break;
        }
        path="./../../WEB-INF/templates/"+a+"_SideBarTemplate.xhtml";
        System.out.println("PATH AREA VALUE: "+path);
    }
    
    public String getAreaAbbreviation()
    {
        return logInBean.getUsuario().isDirectivo()?"SOLEMSA-logo-ok":path.split("templates/")[1].substring(0,3);
    }
    
    public void cambiarContrasenia(AjaxBehaviorEvent e)
    {
        cambios=true;
        success=1;
    }

    private void fetchUsuario()
    {
        String u=logInBean.getUsuario().getUsername();
        int a=logInBean.getUsuario().getArea();
        boolean d=logInBean.getUsuario().isDirectivo();
        this.model=dao.getUsuario(u,a,d);
        this.model2=dao.getUsuario(u,a,d);
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        co2=co2.trim();co3=co3.trim();
        String s1=ca.encryptString(co1),s2=ca.encryptString(co2),s3=ca.encryptString(co3);
        if(s1.equals(model2.getContrasenia())&&(!co2.isEmpty())&&(!co3.isEmpty())&&s3.equals(s2))
        {
            model.setUsuario(model2.getUsuario());
            model.setContrasenia(s2);
            saveUsuario(logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
            success=2;
            cambios=false;
            co3=co2=co1="";
        }
        else
        {
            model.setUsuario(model2.getUsuario());
            model.setContrasenia(model2.getContrasenia());
            success=(co2.isEmpty()||co3.isEmpty())?4:3;
            cambios=true;
        }
        
    }
    
    private void saveUsuario(String p_Usuario)
    {
        System.out.println("saveCliente METHOD REACHED");
        if(logInBean.checkSession(path.split("templates/")[1].substring(0,3)))
        {
            System.out.println("Current ID: "+model.getId_Usuario());
            model.setZz_UsuarioModificacion(p_Usuario);
            System.out.println("cliente "+model.getId_Usuario()+" MODIFY ABOUT TO START");
            model2=dao.modifyUsuario(model);
            model.setZz_FechaModificacion(model2.getZz_FechaModificacion());
        }
    }
    
}