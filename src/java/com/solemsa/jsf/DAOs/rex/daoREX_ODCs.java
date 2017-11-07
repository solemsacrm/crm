package com.solemsa.jsf.DAOs.rex;

import com.solemsa.hibernate.entities.REX.REX_ODCdetalle;
import com.solemsa.hibernate.entities.REX.REX_ODCs;
import com.solemsa.hibernate.entities.REX.REX_Obras;
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

public class daoREX_ODCs {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    
    public daoREX_ODCs(SessionFactory Factory){
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
    
    public daoREX_ODCs(Session Session){
        try{
            this.Session=Session;
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
        }
    }
    
    public List loadREX_ProveedoresList(String tipo)
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(REX_Proveedores.class);
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
    
    public List loadREX_ObrasList()
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(REX_Obras.class);
        System.out.println("CRITERIA CREATED");
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
    
    public List loadREX_MaterialesList(REX_Proveedores proveedor)
    {
        Session=Factory.getCurrentSession();
        System.out.println("CURRENT SESSION GOTTEN");
        Session.beginTransaction();
        System.out.println("load TRANSACTION STARTED");
        Criteria c=Session.createCriteria(com.solemsa.hibernate.entities.REX.REX_Oferta.class,"of");
        System.out.println("CRITERIA CREATED");
        c.createAlias("of.REX_Material","ma");
        c.add(Restrictions.eq("of.REX_Proveedor",proveedor));
        System.out.println("CRITERIA SET");
        ProjectionList pl=Projections.projectionList();
        pl.add(Projections.property("ma.__id_REX_Material"));
        pl.add(Projections.property("ma.Nombre"));
        pl.add(Projections.property("of.Precio"));
        pl.add(Projections.property("ma.Unidad"));
        System.out.println("PROJECTION LIST SET");
        c.setProjection(pl);
        System.out.println("CRITERIA PROJECTION SET");
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

    public List<REX_ODCs> getREX_ODCsList()
    {
        List<REX_ODCs> lista=null;
        try
        {			
            System.out.println("getREX_ODCsList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            // start a transaction
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            try
            {
                lista=Session.createCriteria(REX_ODCs.class).list();
            }
            catch(Exception e)
            {
                if(lista!=null)
                    lista=null;
                System.out.println("lista SET TO NULL: "+e);
                e.printStackTrace();
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
            e.printStackTrace();
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
    
    public REX_ODCs getREX_ODC(long id)
    {
        System.out.println("getREX_ODC METHOD REACHED: "+id);
        REX_ODCs cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(REX_ODCs)Session.get(REX_ODCs.class,id);
            setModelDates(cfg,true);
            System.out.println("NEW LIST CONSTRUCTED");
            int n=cfg.getREX_ODCdetalle().size();
            System.out.println("LAZY FETCH LIST SETTED with "+n+" records");
            System.out.println("REQUESTED cliente FETCHED");
            Session.getTransaction().commit();
            cfg=initializeManyToOneRelationships(cfg);
            try
            {
                Session.close();
                System.out.println("get REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            System.out.println("LIST LOAD SESSION CLOSED");
        }
        else
        {
            cfg=new REX_ODCs();
            cfg.setREX_Proveedor(new REX_Proveedores());
            cfg.setREX_Obra(new REX_Obras());
            cfg.setREX_ODCdetalle(ca.newArrayList());
        }
        System.out.println("getREX_ODC METHOD SUCCESSFUL");
        return cfg;
    }
    
    private void setModelDates(REX_ODCs model,boolean toString)
    {
        com.solemsa.jsf.data.CommonActions ca=new com.solemsa.jsf.data.CommonActions();
        if(toString)
        {
            model.setFechaAutorizacionString(ca.parseDate(model.getFechaAutorizacion()!=null?model.getFechaAutorizacion().toString():null,"-"));
            model.setFechaCancelacionString(ca.parseDate(model.getFechaCancelacion()!=null?model.getFechaCancelacion().toString():null,"-"));
            model.setFechaEntregaString(ca.parseDate(model.getFechaEntrega()!=null?model.getFechaEntrega().toString():null,"-"));
            model.setFechaExpedicionString(ca.parseDate(model.getFechaExpedicion()!=null?model.getFechaExpedicion().toString():null,"-"));
        }
        else
        {
            model.setFechaAutorizacion(ca.stringToDate(model.getFechaAutorizacionString()!=null?model.getFechaAutorizacionString():"","/"));
            model.setFechaCancelacion(ca.stringToDate(model.getFechaCancelacionString()!=null?model.getFechaCancelacionString():"","/"));
            model.setFechaEntrega(ca.stringToDate(model.getFechaEntregaString()!=null?model.getFechaEntregaString():"","/"));
            model.setFechaExpedicion(ca.stringToDate(model.getFechaExpedicionString()!=null?model.getFechaExpedicionString():"","/"));
        }
    }
    
    private REX_ODCs initializeManyToOneRelationships(REX_ODCs cfg)
    {
        if(cfg.getREX_Obra()==null)
                cfg.setREX_Obra(new REX_Obras());
        if(cfg.getREX_Proveedor()==null)
            cfg.setREX_Proveedor(new REX_Proveedores());
        System.out.println("Obra: "+cfg.getREX_Obra()+" Proveedor: "+cfg.getREX_Proveedor());
        return cfg;
    }
    
    public REX_ODCs newREX_ODC(REX_ODCs model)
    {
        System.out.println("newREX_ODC METHOD REACHED");
        REX_ODCs target=new REX_ODCs();
        target.setREX_ODCdetalle(ca.newArrayList());
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Timestamp d=ca.getCurrentTimestamp();
            model.setZz_FechaCreacion(d);
            model.setZz_FechaModificacion(d);
            // start a transaction
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            // Saving ODC
            if(model.getREX_ODCdetalle().size()<1)
                model.setREX_ODCdetalle(null);
            if(model.getREX_Obra().getId_REX_Obra()<1)
                model.setREX_Obra(null);
            if(model.getREX_Proveedor().getId_REX_Proveedor()<1)
                model.setREX_Proveedor(null);
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.flush();
            System.out.println("new TRANSACTION COMMITED");
            Session.refresh(model);
            setModelDates(model,false);
            System.out.println("NEW LIST CONSTRUCTED");
            if(model.getREX_ODCdetalle()==null)
                model.setREX_ODCdetalle(ca.newArrayList());
            int n=model.getREX_ODCdetalle().size();
            System.out.println("LAZY FETCH LIST SETTED with "+n+" records");
            model=initializeManyToOneRelationships(model);
            target=setNewValues(model,target);
            target=initializeManyToOneRelationships(target);
            for(int i=0;i<n;i++)
            {
                REX_ODCdetalle tmp=model.getREX_ODCdetalle().get(i);
                target.getREX_ODCdetalle().add(tmp);
            }
            System.out.println("REFRESHING SUCCESSFUL");
        }
        catch(Exception e)
        {
            System.out.println("new EXCEPTION: "+e.toString());
            e.printStackTrace();
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("new REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return target;
    }
    
    public void deleteREX_ODC(REX_ODCs record)
    {
        try {				
            long id=record.getId_REX_OrdenDeCompra();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
            if(record.getREX_ODCdetalle().size()<1)
                record.setREX_ODCdetalle(null);
            if(record.getREX_Obra().getId_REX_Obra()<1)
                record.setREX_Obra(null);
            if(record.getREX_Proveedor().getId_REX_Proveedor()<1)
                record.setREX_Proveedor(null);
            Session.delete(record);
            System.out.println("ODC "+id+" SUCCESSFULLY DELETED");
            // commit the transaction
            Session.getTransaction().commit();
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
            System.out.println("delete REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleREX_ODCs(REX_ODCs[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple REX_ODC TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            //commit the transaction
            Session.getTransaction().commit();
            System.out.println("delete multiple REX_ODC TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple REX_ODC EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteAllFromODC(REX_ODCs model)
    {
        try{ 
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            int n=model.getREX_ODCdetalle().size();
            float sum=0;
            if(n<1)
                model.setREX_ODCdetalle(null);
            else
                for(int i=0;i<n;i++)
                {
                    REX_ODCdetalle tmp=model.getREX_ODCdetalle().get(i);
                    sum+=tmp.getSubtotal();
                    Session.delete(tmp);
                }
            System.out.println("detalle LIST DELETED FROM DATABASE with "+n+" elements");
            sum=model.getTotal()-sum;
            model.setTotal(sum<0?0:sum);
            if(model.getREX_Obra().getId_REX_Obra()<1)
                model.setREX_Obra(null);
            if(model.getREX_Proveedor().getId_REX_Proveedor()<1)
                model.setREX_Proveedor(null);
            Session.update(model);
            System.out.println("orden de compra UPDATED SUCCESSFULLY");
            model.setREX_ODCdetalle(ca.newArrayList());
            model.getREX_ODCdetalle().size();
            System.out.println("NEW detalle LIST INITIALIZED");
            Session.getTransaction().commit();
            initializeManyToOneRelationships(model);
        }
        catch(Exception e){
            System.out.println("delete detalle REX_ODC EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete detalle REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public REX_ODCs modifyREX_ODC(REX_ODCs source){
        REX_ODCs target=null;
        try {								
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            setModelDates(source,false);
            target=setNewValues(initializeManyToOneRelationships(source),null);
            System.out.println("orden "+target.getId_REX_OrdenDeCompra()+" SUCCESSFULLY MODIFIED");
            System.out.println("LAZY FETCH INITIALIZED WITH: "+target.getREX_ODCdetalle().size()+" ELEMENTS");
            // commit the transaction
            Session.getTransaction().commit();
            target=initializeManyToOneRelationships(target);
            System.out.println("modify TRANSACTION COMMITED");
            return target;
        }
        catch(Exception e){
            System.out.println("modify EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try
            {
                Session.close();
                System.out.println("modify REX_ODC SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target!=null?target:source;
    }
    
    private REX_ODCs setNewValues(REX_ODCs source,REX_ODCs target){
        if(target==null)
            target=(REX_ODCs)Session.get(REX_ODCs.class,source.getId_REX_OrdenDeCompra());
        System.out.println("modify "+target.getId_REX_OrdenDeCompra()+" SUCCESSFULLY RETRIEVED");
        if(source.getREX_Proveedor().getId_REX_Proveedor()>0)
        {
            target.setREX_Proveedor(new REX_Proveedores());
            target.getREX_Proveedor().setId_REX_Proveedor(source.getREX_Proveedor().getId_REX_Proveedor());
            System.out.println("id REX_Proveedor MODIFY SUCCESSFUL TO "+target.getREX_Proveedor().getId_REX_Proveedor());
        }
        else target.setREX_Proveedor(null);
        if(source.getREX_Obra().getId_REX_Obra()>0)
        {
            target.setREX_Obra(new REX_Obras());
            target.getREX_Obra().setId_REX_Obra(source.getREX_Obra().getId_REX_Obra());
            System.out.println("id REX_Obra MODIFY SUCCESSFUL TO "+target.getREX_Obra().getId_REX_Obra());
        }
        else target.setREX_Obra(null);
        target.setFechaExpedicion(source.getFechaExpedicion());
        System.out.println("Fecha Expedición MODIFY SUCCESSFUL TO "+target.getFechaExpedicion());
        target.setFechaEntrega(source.getFechaEntrega());
        System.out.println("Fecha Entrega MODIFY SUCCESSFUL TO "+target.getFechaEntrega());
        target.setFechaAutorizacion(source.getFechaAutorizacion());
        System.out.println("Fecha Autorización MODIFY SUCCESSFUL TO "+target.getFechaAutorizacion());
        target.setFechaCancelacion(source.getFechaCancelacion());
        System.out.println("Fecha Cancelación MODIFY SUCCESSFUL TO "+target.getFechaCancelacion());
        target.setTipo(source.getTipo()==null?null:source.getTipo().trim());
        System.out.println("Descripción MODIFY SUCCESSFUL TO "+target.getTipo());
        target.setNotas(source.getNotas()==null?null:source.getNotas().trim());
        System.out.println("Notas MODIFY SUCCESSFUL TO "+target.getNotas());
        target.setMoneda(source.getMoneda()==null?null:source.getMoneda().trim());
        System.out.println("Monto MODIFY SUCCESSFUL TO "+target.getMoneda());
        target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
        System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
        Timestamp d=ca.getCurrentTimestamp();
        target.setZz_FechaModificacion(d);
        //target.setREX_Avances(modifyAvancesList(source.getREX_Avances(),d,source.getZz_UsuarioModificacion()));
        System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
        return target;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
