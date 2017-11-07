package com.solemsa.hibernate.entities.REX;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="rex_ordendecompra")
public class REX_ODCs implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_OrdenDeCompra")
    private long __id_REX_OrdenDeCompra;
    
    @ManyToOne(targetEntity=REX_Obras.class)
    @JoinColumn(name = "_id_REX_Obra")
    private REX_Obras REX_Obra;
    @ManyToOne(targetEntity=REX_Proveedores.class)
    @JoinColumn(name = "_id_REX_Proveedor")
    private REX_Proveedores REX_Proveedor;
    
    @OneToMany(mappedBy="REX_OrdenDeCompra",fetch=FetchType.LAZY)
    private List<REX_ODCdetalle> REX_ODCdetalle;

    @Column(name="Folio")
    private String Folio;
    @Column(name="Tipo")
    private String Tipo;
    @Column(name="Notas")
    private String Notas;
    @Column(name="Moneda")
    private String Moneda;
    @Column(name="Estatus")
    private int Estatus;
    @Column(name="Subtotal")
    private float Subtotal;
    @Column(name="Total")
    private float Total;
    @Column(name="FechaExpedicion")
    private Date FechaExpedicion;
    @Column(name="FechaEntrega")
    private Date FechaEntrega;
    @Column(name="FechaAutorizacion")
    private Date FechaAutorizacion;
    @Column(name="FechaCancelacion")
    private Date FechaCancelacion;
    @Column(name="UsuarioAutorizacion")
    private String UsuarioAutorizacion;
    @Column(name="UsuarioCancelacion")
    private String UsuarioCancelacion;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    private transient String FechaExpedicionString,FechaEntregaString,FechaAutorizacionString,FechaCancelacionString;

    public REX_ODCs() {

    }

    public long getId_REX_OrdenDeCompra() {
        return __id_REX_OrdenDeCompra;
    }

    public void setId_REX_OrdenDeCompra(long __id_REX_OrdenDeCompra) {
        this.__id_REX_OrdenDeCompra = __id_REX_OrdenDeCompra;
    }

    public REX_Obras getREX_Obra() {
        return REX_Obra;
    }

    public void setREX_Obra(REX_Obras REX_Obra) {
        this.REX_Obra = REX_Obra;
    }

    public REX_Proveedores getREX_Proveedor() {
        return REX_Proveedor;
    }

    public void setREX_Proveedor(REX_Proveedores REX_Proveedor) {
        this.REX_Proveedor = REX_Proveedor;
    }

    public List<REX_ODCdetalle> getREX_ODCdetalle() {
        return REX_ODCdetalle;
    }

    public void setREX_ODCdetalle(List<REX_ODCdetalle> REX_ODCdetalle) {
        this.REX_ODCdetalle = REX_ODCdetalle;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String Folio) {
        this.Folio = Folio;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
    public Date getFechaExpedicion() {
        return FechaExpedicion;
    }

    public void setFechaExpedicion(Date FechaExpedicion) {
        this.FechaExpedicion = FechaExpedicion;
    }
    
    public Date getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Date FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public Date getFechaAutorizacion() {
        return FechaAutorizacion;
    }

    public void setFechaAutorizacion(Date FechaAutorizacion) {
        this.FechaAutorizacion = FechaAutorizacion;
    }

    public Date getFechaCancelacion() {
        return FechaCancelacion;
    }

    public void setFechaCancelacion(Date FechaCancelacion) {
        this.FechaCancelacion = FechaCancelacion;
    }

    public String getUsuarioAutorizacion() {
        return UsuarioAutorizacion;
    }

    public void setUsuarioAutorizacion(String UsuarioAutorizacion) {
        this.UsuarioAutorizacion = UsuarioAutorizacion;
    }

    public String getUsuarioCancelacion() {
        return UsuarioCancelacion;
    }

    public void setUsuarioCancelacion(String UsuarioCancelacion) {
        this.UsuarioCancelacion = UsuarioCancelacion;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }

    public float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(float Subtotal) {
        this.Subtotal = Subtotal;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
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

    @Transient
    public String getFechaExpedicionString() {
        return FechaExpedicionString;
    }
    
    @Transient
    public void setFechaExpedicionString(String FechaExpedicionString) {
        this.FechaExpedicionString = FechaExpedicionString;
    }

    @Transient
    public String getFechaEntregaString() {
        return FechaEntregaString;
    }
    
    @Transient
    public void setFechaEntregaString(String FechaEntregaString) {
        this.FechaEntregaString = FechaEntregaString;
    }
    
    @Transient
    public String getFechaAutorizacionString() {
        return FechaAutorizacionString;
    }
    
    @Transient
    public void setFechaAutorizacionString(String FechaAutorizacionString) {
        this.FechaAutorizacionString = FechaAutorizacionString;
    }

    @Transient
    public String getFechaCancelacionString() {
        return FechaCancelacionString;
    }
    
    @Transient
    public void setFechaCancelacionString(String FechaCancelacionString) {
        this.FechaCancelacionString = FechaCancelacionString;
    }

    @Override
    public String toString() {
        return "REX_ODCs{" + "__id_REX_OrdenDeCompra=" + __id_REX_OrdenDeCompra + ", REX_Obra=" + REX_Obra + ", REX_Proveedor=" + REX_Proveedor + ", REX_ODCdetalle=" + REX_ODCdetalle + ", Folio=" + Folio + ", Tipo=" + Tipo + ", Notas=" + Notas + ", Moneda=" + Moneda + ", Estatus=" + Estatus + ", Subtotal=" + Subtotal + ", Total=" + Total + ", FechaExpedicionString=" + FechaExpedicionString + ", FechaEntregaString=" + FechaEntregaString + ", FechaAutorizacionString=" + FechaAutorizacionString + ", FechaCancelacionString=" + FechaCancelacionString  + ", UsuarioAutorizacion=" + UsuarioAutorizacion + ", UsuarioCancelacion=" + UsuarioCancelacion + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}