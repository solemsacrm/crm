package com.solemsa.jsf.controllerBeans.rex;

import com.solemsa.hibernate.entities.REX.REX_Avances;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.rex.daoREX_Obras;
import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.jsf.data.ListValues;
import com.solemsa.jsf.data.ValueLists;
import com.solemsa.jsf.modelBeans.LogInBean;
import com.solemsa.jsf.data.CommonActions;
import java.util.Iterator;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean()
@ViewScoped
public class ObrasCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoREX_Obras dao;
    private REX_Obras model;
    private REX_Obras model2;
    private int rowID,avN;
    private boolean cambios=false;
    boolean ic,nm,fi,ff,d1,d2,ci,es,pa,mo,pr,nt,cp,et,av[];
    private String FechaInicio,FechaFin,Monto;
    private CommonActions ca;
    
    private ValueLists clientes;
            
    public ObrasCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        System.out.println("@PostConstruct METHOD REACHED");
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoREX_Obras(logInBean.getFactory());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            ca=new CommonActions();
            fetchREX_Obra(Integer.parseInt(id));
            clientes = new ValueLists();
            logInBean.setHideHeaderRecordControls(true);
        }
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public REX_Obras getModel() {
        return model;
    }

    public void setModel(REX_Obras model) {
        this.model = model;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public boolean isCambios() {
        return cambios;
    }

    public void setCambios(boolean cambios) {
        this.cambios = cambios;
    }

    public ValueLists getClientes() {
        return clientes;
    }

    public void setClientes(ValueLists cliNomList) {
        this.clientes = cliNomList;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String Monto) {
        this.Monto = Monto;
    }
    
    public void fetchREX_Obra(long id)
    {
        model=dao.getREX_Obra(id);
        model2=dao.getREX_Obra(id);
        if(id<1)
        {
            List <REX_Avances> tmp=new java.util.ArrayList();
            tmp.add(new REX_Avances());
            model.setREX_Avances(tmp);
            model.setPais("México");
            model.setMoneda("MXN");
            model.setEstatus(2);
            nm=fi=ff=d1=d2=ci=es=pa=mo=pr=nt=cp=et=cambios=true;
            avN=model.getREX_Avances().size();
            av=new boolean[avN];
            setAvancesFlag(true);
            FechaInicio="";
            FechaFin="";
        }
        else
        {
            if(model.getREX_Avances()==null)
            {
                List <REX_Avances> tmp=new java.util.ArrayList();
                REX_Avances ra=new REX_Avances();
                REX_Obras ob=new REX_Obras();
                ob.setId_REX_Obra(model.getId_REX_Obra());
                ra.set_REX_Obra(ob);
                model.getREX_Avances().add(0,ra);
                tmp.add(ra);
                model.setREX_Avances(tmp);
            }
            avN=model.getREX_Avances().size();
            av=new boolean[avN];
            setAvancesFechaString();
            FechaInicio=model.getFechaInicio()==null?"":ca.parseDate(model.getFechaInicio().toString(),"-");
            FechaFin=model.getFechaFin()==null?"":ca.parseDate(model.getFechaFin().toString(),"-");
        }
    }
    
    public void loadClientesList()
    {
        System.out.println("loadClientesList METHOD REACHED");
        List temp=dao.loadClientesList();
        Iterator i=temp.iterator();
        while(i.hasNext())
        {
            Object[] tupla = (Object[]) i.next();
            clientes.add((Long)tupla[0],tupla[1].toString());
        }
        clientes.setLength(clientes.getList().size());
        System.out.println("clientes.legnth: "+clientes.getLength());
        clientes.setChanges(false);
    }
    
    public void updateFormServicios(AjaxBehaviorEvent e)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        saveREX_Obra(logInBean.getUsuario().getUsername());
    }
    
    public String getAvanceGeneral(int p)
    {
        String s="";
        try
        {
            switch(p)
            {
                case 1:int n=avN;
                       float gt=0;
                       for (int i=1;i<n;i++)
                       {
                           float tmp1=model.getREX_Avances().get(i).getPorcentaje(),
                                 tmp2=model.getREX_Avances().get(i-1).getPorcentaje();
                           if(gt<tmp1)
                               gt=tmp1;
                           if(gt<tmp2)
                               gt=tmp2;
                       }
                       s=gt+"";
                       break;
                case 2:s=model.getREX_Avances().get(0).getEtapa();break;
            }
        }
        catch(Exception e)
        {
            System.out.println("getCurrentAvance METHOD EXCEPTION: "+e);
        }
        return s;
    }
    
    public void saveREX_Obra(String usu)
    {
        System.out.println("saveREX_Obra METHOD REACHED");
        System.out.println("Current ID: "+model.getId_REX_Obra());
        model.setZz_UsuarioModificacion(usu);
        model.setFechaInicio(ca.stringToDate(FechaInicio,"/"));
        System.out.println("Fecha Inicio: "+model.getFechaInicio());
        model.setFechaFin(ca.stringToDate(FechaFin,"/"));
        System.out.println("Fecha Fin: "+model.getFechaFin());
        if(model.getId_REX_Obra()<1)
        {
            model.setZz_UsuarioCreacion(usu);
            dao.newREX_Obra(model);
        }
        else
        {
            System.out.println("REX_Obras "+model.getId_REX_Obra()+" MODIFY ABOUT TO START");
            System.out.println("NEW ESTATUS IS: "+model.getEstatus());
            try{
                System.out.println("MODEL 2 ABOUT TO BE MODIFIED");
                model2=dao.modifyREX_Obra(model,getAvancesFlag()?model2.getREX_Avances():null);
                System.out.println("MODEL 2 SUCCESSFULLY MODIFIED");
                //Sets name to show in Value List
                long search=model.get_Cliente().getId_Clientes();
                String txt=null;
                for(ListValues t:clientes.getList())
                    if(t.getId()==search)
                    {
                        txt=t.getLabel();
                        break;
                    }
                model.get_Cliente().setNombre(txt);
                //Sets name to show in Value List
                clientes = new ValueLists();
                sortAvances();
            }catch(Exception e){
                System.out.println("SETTING NEW VALUES TO MODELS EXCEPTION: "+e);
            }
        }
        nm=fi=ff=d1=d2=ci=es=pa=mo=nt=pr=cp=et=cambios=false;
        setAvancesFlag(false);
    }
    
    private void sortAvances()
    {
        System.out.println("sortAvances METHOD REACHED");
        model2.setREX_Avances(null);
        List<REX_Avances> ml=new java.util.ArrayList();
        List<REX_Avances> nl=new java.util.ArrayList();
        for(int i=0; i<avN;i++)
        {
            REX_Avances tmp=new REX_Avances(),
                        mlt=model.getREX_Avances().get(i);
            tmp.setDescripcion(mlt.getDescripcion());
            tmp.setEtapa(mlt.getEtapa());
            tmp.setFecha(mlt.getFecha());
            tmp.setFechaString(mlt.getFechaString());
            tmp.setId_REX_Avance(mlt.getId_REX_Avance());
            tmp.setPorcentaje(mlt.getPorcentaje());
            tmp.setZz_FechaCreacion(mlt.getZz_FechaCreacion());
            tmp.setZz_FechaModificacion(mlt.getZz_FechaModificacion());
            tmp.setZz_UsuarioCreacion(mlt.getZz_UsuarioCreacion());
            tmp.setZz_UsuarioModificacion(mlt.getZz_UsuarioModificacion());
            tmp.set_REX_Obra(mlt.get_REX_Obra());
            if(tmp.getFecha()==null)
                nl.add(tmp);
            else
                ml.add(tmp);
        }
        model.setREX_Avances(null);
        if(ml.size()>0)
            ml=bubbleSortAvances(ml);
        if(nl.size()>0)
        {
            nl=bubbleSortID(nl);
            ml.addAll(nl);
        }
        nl=new java.util.ArrayList();
        for(int i=0; i<avN;i++)
        {
            REX_Avances tmp=new REX_Avances(),
                        mlt=ml.get(i);
            tmp.setDescripcion(mlt.getDescripcion());
            tmp.setEtapa(mlt.getEtapa());
            tmp.setFecha(mlt.getFecha());
            tmp.setFechaString(mlt.getFechaString());
            tmp.setId_REX_Avance(mlt.getId_REX_Avance());
            tmp.setPorcentaje(mlt.getPorcentaje());
            tmp.setZz_FechaCreacion(mlt.getZz_FechaCreacion());
            tmp.setZz_FechaModificacion(mlt.getZz_FechaModificacion());
            tmp.setZz_UsuarioCreacion(mlt.getZz_UsuarioCreacion());
            tmp.setZz_UsuarioModificacion(mlt.getZz_UsuarioModificacion());
            tmp.set_REX_Obra(mlt.get_REX_Obra());
            nl.add(tmp);
        }
        model.setREX_Avances(ml);
        model2.setREX_Avances(nl);
        av=new boolean[avN];
        System.out.println("sortAvances METHOD SUCCESSFUL");
    }
    
    private List<REX_Avances> bubbleSortAvances(List<REX_Avances> arr)
    {
        System.out.println("bubbleSortAvances METHOD REACHED");
        boolean swapped=true;
        int j=0,m=arr.size();
        while(swapped)
        {
            swapped=false;
            j++;
            for(int i=0;i<m-j;i++)
            {          
                REX_Avances a1=arr.get(i);
                REX_Avances a2=arr.get(i+1);
                if(a1.getFecha().compareTo(a2.getFecha())<0)
                {
                    arr.set(i,a2);
                    arr.set(i+1,a1);
                    swapped = true;
                }
            }                
        }
        System.out.println("bubbleSortAvances METHOD SUCCESSFUL");
        return arr;
    }
    
    private List<REX_Avances> bubbleSortID(List<REX_Avances> arr)
    {
        System.out.println("bubbleSortID METHOD REACHED");
        boolean swapped=true;
        int j=0,m=arr.size();
        while(swapped)
        {
            swapped=false;
            j++;
            for(int i=0;i<m-j;i++)
            {          
                REX_Avances a1=arr.get(i);
                REX_Avances a2=arr.get(i+1);
                if(a1.getId_REX_Avance()>a2.getId_REX_Avance())
                {
                    arr.set(i,a2);
                    arr.set(i+1,a1);
                    swapped = true;
                }
            }                
        }
        System.out.println("bubbleSortID METHOD SUCCESSFUL");
        return arr;
    }
    
    public void saveAvances()
    {
        java.util.Date date= new java.util.Date();
        java.sql.Timestamp d=new java.sql.Timestamp(date.getTime());
        model.setREX_Avances(dao.modifyAvancesList(model.getREX_Avances(),d,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre()));
    }
    
    public String deleteREX_Obra()
    {
        if(model.getId_REX_Obra()>0)
        {
            model2.set_Cliente(null);
            dao.deleteREX_Obra(model2);
        }
        return "obras";
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        cambiosSwitch(s+"_"+e.getComponent().getAttributes().get("data-index"));
    }
    
    private void cambiosSwitch(String s)
    {
        boolean av=false;
        String arr[]=s.split("_");
        switch(arr[0])
        {
            case "FI":fi=!FechaInicio.equals(model2.getFechaInicio()+"");break;
            case "FF":ff=!FechaFin.equals(model2.getFechaFin()+"");break;
            case "IC":ic=model.get_Cliente().getId_Clientes()!=model2.get_Cliente().getId_Clientes();break;
            case "PR":pr=model.getMonto()!=model2.getMonto();break;
            case "ET":et=model.getEstatus()!=model2.getEstatus();break;
            case "MO":mo=!model.getMoneda().equals(model2.getMoneda());break;
            case "NM":nm=!model.getNombre().equals(model2.getNombre());break;
            case "D1":d1=!model.getDireccion1().equals(model2.getDireccion1());break;
            case "D2":d2=!model.getDireccion2().equals(model2.getDireccion2());break;
            case "CI":ci=!model.getCiudad().equals(model2.getCiudad());System.out.println("model: "+model.getCiudad()+" model 2: "+model2.getCiudad());break;
            case "ES":es=!model.getEstado().equals(model2.getEstado());break;
            case "PA":pa=!model.getPais().equals(model2.getPais());break;
            case "CP":cp=!model.getCodigoPostal().equals(model2.getCodigoPostal());break;
            case "NT":nt=!model.getDescripcion().equals(model2.getDescripcion());break;
            default:try{av=getAvancesFlag(Integer.parseInt(arr[1]));}catch(Exception e){System.out.println("CHANGES IN AVANCES EXCEPTION: "+e);};break;
        }
        cambios=ic||nm||fi||ff||d1||d2||ci||es||pa||mo||nt||pr||cp||et||av;
        System.out.println("Cambios: "+cambios);
    }
    private void setAvancesFlag(boolean v)
    {
        int n=av.length;
        for(int i=0;i<n;i++)
            av[i]=v;
    }
    
    private boolean getAvancesFlag(int i)
    {
        System.out.println("getAvanceFlag METHOD REACHED with i: "+i);
        av[i]=checkForChangesInAvances(i);
        boolean f=false;
        int n=av.length;
        for(int j=0;j<n;j++)
        {
            if(av[j])
            {
                f=true;
                break;
            }
        }
        return f;
    }
    
    private void setAvancesFechaString()
    {
        for(int j=0;j<avN;j++)
        {
            REX_Avances t=model.getREX_Avances().get(j);
            String f=t.getFecha()==null?null:t.getFecha().toString();
            f=ca.parseDate(f,"-");
            t.setFechaString(f);
            model2.getREX_Avances().get(j).setFechaString(f);
        }
    }
    
    private boolean getAvancesFlag()
    {
        int n=av.length;
        boolean f=false;
        for(int j=0;j<n;j++)
        {
            if(av[j])
            {
                f=true;
                break;
            }
        }
        return f;
    }
    
    public void rmvAccordionNode(AjaxBehaviorEvent e)
    {
        System.out.println("rmvAccordionNode METHOD REACHED");
        try{
            int index=Integer.parseInt(e.getComponent().getAttributes().get("data-index")+"");
            model.getREX_Avances().remove(index);
            avN--;
            cambiosSwitch(e.getComponent().getId()+"_"+index);
            boolean arr[]=av.clone();
            int n=arr.length;
            if(n>1)n--;
            av=new boolean[n];
            for(int i=0;i<n;i++)
                if(i>index)
                    av[i]=arr[i+1];
                else
                    av[i]=arr[i];
            av[n>index?index:n-1]=true;
        }
        catch(Exception ex)
        {
            System.out.println("rmvAccordionNode EXCEPTION: "+ex);
        }
        System.out.println("rmvAccordionNode METHOD SUCCESSFUL");
    }
    
    public void addAccordionNode()
    {
        System.out.println("addAccordionNode METHOD REACHED");
        try
        {
            avN=model.getREX_Avances().size();
            REX_Avances ra=new REX_Avances();
            REX_Obras ob=new REX_Obras();
            ob.setId_REX_Obra(model.getId_REX_Obra());
            ra.set_REX_Obra(ob);
            model.getREX_Avances().add(0,ra);
            boolean arr[]=av.clone();
            int n=arr.length+1;
            av=new boolean[n];
            av[0]=true;
            for(int i=1;i<n;i++)
                av[i]=arr[i-1];
            cambios=true;
            avN++;
        }
        catch(Exception ex)
        {
            System.out.println("addAccordionNode EXCEPTION: "+ex);
        }
        System.out.println("addAccordionNode METHOD SUCCESSFUL");
    }
    
    private boolean checkForChangesInAvances(int i)
    {
        System.out.println("AJAX EVENT COMPONENT LIST INDEX: "+i);
        boolean f;
        f=avN==model2.getREX_Avances().size();
        if(f)
        {
            REX_Avances temp1=model.getREX_Avances().get(i);
            temp1.setFecha(ca.stringToDate(temp1.getFechaString(),"/"));
            model.getREX_Avances().get(i).setFecha(temp1.getFecha());
            System.out.println("if:\n\ttemp1 : "+temp1.getEtapa()+", "+temp1.getFecha()+", "+temp1.getPorcentaje()+", "+temp1.getDescripcion());
            REX_Avances temp2=model2.getREX_Avances().get(i);
            System.out.println("\ttemp2 : "+temp2.getEtapa()+", "+temp2.getFecha()+", "+temp2.getPorcentaje()+", "+temp2.getDescripcion());
            f=(temp1.getEtapa()==null?"":temp1.getEtapa()).equals(temp2.getEtapa()==null?"":temp2.getEtapa());
            if(f)
                f=(temp1.getFechaString()==null?"":temp1.getFechaString()).equals(temp2.getFechaString()==null?"":temp2.getFechaString());
            if(f)
                f=temp1.getPorcentaje()==temp2.getPorcentaje();
            if(f)
                f=(temp1.getDescripcion()==null?"":temp1.getDescripcion()).equals(temp2.getDescripcion()==null?"":temp2.getDescripcion());
        }
        return !f;
    }
    
}