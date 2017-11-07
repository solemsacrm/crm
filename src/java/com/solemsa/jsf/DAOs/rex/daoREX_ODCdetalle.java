package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_Materiales;
import com.solemsa.hibernate.entities.REX.REX_ODCdetalle;
import com.solemsa.hibernate.entities.REX.REX_ODCs;
import com.solemsa.hibernate.entities.REX.REX_Obras;
import com.solemsa.hibernate.entities.REX.REX_Proveedores;
import com.solemsa.jsf.data.CommonActions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class daoREX_ODCdetalle {
    
    private SessionFactory Factory;
    private Session Session;
    private CommonActions ca;
    
    public daoREX_ODCdetalle(SessionFactory Factory){
        try{
            this.Factory=Factory;
            System.out.println("FACTORY CONSTRUCTION");
            Session=this.Factory.getCurrentSession();
            System.out.println("SESSION CONSTRUCTION");
            ca=new CommonActions();
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public List loadREX_MaterialesList(String tipo)
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
        System.out.println("c.list() LIST: "+lista);
        Session.getTransaction().commit();
        System.out.println("SESSION SUCCESSFULY COMMITED");
        try{
            Session.close();
            System.out.println("CURRENT SESSION CLOSED");
        }catch(Exception e){System.out.println("SESSION WAS ALREDY CLOSED: "+e);}
        return lista;
    }
    
    public List<REX_ODCdetalle> getREX_ODCdetalleList()
    {
        List<REX_ODCdetalle> lista=null;
        try
        {			
            System.out.println("getREX_ODCdetalleList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            try{lista=Session.createCriteria(REX_ODCdetalle.class).list();}catch(Exception e){
                if(lista!=null)
                    lista=null;
                System.out.println("lista SET TO NULL: "+e);
            }
            System.out.println("QUERY RESULT: "+lista);
            System.out.println("get QUERY SUCCESSFUL: "+lista.size()+" items in list");
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
    
    public REX_ODCdetalle getREX_ODCdetalle(long id)
    {
        System.out.println("getREX_ODCdetalle METHOD REACHED");
        REX_ODCdetalle cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_ODCdetalle)Session.get(REX_ODCdetalle.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
            System.out.println("get TRANSACTION COMMITED");
            System.out.println("LIST LOAD SESSION CLOSED");
            try
            {
                Session.close();
                System.out.println("LIST LOAD SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        else
            cfg=new REX_ODCdetalle();
        return cfg;
    }
    
    public String newREX_ODCdetalle(REX_ODCs model,String[] arr)
    {
        System.out.println("newREX_ODCdetalle METHOD REACHED");
        REX_ODCdetalle ro=new REX_ODCdetalle();
        Exception ex=null;
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            ro.setREX_Material(new REX_Materiales());
            ro.getREX_Material().setId_REX_Material(Long.parseLong(arr[2]));
            ro.setMaterial(arr[3]);
            ro.setPrecio(arr[4].isEmpty()?0:Float.parseFloat(arr[4]));
            ro.setCantidad(arr[5].isEmpty()?0:Float.parseFloat(arr[5]));
            ro.setSubtotal(ro.getCantidad()*ro.getPrecio());
            ro.setZz_UsuarioCreacion(arr[6]);
            ro.setZz_UsuarioModificacion(ro.getZz_UsuarioCreacion());
            ro.setZz_FechaCreacion(ca.getCurrentTimestamp());
            ro.setZz_FechaModificacion(ro.getZz_FechaCreacion());
            model.setTotal(model.getTotal()+ro.getSubtotal());
            Session.beginTransaction();
            ro.setREX_OrdenDeCompra(updateOrdenDeCompra(model,ro));
            System.out.println("new TRANSACTION STARTED");
            Session.save(ro);
            System.out.println("new cliente SAVE SUCCESSFUL");
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
                System.out.println("new REX_ODCdetalle SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        if(ex!=null)
            return "materiales";
        return ro.getId_REX_OrdenDeCompraDetalle()+"";
    }
    
    public void deleteREX_ODCdetalle(REX_ODCs model,long id,String usuario)
    {
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            REX_ODCdetalle record=findDetalleInOrden(model,id);
            if(record.getId_REX_OrdenDeCompraDetalle()<1)
                record=getREX_ODCdetalle(id);
            float nT=record.getSubtotal();
            Session.delete(record);
            System.out.println("ODC detalle "+id+" SUCCESSFULLY DELETED");
            nT=model.getTotal()-nT;
            model.setTotal(nT<0?0:nT);
            model.setZz_FechaModificacion(ca.getCurrentTimestamp());
            model.setZz_UsuarioModificacion(usuario);
            if(model.getREX_Obra().getId_REX_Obra()<1)
                model.setREX_Obra(null);
            if(model.getREX_Proveedor().getId_REX_Proveedor()<1)
                model.setREX_Proveedor(null);
            Session.update(model);
            System.out.println("ODC "+model.getId_REX_OrdenDeCompra()+" SUCCESSFULLY UPDATED");
            Session.getTransaction().commit();
            if(model.getREX_Obra()==null)
                model.setREX_Obra(new REX_Obras());
            if(model.getREX_Proveedor()==null)
                model.setREX_Proveedor(new REX_Proveedores());
            System.out.println("delete TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("delete REX_ODCdetalle SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    private REX_ODCdetalle findDetalleInOrden(REX_ODCs model,long id)
    {
        REX_ODCdetalle ro=new REX_ODCdetalle();
            int n=model.getREX_ODCdetalle().size();
            for(int i=0;i<n&&id!=ro.getId_REX_OrdenDeCompraDetalle();i++)
                ro=model.getREX_ODCdetalle().get(i);
        return ro;
    }
    
    public void deleteMultipleREX_ODCdetalle(REX_ODCdetalle[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_ODCdetalle TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_ODCdetalle TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_ODCdetalle EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_ODCdetalle SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void modifyREX_ODCdetalle(REX_ODCs model,String[] arr){
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            // retrieve student based on the id: primary key
            long id=Long.parseLong(arr[1]);
            REX_ODCdetalle ro=findDetalleInOrden(model,id);
            ro.setId_REX_OrdenDeCompraDetalle(id);
            ro.setREX_Material(new REX_Materiales());
            ro.getREX_Material().setId_REX_Material(Long.parseLong(arr[2]));
            ro.setMaterial(arr[3]);
            ro.setPrecio(arr[4].isEmpty()?0:Float.parseFloat(arr[4]));
            ro.setCantidad(arr[5].isEmpty()?0:Float.parseFloat(arr[5]));
            float nT=ro.getCantidad()*ro.getPrecio(),pT=model.getTotal()-ro.getSubtotal();
            ro.setSubtotal(nT);
            ro.setZz_UsuarioModificacion(arr[6]);
            ro.setZz_FechaModificacion(ca.getCurrentTimestamp());
            model.setTotal((pT<0?0:pT)+nT);
            ro.setREX_OrdenDeCompra(updateOrdenDeCompra(model,ro));
            Session.update(ro);
            //target=setNewValues(source,null);
            System.out.println("material: "+arr[1]+" SUCCESSFULLY MODIFIED");
            // commit the transaction
            Session.getTransaction().commit();
            System.out.println("modify TRANSACTION COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("modify EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify REX_ODCdetalle SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
    }
    
    private REX_ODCs updateOrdenDeCompra(REX_ODCs model,REX_ODCdetalle ro)
    {
        model.setZz_FechaModificacion(ro.getZz_FechaModificacion()); 
        model.setZz_UsuarioModificacion(ro.getZz_UsuarioModificacion());
        model.getREX_ODCdetalle().add(ro);
        Session.update(model);
        System.out.println("ODC "+model.getId_REX_OrdenDeCompra()+" SUCCESSFULLY UPDATED");
        return model;
    }
    
    private REX_ODCdetalle setNewValues(REX_ODCdetalle source,REX_ODCdetalle target){
        if(target==null)
            target=(REX_ODCdetalle) Session.get(REX_ODCdetalle.class,source.getId_REX_OrdenDeCompraDetalle());
        target.setREX_Material(new REX_Materiales());
        System.out.println("id REX_OrdenDeCompra MODIFY SUCCESSFUL TO "+target.getREX_OrdenDeCompra().getId_REX_OrdenDeCompra());
        target.getREX_Material().setId_REX_Material(source.getREX_Material().getId_REX_Material());
        System.out.println("id REX_Material MODIFY SUCCESSFUL TO "+target.getREX_Material().getId_REX_Material());
        target.setMaterial(source.getMaterial()==null?null:source.getMaterial().trim());
        System.out.println("Material MODIFY SUCCESSFUL TO "+target.getMaterial());
        target.setCantidad(source.getCantidad());
        System.out.println("Cantidad MODIFY SUCCESSFUL TO "+target.getCantidad());
        target.setPrecio(source.getPrecio());
        System.out.println("Precio MODIFY SUCCESSFUL TO "+target.getPrecio());
        target.setSubtotal(source.getSubtotal());
        System.out.println("Subtotal MODIFY SUCCESSFUL TO "+target.getSubtotal());
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
