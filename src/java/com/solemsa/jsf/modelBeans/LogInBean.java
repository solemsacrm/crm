package com.solemsa.jsf.modelBeans;

import javax.faces.bean.ManagedBean;
import com.solemsa.jsf.data.Usuario;
import javax.faces.bean.SessionScoped;
import com.solemsa.jsf.DAOs.daoUsuario;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.context.FacesContext;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@ManagedBean (name = "loginBean")
@SessionScoped
public class LogInBean implements Serializable{
    
    private String nombre,contrasenia,rol,module;
    private String inicio;
    private Usuario usuario;
    private SessionFactory Factory;
    private String[] active;
    private String rowID;
    private boolean hideHeaderRecordControls=false;

    public LogInBean(){
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getContrasenia()
    {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia)
    {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol)
    {
        this.rol = rol;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public SessionFactory getFactory() {
        return Factory;
    }

    public String[] getActive() {
        return active;
    }

    public String getRowId() {
        return rowID;
    }

    public void setRowId(String id) {
        this.rowID = id;
    }

    public boolean isHideHeaderRecordControls() {
        return hideHeaderRecordControls;
    }

    public void setHideHeaderRecordControls(boolean hideHeaderRecordControls) {
        this.hideHeaderRecordControls = hideHeaderRecordControls;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
    
    public String navegar(String p_origen)
    {
        System.out.println("Navegación: INICIADA");
        module=p_origen;
        String cadena=null;
        cadena=new com.solemsa.jsf.data.CommonActions().encryptString(contrasenia);
        if(cadena!=null && !cadena.equals(contrasenia))
        {
            contrasenia=cadena;
            daoUsuario uDAO = new daoUsuario();        
            cadena=uDAO.validarLogIn(nombre,cadena);
            System.out.println("CADENAAAAAAAA: "+cadena);
            if(cadena!=null)//JDBC
            {
                
                String datos[]=cadena.split(",");
                if(nombre.equals(datos[3]) && contrasenia.equals(datos[4]))
                {
                    int length=datos.length;
                    for(int i=0; i<length;i++)
                        if(datos[i].equals("null"))
                            datos[i]="0";
                    contrasenia="";
                    if(length>9)
                        usuario = new Usuario(Long.parseLong(datos[0]),Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),nombre,contrasenia,Integer.parseInt(datos[5]),datos[6],Boolean.parseBoolean(datos[7].equals("1")?"true":"false"),Boolean.parseBoolean(datos[8].equals("1")?"true":"false"),datos[9]);
                    else
                        usuario = new Usuario(Long.parseLong(datos[0]),Integer.parseInt(datos[1]),Integer.parseInt(datos[2]),nombre,contrasenia,0,null,false,false,null);
                    nombre="";
                    rol="";
                    System.out.println("Navegación: SUCCESS - "+p_origen+"success");
                    if(!p_origen.equals(""))
                        p_origen="views/"+module;
                    System.out.println("ORIGEN: "+p_origen);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Calendar cal=Calendar.getInstance();
                    inicio=df.format(cal.getTime());
                    return p_origen+"success";
                }
            }
            else
            {
                try{usuario=null;}
                catch(Exception e){System.out.println("CATCH Navegación");}
                System.out.println("ERROR NAVGACIÓN: Null");
            }
                System.out.println("login Navegación: FAILURE - "+p_origen);
                return p_origen+"failure";
        }
        else
        {
            System.out.println("cadena Navegación: FAILURE - "+p_origen);
            return p_origen+"failure";
        }
    }
    
    public String redirigir() throws IOException
    {
        int ua=usuario.getArea();
        if(ua<1&&usuario.getJur()>0)
            ua=2;
        String a=new com.solemsa.jsf.data.CommonActions().getAreaName(ua,usuario.isDirectivo());
        System.out.println("Area: "+ua);
        System.out.println("a: "+a);
        if(module.equals(""))
            a="../"+a;
        else
            a="views/"+a;
        FacesContext.getCurrentInstance().getExternalContext().redirect(a+"/landing.xhtml");
        buildFactory(!a.equals(""));
        return a;
    }
    
    public boolean validar()
    {
        if(usuario!=null && !usuario.getUsername().equals(""))
        {
            System.out.println("USUARIO NOT NULL");
            return true;
        }
        else
        {
            System.out.println("USUARIO NULL");
            return false;
        }
    }
    
    public void setActiveModule(int j, int n, String module)
    {
        if(usuario==null)
        {
            try {
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                System.out.println("REDIRECTED TO LOGIN");
            } catch (Exception e) {
                System.out.println("LOGGED OUT EXCEPTION");
            }
        }
        else
        {
            this.module=module;
            active=new String[++n];
            for(int i=0; i<n; i++)
                if(i==j)
                    active[i]="active";
                else
                    active[i]="";
        }
    }
    
    private void buildFactory(boolean flag)
    {
        System.out.println("buildFactory CONSTRUCTOR REACHED");
        System.out.println("USER TO BE CONNECTED: "+usuario.getUsername()+", "+usuario.getContrasenia());
        if(flag)
            try{
                Configuration cfg = new Configuration();
                cfg.configure("com/solemsa/hibernate/hibernate.cfg.xml")
                   .addAnnotatedClass(com.solemsa.hibernate.entities.Clientes.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.Correos.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.Empleados.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.Telefonos.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.Usuarios.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_Avances.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_Obras.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.SEG.SEG_Seguimientos.class)
                   .addAnnotatedClass(com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones.class);
                int a=usuario.getArea();
                if(a<1&&usuario.getJur()>0)
                {
                    classesSwitch(cfg,2);
                    com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos rh=new com.solemsa.jsf.DAOs.JUR.daoJUR_RecursosHumanos(Factory).getJUR_RecursoHumano(usuario.getJur());
                    usuario.setArea(2);
                    usuario.setNombre(rh.getNombre()+rh.getApellidos());
                    usuario.setRol(rh.getPuesto());
                }
                else classesSwitch(cfg,a);
            }
            catch(Exception e)
            {
                System.out.println("FACTORY CONSTRUCTION EXCEPTION: "+e);
                e.printStackTrace();
            }
        else if(Factory!=null) Factory=null;
    }
    
    private void classesSwitch(Configuration cfg, int area)
    {
        switch(area)
        {
            case 6: if(usuario.getJur()>0)setJURClasess(cfg);if(usuario.getRex()>0)setREXClasses(cfg);break;
            case 5: setREXClasses(cfg);break;
            case 4: //
                    break;
            case 2: setJURClasess(cfg);break;
        }
        cfg.setProperty("hibernate.connection.username",usuario.getPerfil())
        .setProperty("hibernate.connection.password",usuario.getPass());
        usuario.setPass("");
        usuario.setPerfil("");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
        System.out.println("SERVICE REGISTRY CONSTRUCTION");
        Factory = cfg.buildSessionFactory(serviceRegistry);
        System.out.println("FACTORY CONSTRUCTION");
    }
    
    private void setJURClasess(Configuration cfg)
    {
        cfg.addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Casos.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Clientes.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_EtapaDetalle.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Etapas.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Expedientes.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Fechas.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Notificaciones.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_NotificacionAlcance.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.JUR.JUR_Tareas.class);
    }
    
    private void setREXClasses(Configuration cfg)
    {
        cfg.addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_Materiales.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_Oferta.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_Proveedores.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_ODCs.class)
        .addAnnotatedClass(com.solemsa.hibernate.entities.REX.REX_ODCdetalle.class);
    }
    
