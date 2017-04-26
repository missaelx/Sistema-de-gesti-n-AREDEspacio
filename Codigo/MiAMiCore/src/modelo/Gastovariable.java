/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "gastovariable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gastovariable.findAll", query = "SELECT g FROM Gastovariable g")
    , @NamedQuery(name = "Gastovariable.findById", query = "SELECT g FROM Gastovariable g WHERE g.id = :id")})
public class Gastovariable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idEgreso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Egreso idEgreso;

    public Gastovariable() {
    }

    public Gastovariable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Egreso getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Egreso idEgreso) {
        this.idEgreso = idEgreso;
    }
    
    //setters y getters para el rellenado de columnas
    
    public String getDescripcion(){
        return this.idEgreso.getDescripcion();
    }
    public void setDescripcion(String descripcion){
        this.idEgreso.setDescripcion(descripcion);
    }
    public BigDecimal getMonto(){
        return this.idEgreso.getMonto();
    }
    public void setMonto(BigDecimal monto){
        this.idEgreso.setMonto(monto);
    }
    public Date getFecha(){
        return this.idEgreso.getFecha();
    }
    public void setFecha(Date fecha){
        this.idEgreso.setFecha(fecha);
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gastovariable)) {
            return false;
        }
        Gastovariable other = (Gastovariable) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Gastovariable[ id=" + id + " ]";
    }
    
}
