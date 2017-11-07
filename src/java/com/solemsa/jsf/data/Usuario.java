package com.solemsa.jsf.data;

public class Usuario {
    
    private long id;
    private int jur,rex,area;
    private String username,contrasenia,rol,nombre,perfil,pass;
    private boolean directivo,gerente;
    
    public Usuario(long id, int rex, int jur, String username, String contrasenia, int area, String rol, boolean directivo, boolean gerente, String nombre)
    {
        this.id=id;
        this.jur=jur;
        this.rex=rex;
        this.username=username;
        this.contrasenia=contrasenia;
        this.area=area;
        this.rol=rol;
        this.directivo=directivo;
        this.gerente=gerente;
        this.nombre=nombre;
        chooseUserProfile();
    }
    
    private void chooseUserProfile()
    {
        CommonActions ca=new CommonActions();
        int a;
        System.out.println("area-man: "+area+", jur-man: "+jur);
        if(area<1&&jur>0)
            a=2;
        else a=area;
        System.out.println("area-man: "+a);
        switch(a)
        {
            case 1:if(gerente){perfil="GerenteAOU";pass=ca.encryptString("$0L3MS@Ger_AOU");}else{perfil="EmpAOU";pass=ca.encryptString("$0L3MS@Emp_AOU");}break;
            case 2:if(gerente){perfil="GerenteJUR";pass=ca.encryptString("$0L3MS@Ger_JUR");}else{perfil="EmpJUR";pass=ca.encryptString("$0L3MS@Emp_JUR");}break;
            case 3:if(gerente){perfil="GerenteCNT";pass=ca.encryptString("$0L3MS@Ger_CNT");}else{perfil="EmpCNT";pass=ca.encryptString("$0L3MS@Emp_CNT");}break;
            case 4:if(gerente){perfil="GerenteSEG";pass=ca.encryptString("$0L3MS@Ger_SEG");}else{perfil="EmpSEG";pass=ca.encryptString("$0L3MS@Emp_SEG");}break;
            case 5:if(gerente){perfil="GerenteREX";pass=ca.encryptString("$0L3MS@Ger_REX");}else{perfil="EmpREX";pass=ca.encryptString("$0L3MS@Emp_REX");}break;
            case 6:if(gerente){perfil="GerenteVEN";pass=ca.encryptString("$0L3MS@Ger_VEN");}
            else if(jur<1){perfil="EmpVEN";pass=ca.encryptString("$0L3MS@Emp_VEN");}
            else {perfil="EmpVEN_JUR";pass=ca.encryptString("$0L3MS@Emp_VEN_JUR");}break;
            default:if(directivo){perfil="Directivo";pass=ca.encryptString("$0L3MS@DVO");}break;
        }
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public long getId() {
        return id;
    }

    public int getJur() {
        return jur;
    }

    public void setJur(int jur) {
        this.jur = jur;
    }

    public int getRex() {
        return rex;
    }

    public void setRex(int rex) {
        this.rex = rex;
    }

    public boolean isDirectivo() {
        return directivo;
    }

    public void setDirectivo(boolean directivo) {
        this.directivo = directivo;
    }

    public boolean isGerente() {
        return gerente;
    }

    public void setGerente(boolean gerente) {
        this.gerente = gerente;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
}
