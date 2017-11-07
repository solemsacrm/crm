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
@Table(name="jur_casos")
public class JUR_Casos implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_JUR_Caso")
    private long __id_JUR_Caso;

    @ManyToOne(targetEntity=JUR_Clientes.class)
    @JoinColumn(name = "_id_Cliente")
    private JUR_Clientes Cliente;
    
    @OneToMany(mappedBy = "JUR_Caso",fetch=FetchType.LAZY)
    private List<JUR_Etapas> JUR_Etapas;
    @OneToMany(mappedBy = "JUR_Caso",fetch=FetchType.LAZY)
    private List<JUR_Tareas> JUR_Tareas;

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="NoExpediente")
    private String NoExpediente;
    @Column(name="Costo")
    private float Costo;
    @Column(name="EtapaActual")
    private int EtapaActual;
    @Column(name="PurebasFecha")
    private Date PurebasFecha;
    @Column(name="ContestacionFecha")
    private Date ContestacionFecha;
    @Column(name="Rama")
    private String Rama;
    @Column(name="Juzgado")
    private String Juzgado;
    @Column(name="Tipo")
    private String Tipo;
    @Column(name="PapelCliente")
    private String PapelCliente;

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
    private transient int JUR_EtapasLength,JUR_TareasLength;

    public JUR_Casos() {

    }

    public long getId_JUR_Caso() {
        return __id_JUR_Caso;
    }

    public void setId_JUR_Caso(long __id_JUR_Caso) {
        this.__id_JUR_Caso = __id_JUR_Caso;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public JUR_Clientes getCliente() {
        return Cliente;
    }

    public void setCliente(JUR_Clientes Cliente) {
        this.Cliente = Cliente;
    }

    public List<JUR_Etapas> getJUR_Etapas() {
        return JUR_Etapas;
    }

    public void setJUR_Etapas(List<JUR_Etapas> JUR_Etapas) {
        this.JUR_Etapas = JUR_Etapas;
    }
    
    public List<JUR_Tareas> getJUR_Tareas() {
        return JUR_Tareas;
    }
    
    public void setJUR_Tareas(List<JUR_Tareas> JUR_Tareas) {
        this.JUR_Tareas = JUR_Tareas;
    }
    
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getNoExpediente() {
        return NoExpediente;
    }

    public void setNoExpediente(String NoExpediente) {
        this.NoExpediente = NoExpediente;
    }

    public float getCosto() {
        return Costo;
    }

    public void setCosto(float Costo) {
        this.Costo = Costo;
    }

    public int getEtapaActual() {
        return EtapaActual;
    }

    public void setEtapaActual(int EtapaActual) {
        this.EtapaActual = EtapaActual;
    }

    public Date getPurebasFecha() {
        return PurebasFecha;
    }

    public void setPurebasFecha(Date PurebasFecha) {
        this.PurebasFecha = PurebasFecha;
    }

    public Date getContestacionFecha() {
        return ContestacionFecha;
    }

    public void setContestacionFecha(Date ContestacionFecha) {
        this.ContestacionFecha = ContestacionFecha;
    }

    public String getRama() {
        return Rama;
    }

    public void setRama(String Rama) {
        this.Rama = Rama;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getJuzgado() {
        return Juzgado;
    }

    public void setJuzgado(String Juzgado) {
        this.Juzgado = Juzgado;
    }

    public String getPapelCliente() {
        return PapelCliente;
    }

    public void setPapelCliente(String PapelCliente) {
        this.PapelCliente = PapelCliente;
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
    public int getJUR_EtapasLength() {
        return JUR_EtapasLength;
    }
    @Transient
    public void setJUR_EtapasLength(int JUR_EtapasLength) {
        this.JUR_EtapasLength = JUR_EtapasLength;
    }
    @Transient
    public int getJUR_TareasLength() {
        return JUR_TareasLength;
    }
    @Transient
    public void setJUR_TareasLength(int JUR_TareasLength) {
        this.JUR_TareasLength = JUR_TareasLength;
    }

    @Override
    public String toString() {
        return "JUR_Casos{" + "__id_JUR_Caso=" + __id_JUR_Caso + ", Nombre=" + Nombre + ", Descripcion=" + Descripcion + ", NoExpediente=" + NoExpediente + ", Costo=" + Costo + ", EtapaActual=" + EtapaActual + ", Rama=" + Rama + ", Juzgado=" + Juzgado + ", Tipo=" + Tipo + ", PapelCliente=" + PapelCliente + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}