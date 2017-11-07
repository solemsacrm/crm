package com.solemsa.hibernate.entities.SEG;

import com.solemsa.hibernate.entities.Clientes;
import java.io.Serializable;
import java.sql.Date;
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
@Table(name="seg_cotizaciones")
public class SEG_Cotizaciones implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_SEG_Cotizacion")
    private long __id_SEG_Cotizacion;

    @ManyToOne(targetEntity=Clientes.class)
    @JoinColumn(name = "_id_Cliente")
    private Clientes Cliente;

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="NoElementos")
    private int NoElementos;
    @Column(name="Costo")
    private float Costo;
    @Column(name="Precio")
    private float Precio;
    @Column(name="Estatus")
    private int Estatus;
    @Column(name="Renta")
    private int Renta;
    @Column(name="Fecha")
    private Date Fecha;
    @Column(name="Tipo")
    private String Tipo;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    private transient String FechaString;

    public SEG_Cotizaciones() {

    }

    public long getId_SEG_Cotizacion() {
        return __id_SEG_Cotizacion;
    }

    public void setId_SEG_Seguimiento(long __id_SEG_Cotizacion) {
        this.__id_SEG_Cotizacion = __id_SEG_Cotizacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public Clientes getCliente() {
        return Cliente;
    }

    public void setCliente(Clientes Cliente) {
        this.Cliente = Cliente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getNoElementos() {
        return NoElementos;
    }

    public void setNoElementos(int NoElementos) {
        this.NoElementos = NoElementos;
    }

    public float getCosto() {
        return Costo;
    }

    public void setCosto(float Costo) {
        this.Costo = Costo;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }

    public int getRenta() {
        return Renta;
    }

    public void setRenta(int Renta) {
        this.Renta = Renta;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
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
    
    public String getFechaString() {
        return FechaString;
    }
    
    public void setFechaString(String FechaString) {
        this.FechaString = FechaString;
    }

    @Override
    public String toString() {
        return "SEG_Cotizaciones{" + "__id_SEG_Cotizacion=" + __id_SEG_Cotizacion + ", Nombre=" + Nombre + ", NoElementos=" + NoElementos + ", Costo=" + Costo + ", Precio=" + Precio + ", Estatus=" + Estatus + ", Renta=" + Renta + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + ", FechaString=" + FechaString + '}';
    }

}