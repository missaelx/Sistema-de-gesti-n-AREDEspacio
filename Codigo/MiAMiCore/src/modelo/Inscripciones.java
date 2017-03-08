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
@Table(name = "inscripciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripciones.findAll", query = "SELECT i FROM Inscripciones i")
    , @NamedQuery(name = "Inscripciones.findByIdinscripcion", query = "SELECT i FROM Inscripciones i WHERE i.idinscripcion = :idinscripcion")})
public class Inscripciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinscripcion")
    private Integer idinscripcion;
    @JoinColumn(name = "idalumno", referencedColumnName = "idalumno")
    @ManyToOne(optional = false)
    private Alumnos idalumno;
    @JoinColumn(name = "idingreso", referencedColumnName = "idingreso")
    @ManyToOne(optional = false)
    private Ingresos idingreso;

    public Inscripciones() {
    }

    public Inscripciones(Integer idinscripcion) {
        this.idinscripcion = idinscripcion;
    }

    public Integer getIdinscripcion() {
        return idinscripcion;
    }

    public void setIdinscripcion(Integer idinscripcion) {
        this.idinscripcion = idinscripcion;
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
        hash += (idinscripcion != null ? idinscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripciones)) {
            return false;
        }
        Inscripciones other = (Inscripciones) object;
        if ((this.idinscripcion == null && other.idinscripcion != null) || (this.idinscripcion != null && !this.idinscripcion.equals(other.idinscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Inscripciones[ idinscripcion=" + idinscripcion + " ]";
    }
    
}
