package com.solemsa.jsf.DAOs;

import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.JUR.JUR_Clientes;
import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import com.solemsa.hibernate.entities.SEG.SEG_Cotizaciones;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

public class daoClientes {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    private int AREA;
    
    public daoClientes(SessionFactory Factory,int area){
        try
        {
            this.Factory = Factory;
            System.out.println("daoClientesMSC FACTORY CONSTRUCTION");
            ca=new CommonActions();
            //Session = this.Factory.getCurrentSession();
            AREA=area;
        }
        catch(Exception e)
        {
            System.out.println("daoClientesMSC FACTORY FAILED "+e);
            e.printStackTrace();
        }
    }

    public int getAREA() {
        return AREA;
    }
    
    
    
    public ClientesMSC getCliente(long id)
    {
        System.out.println("getCliente METHOD REACHED: "+id);
        ClientesMSC cfg=null;
        if(id>0)
        {
            try{
                System.out.println("ID RECEIVED: "+id);
                Session=Factory.getCurrentSession();
                System.out.println("CURRENT SESSION GOTTEN");
                Session.beginTransaction();
                System.out.println("get TRANSACTION STARTED");
                switch(AREA)
                {
                    case 2:cfg=(JUR_Clientes)Session.get(JUR_Clientes.class, id);break;
                    default:cfg=(Clientes)Session.get(Clientes.class, id);break;
                }
                System.out.println("This record has "+cfg.getTelefonos().size()+" related Teléfonos");//Forces lazy fetches to initialize LAB
                System.out.println("This record has "+cfg.getCorreos().size()+" related Correos");//Forces lazy fetches to initialize LAB
                System.out.println("REQUESTED cliente FETCHED");
                Session.getTransaction().commit();
                System.out.println("get TRANSACTION COMMITED");
            }
            catch(Exception e)
            {
                cfg=null;
                System.out.println("getCliente METHOD EXCEPTION: "+e);
                e.printStackTrace();
            }
            finally
            {
                if(Session.isOpen())
                {
                    Session.close();
                    System.out.println("get Cliente SESSION CLOSED");
                }
            }            
        }
        else
        {
            switch(AREA)
            {
                case 2:cfg=(JUR_Clientes)new JUR_Clientes();break;
                default:cfg=new Clientes();break;
            }
            cfg.setTelefonos(ca.newArrayList());
            cfg.setCorreos(ca.newArrayList());
            System.out.println("ID-MAAAAAAAAAAAN: "+cfg.getId_Clientes());
        }
        return cfg;
    }
    
