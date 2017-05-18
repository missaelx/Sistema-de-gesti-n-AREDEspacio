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
@Table(name = "asistencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")
        ,@NamedQuery(name = "Asistencia.findByAlumno", query = "Select a FROM Asistencia a WHERE a.idAlumno = :idAlumno AND a.dia > :fecha")
    , @NamedQuery(name = "Asistencia.findById", query = "SELECT a FROM Asistencia a WHERE a.id = :id")
    , @NamedQuery(name = "Asistencia.findByDia", query = "SELECT a FROM Asistencia a WHERE a.dia = :dia")})
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dia")
    @Temporal(TemporalType.DATE)
    private Date dia;
    @JoinColumn(name = "idAlumno", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Alumno idAlumno;
    @JoinColumn(name = "idGrupoClase", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GrupoClase idGrupoClase;

    public Asistencia() {
    }

    public Asistencia(Integer id) {
        this.id = id;
    }

    public Asistencia(Integer id, Date dia) {
        this.id = id;
        this.dia = dia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Alumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumno idAlumno) {
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Asistencia[ id=" + id + " ]";
    }
    
}
