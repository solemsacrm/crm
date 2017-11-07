package com.solemsa.hibernate.entities.REX;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="rex_proveedores")
public class REX_Proveedores implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_Proveedor")
    private long __id_REX_Proveedor;

    @OneToMany(mappedBy="REX_Proveedor")
    @javax.persistence.OrderBy("Precio")
    private List<REX_Oferta> REX_Oferta;
    
    @Column(name="Nombre")
    private String Nombre;
    @Column(name="RazonSocial")
    private String RazonSocial;
    @Column(name="Direccion1")
    private String Direccion1;
    @Column(name="Direccion2")
    private String Direccion2;
    @Column(name="Tipo")
    private String Tipo;
    @Column(name="Notas")
    private String Notas;
    @Column(name="Ciudad")
    private String Ciudad;
    @Column(name="Estado")
    private String Estado;
    @Column(name="Pais")
    private String Pais;
    @Column(name="CodigoPostal")
    private String CodigoPostal;
    
    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public REX_Proveedores() {

    }

    public long getId_REX_Proveedor() {
        return __id_REX_Proveedor;
    }

    public void setId_REX_Proveedor(long __id_REX_Proveedor) {
        this.__id_REX_Proveedor = __id_REX_Proveedor;
    }

    public List<REX_Oferta> getREX_Oferta() {
        return REX_Oferta;
    }

    public void setREX_Oferta(List<REX_Oferta> REX_Oferta) {
        this.REX_Oferta = REX_Oferta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
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

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
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

    @Override
    public String toString() {
        return "REX_Proveedores{" + "__id_REX_Proveedor=" + __id_REX_Proveedor + ", Nombre=" + Nombre + ", RazonSocial=" + RazonSocial + ", Direccion1=" + Direccion1 + ", Direccion2=" + Direccion2 + ", Tipo=" + Tipo + ", Notas=" + Notas + ", Ciudad=" + Ciudad + ", Estado=" + Estado + ", Pais=" + Pais + ", CodigoPostal=" + CodigoPostal + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
}