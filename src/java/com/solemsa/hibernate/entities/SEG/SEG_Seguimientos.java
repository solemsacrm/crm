package com.solemsa.hibernate.entities.SEG;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.Clientes;
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
import javax.persistence.Transient;

@Entity
@Table(name="seg_seguimientos")
public class SEG_Seguimientos implements Serializable {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="__id_SEG_Seguimiento")
    private long __id_SEG_Seguimiento;

    @ManyToOne(targetEntity=Clientes.class)
    @JoinColumn(name = "_id_ClienteSEG")
    private ClientesMSC Cliente;

    @Column(name="Descripcion")
    private String Descripcion;
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
    
    private transient String FechaString;

    public SEG_Seguimientos() {

    }

    public long getId_SEG_Seguimiento() {
        return __id_SEG_Seguimiento;
    }

    public void setId_SEG_Seguimiento(long __id_SEG_Seguimiento) {
        this.__id_SEG_Seguimiento = __id_SEG_Seguimiento;
    }

    public ClientesMSC getCliente() {
        return Cliente;
    }

    public void setCliente(ClientesMSC Cliente) {
        this.Cliente = Cliente;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
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

    @Override
    public String toString() {
        return "REX_Avances{" + "__id_SEG_Seguimiento=" + __id_SEG_Seguimiento + ", Descripcion=" + Descripcion + ", Fecha=" + Fecha + ", zz_FechaCreacion=" + zz_FechaCreacion + ", zz_FechaModificacion=" + zz_FechaModificacion + ", zz_UsuarioCreacion=" + zz_UsuarioCreacion + ", zz_UsuarioModificacion=" + zz_UsuarioModificacion + '}';
    }

}