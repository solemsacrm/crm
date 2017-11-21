package com.solemsa.hibernate.entities.AOU;

import com.solemsa.hibernate.entities.*;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
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
@Table(name="out_proyectos")
public class OUT_Proyectos implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_OUT_Proyecto")
    private long __id_OUT_Proyecto;
    
    @ManyToOne(targetEntity=OUT_Clientes.class)
    @JoinColumn(name = "_id_Cliente")
    private OUT_Clientes Cliente;

    @Column(name="Nombre")
    private String Nombre;

    @Column(name="Descripcion")
    private String Descripcion;

    @Column(name="Estatus")
    private String Estatus;
    
    @Column (name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    
    @Column (name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    
    @Column (name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    
    @Column (name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    public Timestamp getZz_FechaModificacion() {
        return zz_FechaModificacion;
    }
    
    public void setZz_FechaModificacion(Timestamp zz_FechaModificacion) {
        this.zz_FechaModificacion = zz_FechaModificacion;
    }

    public Timestamp getZz_FechaCreacion() {
        return zz_FechaCreacion;
    }

    public void setZz_FechaCreacion(Timestamp zz_FechaCreacion) {
        this.zz_FechaCreacion = zz_FechaCreacion;
    }

    public OUT_Proyectos() {

    }

    public long getId_OUT_Proyecto() {
        return __id_OUT_Proyecto;
    }

    public void setId_OUT_Proyecto(long __id_OUT_Proyecto) {
        this.__id_OUT_Proyecto = __id_OUT_Proyecto;
    }

    public OUT_Clientes getCliente() {
        return Cliente;
    }

    public void setCliente(OUT_Clientes Cliente) {
        this.Cliente = Cliente;
    }
    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getEstatus() {
            return Estatus;
    }

    public void setEstatus(String Estatus) {
            this.Estatus = Estatus;
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
        return "OUT_Proyectos{" + "__id_OUT_Proyecto=" + __id_OUT_Proyecto + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", Estatus=" + Estatus + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}