    public void initRelationshipsByArea(ClientesMSC model,String entity)
    {
        System.out.println("initRelationshipsByArea METHOD REACHED");
        try{
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("ABOUT TO GET INTO SWITCH");
            initRelationshipByEntity(model,entity);
            Session.getTransaction().commit();
        }catch(Exception e)
        {
            System.out.println("initRelationshipsByArea METHOD EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("initRelationshipsByArea METHOD PERFORMED");
    }
    
    private void initRelationshipByEntity(ClientesMSC model,String entity)
    {
        int n;
        switch(entity)
        {
            case "JUR_Casos":JUR_Clientes tmpJUR=(JUR_Clientes)Session.load(JUR_Clientes.class,model.getId_Clientes()),modelJUR=(JUR_Clientes)model;
                             n=tmpJUR.getJUR_Casos().size();
                             System.out.println("JUR_Casos LIST INITIALIZED with: "+n);
                             modelJUR.setJUR_Casos(tmpJUR.getJUR_Casos());
                             modelJUR.setJUR_CasosLength(n);
                             System.out.println("JUR_Casos LIST fechaString SET");
                             break;
            case "SEG_Cotizaciones":Clientes tmp=(Clientes)Session.load(Clientes.class,model.getId_Clientes());
                             n=tmp.getSEG_Cotizaciones().size();
                             System.out.println("SEG_Cotizacions LIST INITIALIZED with: "+n);
                             for(int i=0;i<n;i++)
                             {
                                 SEG_Cotizaciones tmp2=tmp.getSEG_Cotizaciones().get(i);
                                 tmp2.setFechaString(ca.parseDate(tmp2.getFecha().toString(),"-"));
                             }
                             model.setSEG_Cotizaciones(tmp.getSEG_Cotizaciones());
                             model.setSEG_CotizacionesLength(n);
                             System.out.println("SEG_Cotizacions LIST fechaString SET");
                             break;
        }
    }

    public List<ClientesMSC> getClientesList(int area,int jur,boolean f)
    {
        List<ClientesMSC> lista=null;
        try
        {			
            System.out.println("getClientesMSCList METHOD REACHED");
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            String sort;
            switch(area)
            {
                case 1:sort="ClienteOUT";break;
                case 2:sort="ClienteJUR";break;
                case 3:sort="ClienteCON";break;
                case 4:sort="ClienteSEG";break;
                case 5:sort="ClienteREX";break;
                case 6:sort="__id_Clientes";break;
                default: if(f)sort="__id_Clientes";else sort="";break;
            }
            System.out.println("SORT VARIABLE: "+sort);
            lista=Session.createCriteria(ClientesMSC.class).addOrder(Order.desc(sort)).addOrder(Order.asc("Nombre")).list();
            int n=lista.size();
            System.out.println("get QUERY SUCCESSFUL: "+n+" items in list");
            Session.getTransaction().commit();
            if(area==2||jur>0)
            {
                //no sé por qué el área legal duplica la lista de clientes :'v
                n/=2;
                for(int i=0;i<n;i++)
                    lista.remove(0);
            }
        }
        catch(Exception e)
        {
            System.out.println("getClientesList EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return lista;
    }
    
    public ClientesMSC newCliente(ClientesMSC cliente)
    {
        System.out.println("newCliente METHOD REACHED");
        try{
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            java.util.Date date= new java.util.Date();
            Timestamp d=new Timestamp(date.getTime());
            cliente.setZz_FechaCreacion(d);
            cliente.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            // Saving Student
            Session.save(cliente);
            System.out.println("new cliente SAVE SUCCESSFUL");
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("new TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("new EXCEPTION: "+e.toString());
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return cliente;
    }
    
    public void deleteCliente(ClientesMSC record)
    {
        try {								
            // now get a new session and start transaction
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            //ClientesMSC target = (ClientesMSC) Session.get(ClientesMSC.class, record);
            //System.out.println("student "+record+" SUCCESSFULLY RETRIEVED");
            // delete the student
            Session.delete(record);
            System.out.println("student "+record+" SUCCESSFULLY DELETED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete EXCEPTION: "+e.toString());
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete Cliente SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleClientes(ClientesMSC[] records)
    {
        try{
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple Cliente TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+records+" records SUCCESSFULLY DELETED"); 
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple Cliente TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple Cliente EXCEPTION: "+e.toString());
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple Cliente SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public ClientesMSC modifyCliente(ClientesMSC source){
        ClientesMSC target=null;
        try {								
            // now get a new session and start transaction
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            switch(AREA)
            {
                case 2:target=(JUR_Clientes)Session.get(JUR_Clientes.class, source.getId_Clientes());break;
                default:target=(Clientes)Session.get(Clientes.class, source.getId_Clientes());break;
            }
            System.out.println("modify "+target.getId_Clientes()+" SUCCESSFULLY RETRIEVED");
            target.setNombre(source.getNombre().trim()+"");
            //System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
            target.setRazonSocial(source.getRazonSocial().trim()+"");
            //System.out.println("Razon Social MODIFY SUCCESSFUL TO "+target.getRazonSocial());
            target.setRFC(source.getRFC().trim()+"");
            //System.out.println("RFC MODIFY SUCCESSFUL TO "+target.getRFC());
            target.setTipoPersona(source.getTipoPersona().trim()+"");
            //System.out.println("Tipo Persona MODIFY SUCCESSFUL TO "+target.getTipoPersona());
            target.setDescripcion(source.getDescripcion().trim()+"");
            //System.out.println("Descripción MODIFY SUCCESSFUL TO "+target.getDescripcion());
            target.setCiudad(source.getCiudad().trim()+"");
            //System.out.println("Ciudad MODIFY SUCCESSFUL TO "+target.getCiudad());
            target.setEstado(source.getEstado().trim()+"");
            //System.out.println("Estado MODIFY SUCCESSFUL TO "+target.getEstado());
            target.setDelegacion(source.getDelegacion().trim()+"");
            //System.out.println("Delegacion MODIFY SUCCESSFUL TO "+target.getDelegacion());
            target.setPais(source.getPais().trim()+"");
            //System.out.println("Pais MODIFY SUCCESSFUL TO "+target.getPais());
            target.setDireccion(source.getDireccion().trim()+"");
            //System.out.println("Dirección MODIFY SUCCESSFUL TO "+target.getDireccion());
            target.setClienteOUT(source.getClienteOUT());
            //System.out.println("Cliente OUT MODIFY SUCCESSFUL TO "+target.getClienteOUT());
            target.setClienteSEG(source.getClienteSEG());
            //System.out.println("Cliente SEF MODIFY SUCCESSFUL TO "+target.getClienteSEG());
            target.setClienteCON(source.getClienteCON());
            //System.out.println("Cliente CON MODIFY SUCCESSFUL TO "+target.getClienteCON());
            target.setClienteJUR(source.getClienteJUR());
            //System.out.println("Cliente JUR MODIFY SUCCESSFUL TO "+target.getClienteJUR());
            target.setClienteREX(source.getClienteREX());
            //System.out.println("Cliente REX MODIFY SUCCESSFUL TO "+target.getClienteREX());
            target.setEstatus(source.getEstatus());
            //System.out.println("Estatus SUCCESSFUL TO "+target.getEstatus());
            target.setRazonMigracion(source.getRazonMigracion());
            //System.out.println("Razón Migración SUCCESSFUL TO "+target.getRazonMigracion());
            target.setFechaCliente(source.getFechaCliente());
            //System.out.println("Fecha Cliente SUCCESSFUL TO "+target.getFechaCliente());
            target.setFechaMigracion(source.getFechaMigracion());
            target.setLogo(source.getLogo());
            //System.out.println("Fecha Migración SUCCESSFUL TO "+target.getFechaMigracion());
            target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
            //System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
            java.util.Date date= new java.util.Date();
            Timestamp d=new Timestamp(date.getTime());
            target.setZz_FechaModificacion(d);
            //System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
            System.out.println("cliente "+target.getId_Clientes()+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify Cliente SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return target!=null?target:null;
    }
    
    public ClientesMSC updateServices(ClientesMSC source)
    {
        System.out.println("updateServices DAO METHOD REACHED");
        ClientesMSC target=null;
        try
        {
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            switch(AREA)
            {
                case 2:target=(JUR_Clientes)Session.get(JUR_Clientes.class, source.getId_Clientes());break;
                default:target=(Clientes)Session.get(Clientes.class, source.getId_Clientes());break;
            }
            System.out.println("TARGET cliente FETCHED");
            target.setClienteOUT(source.getClienteOUT());
            System.out.println("Cliente OUT MODIFY SUCCESSFUL TO "+target.getClienteOUT());
            target.setClienteSEG(source.getClienteSEG());
            System.out.println("Cliente SEF MODIFY SUCCESSFUL TO "+target.getClienteSEG());
            target.setClienteCON(source.getClienteCON());
            System.out.println("Cliente CON MODIFY SUCCESSFUL TO "+target.getClienteCON());
            target.setClienteJUR(source.getClienteJUR());
            System.out.println("Cliente JUR MODIFY SUCCESSFUL TO "+target.getClienteJUR());
            target.setClienteREX(source.getClienteREX());
            System.out.println("Cliente REX MODIFY SUCCESSFUL TO "+target.getClienteREX());
            target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
            System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
            java.util.Date date= new java.util.Date();
            Timestamp d=new Timestamp(date.getTime());
            target.setZz_FechaModificacion(d);
            System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
            System.out.println("cliente "+target.getId_Clientes()+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify Cliente SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return target;
    }
    
    public Timestamp modifyModDate(long id,String user){
        System.out.println("modify Cliente Date METHOD REACHED");
        Timestamp d=null;
        ClientesMSC model=null;
        try{
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify Cliente Date SESSION BEGUN");
            model = (ClientesMSC) Session.get(ClientesMSC.class,id);
            model.setZz_UsuarioModificacion(user);
            java.util.Date date= new java.util.Date();
            d=new Timestamp(date.getTime());
            model.setZz_FechaModificacion(d);
            Session.getTransaction().commit();
             System.out.println("modify Cliente Date SESSION COMMITED");
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify Cliente SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return d;
    }
    
    public ClientesMSC cloneModel(ClientesMSC source)
    {
        ClientesMSC target=modifyCliente(source);
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
