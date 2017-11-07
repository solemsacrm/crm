package com.solemsa.hibernate.entities.JUR;

import java.io.Serializable;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jur_etapas")
public class JUR_Etapas implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Etapa")
    private long __id_JUR_Etapa;

    @ManyToOne(targetEntity=JUR_Casos.class)
    @JoinColumn(name = "_id_JUR_Caso")
    private JUR_Casos JUR_Caso;
    
    @OneToMany(mappedBy = "JUR_Etapa",fetch=FetchType.LAZY)
    @OrderBy("Fecha")
    private List<JUR_EtapaDetalle> JUR_EtapaDetalle;
    @OneToMany(mappedBy = "JUR_Etapa",fetch=FetchType.LAZY)
    private List<JUR_Tareas> JUR_Tareas;
    
    @Column(name="Nombre")
    private String Nombre;
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
    private transient String EtapaActualNombre;
    @Transient
    private transient int JUR_EtapaDetalleLength;

    public JUR_Etapas() {

    }

    public long getId_JUR_Etapa() {
        return __id_JUR_Etapa;
    }

    public void setId_JUR_Etapa(long __id_JUR_Etapa) {
        this.__id_JUR_Etapa = __id_JUR_Etapa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public JUR_Casos getJUR_Caso() {
        return JUR_Caso;
    }

    public void setJUR_Caso(JUR_Casos JUR_Caso) {
        this.JUR_Caso = JUR_Caso;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public List<JUR_EtapaDetalle> getJUR_EtapaDetalle() {
        return JUR_EtapaDetalle;
    }

    public void setJUR_EtapaDetalle(List<JUR_EtapaDetalle> JUR_EtapaDetalle) {
        this.JUR_EtapaDetalle = JUR_EtapaDetalle;
    }

    public List<JUR_Tareas> getJUR_Tareas() {
        return JUR_Tareas;
    }

    public void setJUR_Tareas(List<JUR_Tareas> JUR_Tareas) {
        this.JUR_Tareas = JUR_Tareas;
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
    public String getEtapaActualNombre() {
        return EtapaActualNombre;
    }
    @Transient
    public void setEtapaActualNombre(String EtapaActualNombre) {
        this.EtapaActualNombre = EtapaActualNombre;
    }
    @Transient
    public int getJUR_EtapaDetalleLength() {
        return JUR_EtapaDetalleLength;
    }
    @Transient
    public void setJUR_EtapaDetalleLength(int JUR_EtapaDetalleLength) {
        this.JUR_EtapaDetalleLength = JUR_EtapaDetalleLength;
    }

    @Override
    public String toString() {
        return "JUR_Etapas{" + "__id_JUR_Etapa=" + __id_JUR_Etapa + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + ", EtapaActualNombre=" + EtapaActualNombre + '}';
    }

}