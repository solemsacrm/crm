package com.solemsa.jsf.modelBeans;

import com.solemsa.jsf.DAOs.daoClientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@ManagedBean
@ViewScoped
public class mCliente implements Serializable{

    private int __id_Clientes;
    private String Nombre;
    private String RazonSocial;
    private String RFC;
    private String TipoPersona;
    private String Descripcion;
    private String Ciudad;
    private String Estado;
    private String Delegacion;
    private String Pais;
    private int ClienteREX;
    private int ClienteJUR;
    private int ClienteSEG;
    private int ClienteOUT;
    private int ClienteCON;
    private Timestamp zz_FechaCreacion;
    private Timestamp zz_FechaModificacion;
    private String zz_UsuarioCreacion;
    private String zz_UsuarioModificacion;
    private boolean cambios=false;
    private SessionFactory Factory;
    private Session Session;
    private daoClientes dao;
    private int[] servicios;
    
    public mCliente() {
        
    }
    
    public mCliente(SessionFactory Factory) {
        try{
            this.Factory = Factory;
            System.out.println("SUCCESSFUL FACTORY CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY CONSTRUCTION FAILED "+e);
        }
    }
    
    public int getId_Clientes() {
        return __id_Clientes;
    }

    public void setId_Clientes(int __id_Clientes) {
        this.__id_Clientes = __id_Clientes;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getTipoPersona() {
        return TipoPersona;
    }

    public void setTipoPersona(String TipoPersona) {
        this.TipoPersona = TipoPersona;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getDelegacion() {
        return Delegacion;
    }

    public void setDelegacion(String Delegacion) {
        this.Delegacion = Delegacion;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public int getClienteREX() {
        return ClienteREX;
    }

    public void setClienteREX(int ClienteREX) {
        this.ClienteREX = ClienteREX;
    }

    public int getClienteJUR() {
        return ClienteJUR;
    }

    public void setClienteJUR(int ClienteJUR) {
        this.ClienteJUR = ClienteJUR;
    }

    public int getClienteSEG() {
        return ClienteSEG;
    }

    public void setClienteSEG(int ClienteSEG) {
        this.ClienteSEG = ClienteSEG;
    }

    public int getClienteOUT() {
        return ClienteOUT;
    }

    public void setClienteOUT(int ClienteOUT) {
        this.ClienteOUT = ClienteOUT;
    }

    public int getClienteCON() {
        return ClienteCON;
    }

    public void setClienteCON(int ClienteCON) {
        this.ClienteCON = ClienteCON;
    }

    public Timestamp getZz_FechaCreacion() {
        return zz_FechaCreacion;
    }

    public void setZz_FechaCreacion(Timestamp zz_FechaCreacion) {
        this.zz_FechaCreacion = zz_FechaCreacion;
    }

    public Timestamp getZz_FechaModificacion() {
        return zz_FechaModificacion;
    }

    public void setZz_FechaModificacion(Timestamp zz_FechaModificacion) {
        this.zz_FechaModificacion = zz_FechaModificacion;
    }

    public String getZz_UsuarioCreacion() {
        return zz_UsuarioCreacion;
    }

    public void setZz_UsuarioCreacion(String zz_UsuarioCreacion) {
        this.zz_UsuarioCreacion = zz_UsuarioCreacion;
    }

    public String getZz_UsuarioModificacion() {
        return zz_UsuarioModificacion;
    }

    public void setZz_UsuarioModificacion(String zz_UsuarioModificacion) {
        this.zz_UsuarioModificacion = zz_UsuarioModificacion;
    }

    public Session getSession() {
        return Session;
    }

    public void setSession(Session Session) {
        this.Session = Session;
    }

    public int[] getServicios() {
        return servicios;
    }

    public void setServicios(int[] servicios) {
        this.servicios = servicios;
    }

    public boolean isCambios() {
        return cambios;
    }

    public void setCambios(boolean cambios) {
        this.cambios = cambios;
    }
    
    public void loadCliente(int area)
    {
        System.out.println("loadCliente METHOD REACHED");
        //__id_Clientes=id;
        System.out.println("ID RECEIVED: "+__id_Clientes+" ÁREA: "+area);
        try{
                //cliente=dao.getCliente(id);
                servicios=new int[5];
                dao=new daoClientes(Factory,area);
                ClientesMSC temp=dao.getCliente(__id_Clientes);
                System.out.println("cliente LOAD SUCCESSFUL");
                Nombre=temp.getNombre();
                System.out.println("Nombre LOADED WITH "+temp.getNombre());
                RazonSocial=temp.getRazonSocial();
                System.out.println("Razón Social LOADED WITH "+temp.getRazonSocial());
                RFC=temp.getRFC();
                System.out.println("RFC LOADED WITH "+temp.getRFC());
                TipoPersona=temp.getTipoPersona();
                System.out.println("Tipo Persona LOADED WITH "+temp.getTipoPersona());
                Ciudad=temp.getCiudad();
                System.out.println("Ciudad LOADED WITH "+temp.getCiudad());
                Estado=temp.getEstado();
                System.out.println("Estado LOADED WITH "+temp.getEstado());
                Pais=temp.getPais();
                System.out.println("País LOADED WITH "+temp.getPais());
                Delegacion=temp.getDelegacion();
                System.out.println("Delegación LOADED WITH "+temp.getDelegacion());
                Descripcion=temp.getDescripcion();
                System.out.println("Descripción LOADED WITH "+temp.getDescripcion());
                servicios[0]=temp.getClienteOUT();
                System.out.println("ClienteOUT LOADED WITH "+temp.getClienteOUT());
                servicios[1]=temp.getClienteSEG();
                System.out.println("ClienteSEG LOADED WITH "+temp.getClienteSEG());
                servicios[2]=temp.getClienteCON();
                System.out.println("ClienteCON LOADED WITH "+temp.getClienteCON());
                servicios[3]=temp.getClienteJUR();
                System.out.println("ClienteJUR LOADED WITH "+temp.getClienteJUR());
                servicios[4]=temp.getClienteREX();
                System.out.println("ClienteREX LOADED WITH "+temp.getClienteREX());
                zz_FechaCreacion=temp.getZz_FechaCreacion();
                System.out.println("Fecha Creación LOADED WITH "+temp.getZz_FechaCreacion());
                zz_FechaModificacion=temp.getZz_FechaModificacion();
                System.out.println("Fecha Modificación LOADED WITH "+temp.getZz_FechaModificacion());
                zz_UsuarioCreacion=temp.getZz_UsuarioCreacion();
                System.out.println("Usuario Creación LOADED WITH "+temp.getZz_UsuarioCreacion());
                zz_UsuarioModificacion=temp.getZz_UsuarioModificacion();
                System.out.println("Usuario Modificación LOADED WITH "+temp.getZz_UsuarioModificacion());
        }
        catch(Exception e)
        {
            System.out.println("cliente LOAD EXCEPTION: "+e.toString());
        }
        if(__id_Clientes<1)
        {
            System.out.println("NO ID RECEIVED");
            //Carga página para insertar un nuevo cliente en la BD
            int[] temp={area};
            servicios=temp;
            //cliente=new Clientes();
        }
        cambios=true;
    }
    
    public void redirectLoad(int area ,SessionFactory Factory)
    {
        LogInBean country = (LogInBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBean");
        if(country==null)
            System.out.println("country OBJECT IS NULL");
        else
            System.out.println("country OBJECT IS NOT NULL");
        //__id_Clientes=Integer.parseInt(country.getRowId());
        System.out.println("PREUBA ID: "+__id_Clientes);
        this.Factory=Factory;
        loadCliente(area);
        //return "clientesD";
    }
    
}
