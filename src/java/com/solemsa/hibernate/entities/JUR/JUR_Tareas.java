package com.solemsa.hibernate.entities.JUR;

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
@Table(name="jur_tareas")
public class JUR_Tareas implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Tarea")
    private long __id_JUR_Tarea;

    @ManyToOne(targetEntity=JUR_Casos.class)
    @JoinColumn(name = "_id_JUR_Caso")
    private JUR_Casos JUR_Caso;
    @ManyToOne(targetEntity=JUR_RecursosHumanos.class)
    @JoinColumn(name = "_id_JUR_RecursoHumano")
    private JUR_RecursosHumanos JUR_RecursoHumano;
    @ManyToOne(targetEntity=JUR_Etapas.class)
    @JoinColumn(name = "_id_JUR_Etapa")
    private JUR_Etapas JUR_Etapa;
    
    @OneToMany(mappedBy = "JUR_Tarea",fetch=FetchType.LAZY)
    private List<JUR_Notificaciones> JUR_Notificaciones;
    
    @Column(name="Asunto")
    private String Asunto;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Notas")
    private String Notas;
    @Column (name="Horas")
    private float Horas;
    @Column(name="FechaAsignacion")
    private Date FechaAsignacion;
    @Column(name="FechaEntrega")
    private Date FechaEntrega;
    @Column(name="Finalizado")
    private boolean Finalizado;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    @Transient
    private transient String FechaAsignacionString,FechaEntregaString;
    
    public JUR_Tareas() {

    }

    public long getId_JUR_Tarea() {
        return __id_JUR_Tarea;
    }

    public void setId_JUR_Tarea(long __id_JUR_Tarea) {
        this.__id_JUR_Tarea = __id_JUR_Tarea;
    }
    
    public JUR_RecursosHumanos getJUR_RecursoHumano() {
        return JUR_RecursoHumano;
    }

    public void setJUR_RecursoHumano(JUR_RecursosHumanos JUR_RecursoHumano) {
        this.JUR_RecursoHumano = JUR_RecursoHumano;
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

    public List<JUR_Notificaciones> getJUR_Notificaciones() {
        return JUR_Notificaciones;
    }

    public void setJUR_Notificaciones(List<JUR_Notificaciones> JUR_Notificaciones) {
        this.JUR_Notificaciones = JUR_Notificaciones;
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

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
    }

    public boolean isFinalizado() {
        return Finalizado;
    }

    public void setFinalizado(boolean Finalizado) {
        this.Finalizado = Finalizado;
    }

    public float getHoras() {
        return Horas;
    }

    public void setHoras(float Horas) {
        this.Horas = Horas;
    }

    public Date getFechaAsignacion() {
        return FechaAsignacion;
    }

    public void setFechaAsignacion(Date FechaAsignacion) {
        this.FechaAsignacion = FechaAsignacion;
    }

    public Date getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(Date FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
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
    public String getFechaAsignacionString() {
        return FechaAsignacionString;
    }
    @Transient
    public void setFechaAsignacionString(String FechaAsignacionString) {
        this.FechaAsignacionString = FechaAsignacionString;
    }
    @Transient
    public String getFechaEntregaString() {
        return FechaEntregaString;
    }
    @Transient
    public void setFechaEntregaString(String FechaEntregaString) {
        this.FechaEntregaString = FechaEntregaString;
    }

    @Override
    public String toString() {
        return "JUR_Tareas{" + "__id_JUR_Tarea=" + __id_JUR_Tarea + "JUR_Etapa=" + JUR_Etapa.getId_JUR_Etapa() + "JUR_RecursoHumano=" + JUR_RecursoHumano.getId_JUR_RecursoHumano() + ", Asunto=" + Asunto + ", Descripcion=" + Descripcion + ", Horas=" + Horas + ", FechaAsignacion=" + FechaAsignacion + ", FechaEntrega=" + FechaEntrega + ", Finalizado=" + Finalizado + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}