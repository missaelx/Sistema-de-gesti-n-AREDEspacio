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
    , @NamedQuery(name = "GrupoClase.findByIdGrupoClase", query = "SELECT g FROM GrupoClase g WHERE g.idGrupoClase = :idGrupoClase")
    , @NamedQuery(name = "GrupoClase.findByActivo", query = "SELECT g FROM GrupoClase g WHERE g.activo = :activo")
    , @NamedQuery(name = "GrupoClase.findByCostoMensual", query = "SELECT g FROM GrupoClase g WHERE g.costoMensual = :costoMensual")})
public class GrupoClase implements Serializable {

    @Basic(optional = false)
    @Column(name = "terminado")
    private boolean terminado;
    @Column(name = "fecha_termino")
    private String fechaTermino;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrupoClase")
    private Integer idGrupoClase;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "costo_mensual")
    private BigDecimal costoMensual;
    @ManyToMany(mappedBy = "grupoClaseList")
    private List<Alumnos> alumnosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoClase")
    private List<Horario> horarioList;
    @JoinColumn(name = "idTipoDanza", referencedColumnName = "idTipoDanza")
    @ManyToOne(optional = false)
    private TipoDanza idTipoDanza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupoClase")
    private List<Asistencias> asistenciasList;

    public GrupoClase() {
    }

    public GrupoClase(Integer idGrupoClase) {
        this.idGrupoClase = idGrupoClase;
    }

    public GrupoClase(Integer idGrupoClase, boolean activo, BigDecimal costoMensual) {
        this.idGrupoClase = idGrupoClase;
        this.activo = activo;
        this.costoMensual = costoMensual;
    }

    public Integer getIdGrupoClase() {
        return idGrupoClase;
    }

    public void setIdGrupoClase(Integer idGrupoClase) {
        this.idGrupoClase = idGrupoClase;
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

    @XmlTransient
    public List<Alumnos> getAlumnosList() {
        return alumnosList;
    }

    public void setAlumnosList(List<Alumnos> alumnosList) {
        this.alumnosList = alumnosList;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    public TipoDanza getIdTipoDanza() {
        return idTipoDanza;
    }

    public void setIdTipoDanza(TipoDanza idTipoDanza) {
        this.idTipoDanza = idTipoDanza;
    }

    @XmlTransient
    public List<Asistencias> getAsistenciasList() {
        return asistenciasList;
    }

    public void setAsistenciasList(List<Asistencias> asistenciasList) {
        this.asistenciasList = asistenciasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupoClase != null ? idGrupoClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoClase)) {
            return false;
        }
        GrupoClase other = (GrupoClase) object;
        if ((this.idGrupoClase == null && other.idGrupoClase != null) || (this.idGrupoClase != null && !this.idGrupoClase.equals(other.idGrupoClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.GrupoClase[ idGrupoClase=" + idGrupoClase + " ]";
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
    
}
