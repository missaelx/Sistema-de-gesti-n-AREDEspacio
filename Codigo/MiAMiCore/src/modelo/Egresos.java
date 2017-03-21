/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "egresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresos.findAll", query = "SELECT e FROM Egresos e")
    , @NamedQuery(name = "Egresos.findByIdegresos", query = "SELECT e FROM Egresos e WHERE e.idegresos = :idegresos")
    , @NamedQuery(name = "Egresos.findByMonto", query = "SELECT e FROM Egresos e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egresos.findByDescripcion", query = "SELECT e FROM Egresos e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Egresos.findByFecha", query = "SELECT e FROM Egresos e WHERE e.fecha = :fecha")})
public class Egresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idegresos")
    private Integer idegresos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idegreso", fetch=FetchType.EAGER)
    private List<Pagosdesalario> pagosdesalarioList;

    public Egresos() {
    }

    public Egresos(Integer idegresos) {
        this.idegresos = idegresos;
    }

    public Egresos(Integer idegresos, BigDecimal monto, Date fecha) {
        this.idegresos = idegresos;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Integer getIdegresos() {
        return idegresos;
    }

    public void setIdegresos(Integer idegresos) {
        this.idegresos = idegresos;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<Pagosdesalario> getPagosdesalarioList() {
        return pagosdesalarioList;
    }

    public void setPagosdesalarioList(List<Pagosdesalario> pagosdesalarioList) {
        this.pagosdesalarioList = pagosdesalarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idegresos != null ? idegresos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egresos)) {
            return false;
        }
        Egresos other = (Egresos) object;
        if ((this.idegresos == null && other.idegresos != null) || (this.idegresos != null && !this.idegresos.equals(other.idegresos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Egresos[ idegresos=" + idegresos + " ]";
    }
    
}