    public boolean checkSession(){
        System.out.println("checkSession() METHOD REACHED");
        boolean flag=false;
        if(usuario!=null)
        {
            System.out.println("USUARIO IS NOT NULL");
            switch(FacesContext.getCurrentInstance().getViewRoot().getViewId().split("views/")[1].substring(0,3))
            {
                case "VEN": flag=usuario.getArea()!=6;break;
                case "AGR": flag=usuario.getArea()!=5&&usuario.getRex()<1;break;
                case "SEG": flag=usuario.getArea()!=4;break;
                case "CNT": flag=usuario.getArea()!=3;break;
                case "JUR": flag=usuario.getArea()!=2&&usuario.getJur()<1;break;
                case "AOU": flag=usuario.getArea()!=1;break;
                default: flag=!usuario.isDirectivo();break;
            }
            System.out.println("FLAG VARIABLE IS: "+flag+" with Area: "+usuario.getArea());
        }
        else flag=true;
        if(flag)
        {
            try{
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                //FacesContext.getCurrentInstance().getExternalContext().redirect("../../LogIn.xhtml");
            }catch(Exception e1){
                System.out.println("REDIRECTING TO LOGIN EXCEPTION 1");
            }
            return false;
        }
        else
        {
            System.out.println("SESSION STILL ON");
            return true;
        }
    }
    
    public boolean checkSession(String p){
        System.out.println("checkSession() METHOD REACHED with: "+p);
        boolean flag=false;
        if(usuario!=null)
        {
            System.out.println("USUARIO IS NOT NULL");
            switch(p)
            {
                case "VEN": flag=usuario.getArea()!=6;break;
                case "AGR": flag=usuario.getArea()!=5;break;
                case "SEG": flag=usuario.getArea()!=4;break;
                case "CNT": flag=usuario.getArea()!=3;break;
                case "JUR": flag=usuario.getArea()!=2;break;
                case "AOU": flag=usuario.getArea()!=1;break;
                default: flag=!usuario.isDirectivo();break;
            }
            System.out.println("FLAG VARIABLE IS: "+flag+" with Area: "+usuario.getArea());
        }
        else flag=true;
        if(flag)
        {
            try{
                System.out.println("ABOUT TO REDIRECT TO LOGIN");
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../LogIn.xhtml");
            }catch(Exception e1){
                System.out.println("REDIRECTING TO LOGIN EXCEPTION 1");
            }
            return false;
        }
        else
        {
            System.out.println("SESSION STILL ON");
            return true;
        }
    }
    
    public void logout(){
        System.out.println("logout() METHOD REACHED");
        try{
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }catch(Exception e)
        {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }
        System.out.println("LOG OUT PERFORMED");
    }
}