package com.solemsa.jsf.DAOs;

import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.Telefonos;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class daoTelefonos {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoTelefonos(SessionFactory Factory){
        try{
            this.Factory = Factory;
            System.out.println("daoConfig FACTORY CONSTRUCTION");
            Session = Factory.getCurrentSession();
            ca=new CommonActions();
            System.out.println("daoConfig SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("daoConfig FACTORY FAILED "+e);
        }
    }

    public Telefonos getTelefono(long id)
    {
        System.out.println("getConfig METHOD REACHED: "+id);
        Telefonos cfg=null;
        if(id>0)
        {
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(Telefonos)Session.get(Telefonos.class,id);
            System.out.println("REQUESTED config FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            try
            {
                Session.close();
                System.out.println("get Config SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new Telefonos();
        }
        System.out.println("getTelefono() METHOD ENDED");
        return cfg;
    }
    
    public long newTelefono(Telefonos model,Object module)
    {
        Exception ex=null;
        System.out.println("newTelefono METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Timestamp d=ca.getCurrentTimestamp();
            model.setZz_FechaCreacion(d);
            model.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new Telefono TRANSACTION STARTED");
            // Saving Telefono
            Session.save(model);
            System.out.println("new Telefono SAVE SUCCESSFUL");
            //commit transaction
            Session.getTransaction().commit();
            setModuleProperties(module,model.getZz_UsuarioModificacion());
            System.out.println("new Telefono TRANSACTION COMMITED: "+model.getId_Telefono());
        }
        catch(Exception e)
        {
            ex=e;
            System.out.println("new EXCEPTION: "+e.toString());
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("new Telefono SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return model.getId_Telefono();
    }
    
    private void setModuleProperties(Object module,String usu)
    {
        Session=Factory.getCurrentSession();
        Session.beginTransaction();
        String c[]=module.getClass().toString().split("[.]");
        System.out.println("setModuleProperties METHOD REACHED: "+c[c.length-1]);
        switch(c[c.length-1])
        {
            case "Clientes":Clientes tmp=(Clientes)module;
                            tmp.setZz_UsuarioModificacion(usu);
                            tmp.setZz_FechaModificacion(ca.getCurrentTimestamp());
                            Session.update(tmp);
                            javax.faces.context.FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("logCard");
                            break;
        }
        Session.getTransaction().commit();
    }
    
    public void deleteTelefono(Telefonos record,Object module)
    {
        try {				
            long id=record.getId_Telefono();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            Session.delete(record);
            System.out.println("student "+id+" SUCCESSFULLY DELETED");
            Session.getTransaction().commit();
            setModuleProperties(module,record.getZz_UsuarioModificacion());
            System.out.println("delete TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete Telefono SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public Telefonos modifyTelefono(Telefonos source,Object module){
        Telefonos target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_Telefono()+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
            setModuleProperties(module,source.getZz_UsuarioModificacion());
            System.out.println("modify TRANSACTION COMMITED");
            return target;
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify Telefono SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private Telefonos setNewValues(Telefonos source,Telefonos target)
    {
        if(target==null)
            target=(Telefonos) Session.get(Telefonos.class,source.getId_Telefono());
        System.out.println("modify "+target.getId_Telefono()+" SUCCESSFULLY RETRIEVED");
        target.setNumero(source.getNumero()==null?null:source.getNumero().trim());
        System.out.println("Numero MODIFY SUCCESSFUL TO "+target.getNumero());
        target.setLada(source.getLada()==null?null:source.getLada().trim());
        System.out.println("Razon Socual MODIFY SUCCESSFUL TO "+target.getLada());
        target.setExtension(source.getExtension()==null?null:source.getExtension().trim());
        System.out.println("Extensión MODIFY SUCCESSFUL TO "+target.getExtension());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setTipo(source.getTipo()==null?null:source.getTipo().trim());
        System.out.println("Tipo MODIFY SUCCESSFUL TO "+target.getTipo());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        target.setZz_FechaModificacion(ca.getCurrentTimestamp());
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
