package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Materiales;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.data.CommonActions;
import java.sql.Timestamp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoREX_Proveedores {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoREX_Proveedores(SessionFactory Factory){
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
    
    public List loadMaterialesList(String tipo)
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(REX_Materiales.class);
        System.out.println("CRITERIA CREATED");
        c.add(Restrictions.eq("Tipo",tipo));
        ProjectionList pl=Projections.projectionList();
        pl.add(Projections.id());
        pl.add(Projections.property("Nombre"));
        c.setProjection(pl);
        System.out.println("CRITERIA SET");
        List lista=c.list();
        Session.getTransaction().commit();
        System.out.println("SESSION SUCCESSFULY COMMITED");
        try{
            Session.close();
            System.out.println("CURRENT SESSION CLOSED");
        }catch(Exception e){System.out.println("SESSION WAS ALREDY CLOSED: "+e);}
        return lista;
    }
    
    public List<REX_Proveedores> getREX_ProveedoresList()
    {
        List<REX_Proveedores> lista=null;
        try
        {			
            System.out.println("getREX_ProveedoresList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            // query students
            String qry="FROM REX_Proveedores ORDER BY Nombre ASC";
            try
            {
                lista=Session.createQuery(qry).list();
                System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
            }
            catch(Exception e)
            {
                if(lista!=null)
                    lista=null;
                System.out.println("get REX_Proveedores List EXCEPTION: "+e);
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
    
    public REX_Proveedores getREX_Proveedor(long id)
    {
        System.out.println("getREX_Proveedor METHOD REACHED: "+id);
        REX_Proveedores cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_Proveedores)Session.get(REX_Proveedores.class,id);
            System.out.println("get TRANSACTION COMMITED");
            cfg.getREX_Oferta().size();//Forces lazy fetches to initialize LAB
            Session.getTransaction().commit();
            try
            {
                Session.close();
                System.out.println("get REX_Proveedor SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new REX_Proveedores();
            //cfg.set_Cliente(new Clientes());
        }
        return cfg;
    }
    
    public String newREX_Proveedor(REX_Proveedores model)
    {
        Exception ex=null;
        System.out.println("newREX_Proveedor METHOD REACHED");
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
            System.out.println("new cliente SAVE SUCCESSFUL");
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
            System.out.println("new REX_Proveedor SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "obras";
        return null;
    }
    
    public void deleteREX_Proveedor(REX_Proveedores record)
    {
        try {				
            long id=record.getId_REX_Proveedor();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            //REX_Proveedores target=(REX_Proveedores) Session.get(REX_Proveedores.class, id);
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
            System.out.println("delete REX_Proveedor SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_Proveedores(REX_Proveedores[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_Proveedor TRANSACTION STARTED");
            //delete student id
            //Session.createQuery("delete from Student where id=2").executeUpdate();
            //System.out.println("student "+id+" records SUCCESSFULLY DELETED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_Proveedor TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_Proveedor EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_Proveedor SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_Proveedores modifyREX_Proveedor(REX_Proveedores source){
        REX_Proveedores target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            target=setNewValues(source,null);
            System.out.println("obra"+target.getId_REX_Proveedor()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify REX_Proveedor SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_Proveedores setNewValues(REX_Proveedores source,REX_Proveedores target)
    {
        if(target==null)
            target=(REX_Proveedores) Session.get(REX_Proveedores.class,source.getId_REX_Proveedor());
        System.out.println("modify "+target.getId_REX_Proveedor()+" SUCCESSFULLY RETRIEVED");
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setRazonSocial(source.getRazonSocial()==null?null:source.getRazonSocial().trim());
        System.out.println("Razon Socual MODIFY SUCCESSFUL TO "+target.getRazonSocial());
        target.setDireccion1(source.getDireccion1()==null?null:source.getDireccion1().trim());
        System.out.println("Dirección 1 MODIFY SUCCESSFUL TO "+target.getDireccion1());
        target.setDireccion2(source.getDireccion2()==null?null:source.getDireccion2().trim());
        System.out.println("Dirección 2 MODIFY SUCCESSFUL TO "+target.getDireccion2());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setCiudad(source.getCiudad()==null?null:source.getCiudad().trim());
        System.out.println("Ciudad MODIFY SUCCESSFUL TO "+target.getCiudad());
        target.setEstado(source.getEstado()==null?null:source.getEstado().trim());
        System.out.println("Estado MODIFY SUCCESSFUL TO "+target.getEstado());
        target.setPais(source.getPais()==null?null:source.getPais().trim());
        System.out.println("Pais MODIFY SUCCESSFUL TO "+target.getPais());
        target.setTipo(source.getCodigoPostal()==null?null:source.getCodigoPostal().trim());
        System.out.println("Código Postal MODIFY SUCCESSFUL TO "+target.getCodigoPostal());
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
