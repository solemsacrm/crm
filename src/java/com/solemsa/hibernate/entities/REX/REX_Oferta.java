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
@Table(name="rex_oferta")
public class REX_Oferta implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_Ofertas")
    private long __id_REX_Ofertas;

    @ManyToOne(targetEntity=REX_Materiales.class)
    @JoinColumn(name = "_id_REX_Material")
    private REX_Materiales REX_Material;
    @ManyToOne(targetEntity=REX_Proveedores.class)
    @JoinColumn(name = "_id_REX_Proveedor")
    private REX_Proveedores REX_Proveedor;

    @Column(name="Precio")
    private float Precio;
    @Column(name="Moneda")
    private String Moneda;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public REX_Oferta() {

    }

    public long getId_REX_Ofertas() {
        return __id_REX_Ofertas;
    }

    public void setId_REX_Ofertas(long __id_REX_Ofertas) {
        this.__id_REX_Ofertas = __id_REX_Ofertas;
    }

    public REX_Materiales getREX_Material() {
        return REX_Material;
    }

    public void setREX_Material(REX_Materiales REX_Material) {
        this.REX_Material = REX_Material;
    }

    public REX_Proveedores getREX_Proveedor() {
        return REX_Proveedor;
    }

    public void setREX_Proveedor(REX_Proveedores REX_Proveedor) {
        this.REX_Proveedor = REX_Proveedor;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
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
        return "REX_Oferta{" + "__id_REX_Ofertas=" + __id_REX_Ofertas + ", Precio=" + Precio + ", Moneda=" + Moneda + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}