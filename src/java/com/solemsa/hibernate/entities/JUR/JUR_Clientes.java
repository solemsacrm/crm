package com.solemsa.hibernate.entities.JUR;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="clientes")
public class JUR_Clientes extends ClientesMSC{

    public JUR_Clientes(){
        
    }
    
    @OneToMany(mappedBy = "Cliente",fetch=FetchType.LAZY)
    private List<JUR_Casos> JUR_Casos;
    
    @Transient
    private transient int JUR_CasosLength;
    
    public List<JUR_Casos> getJUR_Casos() {
        return JUR_Casos;
    }

    public void setJUR_Casos(List<JUR_Casos> JUR_Casos) {
        this.JUR_Casos = JUR_Casos;
    }
    
    @Transient
    public int getJUR_CasosLength() {
        return JUR_CasosLength;
    }
    
    @Transient
    public void setJUR_CasosLength(int JUR_CasosLength) {
        this.JUR_CasosLength = JUR_CasosLength;
    }
    
}
