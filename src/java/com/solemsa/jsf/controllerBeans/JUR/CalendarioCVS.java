package com.solemsa.jsf.controllerBeans.JUR;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import com.solemsa.jsf.DAOs.JUR.daoCalendario;
import com.solemsa.hibernate.entities.JUR.JUR_Fechas;
import static com.solemsa.jsf.data.JUR.CalendarioData.getNumberOfDaysInMonth;
import com.solemsa.jsf.modelBeans.LogInBean;
import java.util.List;
import javax.faces.context.FacesContext;

@ManagedBean()
@ViewScoped
public class CalendarioCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoCalendario dao;
    private JUR_Fechas model,model2;
    private List<JUR_Fechas> lista,lista2;
    private int N,currentYear,displayedYear,years[];
    private boolean[] f1,f2,de,cambios;
    ////////Portal Attributes////////
    private String portalValues;
            
    public CalendarioCVS(){
        
    }
    
    @PostConstruct
    public void initDAO()
    {
        System.out.println("@PostConstruct METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(!logInBean.getActive()[6].equals(""))
            {
                if(dao==null)
                    dao=new daoCalendario(logInBean.getFactory());
                logInBean.setHideHeaderRecordControls(true);
                String d[]=(dao.ca.getCurrentDate()+"").split("-");
                currentYear=displayedYear=Integer.parseInt(d[0]);
                int n=currentYear-2004;
                if(n>=0)
                {
                    years=new int[++n];
                    for(int i=0;i<n;i++)
                        years[i]=currentYear-i;
                }
                fetchJUR_TareasList();
            }
        }
        System.out.println("@PostConstruct METHOD EXITED");
    }
    
    public LogInBean getLogInBean() {
        return logInBean;
    }

    public void setLogInBean(LogInBean logInBean) {
        this.logInBean = logInBean;
    }

    public JUR_Fechas getModel() {
        return model;
    }

    public void setModel(JUR_Fechas model) {
        this.model = model;
    }

    public List<JUR_Fechas> getLista() {
        return lista;
    }

    public void setLista(List<JUR_Fechas> lista) {
        this.lista = lista;
    }

    public int[] getYears() {
        return years;
    }

    public void setYears(int[] years) {
        this.years = years;
    }
    
    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getDisplayedYear() {
        return displayedYear;
    }

    public void setDisplayedYear(int displayedYear) {
        this.displayedYear = displayedYear;
    }

    public boolean[] getCambios() {
        return cambios;
    }

    public void setCambios(boolean[] cambios) {
        this.cambios = cambios;
    }

    public String getPortalValues() {
        return portalValues;
    }

    public void setPortalValues(String portalValues) {
        this.portalValues = portalValues;
    }
    
    public void setFechaFinAfterAjax(JUR_Fechas fecha,int index)
    {
        System.out.println("setFechaFinAfterAjax METHOD REACHED with: "+fecha.getFechaFinString()+","+index);
        if(fecha.isTipo())
            fecha.setFechaFinString("");
        else
        {
            String arr[]=fecha.getFechaInicioString().split("/");
            int auxMes=Integer.parseInt(arr[1]),auxDia=Integer.parseInt(arr[0]),auxYear=displayedYear;
            if(auxDia<getNumberOfDaysInMonth(auxMes,auxYear))
                auxDia++;
            else if(auxMes<12)
            {
                auxMes++;
                auxDia=1;
            }
            else
            {
                auxYear++;
                auxMes=1;
                auxDia=1;
            }
            fecha.setFechaFinString((auxDia<10?("0"+auxDia):auxDia)+"/"+(auxMes<10?("0"+auxMes):auxMes)+"/"+auxYear);
        }
        changesSwitch("F2",index,fecha,null);
    }
    
    public String getAccordeonTitle(JUR_Fechas fecha)
    {
        String s="";
        if(fecha.getFechaFinString()!=null)
            if(fecha.getFechaFinString().isEmpty())
                s=fecha.getFechaInicioString();
            else s=fecha.getFechaInicioString()+" - "+fecha.getFechaFinString();
        else s=fecha.getFechaInicioString();
        return s;
    }
    
    /*public int getDaysNumber(JUR_Fechas fecha)
    {
        int n=fecha.getFechaFin();
        System.out.println("getDaysNumber METHOD REACHED with: "+n);
        n=getNumberOfDaysInMonth(n,displayedYear);
        System.out.println("getDaysNumber METHOD EXITED with: "+n);
        return n;
    }
    
    public void changeDaysNumber(JUR_Fechas fecha)
    {
        int n=fecha.getFechaFin();
        System.out.println("changeDaysNumber METHOD REACHED with: "+n);
        n=getNumberOfDaysInMonth(n,displayedYear);
        days=new int[n];
        for(int i=0;i<n;i++)
            days[i]=i+1;
        System.out.println("changeDaysNumber METHOD EXITED with: "+n);
    }*/
    
    public void changeDisplayedYear(int action)
    {
        System.out.println("changeDisplayedYear METHOD REACHED with: "+action);
        if(action==1)
            displayedYear+=displayedYear<currentYear?1:0;
        else if(action==2)
            displayedYear-=displayedYear>2004?1:0;
        fetchJUR_TareasList();
        System.out.println("changeDisplayedYear METHOD PERFORMED");
    }
    
    public void fetchJUR_TareasList()
    {
        System.out.println("fetchJUR_TareasList METHOD REACHED");
        lista=dao.getJUR_Fechas(displayedYear);
        lista2=dao.reloadJUR_Tareas(lista,displayedYear);
        N=lista.size();
        System.out.println("\tN: "+N);
        cambios=new boolean[N];
        de=new boolean[N];
        f1=new boolean[N];
        f2=new boolean[N];
        setBooleanArrays(false,-1);
        //changeDaysNumber(lista.get(i));
        System.out.println("fetchJUR_TareasList METHOD PERFORMED");
    }
    
    public void updateFormServicios(JUR_Fechas fecha,int index)
    {
        System.out.println("updateFormServicios METHOD REACHED");
        try
        {
            saveJUR_Fechas(fecha,index,logInBean.getUsuario().getArea()+"_"+logInBean.getUsuario().getNombre());
        }
        catch(Exception e)
        {
            System.out.println("updateFormServicios EXCEPTION: "+e);
            e.printStackTrace();
        }
        System.out.println("updateFormServicios METHOD REACHED");
    }
    
    public void saveJUR_Fechas(JUR_Fechas model,int index,String usu)
    {
        System.out.println("saveJUR_Fechas METHOD REACHED");
        dao.saveJUR_Fecha(model,usu);
        System.out.println("\tid: "+model.getId_JUR_Fecha());
        lista2.set(index,dao.loadJUR_Fecha(model));
        setBooleanArrays(false,index);
        System.out.println("saveJUR_Fechas METHOD PERFORMED");
    }
    
    public void checkForChanges(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        int index=Integer.parseInt(e.getComponent().getAttributes().get("data-index").toString());
        changesSwitch(s,index,null,null);
    }
    
    private void changesSwitch(String id,int index,JUR_Fechas model,JUR_Fechas model2)
    {
        System.out.println("checkForChanges METHOD REACHED with: "+id+"@"+index);
        if(model==null)
            model=lista.get(index);
        if(model2==null)
            model2=lista2.get(index);
        switch(id)
        {
            case "F2":f2[index]=!(model.getFechaFinString()!=null?model.getFechaFinString():"").equals(model2.getFechaFinString()!=null?model2.getFechaFinString():"");break;
            case "DE":de[index]=!(model.getDescripcion()!=null?model.getDescripcion():"").equals(model2.getDescripcion()!=null?model2.getDescripcion():"");break;
            case "F1":f1[index]=!(model.getFechaInicioString()!=null?model.getFechaInicioString():"").equals(model2.getFechaInicioString()!=null?model2.getFechaInicioString():"");break;
        }
        cambios[index]=f2[index]||f1[index]||de[index];
        System.out.println("Cambios: "+cambios[index]);
    }
    
    public void rmvAccordionNode(JUR_Fechas fecha,int index)
    {
        System.out.println("rmvAccordionNode METHOD REACHED");
        try
        {
            dao.deleteJUR_Fecha(fecha);
            lista.remove(index);
            lista2.remove(index);
            N--;
            boolean[] tmpC=new boolean[N],
                    tmpD1=new boolean[N],
                    tmpD2=new boolean[N],
                    tmpD=new boolean[N];
            for(int i=0;i<N;i++)
            {
                if(i<index)
                {
                    tmpC[i]=cambios[i];
                    tmpD[i]=de[i];
                    tmpD1[i]=f1[i];
                    tmpD2[i]=f2[i];
                }
                else
                {
                    tmpC[i]=cambios[i+1];
                    tmpD[i]=de[i+1];
                    tmpD1[i]=f1[i+1];
                    tmpD2[i]=f2[i+1];
                }
            }
            cambios=tmpC;
            de=tmpD;
            f1=tmpD1;
            f2=tmpD2;
        }
        catch(Exception e)
        {
            System.out.println("rmvAccordionNode EXCEPTION: "+e);
            e.printStackTrace();
        }
        System.out.println("rmvAccordionNode METHOD PERFORMED");
    }
    
    public void addAccordionNode()
    {
        System.out.println("addAccordionNode METHOD REACHED");
        try
        {
            JUR_Fechas tmp=null;
            if(N>0)
            {
                tmp=lista.get(N-1);
                String arr[];
                if(tmp.getFechaFinString().isEmpty())
                    arr=tmp.getFechaInicioString().split("/");
                else arr=tmp.getFechaFinString().split("/");
                int auxMes=Integer.parseInt(arr[1]),auxDia=Integer.parseInt(arr[0]),auxYear=displayedYear;
                if(auxDia<getNumberOfDaysInMonth(auxMes,auxYear))
                    auxDia++;
                else if(auxMes<12)
                {
                    auxMes++;
                    auxDia=1;
                }
                else
                {
                    auxYear++;
                    auxMes=1;
                    auxDia=1;
                }
                tmp=new JUR_Fechas();
                tmp.setFechaInicioString((auxDia<10?("0"+auxDia+"/"):(auxDia+"/"))+(auxMes<10?("0"+auxMes+"/"):(auxMes+"/"))+auxYear);
            }
            else 
            {
                tmp=new JUR_Fechas();
                tmp.setFechaInicioString("01/01/"+displayedYear);
            }
            tmp.setFechaFinString("");
            tmp.setTipo(true);
            lista.add(tmp);
            lista2.add(new JUR_Fechas());
            N++;
            System.out.println("\tN: "+N);
            setBooleanArrays(true,N-1);
        }
        catch(Exception e)
        {
            System.out.println("addAccordionNode EXCEPTION: "+e);
            e.printStackTrace();
        }
        System.out.println("addAccordionNode METHOD SUCCESSFUL");
    }
    
    private void setBooleanArrays(boolean value,int index)
    {
        System.out.println("setBooleanArrays METHOD REACHED with: "+value+", "+index);
        if(index<0)
            for(int i=0;i<N;i++)
            {
                cambios[i]=value;
                f1[i]=value;
                f2[i]=value;
                de[i]=value;
            }
        else
        {
            System.out.println("cambis.length: "+cambios.length);
            if(N!=cambios.length)
            {
                boolean[] tmpC=new boolean[N],
                         tmpF1=new boolean[N],
                         tmpF2=new boolean[N],
                         tmpD=new boolean[N];
                int n=N-1;
                for(int i=0;i<n;i++)
                {
                    tmpC[i]=cambios[i];
                    tmpD[i]=de[i];
                    tmpF1[i]=f1[i];
                    tmpF2[i]=f2[i];
                }
                tmpC[index]=value;
                tmpD[index]=value;
                tmpF1[index]=value;
                tmpF2[index]=value;
                cambios=tmpC;
                de=tmpD;
                f1=tmpF1;
                f2=tmpF2;
            }
            else 
            {
                cambios[index]=value;
                de[index]=value;
                f1[index]=value;
                f2[index]=value;
            }
        }
        System.out.println("setBooleanArrays METHOD PERFORMED");
    }
    
    public String redirigir()
    {
        logInBean.setActiveModule(6,10,"calendario");
        logInBean.setHideHeaderRecordControls(true);
        try
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("calendarioD.xhtml");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }
    
}