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

@Entity
@Table(name="rex_contrataciones")
public class REX_Contrataciones implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_REX_Contratacion")
    private long __id_REX_Contratacion;

    /*@ManyToOne(targetEntity=REX_Obras.class)
    @JoinColumn(name = "_id_REX_Obra")
    private REX_Obras REX_Obra;*/

    @Column(name="Nombre")
    private String Nombre;
    @Column(name="FechaContratacion")
    private Date FechaContratacion;
    @Column(name="Edad")
    private int Edad;
    @Column(name="IFE")
    private String IFE;
    @Column(name="NumSeguro")
    private String NumSeguro;
    @Column(name="Puesto")
    private String Puesto;
    @Column(name="Seguro")
    private boolean Seguro;
    @Column(name="DiasContratacion")
    private int DiasContratacion;
    @Column(name="Notas")
    private String Notas;
    @Column(name="Sueldo")
    private float Sueldo;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public REX_Contrataciones() {

    }

    public long getId_REX_Contratacion() {
        return __id_REX_Contratacion;
    }

    /*public REX_Obras get_REX_Obra() {
        return REX_Obra;
    }

    public void set_REX_Obra(REX_Obras REX_Obra) {
        this.REX_Obra = REX_Obra;
    }*/

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Date getFechaContratacion() {
        return FechaContratacion;
    }

    public void setFechaContratacion(Date FechaContratacion) {
        this.FechaContratacion = FechaContratacion;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    public String getIFE() {
        return IFE;
    }

    public void setIFE(String IFE) {
        this.IFE = IFE;
    }

    public String getNumSeguro() {
        return NumSeguro;
    }

    public void setNumSeguro(String NumSeguro) {
        this.NumSeguro = NumSeguro;
    }

    public String getPuesto() {
        return Puesto;
    }

    public void setPuesto(String Puesto) {
        this.Puesto = Puesto;
    }

    public boolean isSeguro() {
        return Seguro;
    }

    public void setSeguro(boolean Seguro) {
        this.Seguro = Seguro;
    }

    public int getDiasContratacion() {
        return DiasContratacion;
    }

    public void setDiasContratacion(int DiasContratacion) {
        this.DiasContratacion = DiasContratacion;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String Notas) {
        this.Notas = Notas;
    }

    public float getSueldo() {
        return Sueldo;
    }

    public void setSueldo(float Sueldo) {
        this.Sueldo = Sueldo;
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

    /*@Override
    public String toString() {
        return "REX_Contrataciones{" + "__id_REX_Contratacion=" + __id_REX_Contratacion + ", REX_Obra=" + REX_Obra + ", Nombre=" + Nombre + ", FechaContratacion=" + FechaContratacion + ", Edad=" + Edad + ", IFE=" + IFE + ", NumSeguro=" + NumSeguro + ", Puesto=" + Puesto + ", Seguro=" + Seguro + ", DiasContratacion=" + DiasContratacion + ", Notas=" + Notas + ", Sueldo=" + Sueldo + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }*/

    @Override
    public String toString() {
        return "REX_Contrataciones{" + "__id_REX_Contratacion=" + __id_REX_Contratacion + ", Nombre=" + Nombre + ", FechaContratacion=" + FechaContratacion + ", Edad=" + Edad + ", IFE=" + IFE + ", NumSeguro=" + NumSeguro + ", Puesto=" + Puesto + ", Seguro=" + Seguro + ", DiasContratacion=" + DiasContratacion + ", Notas=" + Notas + ", Sueldo=" + Sueldo + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }
    
    
}