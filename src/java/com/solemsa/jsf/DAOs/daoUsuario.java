package com.solemsa.jsf.DAOs;

import com.solemsa.hibernate.entities.Empleados;
import com.solemsa.hibernate.entities.JUR.JUR_RecursosHumanos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.solemsa.hibernate.entities.Usuarios;
import java.util.List;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;

public class daoUsuario {
    
    private int __id_Usuarios;
    private String Usuario, Contrasenia;
    private SessionFactory Factory;
    private Session Session;
    private StandardServiceRegistry serviceRegistry;

    public daoUsuario(){
        try{
            Configuration configuration = new Configuration();
            configuration.configure("com/solemsa/hibernate/hibernate.cfg.xml").addAnnotatedClass(Empleados.class).addAnnotatedClass(Usuarios.class);
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("SERVICE REGISTRY CONSTRUCTION: "+(serviceRegistry!=null?" true":" false"));
            
            Factory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("FACTORY CONSTRUCTION");

            Session = Factory.getCurrentSession();
            System.out.println("SESSION CONSTRUCTION");
        }catch(Exception e){
            System.out.println("FACTORY FAILED "+e);
            e.printStackTrace();
        }
    }
    
    public void createSessiobFactory(){
        
    }

    public int getId_Usuarios() {
        return __id_Usuarios;
    }

    public void setId_Usuarios(int __id_Usuarios) {
        this.__id_Usuarios = __id_Usuarios;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String Contrasenia) {
        this.Contrasenia = Contrasenia;
    }
    
    public String validarLogIn (String Usuario, String Contrasenia)
    {
        if(Session!=null)
        try
        {			
            // start a transaction
            Session.beginTransaction();
            System.out.println("AAAAAAAAAAAAAAa");
            // query students
            List<Usuarios> lista=Session.createCriteria(Usuarios.class).add(Restrictions.eq("Usuario",Usuario)).add(Restrictions.eq("Contrasenia",Contrasenia)).list();
            System.out.println("QUERY RESULT: "+lista.size());
            //commit transaction
            Session.getTransaction().commit();
            System.out.println("TRANSACTION COMMITED");
            Factory.close();
            
            return lista.get(0).toString();
        }
        catch(Exception e)
        {
            System.out.println("EXCEPCIÓN DE CONSULTA: "+e.toString());
            e.printStackTrace();
            if(!Factory.isClosed())
                Factory.close();
            return null;
        }
        else
            return null;
    }
    
}
