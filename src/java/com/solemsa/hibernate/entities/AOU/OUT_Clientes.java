package com.solemsa.hibernate.entities.AOU;

import com.solemsa.hibernate.entities.JUR.*;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="clientes")
public class OUT_Clientes extends ClientesMSC{

    public OUT_Clientes(){
        
    }
    
    @Transient
    private transient int OUT_ProyectosLength;
    
    @Transient
    public int getJUR_CasosLength() {
        return OUT_ProyectosLength;
    }
    
    @Transient
    public void setJUR_CasosLength(int OUT_ProyectosLength) {
        this.OUT_ProyectosLength = OUT_ProyectosLength;
    }
    
}
