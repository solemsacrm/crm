package com.solemsa.hibernate.entities.REX;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="rex_avances")
public class REX_Avances implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_Avance")
    private long __id_REX_Avance;

    @ManyToOne(targetEntity=REX_Obras.class)
    @JoinColumn(name = "_id_REX_Obra")
    private REX_Obras REX_Obra;

    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Etapa")
    private String Etapa;
    @Column(name="Fecha")
    private Date Fecha;
    @Column(name="Porcentaje")
    private float Porcentaje;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    private transient String FechaString;

    public REX_Avances() {

    }

    public long getId_REX_Avance() {
        return __id_REX_Avance;
    }

    public void setId_REX_Avance(long __id_REX_Avance) {
        this.__id_REX_Avance = __id_REX_Avance;
    }
    
    public REX_Obras get_REX_Obra() {
        return REX_Obra;
    }

    public void set_REX_Obra(REX_Obras REX_Obra) {
        this.REX_Obra = REX_Obra;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getEtapa() {
        return Etapa;
    }

    public void setEtapa(String Etapa) {
        this.Etapa = Etapa;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public float getPorcentaje() {
        return Porcentaje;
    }

    public void setPorcentaje(float Porcentaje) {
        this.Porcentaje = Porcentaje;
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
    public String getFechaString() {
        return FechaString;
    }

    @Transient
    public void setFechaString(String FechaString) {
        this.FechaString = FechaString;
    }

    @Override
    public String toString() {
        return "REX_Avances{" + "__id_REX_Avance=" + __id_REX_Avance + ", Descripcion=" + Descripcion + ", Etapa=" + Etapa + ", Fecha=" + Fecha + ", Porcentaje=" + Porcentaje + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}