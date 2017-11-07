package com.solemsa.hibernate.entities.JUR;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jur_fechas")
public class JUR_Fechas implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Fecha")
    private long __id_JUR_Fecha;

    @Column(name="FechaInicio")
    private Date FechaInicio;
    @Column(name="FechaFin")
    private Date FechaFin;
    @Column(name="Descripcion")
    private String Descripcion;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    @Transient
    private String FechaInicioString,FechaFinString;
    @Transient
    private boolean Tipo;

    public JUR_Fechas() {

    }

    public long getId_JUR_Fecha() {
        return __id_JUR_Fecha;
    }

    public void setId_JUR_Fecha(long __id_JUR_Fecha) {
        this.__id_JUR_Fecha = __id_JUR_Fecha;
    }
    
    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Date getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
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
    public String getFechaInicioString() {
        return FechaInicioString;
    }
    @Transient
    public void setFechaInicioString(String FechaInicioString) {
        this.FechaInicioString = FechaInicioString;
    }
    @Transient
    public String getFechaFinString() {
        return FechaFinString;
    }
    @Transient
    public void setFechaFinString(String FechaFinString) {
        this.FechaFinString = FechaFinString;
    }
    @Transient
    public boolean isTipo() {
        return Tipo;
    }
    @Transient
    public void setTipo(boolean Tipo) {
        this.Tipo = Tipo;
    }
    
    @Override
    public String toString() {
        return "JUR_Fechas{" + "__id_JUR_Fecha=" + __id_JUR_Fecha + ", FechaInicio=" + FechaInicio + ", FechaFin=" + FechaFin + ", Descripcion=" + Descripcion + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + ", FechaInicioString=" + FechaInicioString + ", FechaFinString=" + FechaFinString + '}';
    }
    
}