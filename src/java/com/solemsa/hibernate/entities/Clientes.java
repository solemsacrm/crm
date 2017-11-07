package com.solemsa.hibernate.entities;

import com.solemsa.hibernate.MappedSuperClasses.ClientesMSC;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="clientes")
public class Clientes extends ClientesMSC {

}