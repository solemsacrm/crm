package com.solemsa.jsf.DAOs;

import com.solemsa.hibernate.entities.Usuarios;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class daoConfig {
    
    private SessionFactory Factory;
    private Session Session;
    
    public daoConfig(SessionFactory Factory){
        try{
            this.Factory = Factory;
            System.out.println("daoConfig FACTORY CONSTRUCTION");
            Session = Factory.getCurrentSession();
            System.out.println("daoConfig SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("daoConfig FACTORY FAILED "+e);
        }
    }

    public Usuarios getUsuario(String u,int a,boolean d)
    {
        System.out.println("getConfig METHOD REACHED: "+u);
        List<Usuarios> lCfg;
        Usuarios cfg=null;
        if(u.length()>0)
        {
            System.out.println("ID RECEIVED: "+u);
            Session = Factory.getCurrentSession();
            System.out.println("CURRENT SESSION GOTTEN");
            Session.beginTransaction();
            System.out.println("get TRANSACTION STARTED");
            lCfg=Session.createCriteria(Usuarios.class).add(Restrictions.eq("Usuario",u)).list();
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
            int n=lCfg.size(),i;
            if(d)
                for(i=0;i<n;i++)
                {
                    cfg=lCfg.get(i);
                    if(cfg.getEmpleado().getDirectivo()==1)
                        break;
                }
            else
                for(i=0;i<n;i++)
                {
                    cfg=lCfg.get(i);
                    if(Integer.parseInt(cfg.getArea())==a)
                        break;
                }
            if(i>=n)
                cfg=null;
        }
        System.out.println("getUsuario() METHOD ENDED");
        return cfg;
    }

    public Usuarios modifyUsuario(Usuarios source){
        Usuarios target=null;
        String un=null,co=null;
        try {
            Session = Factory.getCurrentSession();
            Session.beginTransaction();
            System.out.println("modify TRANSACTION STARTED");
            target = (Usuarios) Session.get(Usuarios.class,source.getId_Usuario());
            System.out.println("modify "+target.getId_Usuario()+" SUCCESSFULLY RETRIEVED");
            un=source.getUsuario();
            target.setUsuario(source.getUsuario().trim()+"");
            System.out.println("Usuario MODIFY SUCCESSFUL TO "+un);
            co=source.getContrasenia();
            target.setContrasenia(source.getContrasenia().trim()+"");
            System.out.println("Contrasenia MODIFY SUCCESSFUL TO "+co);
            target.setZz_UsuarioModificacion(source.getZz_UsuarioModificacion());
            System.out.println("Usuario Modificación MODIFY SUCCESSFUL TO "+target.getZz_UsuarioModificacion());
            java.util.Date date= new java.util.Date();
            Timestamp d=new Timestamp(date.getTime());
            target.setZz_FechaModificacion(d);
            System.out.println("Fecha Modificación MODIFY SUCCESSFUL TO "+target.getZz_FechaModificacion());
            System.out.println("config "+target.getId_Usuario()+" SUCCESSFULLY MODIFIED");
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
                System.out.println("modify Config SESSION CLOSED");
            }catch(Exception e)
            {
                System.out.println("CLOSE SESSION EXCEPTION :"+e);
            }
        }
        return target!=null?target:null;
    }
    
    public void cerrarFactorySession()
    {
        Factory.close();
    }
    
}
