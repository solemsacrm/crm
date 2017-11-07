package com.solemsa.jsf.DAOs;

import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.Correos;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class daoCorreos {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoCorreos(SessionFactory Factory){
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

    public Correos getCorreo(long id)
    {
        System.out.println("getConfig METHOD REACHED: "+id);
        Correos cfg=null;
        if(id>0)
        {
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(Correos)Session.get(Correos.class,id);
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
            cfg=new Correos();
        }
        System.out.println("getCorreo() METHOD ENDED");
        return cfg;
    }
    
    public long newCorreo(Correos model, Object module)
    {
        Exception ex=null;
        System.out.println("newCorreo METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Timestamp d=ca.getCurrentTimestamp();
            model.setZz_FechaCreacion(d);
            model.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new Correo TRANSACTION STARTED");
            // Saving Correo
            Session.save(model);
            System.out.println("new Correo SAVE SUCCESSFUL");
            //commit transaction
            Session.getTransaction().commit();
            setModuleProperties(module,model.getZz_UsuarioModificacion());
            System.out.println("new Correo TRANSACTION COMMITED: "+model.getId_Correo());
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
            System.out.println("new Correo SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return model.getId_Correo();
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
    
    public void deleteCorreo(Correos record,Object module)
    {
        try {				
            long id=record.getId_Correo();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            Session.delete(record);
            System.out.println("student "+id+" SUCCESSFULLY DELETED");
            // commit the transaction
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
            System.out.println("delete Correo SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public Correos modifyCorreo(Correos source,Object module){
        Correos target=null;
        try {							
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_Correo()+" SUCCESSFULLY MODIFIED");
            Session.update(target);
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
                System.out.println("modify Correo SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private Correos setNewValues(Correos source,Correos target)
    {
        if(target==null)
            target=(Correos) Session.get(Correos.class,source.getId_Correo());
        System.out.println("modify "+target.getId_Correo()+" SUCCESSFULLY RETRIEVED");
        target.setCorreo(source.getCorreo()==null?null:source.getCorreo().trim());
        System.out.println("Correo MODIFY SUCCESSFUL TO "+target.getCorreo());
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
