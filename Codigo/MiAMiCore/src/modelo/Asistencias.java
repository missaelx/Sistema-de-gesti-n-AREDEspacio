/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "asistencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencias.findAll", query = "SELECT a FROM Asistencias a")
    , @NamedQuery(name = "Asistencias.findByIdasistencia", query = "SELECT a FROM Asistencias a WHERE a.idasistencia = :idasistencia")
    , @NamedQuery(name = "Asistencias.findByDia", query = "SELECT a FROM Asistencias a WHERE a.dia = :dia")})
public class Asistencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasistencia")
    private Integer idasistencia;
    @Basic(optional = false)
    @Column(name = "dia")
    @Temporal(TemporalType.DATE)
    private Date dia;
    @JoinColumn(name = "idAlumno", referencedColumnName = "idalumno")
    @ManyToOne(optional = false)
    private Alumnos idAlumno;
    @JoinColumn(name = "idGrupoClase", referencedColumnName = "idGrupoClase")
    @ManyToOne(optional = false)
    private GrupoClase idGrupoClase;

    public Asistencias() {
    }

    public Asistencias(Integer idasistencia) {
        this.idasistencia = idasistencia;
    }

    public Asistencias(Integer idasistencia, Date dia) {
        this.idasistencia = idasistencia;
        this.dia = dia;
    }

    public Integer getIdasistencia() {
        return idasistencia;
    }

    public void setIdasistencia(Integer idasistencia) {
        this.idasistencia = idasistencia;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Alumnos getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumnos idAlumno) {
        this.idAlumno = idAlumno;
    }

    public GrupoClase getIdGrupoClase() {
        return idGrupoClase;
    }

    public void setIdGrupoClase(GrupoClase idGrupoClase) {
        this.idGrupoClase = idGrupoClase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasistencia != null ? idasistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencias)) {
            return false;
        }
        Asistencias other = (Asistencias) object;
        if ((this.idasistencia == null && other.idasistencia != null) || (this.idasistencia != null && !this.idasistencia.equals(other.idasistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Asistencias[ idasistencia=" + idasistencia + " ]";
    }
    
}
