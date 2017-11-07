package com.solemsa.jsf.controllerBeans;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import com.solemsa.jsf.DAOs.daoClientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean()
@RequestScoped
public class ClientesCRS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoClientes dao;
    private List<ClientesMSC> lista;
    private ClientesMSC cliente;
    private String rowID;
            
    public ClientesCRS(){
        
    }

    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public List<ClientesMSC> getLista() {
        return lista;
    }

    public void setLista(List<ClientesMSC> lista) {
        this.lista = lista;
    }

    public ClientesMSC getCliente() {
        return cliente;
    }

    public void setCliente(ClientesMSC cliente) {
        this.cliente = cliente;
    }

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }
    
    public void fetchList(){
        this.lista=dao.getClientesList(dao.getAREA(),logInBean.getUsuario().getJur(),logInBean.getUsuario().isDirectivo());
    }
    
    public void fetchCliente(int id)
    {
        this.cliente=dao.getCliente(id);
    }
    
    public String goToDetail()
    { 
        return "clientesD?faces-redirect-true";
    }
    
    public void rmvRecord()
    {
        System.out.println("Row: "+rowID);
        String[] arr=rowID.split(",");
        int c=arr.length,c2=c;
        System.out.println("arr.length: "+c);
        ClientesMSC[] del=new ClientesMSC[c];
        int a=logInBean.getUsuario().getArea();
        String un=logInBean.getUsuario().getNombre();
        boolean d=logInBean.getUsuario().isDirectivo(),g=logInBean.getUsuario().isGerente();
        for(int i=0;i<c;i++)
        {
            long n=Long.parseLong(arr[i]);
            long k=lista.size();
            ClientesMSC t=null;
            for(int j = 0; j < k; j++){
                t=lista.get(j);
                String[] tun=t.getZz_UsuarioCreacion().split("_");
                boolean f=((t.getClienteCON()+t.getClienteJUR()+t.getClienteOUT()+t.getClienteREX()+t.getClienteSEG())<2);
                if(f)
                    switch(a)
                    {
                        case 1:f=(Integer.parseInt(tun[0])==1);break;
                        case 2:f=(Integer.parseInt(tun[0])==2);break;
                        case 3:f=false;break;
                        case 4:f=(Integer.parseInt(tun[0])==4);break;
                        case 5:f=(Integer.parseInt(tun[0])==5);break;
                        case 6:f=(Integer.parseInt(tun[0])==6&&(t.getClienteCON()+t.getClienteJUR()+t.getClienteOUT()+t.getClienteREX()+t.getClienteSEG())<1);break;
                    }
                if(t.getId_Clientes()==n&&(d||(f&&(g||un.equals(tun[1])))))
                {
                    del[--c2]=t;
                    break;
                }
            }
            lista.remove(t);
            dao.deleteMultipleClientes(del);
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try{
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }catch(Exception e){System.out.println("EROR REDIRECTING ON Delete");}
    }
    
    public int fromVentas(char p)
    {
        return p=='6'?1:0;
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct Clientes METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(dao==null)
            {
                dao=new daoClientes(logInBean.getFactory(),logInBean.getUsuario().getArea());
                fetchList();
            }
        }
        System.out.println("@PostConstruct Clientes METHOD PERFORMED");
    }
    
}
