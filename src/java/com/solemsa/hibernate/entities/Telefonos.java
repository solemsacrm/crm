package com.solemsa.hibernate.entities;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
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

@Entity
@Table(name="telefonos")
public class Telefonos implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_Telefono")
    private long __id_Telefono;
    
    @ManyToOne(targetEntity=Clientes.class)
    @JoinColumn(name = "_id_Cliente")
    private ClientesMSC Cliente;

    @Column(name="Numero")
    private String Numero;
    @Column(name="Tipo")
    private String Tipo;
    @Column(name="Lada")
    private String Lada;
    @Column(name="Extension")
    private String Extension;
    @Column(name="Notas")
    private String Notas;

    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public Telefonos() {

    }
        
    public Telefonos(String Numero,String Tipo,String Lada,String Extension,String Notas,String zz_UsuarioCreacion,String zz_UsuarioModificacion,Timestamp zz_FechaCreacion,Timestamp zz_FechaModificacion) {
        this.Numero=Numero;
        this.Tipo=Tipo;
        this.Lada=Lada;
        this.Extension=Extension;
        this.Notas=Notas;
        this.zz_UsuarioCreacion=zz_UsuarioCreacion;
        this.zz_UsuarioModificacion=zz_UsuarioModificacion;
        this.zz_FechaCreacion=zz_FechaCreacion;
        this.zz_FechaModificacion=zz_FechaModificacion;
    }

    public long getId_Telefono() {
        return __id_Telefono;
    }

    public void setId_Telefono(long __id_Telefono) {
        this.__id_Telefono = __id_Telefono;
    }

    public ClientesMSC getCliente() {
        return Cliente;
    }

    public void setCliente(ClientesMSC Cliente) {
        this.Cliente = Cliente;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getLada() {
        return Lada;
    }

    public void setLada(String Lada) {
        this.Lada = Lada;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String Extension) {
        this.Extension = Extension;
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

    @Override
    public String toString() {
        return "Telefonos{" + "__id_Telefonos=" + __id_Telefono +", Numero=" + Numero + ", Tipo=" + Tipo + ", Lada=" + Lada + ", Extension=" + Extension + ", Notas=" + Notas + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}