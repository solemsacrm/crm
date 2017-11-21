package com.solemsa.jsf.controllerBeans;

import java.io.Serializable;
import java.util.List;
import java.sql.Date;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import com.solemsa.jsf.DAOs.daoClientes;
import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.Correos;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.Telefonos;
import com.solemsa.jsf.DAOs.daoCorreos;
import com.solemsa.jsf.DAOs.daoTelefonos;
import com.solemsa.jsf.modelBeans.LogInBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;

@ManagedBean()
@ViewScoped
public class ClientesCVS implements Serializable{

    @ManagedProperty(value="#{loginBean}")
    private LogInBean logInBean;
    private daoClientes dao;
    private List<ClientesMSC> lista;
    private ClientesMSC model,model2;
    private int rowID;
    private boolean cambios=false;
    boolean nm,rs,rf,tp,ci,es,pa,cp,nt,se,st,rm,fm,fc,di,lo;
    private int[] servicios2={};
    private String servicios,fechaCliente,fechaMigracion;
    ////////Portal Attributes////////
    private String portalValues;
    private Part Logo;
            
    public ClientesCVS(){
        
    }
    
    @PostConstruct  
    public void initDAO(){
        if (logInBean.checkSession())
        {
            if(dao==null)
                dao=new daoClientes(logInBean.getFactory(),logInBean.getUsuario().getArea());
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            String id = ec.getRequestParameterMap().get("formu:idHidden");
            System.out.println("POSTCONSTRUCT ID: "+id);
            id=(id==null)?"0":id;
            System.out.println("IT WAS CONVERTED BITHC!!!");
            fetchCliente(Long.parseLong(id));
        }
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

    public ClientesMSC getModel() {
        return model;
    }

    public void setModel(ClientesMSC model) {
        this.model = model;
    }
    
    public Clientes getCliente()
    {
        return (Clientes)model;
    }
    
    public JUR_Clientes getJUR_Cliente()
    {
        return (JUR_Clientes)model;
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

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    public int[] getServicios2() {
        return servicios2;
    }

    public void setServicios2(int[] servicios2) {
        this.servicios2 = servicios2;
    }

    public String getFechaCliente() {
        return fechaCliente;
    }

    public void setFechaCliente(String fechaCliente) {
        this.fechaCliente = fechaCliente;
    }

    public String getFechaMigracion() {
        return fechaMigracion;
    }

    public void setFechaMigracion(String fechaMigracion) {
        this.fechaMigracion = fechaMigracion;
    }

    public String getPortalValues() {
        return portalValues;
    }

    public void setPortalValues(String portalValues) {
        this.portalValues = portalValues;
    }

    public Part getLogo() {
        return Logo;
    }

    public void setLogo(Part Logo) {
        this.Logo = Logo;
    }
    
    public void fetchList(){
        this.lista=dao.getClientesList(dao.getAREA(),logInBean.getUsuario().getJur(),logInBean.getUsuario().isDirectivo());
    }
    
    public void fetchCliente(long id)
    {
        this.model=dao.getCliente(id);
        this.model2=dao.getCliente(id);
        if(id>0)
        {
            servicios=(model.getClienteOUT()!=0)?"1":"";
            servicios=(model.getClienteJUR()!=0)?servicios+",2":servicios;
            servicios=(model.getClienteCON()!=0)?servicios+",3":servicios;
            servicios=(model.getClienteSEG()!=0)?servicios+",4":servicios;
            servicios=(model.getClienteREX()!=0)?servicios+",5":servicios;
            if(!servicios.equals(""))
            {
                System.out.println("s VARIABLE BEFORE REPLACE: "+servicios);
                servicios=(servicios.charAt(0)==',')?servicios.replaceFirst(",",""):servicios;
                System.out.println("s VARIABLE AFTE REPLACE: "+servicios);
                String[] se=servicios.split(",");
                int n=se.length;
                servicios2=new int[n];
                for (int i=0;i<n;i++)
                    servicios2[i]=Integer.parseInt(se[i]);
            }
            fechaCliente=model.getFechaCliente()==null?"":dao.ca.parseDate(model.getFechaCliente().toString(),"-");
            fechaMigracion=model.getFechaMigracion()==null?"":dao.ca.parseDate(model.getFechaMigracion().toString(),"-");
        }
        else
        {
            model.setZz_UsuarioCreacion(dao.getAREA()+"_"+logInBean.getUsuario().getNombre());
            model.setPais("México");
            model.setEstatus(1);
            model2.setEstatus(1);
            int a=dao.getAREA();
            if(a!=6)
            {
                servicios2=new int[1];
                servicios2[0]=a;
                servicios=a+"";
            }
            fechaCliente="";
            fechaMigracion="";
            setBooleansTo(true);
            lo=false;
        }
    }
    
    public void serviciosSetter()
    {
        System.out.println("setServicios METHOD REACHED");
        int a=dao.getAREA();
        if(a!=6)
            switch(a)
            {
                case 1:model.setClienteOUT(0);break;
                case 2:model.setClienteJUR(0);break;
                case 3:model.setClienteCON(0);break;
                case 4:model.setClienteSEG(0);break;
                case 5:model.setClienteREX(0);break;
            }
        else
        {
            model.setClienteOUT(0);
            model.setClienteJUR(0);
            model.setClienteCON(0);
            model.setClienteSEG(0);
            model.setClienteREX(0);
        }
        //System.out.println("Servicios:\nOUT:"+model.getClienteOUT()+"\nJUR: "+model.getClienteJUR()+"\nCON: "+model.getClienteCON()+"\nSEG: "+model.getClienteSEG()+"\nREX: "+model.getClienteREX());
        if(servicios==null)servicios="";
        if(!servicios.isEmpty())
        {
            int[] arr=dao.ca.parseCheckListValuesToIntegerArray(servicios);
            int n=arr.length;
            System.out.println("servicios SELECTED: "+n);
            for (int i=0;i<n;i++)
                if(a==6 || arr[i]==a)
                    switch(arr[i])
                    {
                        case 1: model.setClienteOUT(1);break;
                        case 2: model.setClienteJUR(1);break;
                        case 3: model.setClienteCON(1);break;
                        case 4: model.setClienteSEG(1);break;
                        case 5: model.setClienteREX(1);break;
                    }
        }
        System.out.println("SERVICIOS FROM ARRAY SET");
        //System.out.println("Servicios:\nOUT:"+model.getClienteOUT()+"\nJUR: "+model.getClienteJUR()+"\nCON: "+model.getClienteCON()+"\nSEG: "+model.getClienteSEG()+"\nREX: "+model.getClienteREX());
    }
    
    public void updateFormServicios()
    {
        //serviciosSetter();
        saveCliente(dao.getAREA()+"_"+logInBean.getUsuario().getNombre());
        getNewServicios();
    }
    
    public void getNewServicios()
    {
        if(!servicios.isEmpty())
            servicios2=dao.ca.parseCheckListValuesToIntegerArray(servicios);
        else servicios2=new int[1];
    }
    
    public void saveCliente(String p_Usuario)
    {
        System.out.println("saveCliente METHOD REACHED");
        if(nm||rs||rf||tp||ci||es||pa||cp||nt||se||st||rm||fc||fm||di||model.getId_Clientes()<1)
            if(logInBean.checkSession())
            {
                System.out.println("Current ID: "+model.getId_Clientes());
                //serviciosSetter();
                model.setZz_UsuarioModificacion(p_Usuario);
                if(model.getId_Clientes()>0)
                {
                    if(validateModification(p_Usuario))
                    {
                        System.out.println("cliente "+model.getId_Clientes()+" MODIFY ABOUT TO START");
                        model2=dao.modifyCliente(model);
                        model.setZz_FechaModificacion(model2.getZz_FechaModificacion());
                        nm=rs=rf=tp=di=ci=es=pa=cp=nt=se=st=rm=fm=fc=lo=cambios=false;
                    }
                    else if(se)
                    {
                        System.out.println("cliente "+model.getId_Clientes()+" SERVICES MODIFY ABOUT TO START");
                        model2=dao.updateServices(model);
                        model.setZz_FechaModificacion(model2.getZz_FechaModificacion());
                        setBooleansTo(false);
                    }
                    else
                        System.out.println("No modifications where allowed.");
                }
                else
                {
                    serviciosSetter();
                    model.setZz_UsuarioCreacion(p_Usuario);
                    model2=dao.newCliente(model);
                    model.setZz_FechaModificacion(model2.getZz_FechaModificacion());
                    setBooleansTo(false);
                    lo=false;
                }
            }
        System.out.println("saveCliente METHOD PERFORMED");
    }
    
    public String deleteCliente()
    {
        System.out.println("deleteCliente METHOD REACHED");
        if(logInBean.checkSession())
        {
            if(model.getId_Clientes()>0)
            {
                if(servicios==null)servicios="";
                System.out.println("SERVICIOS VALUE: "+servicios);
                if(!servicios.isEmpty() && !servicios.trim().equals(","))
                {
                    System.out.println("IT'S NOT EMPTY LAB");
                    String[] temp=servicios.split(",");
                    if(temp.length==1)
                    {
                        int a=dao.getAREA();
                        System.out.println("AREA: "+a);
                        if(logInBean.getUsuario().isDirectivo())
                            dao.deleteCliente(model2);
                        else if(a!=6)
                        {
                            if(logInBean.getUsuario().isGerente())
                                switch(Integer.parseInt(temp[0]))
                                {
                                    case 1:if(a==1)dao.deleteCliente(model2);break;
                                    case 2:if(a==2)dao.deleteCliente(model2);break;
                                    case 3://if(a==3)dao.deleteCliente(model2);
                                        break;
                                    case 4:if(a==4)dao.deleteCliente(model2);break;
                                    case 5:if(a==5)dao.deleteCliente(model2);break;
                                }
                        }
                    }
                }
                else
                {   System.out.println("IT'S EMPTY AF");
                    try{
                        String[] cu=model2.getZz_UsuarioCreacion().split("_");
                        if(logInBean.getUsuario().isDirectivo()||dao.getAREA()==Integer.parseInt(cu[0])&&(logInBean.getUsuario().isGerente() || cu[1].equals(logInBean.getUsuario().getNombre())))
                        {
                            System.out.println("THEY'RE THE SAME");
                            dao.deleteCliente(model2);
                        }
                    }catch(Exception e){System.out.println("CLIENTE PARSING EXCEPTION: "+e);}
                }
            }
            return "clientes";
        }
        return null;
    }
    
    public String logCardData(String p)
    {
        String[]arr=p.split("_");
        int n=arr.length;
        if(n>1)
        {
            for(int i=2;i<n;i++)
                arr[1]+=arr[i];
            return arr[1];
        }
        else return arr[0];
    }
    
    public void checkForChangesNombre(AjaxBehaviorEvent e){
        String s=e.getComponent().getId();
        System.out.println("AJAX EVENT COMPONENT ID: "+s);
        changesSwitch(s);
        evaluateBooleans(false);
        System.out.println("Cambios: "+cambios);
    }
    
    public void changesSwitch(String s)
    {
        switch(s)
        {
            case "NM":nm=!model.getNombre().equals(model2.getNombre()==null?"":model2.getNombre());System.out.println(nm);break;
            case "RS":rs=!model.getRazonSocial().equals(model2.getRazonSocial()==null?"":model2.getRazonSocial());break;
            case "RF":rf=!model.getRFC().equals(model2.getRFC()==null?"":model2.getRFC());break;
            case "TP":tp=!model.getTipoPersona().equals(model2.getTipoPersona()==null?"":model2.getTipoPersona());break;
            case "CI":ci=!model.getCiudad().equals(model2.getCiudad()==null?"":model2.getCiudad());break;
            case "ES":es=!model.getEstado().equals(model2.getEstado()==null?"":model2.getEstado());break;
            case "PA":pa=!model.getPais().equals(model2.getPais()==null?"":model2.getPais());break;
            case "CP":cp=!model.getDelegacion().equals(model2.getDelegacion()==null?"":model2.getDelegacion());break;
            case "NT":nt=!model.getDescripcion().equals(model2.getDescripcion()==null?"":model2.getDescripcion());break;
            case "DI":di=!model.getDireccion().equals(model2.getDireccion()==null?"":model2.getDireccion());break;
            case "RM":rm=!model.getRazonMigracion().equals(model2.getRazonMigracion()==null?"":model2.getRazonMigracion());break;
            case "FM":Date tempM=dao.ca.stringToDate(fechaMigracion,"/");model.setFechaMigracion(tempM);fm=tempM!=model2.getFechaMigracion();break;
            case "FC":Date tempC=dao.ca.stringToDate(fechaCliente,"/");model.setFechaCliente(tempC);fc=tempC!=model2.getFechaCliente();break;
            case "ST":int e1=model.getEstatus();st=e1!=model2.getEstatus();
                      if(e1<3)
                      {
                          model.setRazonMigracion(null);
                          model.setFechaMigracion(null);
                          fechaMigracion="";
                          if(e1<2)
                          {
                              model.setFechaCliente(null);
                              fechaCliente="";
                          }
                          else if(fechaCliente.isEmpty())
                          {
                              Date dC=new Date(new java.util.Date().getTime());
                              fechaCliente=dao.ca.parseDate(dC.toString(),"-");
                              model.setFechaCliente(dC);
                          }
                      }
                      else if(fechaCliente.isEmpty() || fechaMigracion.isEmpty())
                      {
                          Date d=new Date(new java.util.Date().getTime());
                          String tempD=dao.ca.parseDate(d.toString(),"-");
                          if(fechaCliente.isEmpty())
                          {
                              fechaCliente=tempD;
                              model.setFechaCliente(d);
                          }
                          if(fechaMigracion.isEmpty())
                          {
                            fechaMigracion=tempD;
                            model.setFechaMigracion(d);
                          }
                      }
                      fm=model.getFechaMigracion()!=model2.getFechaMigracion();
                      fc=model.getFechaCliente()!=model2.getFechaCliente();
                      rm=!(model.getRazonMigracion()+"").equals(model2.getRazonMigracion()+"");
                      System.out.println("ST1:"+e1+"\nST2:"+model2.getEstatus()+"\nRM: "+model.getRazonMigracion()+"\nFM: "+model.getFechaMigracion()+"\nFC: "+model.getFechaCliente());
                      break;
            case "SV":serviciosSetter();
                      boolean out=model.getClienteOUT()==model2.getClienteOUT(),
                      jur=model.getClienteJUR()==model2.getClienteJUR(),
                      con=model.getClienteCON()==model2.getClienteCON(),
                      seg=model.getClienteSEG()==model2.getClienteSEG(),
                      rex=model.getClienteREX()==model2.getClienteREX();
                      se=!(out&&seg&&con&&jur&&rex);break;
        }
    }
    
    public void evaluateBooleans(boolean p)
    {
        //Method created to be called from SEG.SeguimentosCVS bean
        cambios=nm||rs||rf||tp||ci||es||pa||cp||nt||se||st||rm||fc||fm||di||lo||p;
    }
    
    public void setBooleansTo(boolean p)
    {
        nm=rs=rf=tp=di=ci=es=pa=cp=nt=se=st=rm=fm=fc=lo=cambios=p;
    }
    
    private boolean validateModification(String p_Usuario)
    {
        int a=dao.getAREA();
        boolean f=false;
        if((a+"").equals(model2.getZz_UsuarioCreacion().split("_")[0]))
            if(a!=6||(a==6&&model2.getZz_UsuarioCreacion().equals(p_Usuario)))
                f=true;
        return f;
    }
    
    public void commitPortal(boolean tel)
    {
        System.out.println("commitPortal METHOD REACHED with: "+portalValues);
        String usu=dao.getAREA()+"_"+logInBean.getUsuario().getNombre();
        if(validateModification(usu))
        {
            String[] arr=dao.ca.commitPortal(portalValues);
            if(tel)
                commitTel(arr,usu);
            else
                commitMail(arr,usu);
        }
    }
    
    public void commitTel(String[] arr,String usu)
    {
        daoTelefonos dat=new daoTelefonos(logInBean.getFactory());
        if(arr[1].isEmpty())//id
        {
            System.out.println("Teléfono ID IS EMPTY");
            Telefonos t=new Telefonos();
            t.setCliente(model);
            t.setTipo(arr[2]);
            t.setLada(arr[3]);
            t.setNumero(arr[4]);
            t.setExtension(arr[5]);
            t.setNotas(arr[6]);
            t.setZz_UsuarioCreacion(usu);
            t.setZz_UsuarioModificacion(usu);
            portalValues=dat.newTelefono(t,model)+"";
        }
        else
        {
            System.out.println("Teléfono ID IS NOT EMPTY: "+arr[1]);
            Telefonos t=dat.getTelefono(Long.parseLong(arr[1]));
            t.setTipo(arr[2]);
            t.setLada(arr[3]);
            t.setNumero(arr[4]);
            t.setExtension(arr[5]);
            t.setNotas(arr[6]);
            t.setZz_UsuarioModificacion(usu);
            dat.modifyTelefono(t,model);
            portalValues=arr[1];
        }
    }
    
    public void commitMail(String[] arr,String usu)
    {
        daoCorreos dac=new daoCorreos(logInBean.getFactory());
        if(arr[1].isEmpty())//id
        {
            System.out.println("Correo ID IS EMPTY");
            Correos c=new Correos();
            c.setCliente(model);
            c.setTipo(arr[2]);
            c.setCorreo(arr[3]);
            c.setNotas(arr[4]);
            c.setZz_UsuarioCreacion(usu);
            c.setZz_UsuarioModificacion(usu);
            portalValues=dac.newCorreo(c,model)+"";
        }
        else
        {
            System.out.println("Correo ID IS NOT EMPTY: "+arr[1]);
            Correos c=dac.getCorreo(Long.parseLong(arr[1]));
            c.setTipo(arr[2]);
            c.setCorreo(arr[3]);
            c.setNotas(arr[4]);
            c.setZz_UsuarioModificacion(usu);
            dac.modifyCorreo(c,model);
            portalValues=arr[1];
        }
    }
    
    public void deleteFromPortal(boolean tel)
    {
        String usu=dao.getAREA()+"_"+logInBean.getUsuario().getNombre();
        if(validateModification(usu))
        {
            if(tel)
            {
                daoTelefonos dro=new daoTelefonos(logInBean.getFactory());
                Telefonos tmp=dro.getTelefono(Long.parseLong(portalValues));
                tmp.setZz_UsuarioModificacion(usu);
                dro.deleteTelefono(tmp,model);
            }
            else
            {
                daoCorreos dro=new daoCorreos(logInBean.getFactory());
                Correos tmp=dro.getCorreo(Long.parseLong(portalValues));
                tmp.setZz_UsuarioCreacion(usu);
                dro.deleteCorreo(tmp,model);
            }
        }
    }
    
    public void modFechaMod()
    {
        String u=model.getZz_UsuarioModificacion();
        System.out.println("modFechaMod() METHOD REACHED: "+u);
        if(validateModification(dao.getAREA()+"_"+logInBean.getUsuario().getNombre()))
        {
            if(model.getZz_UsuarioModificacion().equals("@@cameTroughPortal@@"))
            {
                u=dao.getAREA()+"_"+logInBean.getUsuario().getNombre();
                model2.setZz_FechaModificacion(dao.modifyModDate(model.getId_Clientes(),u));
                model2.setZz_UsuarioModificacion(u);
                model.setZz_FechaModificacion(model2.getZz_FechaModificacion());
                model.setZz_UsuarioModificacion(u);
            }
        }
        else
            model.setZz_UsuarioModificacion(model2.getZz_UsuarioModificacion());
        System.out.println("modFechaMod() METHOD DONE");
    }
    
    public String getLogoPath()
    {
        String path=FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(lo)
            if(model.getLogo()!=null)
                if(!model.getLogo().isEmpty())
                    return path+model.getLogo();
                else return path+"/resources/img/logos/logoPH.png";
            else return path+"/resources/img/logos/logoPH.png";
        return path+"/resources/img/logos/logoPH.png";
    }
    
    public void setLogoChange()
    {
        lo=cambios=true;
        System.out.println("Logo CHANGE HAS BEEN ACQUAINTED");
    }
    
    public void modificarLogo()
    {
        if(lo)
        {
            System.out.println("cliente LOGO ABOUT TO BE UPLOADED");
            String area=dao.ca.getAreaName(dao.getAREA(),logInBean.getUsuario().isDirectivo()),
                   path="/resources/img/logos/"+area,
                   ext=dao.ca.getFileExtension(Logo);
            area="cli"+model.getId_Clientes()+"_"+area+"."+ext;
            ext=null;
            if(dao.ca.uploadFileToServer(Logo,path,area))
            {
                model.setLogo(path+"/"+area);
                System.out.println("cliente LOGO "+area+" SUCCESSFULLY UPLOADED");
            }
            else System.out.println("cliente LOGO "+area+" FAILED TO BE UPLOADED");
        }
        else System.out.println("cliente HAS NOT BEEN MODIFIED");
    }
    
    public void initializeRelationship()
    {
        System.out.println("INITIALIZE RELATIONSHIP BY AREA");
        switch(dao.getAREA())
        {
            case 4: if(model.getSEG_CotizacionesLength()<1)
                    {
                        dao.initRelationshipsByArea(model,"SEG_Cotizaciones");
                        System.out.println("SEG_Cotizaciones INITIALIZED WITH: "+model.getSEG_CotizacionesLength());
                    }
                    break;
            case 2: if(getJUR_Cliente().getJUR_CasosLength()<1)
                    {
                        dao.initRelationshipsByArea(model,"JUR_Casos");
                        System.out.println("JUR_Casos INITIALIZED WITH: "+getJUR_Cliente().getJUR_CasosLength());
                    }
                    break;
        }
    }
    
}