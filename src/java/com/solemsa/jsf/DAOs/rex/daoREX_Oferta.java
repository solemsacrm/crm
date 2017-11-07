package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Oferta;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class daoREX_Oferta {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoREX_Oferta(SessionFactory Factory){
        try{
            this.Factory=Factory;
            System.out.println("FACTORY CONSTRUCTION");
            
            Session=this.Factory.getCurrentSession();
            ca=new CommonActions();
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }

    public List<REX_Oferta> getREX_OfertaList()
    {
        List<REX_Oferta> lista=null;
        try
        {			
            System.out.println("getREX_OfertaList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            String qry="FROM REX_Oferta ORDER BY Nombre ASC";
            try
            {
                lista=Session.createQuery(qry).list();
                System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
            }
            catch(Exception e)
            {
                if(lista!=null)
                    lista=null;
                System.out.println("get REX_Oferta List EXCEPTION: "+e);
                System.out.println("lista SET TO NULL");
            }
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("EXCEPCIÓN DE CONSULTA: "+e.toString());
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("LIST LOAD SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return lista;
    }
    
    public REX_Oferta getREX_Oferta(long id)
    {
        System.out.println("getREX_Oferta METHOD REACHED: "+id);
        REX_Oferta cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_Oferta)Session.get(REX_Oferta.class,id);
            System.out.println("REQUESTED oferta FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            if(cfg.getREX_Proveedor()==null)
                cfg.setREX_Proveedor(new REX_Proveedores());
            try
            {
                Session.close();
                System.out.println("get REX_Oferta SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new REX_Oferta();
            cfg.setREX_Proveedor(new REX_Proveedores());
        }
        return cfg;
    }
    
    public String newREX_Oferta(REX_Oferta model)
    {
        Exception ex=null;
        System.out.println("newREX_Oferta METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Timestamp d=ca.getCurrentTimestamp();
            model.setZz_FechaCreacion(d);
            model.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            // Saving Student
            Session.save(model);
            System.out.println("new oferta SAVE SUCCESSFUL");
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("new TRANSACTION COMMITED");
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
            System.out.println("new REX_Oferta SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "obras";
        return model.getId_REX_Ofertas()+"";
    }
    
    public void deleteREX_Oferta(REX_Oferta record)
    {
        try {				
            long id=record.getId_REX_Ofertas();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            //REX_Oferta target=(REX_Oferta) Session.get(REX_Oferta.class, id);
            //System.out.println("student "+id+" SUCCESSFULLY RETRIEVED");
            // delete the student
            Session.delete(record);
            System.out.println("student "+id+" SUCCESSFULLY DELETED");
            // commit the transaction
            Session.getTransaction().commit();
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
            System.out.println("delete REX_Oferta SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Oferta(REX_Oferta[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Oferta TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+id+" records SUCCESSFULLY DELETED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Oferta TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Oferta EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Oferta SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Oferta modifyREX_Oferta(REX_Oferta source){
        REX_Oferta target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_REX_Ofertas()+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
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
                System.out.println("modify REX_Oferta SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_Oferta setNewValues(REX_Oferta source,REX_Oferta target){
        if(target==null)
            target=(REX_Oferta) Session.get(REX_Oferta.class,source.getId_REX_Ofertas());
        target.setREX_Proveedor(new REX_Proveedores());
        System.out.println("modify "+target.getId_REX_Ofertas()+" SUCCESSFULLY RETRIEVED");
        target.getREX_Proveedor().setId_REX_Proveedor(source.getREX_Proveedor().getId_REX_Proveedor());
        System.out.println("id REX_Proveedor MODIFY SUCCESSFUL TO "+target.getREX_Proveedor().getId_REX_Proveedor());
        target.setPrecio(source.getPrecio());
        System.out.println("Precio MODIFY SUCCESSFUL TO "+target.getPrecio());
        target.setMoneda(source.getMoneda()==null?null:source.getMoneda().trim());
        System.out.println("Precio MODIFY SUCCESSFUL TO "+target.getMoneda());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        Timestamp d=ca.getCurrentTimestamp();
        target.setZz_FechaModificacion(d);
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
