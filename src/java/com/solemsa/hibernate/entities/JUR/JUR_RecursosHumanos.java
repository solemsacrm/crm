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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="jur_recursoshumanos")
public class JUR_RecursosHumanos implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_RecursoHumano")
    private long __id_JUR_RecursoHumano;

    @OneToMany(mappedBy = "JUR_RecursoHumano",fetch=FetchType.LAZY)
    @OrderBy("FechaEntrega")
    private List<JUR_Tareas> JUR_Tareas;
    
    @Column(name="Nombre")
    private String Nombre;
    @Column(name="Apellidos")
    private String Apellidos;
    @Column(name="Puesto")
    private String Puesto;
    @Column(name="Observaciones")
    private String Observaciones;
    @Column(name="Interno")
    private boolean Interno;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    @Transient
    private transient int JUR_TareasLength;
    @Transient
    private transient String NombreCompleto;

    public JUR_RecursosHumanos() {

    }

    public long getId_JUR_RecursoHumano() {
        return __id_JUR_RecursoHumano;
    }

    public void setId_JUR_RecursoHumano(long __id_JUR_RecursoHumano) {
        this.__id_JUR_RecursoHumano = __id_JUR_RecursoHumano;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public boolean isInterno() {
        return Interno;
    }

    public void setInterno(boolean Interno) {
        this.Interno = Interno;
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
    public int getJUR_TareasLength() {
        return JUR_TareasLength;
    }
    @Transient
    public void setJUR_TareasLength(int JUR_TareasLength) {
        this.JUR_TareasLength = JUR_TareasLength;
    }
    @Transient
    public String getNombreCompleto() {
        return NombreCompleto;
    }
    @Transient
    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    @Override
    public String toString() {
        return "JUR_RecursosHumanos{" + "__id_JUR_RecursoHumano=" + __id_JUR_RecursoHumano + ", Nombre=" + Nombre + ", Apellidos=" + Apellidos + ", Puesto=" + Puesto + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}