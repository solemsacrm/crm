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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jur_etapadetalle")
public class JUR_EtapaDetalle implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_EtapaDetalle")
    private long __id_JUR_EtapaDetalle;

    @ManyToOne(targetEntity=JUR_Etapas.class)
    @JoinColumn(name = "_id_JUR_Etapa")
    private JUR_Etapas JUR_Etapa;
    
    @OneToMany(mappedBy="JUR_EtapaDetalle",fetch=FetchType.LAZY)
    private List<JUR_Expedientes> JUR_Expedientes;
    
    @OneToOne(mappedBy="JUR_EtapaDetalle")
    private JUR_Notificaciones JUR_Notificacion;

    @Column(name="AsuntosTratados")
    private String AsuntosTratados;
    @Column(name="Resultados")
    private String Resultados;
    @Column(name="Conclusiones")
    private String Conclusiones;
    @Column(name="Notas")
    private String Notas;
    @Column(name="UnidadTiempo")
    private String UnidadTiempo;
    @Column (name="Duracion")
    private float Duracion;
    @Column(name="Fecha")
    private Date Fecha;
    

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    @Transient
    private transient String FechaString;
    @Transient
    private transient int defaultRowNumber;
    @Transient
    private transient int JUR_ExpedientesLength;
    
    public JUR_EtapaDetalle() {

    }

    public long getId_JUR_EtapaDetalle() {
        return __id_JUR_EtapaDetalle;
    }

    public void setId_SEG_Seguimiento(long __id_JUR_EtapaDetalle) {
        this.__id_JUR_EtapaDetalle = __id_JUR_EtapaDetalle;
    }
    
    public JUR_Etapas getJUR_Etapa() {
        return JUR_Etapa;
    }

    public void setJUR_Etapa(JUR_Etapas JUR_Etapa) {
        this.JUR_Etapa = JUR_Etapa;
    }

    public List<JUR_Expedientes> getJUR_Expedientes() {
        return JUR_Expedientes;
    }

    public JUR_Notificaciones getJUR_Notificacion() {
        return JUR_Notificacion;
    }

    public void setJUR_Notificacion(JUR_Notificaciones JUR_Notificacion) {
        this.JUR_Notificacion = JUR_Notificacion;
    }

    public void setJUR_Expedientes(List<JUR_Expedientes> JUR_Expedientes) {
        this.JUR_Expedientes = JUR_Expedientes;
    }

    public String getAsuntosTratados() {
        return AsuntosTratados;
    }

    public void setAsuntosTratados(String AsuntosTratados) {
        this.AsuntosTratados = AsuntosTratados;
    }

    public String getResultados() {
        return Resultados;
    }

    public void setResultados(String Resultados) {
        this.Resultados = Resultados;
    }

    public String getConclusiones() {
        return Conclusiones;
    }

    public void setConclusiones(String Conclusiones) {
        this.Conclusiones = Conclusiones;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
    }

    public String getUnidadTiempo() {
        return UnidadTiempo;
    }

    public void setUnidadTiempo(String UnidadTiempo) {
        this.UnidadTiempo = UnidadTiempo;
    }

    public float getDuracion() {
        return Duracion;
    }

    public void setDuracion(float Duracion) {
        this.Duracion = Duracion;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
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
    @Transient
    public int getDefaultRowNumber() {
        return defaultRowNumber;
    }
    @Transient
    public void setDefaultRowNumber(int defaultRowNumber) {
        this.defaultRowNumber = defaultRowNumber;
    }
    @Transient
    public int getJUR_ExpedientesLength() {
        return JUR_ExpedientesLength;
    }
    @Transient
    public void setJUR_ExpedientesLength(int JUR_ExpedientesLength) {
        this.JUR_ExpedientesLength = JUR_ExpedientesLength;
    }

    @Override
    public String toString() {
        return "JUR_EtapaDetalle{" + "__id_JUR_EtapaDetalle=" + __id_JUR_EtapaDetalle + ", AsuntosTratados=" + AsuntosTratados + ", Resultados=" + Resultados + ", Conclusiones=" + Conclusiones + ", Notas=" + Notas + ", UnidadTiempo=" + UnidadTiempo + ", Duracion=" + Duracion + ", Fecha=" + Fecha + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}