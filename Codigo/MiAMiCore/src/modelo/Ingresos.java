/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingresos.findAll", query = "SELECT i FROM Ingresos i")
    , @NamedQuery(name = "Ingresos.findByIdingreso", query = "SELECT i FROM Ingresos i WHERE i.idingreso = :idingreso")
    , @NamedQuery(name = "Ingresos.findByMonto", query = "SELECT i FROM Ingresos i WHERE i.monto = :monto")
    , @NamedQuery(name = "Ingresos.findByDescripcion", query = "SELECT i FROM Ingresos i WHERE i.descripcion = :descripcion")})
public class Ingresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idingreso")
    private Integer idingreso;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idingreso")
    private List<Inscripciones> inscripcionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idingreso")
    private List<Mensualidades> mensualidadesList;

    public Ingresos() {
    }

    public Ingresos(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public Ingresos(Integer idingreso, BigDecimal monto) {
        this.idingreso = idingreso;
        this.monto = monto;
    }

    public Integer getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Integer idingreso) {
        this.idingreso = idingreso;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Inscripciones> getInscripcionesList() {
        return inscripcionesList;
    }

    public void setInscripcionesList(List<Inscripciones> inscripcionesList) {
        this.inscripcionesList = inscripcionesList;
    }

    @XmlTransient
    public List<Mensualidades> getMensualidadesList() {
        return mensualidadesList;
    }

    public void setMensualidadesList(List<Mensualidades> mensualidadesList) {
        this.mensualidadesList = mensualidadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idingreso != null ? idingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingresos)) {
            return false;
        }
        Ingresos other = (Ingresos) object;
        if ((this.idingreso == null && other.idingreso != null) || (this.idingreso != null && !this.idingreso.equals(other.idingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Ingresos[ idingreso=" + idingreso + " ]";
    }
    
}
