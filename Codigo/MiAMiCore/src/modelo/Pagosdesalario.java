/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
@Table(name = "pagosdesalario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagosdesalario.findAll", query = "SELECT p FROM Pagosdesalario p")
    , @NamedQuery(name = "Pagosdesalario.findByIdpagodesalario", query = "SELECT p FROM Pagosdesalario p WHERE p.idpagodesalario = :idpagodesalario")})
public class Pagosdesalario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpagodesalario")
    private Integer idpagodesalario;
    @JoinColumn(name = "idegreso", referencedColumnName = "idegresos")
    @ManyToOne(optional = false)
    private Egresos idegreso;
    @JoinColumn(name = "idmaestro", referencedColumnName = "idmaestro")
    @ManyToOne(optional = false)
    private Maestros idmaestro;

    public Pagosdesalario() {
    }

    public Pagosdesalario(Integer idpagodesalario) {
        this.idpagodesalario = idpagodesalario;
    }

    public Integer getIdpagodesalario() {
        return idpagodesalario;
    }

    public void setIdpagodesalario(Integer idpagodesalario) {
        this.idpagodesalario = idpagodesalario;
    }

    public Egresos getIdegreso() {
        return idegreso;
    }

    public void setIdegreso(Egresos idegreso) {
        this.idegreso = idegreso;
    }

    public Maestros getIdmaestro() {
        return idmaestro;
    }

    public void setIdmaestro(Maestros idmaestro) {
        this.idmaestro = idmaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpagodesalario != null ? idpagodesalario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagosdesalario)) {
            return false;
        }
        Pagosdesalario other = (Pagosdesalario) object;
        if ((this.idpagodesalario == null && other.idpagodesalario != null) || (this.idpagodesalario != null && !this.idpagodesalario.equals(other.idpagodesalario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Pagosdesalario[ idpagodesalario=" + idpagodesalario + " ]";
    }
    
}
