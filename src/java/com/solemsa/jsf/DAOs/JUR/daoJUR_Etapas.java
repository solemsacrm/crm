package com.solemsa.jsf.DAOs.JUR;

import com.solemsa.hibernate.entities.Clientes;
import com.solemsa.hibernate.entities.JUR.JUR_Etapas;
import com.solemsa.hibernate.entities.JUR.JUR_Casos;
import com.solemsa.hibernate.entities.JUR.JUR_EtapaDetalle;
import com.solemsa.hibernate.entities.JUR.JUR_Fechas;
import com.solemsa.hibernate.entities.JUR.JUR_NotificacionAlcance;
import com.solemsa.hibernate.entities.JUR.JUR_Notificaciones;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import com.solemsa.jsf.data.CommonActions;
import com.solemsa.jsf.data.JUR.JUR_EtapasOrden;
import static com.solemsa.jsf.data.JUR.JUR_EtapasOrden.setDefaultPruebasDetalle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class daoJUR_Etapas {
    
    private SessionFactory Factory;
    private Session Session;
    public CommonActions ca;
    public JUR_RecursosHumanos RecursosHumanos[];
    
    public daoJUR_Etapas(SessionFactory Factory){
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
    
    public JUR_Casos loadJUR_Caso(long id)
    {
        JUR_Casos caso=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            caso=(JUR_Casos)Session.load(JUR_Casos.class,id);
            if(caso==null)
                caso=(JUR_Casos)Session.get(JUR_Casos.class,id);
            if(caso!=null)
                caso.setJUR_EtapasLength(caso.getJUR_Etapas().size());
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_Caso EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return caso;
    }
    
    public void loadJUR_EtapaDetalle(JUR_Etapas node)
    {
        System.out.println("loadJUR_EtapaDetalle METHOD REACHED WITH JUR_CASO: "+node.getJUR_Caso().getId_JUR_Caso());
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            if(node.getId_JUR_Etapa()>0)
            {
                JUR_Etapas tmp=(JUR_Etapas)Session.load(JUR_Etapas.class,node.getId_JUR_Etapa());
                int n=tmp.getJUR_EtapaDetalle().size();
                System.out.println("JUR_EtapaDetalle() LIST INITIALIZED with: "+n);
                for(int i=0;i<n;i++)
                {
                    JUR_EtapaDetalle tmp2=tmp.getJUR_EtapaDetalle().get(i);
                    if(tmp2.getFecha()!=null)
                        tmp2.setFechaString(ca.parseDate(tmp2.getFecha().toString(),"-"));
                }
                node.setJUR_EtapaDetalle(tmp.getJUR_EtapaDetalle());
                node.setJUR_EtapaDetalleLength(n);
            }
            else
            {
                node.setJUR_EtapaDetalle((List<JUR_EtapaDetalle>)ca.newArrayList());
                node.setJUR_EtapaDetalleLength(node.getJUR_EtapaDetalle().size());
            }
            System.out.println("JUR_EtapaDetalle() LIST fechaString SET");
            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_EtapaDetalle METHOD ECEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("loadJUR_EtapaDetalle METHOD PERFORMED");
    }

    public void loadJUR_Expediente(JUR_EtapaDetalle node)
    {
        try
        {
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("load TRANSACTION STARTED");
            JUR_EtapaDetalle tmp=(JUR_EtapaDetalle)Session.load(Clientes.class,node.getId_JUR_EtapaDetalle());
            int n=tmp.getJUR_Expedientes().size();
            System.out.println("JUR_Expedientes LIST INITIALIZED with: "+n);
            node.setJUR_Expedientes(tmp.getJUR_Expedientes());
            node.setJUR_ExpedientesLength(n);
            System.out.println("JUR_Expedientes LIST fechaString SET");

            Session.getTransaction().commit();
            System.out.println("SESSION SUCCESSFULY COMMITED");
        }
        catch(Exception e)
        {
            System.out.println("loadJUR_Expediente METHOD ECEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            try{
                Session.close();
                System.out.println("CURRENT SESSION CLOSED");
            }catch(Exception e){
                System.out.println("SESSION WAS ALREDY CLOSED: "+e);
                e.printStackTrace();
            }
        }
    }
    
    public List<JUR_Etapas> getJUR_EtapasList(JUR_Casos caso)
    {
        List<JUR_Etapas> lista=null;
        try
        {			
            System.out.println("getJUR_EtapaDetalleList METHOD REACHED");
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            if(caso==null)
                System.out.println("NONSENSE PRINCE!");
            else
                System.out.println("PLUS ULTRA!");
            try{
                lista=Session.createCriteria(JUR_Etapas.class).add(Restrictions.eq("JUR_Caso",caso)).addOrder(Order.desc("__id_JUR_Etapa")).list();
            }catch(Exception e){
                if(lista==null)
                    lista=ca.newArrayList();
                System.out.println("lista SET TO NULL: "+e);
            }
            caso.setJUR_EtapasLength(lista.size());
            if(caso.getJUR_EtapasLength()<1)
            {
                lista.add(new JUR_Etapas());
                caso.setJUR_EtapasLength(lista.size());
            }
            System.out.println("get QUERY SUCCESSFUL: "+caso+" items in list");
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
            if(Session.isOpen())
                Session.close();
        }
        return lista;
    }
    
    public JUR_Etapas getJUR_Etapa(long id)
    {
        System.out.println("getJUR_Etapa METHOD REACHED");
        JUR_Etapas cfg;
        if(id>0)
        {
            System.out.println("ID RECEIVED: "+id);
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            cfg=(JUR_Etapas)Session.get(JUR_Etapas.class,id);
            System.out.println("REQUESTED cliente FETCHED");
            if(cfg.getJUR_EtapaDetalle()==null)
                cfg.setJUR_EtapaDetalle(ca.newArrayList());
            int n=cfg.getJUR_EtapaDetalle().size();
            System.out.println("JUR_Etapas LIST INITIALIZED WITH: "+n);
            Session.getTransaction().commit();
            if(cfg.getJUR_Caso()==null)
                cfg.setJUR_Caso(new JUR_Casos());
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
        {
            cfg=new JUR_Etapas();            
            cfg.setJUR_Caso(new JUR_Casos());
            cfg.setJUR_EtapaDetalle(ca.newArrayList());
        }
        return cfg;
    }
    
    public JUR_Etapas newJUR_Etapa(JUR_Etapas model)
    {
        System.out.println("newJUR_Etapa METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            model.setZz_FechaCreacion(ca.getCurrentTimestamp());
            model.setZz_FechaModificacion(model.getZz_FechaCreacion());
            if(model.getJUR_Caso().getId_JUR_Caso()<1)
                model.setJUR_Caso(null);
            Session.beginTransaction();
            System.out.println("new TRANSACTION STARTED");
            Session.save(model);
            System.out.println("new cliente SAVE SUCCESSFUL");
            Session.getTransaction().commit();
            if(model.getJUR_Caso()==null)
                model.setJUR_Caso(new JUR_Casos());
            System.out.println("new TRANSACTION COMMITED");
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
                System.out.println("new JUR_Etapa SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return cloneModel(model);
    }
    
    public void deleteJUR_Etapa(JUR_Etapas record)
    {
        try {				
            long id=record.getId_JUR_Etapa();
            // now get a new session and start transaction
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete TRANSACTION STARTED");
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
            System.out.println("delete JUR_Etapa SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public void deleteMultipleJUR_Etapas(JUR_Etapas[] records)
    {
        try{
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("delete multiple JUR_Etapa TRANSACTION STARTED");
            int n=records.length;
            for(int i=0;i<n;i++)
                Session.delete(records[i]);
            Session.getTransaction().commit();
            System.out.println("delete multiple JUR_Etapa TRANSACTION COMMITED");
        }
        catch(Exception e){
            System.out.println("delete multiple JUR_Etapa EXCEPTION: "+e);
        }
        finally
        {
            try
            {
            Session.close();
            System.out.println("delete multiple JUR_Etapa SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
    }
    
    public JUR_Etapas modifyJUR_Etapa(JUR_Etapas source){
        JUR_Etapas target=null;
        try {						
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            if(source.getJUR_Caso().getId_JUR_Caso()<1)
                source.setJUR_Caso(null);
            target=setNewValues(source,new JUR_Etapas());
            if(target.getJUR_Caso()==null)
                target.setJUR_Caso(new JUR_Casos());
            System.out.println("material"+target.getId_JUR_Etapa()+" SUCCESSFULLY MODIFIED");
            Session.update(source);
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
                System.out.println("modify JUR_Etapa SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
            
        }
        return target;
    }
    
    private JUR_Etapas setNewValues(JUR_Etapas source,JUR_Etapas target){
        if(target==null)
            target=(JUR_Etapas) Session.get(JUR_Etapas.class,source.getId_JUR_Etapa());
        else if(target.getId_JUR_Etapa()<1)
        {
            target.setZz_FechaCreacion(source.getZz_FechaCreacion());
            target.setZz_UsuarioCreacion(source.getZz_UsuarioCreacion());
        }
        target.setJUR_Caso(source.getJUR_Caso());
        System.out.println("JUR_Caso MODIFY SUCCESSFUL TO "+target.getJUR_Caso().getId_JUR_Caso());
        target.setNombre(source.getNombre()==null?null:source.getNombre().trim());
        System.out.println("Nombre MODIFY SUCCESSFUL TO "+target.getNombre());
        target.setDescripcion(source.getDescripcion()==null?null:source.getDescripcion().trim());
        System.out.println("Descripcion MODIFY SUCCESSFUL TO "+target.getDescripcion());
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
    
    public JUR_EtapaDetalle getDetalleFromList(long id,List<JUR_EtapaDetalle>list,int n)
    {
        JUR_EtapaDetalle tmp=new JUR_EtapaDetalle();
        for(int i=0;i<n&&tmp.getId_JUR_EtapaDetalle()!=id;i++)
        {
            System.out.println("i: "+i+" < "+n);
            tmp=list.get(i);
        }
        return tmp;
    }
    
    public JUR_EtapaDetalle getDetalleFromList(String portalValues,List<JUR_EtapaDetalle>list,int n)
    {
        System.out.println("getDetalleFromList (String) METHOD REACHED with: "+portalValues);
        JUR_EtapaDetalle tmp=null;
        String arr[]=portalValues.split("_");
        long id=ca.parseStringLong(arr[0]);
        if(id>0)
            tmp=getDetalleFromList(id,list,n);
        else
        {
            id=ca.parseStringInt(arr[1]);
            /*for(int i=0,j=0;i<n;i++)
            {
                tmp=list.get(i);
                System.out.println(i+", "+j+", "+id+"\n"+tmp);
                if(tmp.getId_JUR_EtapaDetalle()==0)
                {
                    if(i-j==id-1)
                        break;
                    else j++;
                }
            }*/
            tmp=getDefaultDetalle((int)id,list,n);
        }
        System.out.println("getDetalleFromList (String) METHOD PERFORMED");
        return tmp;
    }
    
    public JUR_EtapaDetalle setDetalleFromList(int row,JUR_EtapaDetalle node,List<JUR_EtapaDetalle>list,int n)
    {
        int j=0;
        for(int i=0;i<n;i++)
        {
            if(list.get(i).getId_JUR_EtapaDetalle()<1)
                if(++j==row)
                {
                    list.set(i,node);
                    j=i;
                    break;
                }
        }
        return j<n?list.get(j):new JUR_EtapaDetalle();
    }
    
    public JUR_EtapaDetalle getDefaultDetalle(int row,List<JUR_EtapaDetalle>list,int n)
    {
        System.out.println("getDefaultDetalle METHOD REACHED with: "+row);
        int i,j=0;
        JUR_EtapaDetalle node=null;
        for(i=0;i<n;i++)
        {
            node=list.get(i);
            if(node.getDefaultRowNumber()==row)
                break;
        }
        if(i>=n)
            node=new JUR_EtapaDetalle();
        System.out.println("NODE GOTTEN: "+node);
        System.out.println("getDefaultDetalle METHOD PERFORMED");
        return node;
    }
    
    public JUR_EtapaDetalle newJUR_EtapaDetalle(JUR_Etapas model,String[] arr,String usu)
    {
        System.out.println("newJUR_EtapaDetalle METHOD REACHED");
        boolean f=arr[1].isEmpty();
        JUR_EtapaDetalle record=null;
        try{
            Session=Factory.getCurrentSession();
            if(f)
            {
                record=new JUR_EtapaDetalle();
                System.out.println("CURRENT SESSION GOTTEN");
                record.setZz_FechaCreacion(ca.getCurrentTimestamp());
                record.setZz_FechaModificacion(record.getZz_FechaModificacion());
                record.setFechaString(arr[2]);
                record.setFecha(ca.stringToDate(arr[2],"/"));
                record.setDuracion(ca.parseStringFloat(arr[3]));
                record.setUnidadTiempo(arr[4]);
                record.setAsuntosTratados(arr[5]);
                record.setResultados(arr[6]);
                record.setConclusiones(arr[7]);
                record.setNotas(arr[8]);
                record.setZz_UsuarioCreacion(usu);
                record.setZz_UsuarioModificacion(usu);
                model.setZz_FechaModificacion(record.getZz_FechaCreacion());
                model.setZz_UsuarioModificacion(usu);
                model.setJUR_EtapaDetalleLength(1+model.getJUR_EtapaDetalleLength());
                record.setJUR_Etapa(model);
                Session.beginTransaction();
                System.out.println("newJUR_EtapaDetalle TRANSACTION STARTED");
                Session.update(model);
                Session.update(model.getJUR_Caso());
                Session.save(record);
                System.out.println("new JUR_EtapaDetalle SAVE SUCCESSFUL");
                Session.getTransaction().commit();
                System.out.println("new JUR_EtapaDetalle TRANSACTION COMMITED: "+record.getId_JUR_EtapaDetalle());
                model.getJUR_EtapaDetalle().add(record);
            }
            else
            {
                Session.beginTransaction();
                System.out.println("newJUR_EtapaDetalle TRANSACTION STARTED");
                int row=ca.parseStringInt(arr[0]);
                record=getDefaultDetalle(row,model.getJUR_EtapaDetalle(),model.getJUR_EtapaDetalleLength());
                record.setJUR_Etapa(model);
                Session.update(model);
                Session.update(model.getJUR_Caso());
                Session.save(record);
                Session.save(record.getJUR_Notificacion());
                List<JUR_NotificacionAlcance>l=record.getJUR_Notificacion().getJUR_Alcance();
                for(int i=0;i<RecursosHumanos.length;i++)
                {
                    JUR_NotificacionAlcance tmp=l.get(i);
                    tmp.setZz_FechaCreacion(record.getZz_FechaCreacion());
                    tmp.setZz_FechaModificacion(record.getZz_FechaCreacion());
                    tmp.setZz_UsuarioCreacion(usu);
                    tmp.setZz_UsuarioModificacion(usu);
                    Session.save(tmp);
                }
                Session.getTransaction().commit();
                System.out.println("new JUR_EtapaDetalle TRANSACTION COMMITED: "+record.getId_JUR_EtapaDetalle());
                setDetalleFromList(row,record,model.getJUR_EtapaDetalle(),model.getJUR_EtapaDetalleLength());
            }
        }
        catch(Exception e)
        {
            System.out.println("newJUR_EtapaDetalle EXCEPTION: "+e.toString());
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        return record;
    }
    
    public void modifyJUR_EtapaDetalle(JUR_EtapaDetalle record,String[] arr,String usu)
    {
        System.out.println("modifyJUR_EtapaDetalle METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            record.setFechaString(arr[2]);
            record.setFecha(ca.stringToDate(arr[2],"/"));
            try{
                record.setDuracion(Float.parseFloat(arr[3]));
            }catch(Exception e)
            {
                System.out.println("Duración parsing EXCEPTION:"+e);
                record.setDuracion(0);
            }
            record.setUnidadTiempo(arr[4]);
            record.setAsuntosTratados(arr[5]);
            record.setResultados(arr[6]);
            record.setConclusiones(arr[7]);
            record.setNotas(arr[8]);
            record.setZz_UsuarioModificacion(usu);
            record.setZz_FechaModificacion(ca.getCurrentTimestamp());
            record.getJUR_Etapa().setZz_FechaModificacion(record.getZz_FechaModificacion());
            record.getJUR_Etapa().setZz_UsuarioModificacion(usu);
            if(record.getJUR_ExpedientesLength()<1)
                record.setJUR_Expedientes(null);
            Session.beginTransaction();
            Session.update(record.getJUR_Etapa());
            Session.update(record);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("modifyJUR_EtapaDetalle EXCEPTION: "+e);
            e.printStackTrace();
        }
        System.out.println("modifyJUR_EtapaDetalle METHOD PERFORMED");
    }
    
    public void deleteJUR_EtapaDetalle(JUR_EtapaDetalle record,String usu)
    {
        System.out.println("deleteJUR_EtapaDetalle METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            record.getJUR_Etapa().setZz_FechaModificacion(ca.getCurrentTimestamp());
            record.getJUR_Etapa().setZz_UsuarioModificacion(usu);
            Session.beginTransaction();
            Session.delete(record);
            record.getJUR_Etapa().getJUR_EtapaDetalle().remove(record);
            Session.update(record.getJUR_Etapa());
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("deleteJUR_EtapaDetalle EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("deleteJUR_EtapaDetalle METHOD PERFORMED");
    }
    
    public JUR_Etapas cloneModel(JUR_Etapas model)
    {
        return setNewValues(model,new JUR_Etapas());
    }
    
    public void saveNotificacion(JUR_Notificaciones model, String usu)
    {
        System.out.println("saveNotification METHOD REACHED");
        try{
            Session=Factory.getCurrentSession();
            System.out.println("CURRENT SESSION SET");
            model.setZz_FechaModificacion(ca.getCurrentTimestamp());
            model.setZz_UsuarioModificacion(usu);
            model.setFechaInicio(ca.stringToDate(model.getFechaInicioString(),"/"));
            model.setFechaFin(ca.stringToDate(model.getFechaFinString(),"/"));
            model.setJUR_Etapa(model.getJUR_EtapaDetalle().getJUR_Etapa());
            Session.beginTransaction();
            System.out.println("TRANSACTION BEGAN WITH: "+model.getId_JUR_Notificacion());
            if(model.getId_JUR_Notificacion()<1)
            {
                model.setZz_FechaCreacion(model.getZz_FechaModificacion());
                model.setZz_UsuarioCreacion(usu);
                Session.save(model);
                System.out.println("Notificación SAVED");
                int n=model.getJUR_AlcanceLength();
                for(int i=0;i<n;i++)
                {
                    JUR_NotificacionAlcance tmp=model.getJUR_Alcance().get(i);
                    if(tmp.isSet())
                    {
                        tmp.setJUR_Notificacion(model);
                        tmp.setZz_FechaCreacion(model.getZz_FechaCreacion());
                        tmp.setZz_FechaModificacion(model.getZz_FechaModificacion());
                        tmp.setZz_UsuarioCreacion(usu);
                        tmp.setZz_UsuarioModificacion(usu);
                        Session.save(tmp);
                    }
                }
                System.out.println("Notificación Alcance SET AND SAVED");
            }
            else
            {
                Session.update(model);
                int n=model.getJUR_AlcanceLength();
                System.out.println("n-man: "+n);
                for(int i=0;i<n;i++)
                {
                    JUR_NotificacionAlcance tmp=model.getJUR_Alcance().get(i);
                    tmp.setZz_FechaModificacion(model.getZz_FechaModificacion());
                    tmp.setZz_UsuarioModificacion(usu);
                    if(tmp.getId_JUR_NotificacionAlcance()>0)
                    {
                        if(tmp.isSet())
                            Session.update(tmp);
                        else Session.delete(tmp);
                    }
                    else if(tmp.isSet())
                    {
                        tmp.setZz_FechaCreacion(model.getZz_FechaCreacion());
                        tmp.setZz_UsuarioCreacion(usu);
                        Session.save(tmp);
                    }
                }
                
            }
            Session.getTransaction().commit();
        }catch(Exception e){
            System.out.println("saveNotification EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("saveNotification METHOD PERFORMED");
    }
    
    public void deleteNotificacion(JUR_Notificaciones model, String usu)
    {
        System.out.println("deleteNotificacion METHOD REACHED");
        if(model.getId_JUR_Notificacion()>0)
            try{
                Session=Factory.getCurrentSession();
                System.out.println("CURRENT SESSION SET");
                model.getJUR_EtapaDetalle().setZz_FechaModificacion(ca.getCurrentTimestamp());
                model.getJUR_EtapaDetalle().setZz_UsuarioModificacion(usu);
                Session.beginTransaction();
                System.out.println("TRANSACTION BEGAN");
                Session.delete(model);
                model.getJUR_EtapaDetalle().setJUR_Notificacion(null);
                Session.getTransaction().commit();
            }catch(Exception e){
                System.out.println("deleteNotificacion EXCEPTION: "+e);
                e.printStackTrace();
            }
            finally
            {
                if(Session.isOpen())
                    Session.close();
            }
        System.out.println("deleteNotificacion METHOD PERFORMED");
    }
    
    public JUR_Notificaciones loadJUR_NotificacionModelFromList(List<JUR_Etapas> modelList,int etapa, int detalle)
    {
        System.out.println("loadJUR_NotificacionModelFromList METHOD REACHED: "+etapa+","+detalle);
        JUR_EtapaDetalle tmp=modelList.get(etapa).getJUR_EtapaDetalle().get(detalle);
        System.out.println("loadJUR_NotificacionModelFromList METHOD PERFORMED");
        return loadJUR_NotificacionModel(tmp);
    }
    
    public JUR_Notificaciones loadJUR_NotificacionModel(JUR_EtapaDetalle tmp)
    {
        System.out.println("loadJUR_NotificacionModel METHOD REACHED");
        JUR_Notificaciones model=tmp.getJUR_Notificacion();
        System.out.println("model: "+model);
        if(model==null)
        {
            model=new JUR_Notificaciones();
            model.setJUR_EtapaDetalle(tmp);
            tmp.setJUR_Notificacion(model);
            setJUR_Alcance(model);
            System.out.println("model.getJUR_AlcanceLength: "+model.getJUR_AlcanceLength());
        }
        else
        {            
            if(model.getFechaInicio()!=null)
                model.setFechaInicioString(ca.parseDate(model.getFechaInicio().toString(),"-"));
            else
                model.setFechaInicioString("");
            if(model.getFechaFin()!=null)
                model.setFechaFinString(ca.parseDate(model.getFechaFin().toString(),"-"));
            else
                model.setFechaFinString("");
            setJUR_Alcance(model);
        }
        System.out.println("loadJUR_Notificacion METHOD PERFORMED");
        return model;
    }
    
    private void getRecuersosHumanos()
    {
        System.out.println("getRecuersosHumanos METHOD REACHED");
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            List<JUR_RecursosHumanos> tmp=Session.createCriteria(JUR_RecursosHumanos.class).list();
            RecursosHumanos=new JUR_RecursosHumanos[tmp.size()];
            tmp.toArray(RecursosHumanos);
            System.out.println("RecursosHumanos.length: "+RecursosHumanos.length);
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("getRecuersosHumanos EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("getRecuersosHumanos METHOD PERFORMED");
    }
    
    private void setJUR_Alcance(JUR_Notificaciones model)
    {
        System.out.println("setJUR_Alcance METHOD REACHED");
        if(RecursosHumanos==null)
            getRecuersosHumanos();
        int n=RecursosHumanos.length;
        List<JUR_NotificacionAlcance>l=ca.newArrayList(n);
        for(int i=0;i<n;i++)
        {
            JUR_NotificacionAlcance na=new JUR_NotificacionAlcance();
            na.setJUR_RecursoHumano(RecursosHumanos[i]);
            na.setJUR_Notificacion(model);
            na.setSet(true);
            l.add(na);
        }
        model.setJUR_AlcanceLength(n);
        if(model.getId_JUR_Notificacion()>0)
        {
            model.setJUR_Alcance(initializeJUR_Alcance(model));
            if(model.getJUR_Alcance()!=null)
            {
                int m=n;
                n=model.getJUR_Alcance().size();
                for(int j=0;j<m;j++)
                {
                    JUR_NotificacionAlcance o=l.get(j);
                    int i;
                    for(i=0;i<n;i++)
                    {
                        JUR_NotificacionAlcance na=model.getJUR_Alcance().get(i);
                        if(na.getJUR_RecursoHumano().getId_JUR_RecursoHumano()==o.getJUR_RecursoHumano().getId_JUR_RecursoHumano())
                        {
                            o.setId_SEG_Seguimiento(na.getId_JUR_NotificacionAlcance());
                            o.setNotas(na.getNotas());
                            o.setMostrar(na.isMostrar());
                            o.setVisto(na.isVisto());
                            o.setZz_FechaCreacion(na.getZz_FechaCreacion());
                            o.setZz_FechaModificacion(na.getZz_FechaModificacion());
                            o.setZz_UsuarioCreacion(na.getZz_UsuarioCreacion());
                            o.setZz_UsuarioModificacion(na.getZz_UsuarioModificacion());
                            break;
                        }
                    }
                    if(i>=n)
                        o.setSet(false);
                }
            }
        }
        model.setJUR_Alcance(l);
        System.out.println("setJUR_Alcance METHOD PERFORMED");
    }
    
    private List<JUR_NotificacionAlcance> initializeJUR_Alcance(JUR_Notificaciones model)
    {
        System.out.println("initializeJUR_Alcance METHOD REACHED");
        List<JUR_NotificacionAlcance> l=null;
        try
        {
            //if(model.getId_JUR_Notificacion()>0)
            {
                Session=Factory.getCurrentSession();
                Session.beginTransaction();
                model=(JUR_Notificaciones)Session.load(JUR_Notificaciones.class,model.getId_JUR_Notificacion());
                int n=model.getJUR_Alcance().size();
                System.out.println("JUR_Alcance INITIALIZED WITH: "+n);
                l=model.getJUR_Alcance();
                Session.getTransaction().commit();
            }
            /*else
            {
                int n=RecursosHumanos.length;
                l=ca.newArrayList(n);
                for(int i=0;i<n;i++)
                {
                    JUR_NotificacionAlcance tmp=new JUR_NotificacionAlcance();
                    tmp.setJUR_Notificacion(model);
                    tmp.setJUR_RecursoHumano(RecursosHumanos[i]);
                    tmp.setSet(true);
                    tmp.setZz_FechaCreacion(model.getZz_FechaCreacion());
                    tmp.setZz_FechaModificacion(tmp.getZz_FechaCreacion());
                    tmp.setZz_UsuarioCreacion(model.getZz_UsuarioCreacion());
                    tmp.setZz_UsuarioModificacion(tmp.getZz_UsuarioCreacion());
                }
            }*/
        }
        catch(Exception e)
        {
            System.out.println("initializeJUR_Alcance EXCEPTION: "+e);
            e.printStackTrace();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
            System.out.println("initializeJUR_Alcance METHOD PERFORMED");
            return l;
        }
    }
    
    public void setDefaultData(JUR_Etapas etapa,String usu)
    {
        System.out.println("setDefaultData METHOD REACHED");
        try
        {
            String nombre=com.solemsa.jsf.data.JUR.JUR_EtapasOrden.getNextEtapa(etapa.getJUR_Caso());
            etapa.setNombre(nombre);
            int n=nombre.length()-1;
            if(nombre.charAt(n)==')')
            nombre=nombre.substring(0,n);
            if(nombre.toLowerCase().endsWith("pruebas")&&etapa.getJUR_Caso().getPurebasFecha()==null)
            {
                etapa.setJUR_EtapaDetalle((List<JUR_EtapaDetalle>)ca.newArrayList());
                etapa.getJUR_Caso().setPurebasFecha(ca.getCurrentDate());
                String[] fa=etapa.getJUR_Caso().getPurebasFecha().toString().split("-");
                JUR_EtapaDetalle tmp=setDefaultPruebasDetalle(etapa,getJUR_Fechas(Integer.parseInt(fa[1]),Integer.parseInt(fa[0])),usu,ca);
                setJUR_Alcance(tmp.getJUR_Notificacion());
                etapa.getJUR_EtapaDetalle().add(tmp);
                etapa.setJUR_EtapaDetalleLength(1);
            }
            else if((nombre.toLowerCase().contains("contestación")||nombre.toLowerCase().contains("contestacion"))&&etapa.getJUR_Caso().getContestacionFecha()==null)
            {
                etapa.setJUR_EtapaDetalle((List<JUR_EtapaDetalle>)ca.newArrayList());
                etapa.getJUR_Caso().setContestacionFecha(ca.getCurrentDate());
                String[] fa=etapa.getJUR_Caso().getContestacionFecha().toString().split("-");
                JUR_EtapaDetalle tmp=JUR_EtapasOrden.setDefaultContestacionDetalle(etapa,getJUR_Fechas(Integer.parseInt(fa[1]),Integer.parseInt(fa[0])),usu,ca);
                setJUR_Alcance(tmp.getJUR_Notificacion());
                etapa.getJUR_EtapaDetalle().add(tmp);
                etapa.setJUR_EtapaDetalleLength(1);
            }
        }
        catch(Exception e)
        {
            System.out.println("setDefaultData EXCEPTION: "+ e);
            e.printStackTrace();
        }             
        System.out.println("setDefaultData METHOD PERFORMED");
    }
    
    private List<JUR_Fechas> getJUR_Fechas(int month,int year)
    {
        System.out.println("getJUR_Fechas METHOD REACHED");
        List<JUR_Fechas> lista=null;
        try
        {
            Session=Factory.getCurrentSession();
            Session.beginTransaction();
            String minDateF,maxDateI,minDateI,maxDateF;
            if(month>1)
            {
                month--;
                minDateI="01/"+(month<10?"0":"")+month+"/"+year;
                month++;
            }
            else minDateI="01/12/"+(year-1);
            minDateF="01/"+(month<10?"0":"")+month+"/"+year;
            if(month<10)
            {
                month+=3;
                maxDateI="01/"+(month<10?"0":"")+month+"/"+year;
                if(month<12)
                {
                    month++;
                    maxDateF="01/"+(month<10?"0":"")+month+"/"+year;
                }
                else maxDateF="01/01/"+(year+1);
            }
            else
            {
                year++;
                month=1+month-10;
                maxDateI="01/"+month+"/"+year;
                maxDateF="01/"+(month+1)+"/"+year;
            }
            System.out.println("\t"+minDateI+" - "+maxDateI);
            System.out.println("\t"+minDateF+" - "+maxDateF);
            lista=Session.createCriteria(JUR_Fechas.class).add(Restrictions
                    .or(
                        Restrictions.between("FechaInicio",ca.stringToDate(minDateI,"/"),ca.stringToDate(maxDateI,"/")),
                        Restrictions.between("FechaFin",ca.stringToDate(minDateF,"/"),ca.stringToDate(maxDateF,"/"))
                    )).addOrder(Order.asc("FechaInicio")).list();
            Session.getTransaction().commit();
        }
        catch(Exception e)
        {
            System.out.println("getJUR_Fechas EXCEPTION: "+e);
            e.printStackTrace();
            lista=ca.newArrayList();
        }
        finally
        {
            if(Session.isOpen())
                Session.close();
        }
        System.out.println("getJUR_Fechas METHOD PERFORMED");
        return lista;
    }
}
