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
@Table(name = "mensualidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensualidades.findAll", query = "SELECT m FROM Mensualidades m")
    , @NamedQuery(name = "Mensualidades.findByIdmensualidad", query = "SELECT m FROM Mensualidades m WHERE m.idmensualidad = :idmensualidad")})
public class Mensualidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmensualidad")
    private Integer idmensualidad;
    @JoinColumn(name = "idalumno", referencedColumnName = "idalumno")
    @ManyToOne(optional = false)
    private Alumnos idalumno;
    @JoinColumn(name = "idingreso", referencedColumnName = "idingreso")
    @ManyToOne(optional = false)
    private Ingresos idingreso;

    public Mensualidades() {
    }

    public Mensualidades(Integer idmensualidad) {
        this.idmensualidad = idmensualidad;
    }

    public Integer getIdmensualidad() {
        return idmensualidad;
    }

    public void setIdmensualidad(Integer idmensualidad) {
        this.idmensualidad = idmensualidad;
    }

    public Alumnos getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(Alumnos idalumno) {
        this.idalumno = idalumno;
    }

    public Ingresos getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Ingresos idingreso) {
        this.idingreso = idingreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmensualidad != null ? idmensualidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensualidades)) {
            return false;
        }
        Mensualidades other = (Mensualidades) object;
        if ((this.idmensualidad == null && other.idmensualidad != null) || (this.idmensualidad != null && !this.idmensualidad.equals(other.idmensualidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Mensualidades[ idmensualidad=" + idmensualidad + " ]";
    }
    
}
