package com.solemsa.hibernate.entities.JUR;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jur_notificaciones")
public class JUR_Notificaciones implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Notificacion")
    private long __id_JUR_Notificacion;

    @ManyToOne(targetEntity=JUR_Casos.class)
    @JoinColumn(name = "_id_JUR_Caso")
    private JUR_Casos JUR_Caso;
    @ManyToOne(targetEntity=JUR_Etapas.class)
    @JoinColumn(name = "_id_JUR_Etapa")
    private JUR_Etapas JUR_Etapa;
    @ManyToOne(targetEntity=JUR_EtapaDetalle.class)
    @JoinColumn(name = "_id_JUR_EtapaDetalle")
    private JUR_EtapaDetalle JUR_EtapaDetalle;
    @OneToOne(targetEntity=JUR_Tareas.class)
    @JoinColumn(name = "_id_JUR_Tarea")
    private JUR_Tareas JUR_Tarea;
    
    @OneToMany(mappedBy = "JUR_Notificacion")
    private List<JUR_NotificacionAlcance> JUR_Alcance;
    
    @Column(name="Asunto")
    private String Asunto;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Visto")
    private boolean Visto;
    @Column(name="Prioridad")
    private boolean Prioridad;
    @Column(name="Clipped")
    private boolean Clipped;
    @Column(name="Oculto")
    private boolean Oculto;
    @Column(name="Pruebas")
    private boolean Pruebas;
    @Column(name="ParaGerente")
    private boolean ParaGerente;
    @Column(name="FechaInicio")
    private Date FechaInicio;
    @Column(name="FechaFin")
    private Date FechaFin;


    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    @Transient
    private transient String FechaInicioString;
    @Transient
    private transient String FechaFinString,clippedString;
    @Transient
    private transient Integer JUR_AlcanceLength;

    public JUR_Notificaciones() {

    }

    public long getId_JUR_Notificacion() {
        return __id_JUR_Notificacion;
    }

    public void setId_JUR_Notificacion(long __id_JUR_Notificacion) {
        this.__id_JUR_Notificacion = __id_JUR_Notificacion;
    }

    public JUR_Casos getJUR_Caso() {
        return JUR_Caso;
    }

    public void setJUR_Caso(JUR_Casos JUR_Caso) {
        this.JUR_Caso = JUR_Caso;
    }

    public JUR_Etapas getJUR_Etapa() {
        return JUR_Etapa;
    }

    public void setJUR_Etapa(JUR_Etapas JUR_Etapa) {
        this.JUR_Etapa = JUR_Etapa;
    }

    public JUR_EtapaDetalle getJUR_EtapaDetalle() {
        return JUR_EtapaDetalle;
    }

    public void setJUR_EtapaDetalle(JUR_EtapaDetalle JUR_EtapaDetalle) {
        this.JUR_EtapaDetalle = JUR_EtapaDetalle;
    }

    public JUR_Tareas getJUR_Tarea() {
        return JUR_Tarea;
    }

    public void setJUR_Tarea(JUR_Tareas JUR_Tarea) {
        this.JUR_Tarea = JUR_Tarea;
    }

    public List<JUR_NotificacionAlcance> getJUR_Alcance() {
        return JUR_Alcance;
    }

    public void setJUR_Alcance(List<JUR_NotificacionAlcance> JUR_Etapas) {
        this.JUR_Alcance = JUR_Etapas;
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

    public String getAsunto() {
        return Asunto;
    }

    public void setAsunto(String Asunto) {
        this.Asunto = Asunto;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public boolean isPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(boolean Prioridad) {
        this.Prioridad = Prioridad;
    }

    public boolean isVisto() {
        return Visto;
    }

    public void setVisto(boolean Visto) {
        this.Visto = Visto;
    }

    public boolean isClipped() {
        return Clipped;
    }

    public void setClipped(boolean Clipped) {
        this.Clipped = Clipped;
    }

    public boolean isOculto() {
        return Oculto;
    }

    public void setOculto(boolean Oculto) {
        this.Oculto = Oculto;
    }

    public boolean isPruebas() {
        return Pruebas;
    }

    public void setPruebas(boolean Pruebas) {
        this.Pruebas = Pruebas;
    }

    public boolean isParaGerente() {
        return ParaGerente;
    }

    public void setParaGerente(boolean ParaGerente) {
        this.ParaGerente = ParaGerente;
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
    public String getFechaFinString() {
        return FechaFinString;
    }
    @Transient
    public void setFechaFinString(String FechaFinString) {
        this.FechaFinString = FechaFinString;
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
    public Integer getJUR_AlcanceLength() {
        return JUR_AlcanceLength;
    }
    @Transient
    public void setJUR_AlcanceLength(Integer JUR_AlcanceLength) {
        this.JUR_AlcanceLength = JUR_AlcanceLength;
    }
    @Transient
    public String getClippedString() {
        return clippedString;
    }
    @Transient
    public void setClippedString(String clippedString) {
        this.clippedString = clippedString;
    }

    @Override
    public String toString() {
        return "JUR_Notificaciones{" + "__id_JUR_Notificacion=" + __id_JUR_Notificacion + ", Asunto=" + Asunto + ", Descripcion=" + Descripcion + ", Visto=" + Visto + ", Clipped=" + Clipped + ", Prioridad=" + Prioridad + ", FechaInicio=" + FechaInicio + ", FechaFin=" + FechaFin + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}