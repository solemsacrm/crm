package com.solemsa.hibernate.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="empleados")
public class Empleados implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_Empleado")
    private long __id_Empleado;

    @Column(name="Nombre")
    private String Nombre;

    @Column(name="ApellidoPaterno")
    private String ApellidoPaterno;

    @Column(name="ApellidoMaterno")
    private String ApellidoMaterno;

    @Column(name="Puesto")
    private String Puesto;

    @Column(name="Gerente")
    private int Gerente;

    @Column(name="Directivo")
    private int Directivo;

    @Column(name="FechaIngreso")
    private Date FechaIngreso;

    public Empleados() {

    }

    public long getId_Empleado() {
        return __id_Empleado;
    }

    public void setId_Empleado(long __id_Empleado) {
        this.__id_Empleado = __id_Empleado;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public String getApellidoMaterno() {
            return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
            this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getPuesto() {
            return Puesto;
    }

    public void setPuesto(String Puesto) {
            this.Puesto = Puesto;
    }

    public int getGerente() {
        return Gerente;
    }

    public void setGerente(int Gerente) {
        this.Gerente = Gerente;
    }

    public int getDirectivo() {
        return Directivo;
    }

    public void setDirectivo(int Directivo) {
        this.Directivo = Directivo;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date FechaIngreso) {
        this.FechaIngreso = FechaIngreso;
    }

    @Override
    public String toString() {
        return "Empleados{" + "__id_Empleado=" + __id_Empleado + ", Nombre=" + Nombre + ", ApellidoPaterno=" + ApellidoPaterno + ", ApellidoMaterno=" + ApellidoMaterno + ", Puesto=" + Puesto + ", Gerente=" + Gerente + ", Directivo=" + Directivo + ", FechaIngreso=" + FechaIngreso + '}';
    }
    
}