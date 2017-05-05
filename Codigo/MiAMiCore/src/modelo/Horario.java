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
@Table(name = "horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")
    , @NamedQuery(name = "Horario.findById", query = "SELECT h FROM Horario h WHERE h.id = :id")
        , @NamedQuery(name = "Horario.findClaseHoy", query = "SELECT h FROM Horario h WHERE h.dia = :dia and h.idGrupoClase.activo = 1")
    , @NamedQuery(name = "Horario.findByDia", query = "SELECT h FROM Horario h WHERE h.dia = :dia")
    , @NamedQuery(name = "Horario.findByHorainicio", query = "SELECT h FROM Horario h WHERE h.horainicio = :horainicio")
    , @NamedQuery(name = "Horario.findByHorafinal", query = "SELECT h FROM Horario h WHERE h.horafinal = :horafinal")})
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dia")
    private String dia;
    @Basic(optional = false)
    @Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Basic(optional = false)
    @Column(name = "horafinal")
    @Temporal(TemporalType.TIME)
    private Date horafinal;
    @JoinColumn(name = "idGrupoClase", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GrupoClase idGrupoClase;

    public Horario() {
    }

    public Horario(Integer id) {
        this.id = id;
    }

    public Horario(Integer id, String dia, Date horainicio, Date horafinal) {
        this.id = id;
        this.dia = dia;
        this.horainicio = horainicio;
        this.horafinal = horafinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(Date horafinal) {
        this.horafinal = horafinal;
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
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Horario[ id=" + id + " ]";
    }
    
}
