package com.solemsa.hibernate.entities.JUR;

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
@Table(name="jur_expedientes")
public class JUR_Expedientes implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Expediente")
    private long __id_JUR_Expediente;
    
    @ManyToOne(targetEntity=JUR_EtapaDetalle.class)
    @JoinColumn(name = "_id_JUR_EtapaDetalle")
    private JUR_EtapaDetalle JUR_EtapaDetalle;

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Ruta")
    private String Ruta;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    private transient String EtapaActualNombre;

    public JUR_Expedientes() {

    }

    public long getId_JUR_Expediente() {
        return __id_JUR_Expediente;
    }

    public void setId_SEG_Seguimiento(long __id_JUR_Caso) {
        this.__id_JUR_Expediente = __id_JUR_Caso;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public JUR_EtapaDetalle getJUR_EtapaDetalle() {
        return JUR_EtapaDetalle;
    }

    public void setJUR_EtapaDetalle(JUR_EtapaDetalle JUR_EtapaDetalle) {
        this.JUR_EtapaDetalle = JUR_EtapaDetalle;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getRuta() {
        return Ruta;
    }

    public void setRuta(String Ruta) {
        this.Ruta = Ruta;
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

    public String getEtapaActualNombre() {
        return EtapaActualNombre;
    }

    public void setEtapaActualNombre(String EtapaActualNombre) {
        this.EtapaActualNombre = EtapaActualNombre;
    }
    
    @Override
    public String toString() {
        return "JUR_Casos{" + "__id_JUR_Caso=" + __id_JUR_Expediente + ", Nombre=" + Nombre + ", Ruta=" + Ruta + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + ", EtapaActualNombre=" + EtapaActualNombre + '}';
    }

}