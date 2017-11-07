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
import javax.persistence.Transient;

@Entity
@Table(name="jur_notificacionalcance")
public class JUR_NotificacionAlcance implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_NotificacionAlcance")
    private long __id_JUR_NotificacionAlcance;

    @ManyToOne(targetEntity=JUR_Notificaciones.class)
    @JoinColumn(name = "_id_JUR_Notificacion")
    private JUR_Notificaciones JUR_Notificacion;
    @ManyToOne(targetEntity=JUR_RecursosHumanos.class)
    @JoinColumn(name = "_id_JUR_RecursoHumano")
    private JUR_RecursosHumanos JUR_RecursoHumano;
    
    @Column(name="Notas")
    private String Notas;
    @Column(name="Visto")
    private boolean Visto;
    @Column(name="Mostrar")
    private boolean Mostrar;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    @Transient
    private boolean Set;
    
    public JUR_NotificacionAlcance() {

    }

    public long getId_JUR_NotificacionAlcance() {
        return __id_JUR_NotificacionAlcance;
    }

    public void setId_SEG_Seguimiento(long __id_JUR_NotificacionAlcance) {
        this.__id_JUR_NotificacionAlcance = __id_JUR_NotificacionAlcance;
    }
    
    public JUR_Notificaciones getJUR_Notificacion() {
        return JUR_Notificacion;
    }

    public void setJUR_Notificacion(JUR_Notificaciones JUR_Notificacion) {
        this.JUR_Notificacion = JUR_Notificacion;
    }

    public JUR_RecursosHumanos getJUR_RecursoHumano() {
        return JUR_RecursoHumano;
    }

    public void setJUR_RecursoHumano(JUR_RecursosHumanos JUR_RecursoHumano) {
        this.JUR_RecursoHumano = JUR_RecursoHumano;
    }

    public boolean isVisto() {
        return Visto;
    }

    public void setVisto(boolean Visto) {
        this.Visto = Visto;
    }

    public boolean isMostrar() {
        return Mostrar;
    }

    public void setMostrar(boolean Mostrar) {
        this.Mostrar = Mostrar;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
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
    public boolean isSet() {
        return Set;
    }
    @Transient
    public void setSet(boolean Set) {
        this.Set = Set;
    }
    
    @Override
    public String toString() {
        return "JUR_NotificacionAlcance{" + "__id_JUR_NotificacionAlcance=" + __id_JUR_NotificacionAlcance + ", Notas=" + Notas + ", Visto=" + Visto + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}