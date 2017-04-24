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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "pagodesalario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagodesalario.findAll", query = "SELECT p FROM Pagodesalario p")
    , @NamedQuery(name = "Pagodesalario.findById", query = "SELECT p FROM Pagodesalario p WHERE p.id = :id")
    , @NamedQuery(name = "Pagodesalario.findByMaestro", query = "SELECT p FROM Pagodesalario p WHERE p.idmaestro.id = :idmaestro")})
public class Pagodesalario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idegreso", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Egreso idegreso;
    @JoinColumn(name = "idmaestro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Maestro idmaestro;

    public String getDescripcion() {
        return idegreso.getDescripcion();
    }

    public BigDecimal getMonto() {
        return idegreso.getMonto();
    }

    public Date getFecha() {
        return idegreso.getFecha();
    }
    
    public String getMaestro(){
        return this.idmaestro.getNombre() + " " + this.idmaestro.getApellidos();
    }
    
    
    

    public Pagodesalario() {
    }

    public Pagodesalario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Egreso getIdegreso() {
        return idegreso;
    }

    public void setIdegreso(Egreso idegreso) {
        this.idegreso = idegreso;
    }

    public Maestro getIdmaestro() {
        return idmaestro;
    }

    public void setIdmaestro(Maestro idmaestro) {
        this.idmaestro = idmaestro;
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
        if (!(object instanceof Pagodesalario)) {
            return false;
        }
        Pagodesalario other = (Pagodesalario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Pagodesalario[ id=" + id + " ]";
    }
    
}
