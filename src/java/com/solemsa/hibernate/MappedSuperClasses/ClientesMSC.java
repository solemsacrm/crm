package com.solemsa.hibernate.MappedSuperClasses;

import com.solemsa.hibernate.entities.*;
import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class ClientesMSC implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_Clientes")
    private long __id_Clientes;

    @OneToMany(mappedBy = "Cliente",fetch=FetchType.LAZY)
    private List<REX_Obras> REX_Obras;
    @OneToMany(mappedBy = "Cliente",fetch=FetchType.LAZY)
    private List<Telefonos> Telefonos;
    @OneToMany(mappedBy = "Cliente",fetch=FetchType.LAZY)
    private List<Correos> Correos;
    @OneToMany(mappedBy = "Cliente",fetch=FetchType.LAZY)
    private List<SEG_Cotizaciones> SEG_Cotizaciones;

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="RazonSocial")
    private String RazonSocial;
    @Column(name="RFC")
    private String RFC;
    @Column(name="TipoPersona")
    private String TipoPersona;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Ciudad")
    private String Ciudad;
    @Column(name="Estado")
    private String Estado;
    @Column(name="Delegacion")
    private String Delegacion;
    @Column(name="Pais")
    private String Pais;
    @Column(name="Direccion")
    private String Direccion;
    @Column(name="ClienteREX")
    private int ClienteREX;
    @Column(name="ClienteJUR")
    private int ClienteJUR;
    @Column(name="ClienteOUT")
    private int ClienteOUT;
    @Column(name="ClienteSEG")
    private int ClienteSEG;
    @Column(name="ClienteCON")
    private int ClienteCON;
    @Column(name="Estatus")
    private int Estatus;
    @Column(name="RazonMigracion")
    private String RazonMigracion;
    @Column(name="FechaCliente")
    private Date FechaCliente;
    @Column(name="FechaMigracion")
    private Date FechaMigracion;
    @Column(name="Logo")
    private String Logo;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    private transient int SEG_CotizacionesLength;

    public ClientesMSC() {

    }

    public long getId_Clientes() {
        return __id_Clientes;
    }

    public void setId_Clientes(long __id_Clientes) {
        this.__id_Clientes = __id_Clientes;
    }

    public List<REX_Obras> getREX_Obras() {
        return REX_Obras;
    }

    public void setREX_Obras(List<REX_Obras> REX_Obras) {
        this.REX_Obras = REX_Obras;
    }

    public List<Telefonos> getTelefonos() {
        return Telefonos;
    }

    public void setTelefonos(List<Telefonos> Telefonos) {
        this.Telefonos = Telefonos;
    }

    public List<Correos> getCorreos() {
        return Correos;
    }

    public void setCorreos(List<Correos> Correos) {
        this.Correos = Correos;
    }

    public List<SEG_Cotizaciones> getSEG_Cotizaciones() {
        return SEG_Cotizaciones;
    }

    public void setSEG_Cotizaciones(List<SEG_Cotizaciones> SEG_Cotizaciones) {
        this.SEG_Cotizaciones = SEG_Cotizaciones;
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

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public int getClienteREX() {
        return ClienteREX;
    }

    public int getClienteJUR() {
        return ClienteJUR;
    }

    public int getClienteOUT() {
        return ClienteOUT;
    }

    public int getClienteSEG() {
        return ClienteSEG;
    }

    public int getClienteCON() {
        return ClienteCON;
    }

    public void setClienteREX(int ClienteREX) {
        this.ClienteREX = ClienteREX;
    }

    public int isClienteJUR() {
        return ClienteJUR;
    }

    public void setClienteJUR(int ClienteJUR) {
        this.ClienteJUR = ClienteJUR;
    }

    public int isClienteSEG() {
        return ClienteSEG;
    }

    public void setClienteSEG(int ClienteSEG) {
        this.ClienteSEG = ClienteSEG;
    }

    public int isClienteOUT() {
        return ClienteOUT;
    }

    public void setClienteOUT(int ClienteOUT) {
        this.ClienteOUT = ClienteOUT;
    }

    public int isClienteCON() {
        return ClienteCON;
    }

    public void setClienteCON(int ClienteCON) {
        this.ClienteCON = ClienteCON;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }

    public String getRazonMigracion() {
        return RazonMigracion;
    }

    public void setRazonMigracion(String RazonMigracion) {
        this.RazonMigracion = RazonMigracion;
    }

    public Date getFechaCliente() {
        return FechaCliente;
    }

    public void setFechaCliente(Date FechaCliente) {
        this.FechaCliente = FechaCliente;
    }

    public Date getFechaMigracion() {
        return FechaMigracion;
    }

    public void setFechaMigracion(Date FechaMigracion) {
        this.FechaMigracion = FechaMigracion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
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

    public int getSEG_CotizacionesLength() {
        return SEG_CotizacionesLength;
    }

    public void setSEG_CotizacionesLength(int SEG_CotizacionesLength) {
        this.SEG_CotizacionesLength = SEG_CotizacionesLength;
    }

    @Override
    public String toString() {
        return "Clientes{" + "__id_Clientes=" + __id_Clientes + ", Nombre=" + Nombre + ", RazonSocial=" + RazonSocial + ", RFC=" + RFC + ", TipoPersona=" + TipoPersona + ", Descripcion=" + Descripcion + ", Ciudad=" + Ciudad + ", Estado=" + Estado + ", Delegacion=" + Delegacion + ", Pais=" + Pais +  ", Direccion=" + Direccion + ", ClienteREX=" + ClienteREX + ", ClienteJUR=" + ClienteJUR + ", ClienteOUT=" + ClienteOUT + ", ClienteSEG=" + ClienteSEG + ", ClienteCON=" + ClienteCON + ", Estatus=" + Estatus + ", RazonMigracion=" + RazonMigracion + ", FechaCliente=" + FechaCliente + ", FechaMigracion=" + FechaMigracion + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}