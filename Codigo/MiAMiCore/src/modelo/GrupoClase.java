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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "grupoClase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoClase.findAll", query = "SELECT g FROM GrupoClase g")
    , @NamedQuery(name = "GrupoClase.findById", query = "SELECT g FROM GrupoClase g WHERE g.id = :id")
    , @NamedQuery(name = "GrupoClase.findByActivo", query = "SELECT g FROM GrupoClase g WHERE g.activo = :activo")
    , @NamedQuery(name = "GrupoClase.findByCostoMensual", query = "SELECT g FROM GrupoClase g WHERE g.costoMensual = :costoMensual")
    , @NamedQuery(name = "GrupoClase.findByTerminado", query = "SELECT g FROM GrupoClase g WHERE g.terminado = :terminado")
    , @NamedQuery(name = "GrupoClase.findByFechaTermino", query = "SELECT g FROM GrupoClase g WHERE g.fechaTermino = :fechaTermino")})
public class GrupoClase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "costo_mensual")
    private BigDecimal costoMensual;
    @Basic(optional = false)
    @Column(name = "terminado")
    private boolean terminado;
    @Column(name = "fecha_termino")
    private String fechaTermino;
    @ManyToMany(mappedBy = "grupoClaseList")
    private List<Alumno> alumnoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoClase")
    private List<Horario> horarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoClase")
    private List<Asistencia> asistenciaList;
    @JoinColumn(name = "idMaestro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Maestro idMaestro;
    @JoinColumn(name = "idTipoDanza", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoDanza idTipoDanza;

    public GrupoClase() {
    }

    public GrupoClase(Integer id) {
        this.id = id;
    }

    public GrupoClase(Integer id, boolean activo, BigDecimal costoMensual, boolean terminado) {
        this.id = id;
        this.activo = activo;
        this.costoMensual = costoMensual;
        this.terminado = terminado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public BigDecimal getCostoMensual() {
        return costoMensual;
    }

    public void setCostoMensual(BigDecimal costoMensual) {
        this.costoMensual = costoMensual;
    }

    public boolean getTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    @XmlTransient
    public List<Alumno> getAlumnoList() {
        return alumnoList;
    }

    public void setAlumnoList(List<Alumno> alumnoList) {
        this.alumnoList = alumnoList;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
    }

    public Maestro getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Maestro idMaestro) {
        this.idMaestro = idMaestro;
    }

    public TipoDanza getIdTipoDanza() {
        return idTipoDanza;
    }

    public void setIdTipoDanza(TipoDanza idTipoDanza) {
        this.idTipoDanza = idTipoDanza;
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
        if (!(object instanceof GrupoClase)) {
            return false;
        }
        GrupoClase other = (GrupoClase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GrupoClase[ id=" + id + " ]";
    }
    
}
