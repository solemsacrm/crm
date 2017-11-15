package com.solemsa.hibernate.entities.REX;

import com.solemsa.hibernate.entities.Clientes;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;
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
@Table(name="rex_obras")
public class REX_Obras implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_Obra")
    private long __id_REX_Obra;

    @ManyToOne(targetEntity=Clientes.class)
    @JoinColumn(name = "_id_Clientes")
    private Clientes Cliente;
    
    @OneToMany(mappedBy="REX_Obra",fetch=FetchType.LAZY)
    @javax.persistence.OrderBy("Fecha desc")
    private List<REX_Avances> REX_Avances;

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="FechaInicio")
    private Date FechaInicio;
    @Column(name="FechaFin")
    private Date FechaFin;
    @Column(name="Estatus")
    private int Estatus;
    @Column(name="Direccion1")
    private String Direccion1;
    @Column(name="Direccion2")
    private String Direccion2;
    @Column(name="Ciudad")
    private String Ciudad;
    @Column(name="Estado")
    private String Estado;
    @Column(name="Pais")
    private String Pais;
    @Column(name="CodigoPostal")
    private String CodigoPostal;
    @Column(name="Descripcion")
    private String Descripcion;
    @Column(name="Monto")
    private float Monto;
    @Column(name="Moneda")
    private String Moneda;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;
    
    @Transient
    private int REX_AvanceLenght;
    @Transient
    private String FechaInicioString,FechaFinString;

    public REX_Obras() {

    }

    public long getId_REX_Obra() {
        return __id_REX_Obra;
    }

    public void setId_REX_Obra(long __id_REX_Obra) {
        this.__id_REX_Obra = __id_REX_Obra;
    }
    
    public Clientes get_Cliente() {
        return Cliente;
    }

    public void set_Cliente(Clientes Cliente) {
        this.Cliente = Cliente;
    }
    
    public List<REX_Avances> getREX_Avances() {
        return REX_Avances;
    }

    public void setREX_Avances(List<REX_Avances> REX_Avances) {
        this.REX_Avances = REX_Avances;
    }
    
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
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

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }

    public String getDireccion1() {
        return Direccion1;
    }

    public void setDireccion1(String Direccion1) {
        this.Direccion1 = Direccion1;
    }

    public String getDireccion2() {
        return Direccion2;
    }

    public void setDireccion2(String Direccion2) {
        this.Direccion2 = Direccion2;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String Ciudad) {
        this.Ciudad = Ciudad;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public String getCodigoPostal() {
        return CodigoPostal;
    }

    public void setCodigoPostal(String CodigoPostal) {
        this.CodigoPostal = CodigoPostal;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public float getMonto() {
        return Monto;
    }

    public void setMonto(float Monto) {
        this.Monto = Monto;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
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
    public int getREX_AvanceLenght() {
        return REX_AvanceLenght;
    }
    @Transient
    public void setREX_AvanceLenght(int REX_AvanceLenght) {
        this.REX_AvanceLenght = REX_AvanceLenght;
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

    @Override
    public String toString() {
        return "REX_Obras{" + "__id_REX_Obra=" + __id_REX_Obra + ", Nombre=" + Nombre + ", FechaInicio=" + FechaInicio + ", FechaFin=" + FechaFin + ", Estatus=" + Estatus + ", Direccion1=" + Direccion1 + ", Direccion2=" + Direccion2 + ", Ciudad=" + Ciudad + ", Estado=" + Estado + ", Pais=" + Pais + ", Descripcion=" + Descripcion + ", CodigoPostal=" + CodigoPostal + ", Monto=" + Monto + ", Moneda=" + Moneda + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}