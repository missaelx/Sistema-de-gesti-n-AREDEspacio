package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author macbookpro
 */
@Entity
@Table(name = "mensualidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensualidad.findAll", query = "SELECT m FROM Mensualidad m")
        , @NamedQuery(name = "Mensualidad.findEntreFechas", query = "SELECT m FROM Mensualidad m WHERE m.idingreso.fecha >= :inicio and m.idingreso.fecha <= :fin")
    , @NamedQuery(name = "Mensualidad.findById", query = "SELECT m FROM Mensualidad m WHERE m.id = :id")})
public class Mensualidad implements Serializable {

    @ManyToMany(mappedBy = "mensualidadList")
    private List<GrupoClase> grupoClaseList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idalumno", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Alumno idalumno;
    @JoinColumn(name = "idingreso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ingreso idingreso;

    public Mensualidad() {
    }

    public Mensualidad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alumno getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(Alumno idalumno) {
        this.idalumno = idalumno;
    }

    public Ingreso getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(Ingreso idingreso) {
        this.idingreso = idingreso;
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
        if (!(object instanceof Mensualidad)) {
            return false;
        }
        Mensualidad other = (Mensualidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Mensualidad[ id=" + id + " ]";
    }

    @XmlTransient
    public List<GrupoClase> getGrupoClaseList() {
        return grupoClaseList;
    }

    public void setGrupoClaseList(List<GrupoClase> grupoClaseList) {
        this.grupoClaseList = grupoClaseList;
    }
    
}
