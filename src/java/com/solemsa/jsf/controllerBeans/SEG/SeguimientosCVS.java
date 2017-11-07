package com.solemsa.jsf.controllerBeans.SEG;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.SEG.daoSEG_Seguimientos;
import com.solemsa.hibernate.entities.SEG.SEG_Seguimientos;
import com.solemsa.jsf.controllerBeans.ClientesCVS;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean()
@ViewScoped
public class SeguimientosCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    @ManagedProperty(value="#{clientesCVS}")
    private ClientesCVS clientesCVS;
    private daoSEG_Seguimientos dao;
    private List<SEG_Seguimientos> lista,lista2;
    private int N=0;
    private boolean cambios=false,fe[],de[];
    private CommonActions ca;
            
    public SeguimientosCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoSEG_Seguimientos(logInBean.getFactory());
            ca=new CommonActions();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            fetchListSEG_Seguimiento(Long.parseLong(id));
        }
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }
    
    public ClientesCVS getClientesCVS() {
        return clientesCVS;
    }

    public void setClientesCVS(ClientesCVS clientesCVS) {
        this.clientesCVS = clientesCVS;
    }
    
    public List<SEG_Seguimientos> getLista() {
        return lista;
    }

    public void setLista(List<SEG_Seguimientos> lista) {
        this.lista = lista;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public boolean isCambios() {
        return cambios;
    }

    public void setCambios(boolean cambios) {
        this.cambios = cambios;
    }

    public void fetchListSEG_Seguimiento(long cliente)
    {
        lista=dao.getSEG_SeguimientosList(cliente);
        N=lista.size();
        if(N<1)
        {
            lista.add(initializeNewNode(new SEG_Seguimientos()));
            N++;
        }
        lista2=cloneLista();
        fe=new boolean[N];
        de=new boolean[N];
        setBooleansTo(false);
        clientesCVS.setBooleansTo(false);
    }
    
    private SEG_Seguimientos initializeNewNode(SEG_Seguimientos s)
    {
        s.setCliente(getCliente());
        s.setFechaString("");
        s.setDescripcion("");
        return s;
    }
    
    private ClientesMSC getCliente(){
        switch(logInBean.getUsuario().getArea())
        {
            case 2: return clientesCVS.getJUR_Cliente();
            default: return clientesCVS.getCliente();
        }
    }
    
    private List cloneLista()
    {
        List<SEG_Seguimientos> l=new java.util.ArrayList();
        for (int i=0; i<N;i++)
            l.add(dao.clone(lista.get(i)));
        return l;
    }
    
    public void addAccordionNode()
    {
        System.out.println("addAccordionNode METHOD REACHED");
        try
        {
            lista.add(0,initializeNewNode(new SEG_Seguimientos()));
            boolean fet[]=fe.clone(),det[]=de.clone();
            int n=fet.length+1;
            fe=new boolean[n];
            de=new boolean[n];
            fe[0]=de[0]=true;
            for(int i=1;i<n;i++)
            {
                fe[i]=fet[i-1];
                de[i]=det[i-1];
            }
            cambios=true;
            clientesCVS.evaluateBooleans(cambios);
            N++;
        }
        catch(Exception e)
        {
            System.out.println("addAccordionNode EXCEPTION: "+e);
        }
        System.out.println("addAccordionNode METHOD SUCCESSFUL");
    }
    
    public void rmvAccordionNode(AjaxBehaviorEvent e)
    {
        System.out.println("rmvAccordionNode METHOD REACHED");
        try{
            int index=Integer.parseInt(e.getComponent().getAttributes().get("data-index")+"");
            System.out.println("\tindex: "+index);
            System.out.println("\tsize 1: "+lista.size());
            lista.remove(index);
            boolean fet[]=fe.clone(),det[]=de.clone();
            int n=fet.length;
            if(n>1)n--;
            fe=new boolean[n];
            for(int i=0;i<n;i++)
                if(i>index)
                {
                    fe[i]=fet[i+1];
                    de[i]=det[i+1];
                }
                else
                {
                    fe[i]=fet[i];
                    de[i]=det[i];
                }
            if(n>index)
            {
                fe[index]=true;
                de[index]=true;
            }
            else
            {
                fe[n-1]=true;
                de[n-1]=true;
            }
            clientesCVS.evaluateBooleans(true);
            cambios=true;
            N--;
        }
        catch(Exception ex)
        {
            System.out.println("rmvAccordionNode EXCEPTION: "+ex);
        }
        System.out.println("\tsize 2: "+lista.size());
        System.out.println("rmvAccordionNode METHOD SUCCESSFUL");
    }
    
    public void checkForChanges(AjaxBehaviorEvent e)
    {
        System.out.println("checkForChanges METHOD REACHED with: "+cambios);
        
        cambios=false;
        if(N==lista2.size())
        {
            Map<String,Object> m=e.getComponent().getAttributes();
            String s=m.get("id").toString();
            System.out.println("AJAX EVENT COMPONENT: "+s);
            int i;
            //System.out.println("1) "+lista.get(i).getFechaString()+", "+lista.get(i).getDescripcion()+"\n2) "+lista2.get(i).getFechaString()+", "+lista2.get(i).getDescripcion());
            switch(s)
            {
                case "FS":i=Integer.parseInt(m.get("data-index").toString());
                          String tf=lista2.get(i).getFechaString();
                          cambios=fe[i]=!lista.get(i).getFechaString().equals(tf==null?"":tf);
                          break;
                case "DS":i=Integer.parseInt(m.get("data-index").toString());
                          String td=lista2.get(i).getDescripcion();
                          cambios=de[i]=!lista.get(i).getDescripcion().equals(td==null?"":td);
                          break;
                default: clientesCVS.changesSwitch(s);break;
            }
            System.out.println("N: "+N+", "+"c: "+cambios);
            for(i=0;i<N&&cambios==false;i++)
                cambios=fe[i]||de[i];
        }
        else cambios=true;
        clientesCVS.evaluateBooleans(cambios);
        System.out.println("checkForChanges METHOD SUCCESSFUL");
    }
    
    public void saveCienteAndSeguimientos()
    {
        System.out.println("saveCienteAndSeguimientos METHOD REACHED");
        String u=logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre();
        java.sql.Timestamp t=clientesCVS.getModel().getZz_FechaModificacion();
        clientesCVS.saveCliente(u);
        boolean timestampFlag=t!=clientesCVS.getModel().getZz_FechaModificacion();
        t=null;
        if(timestampFlag||cambios)
        {
            if(timestampFlag)
                clientesCVS.getNewServicios();
            if(cambios)
            {
                dao.saveFromCliente(lista,lista2,u,getCliente());
                lista2=cloneLista();
            }
        }
        setBooleansTo(false);
        clientesCVS.setBooleansTo(false);
        System.out.println("saveCienteAndSeguimientos PERFROMED");
    }
    
    private void setBooleansTo(boolean p)
    {
        for(int i=0;i<N;i++)
        {
            fe[i]=p;
            de[i]=p;
        }
        cambios=p;
    }
    
}