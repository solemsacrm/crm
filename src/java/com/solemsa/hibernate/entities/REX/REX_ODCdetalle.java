package com.solemsa.hibernate.entities.REX;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rex_ordendecompradetalle")
public class REX_ODCdetalle implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_OrdenDeCompraDetalle")
    private long __id_REX_OrdenDeCompraDetalle;

    @ManyToOne(targetEntity=REX_ODCs.class)
    @JoinColumn(name = "_id_REX_OrdenDeCompra")
    private REX_ODCs REX_OrdenDeCompra;
    @ManyToOne(targetEntity=REX_Materiales.class)
    @JoinColumn(name = "_id_REX_Material")
    private REX_Materiales REX_Material;

    @Column(name="Material")
    private String Material;
    @Column(name="Cantidad")
    private float Cantidad;
    @Column(name="Precio")
    private float Precio;
    @Column(name="Subtotal")
    private float Subtotal;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public REX_ODCdetalle() {

    }

    public long getId_REX_OrdenDeCompraDetalle() {
        return __id_REX_OrdenDeCompraDetalle;
    }

    public void setId_REX_OrdenDeCompraDetalle(long __id_REX_OrdenDeCompraDetalle) {
        this.__id_REX_OrdenDeCompraDetalle = __id_REX_OrdenDeCompraDetalle;
    }

    public REX_ODCs getREX_OrdenDeCompra() {
        return REX_OrdenDeCompra;
    }

    public void setREX_OrdenDeCompra(REX_ODCs REX_OrdenDeCompra) {
        this.REX_OrdenDeCompra = REX_OrdenDeCompra;
    }

    public REX_Materiales getREX_Material() {
        return REX_Material;
    }

    public void setREX_Material(REX_Materiales REX_Material) {
        this.REX_Material = REX_Material;
    }
    
    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String Material) {
        this.Material = Material;
    }

    public float getCantidad() {
        return Cantidad;
    }

    public void setCantidad(float Cantidad) {
        this.Cantidad = Cantidad;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    public float getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(float Subtotal) {
        this.Subtotal = Subtotal;
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

    @Override
    public String toString() {
        return "REX_OrdenDeCompraDetalles{" + "__id_REX_OrdenDeCompraDetalle=" + __id_REX_OrdenDeCompraDetalle + ", Material=" + Material + ", Cantidad=" + Cantidad + ", Precio=" + Precio + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}