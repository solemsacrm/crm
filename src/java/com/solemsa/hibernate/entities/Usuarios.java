package com.solemsa.hibernate.entities;

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
@Table(name="usuarios")
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_Usuario")
    private String __id_Usuario;

    @ManyToOne(targetEntity=Empleados.class)
    @JoinColumn(name = "_id_Empleado")
    private Empleados Empleado;
    @Column(name="_id_REX_RecursoHumano")
    private String _id_REX_RecursoHumano;
    @Column(name="_id_JUR_RecursoHumano")
    private String _id_JUR_RecursoHumano;

    @Column(name="Usuario")
    private String Usuario;
    @Column(name="Contrasenia")
    private String Contrasenia;
    @Column(name="Area")
    private String Area;
    
    @Column(name="zz_FechaCreacion")
    private Timestamp zz_FechaCreacion;
    @Column(name="zz_FechaModificacion")
    private Timestamp zz_FechaModificacion;
    @Column(name="zz_UsuarioCreacion")
    private String zz_UsuarioCreacion;
    @Column(name="zz_UsuarioModificacion")
    private String zz_UsuarioModificacion;

    public Usuarios() {

    }

    public String getId_Usuario() {
        return __id_Usuario;
    }

    public String getId_REX_RecursoHumano() {
        return _id_REX_RecursoHumano;
    }

    public Empleados getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(Empleados Empleado) {
        this.Empleado = Empleado;
    }

    public String getId_JUR_RecursoHumano() {
        return _id_JUR_RecursoHumano;
    }

    public String getUsuario() {
            return Usuario;
    }

    public void setUsuario(String Usuario) {
            this.Usuario = Usuario;
    }

    public String getContrasenia() {
            return Contrasenia;
    }

    public void setContrasenia(String Contrasenia) {
            this.Contrasenia = Contrasenia;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
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
        return __id_Usuario + "," + _id_REX_RecursoHumano + "," + _id_JUR_RecursoHumano + "," + Usuario + "," + Contrasenia + "," + Area+(Empleado!=null?(","+Empleado.getPuesto()+","+Empleado.getDirectivo()+","+Empleado.getGerente()+","+(((Empleado.getNombre()!=null?Empleado.getNombre():"")+(Empleado.getApellidoPaterno()!=null?(" "+Empleado.getApellidoPaterno()):"")).trim())):"");
    }
    
}