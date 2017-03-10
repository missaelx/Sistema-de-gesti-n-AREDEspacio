/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "alumnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a")
    , @NamedQuery(name = "Alumnos.findByIdalumno", query = "SELECT a FROM Alumnos a WHERE a.idalumno = :idalumno")
    , @NamedQuery(name = "Alumnos.findByCorreo", query = "SELECT a FROM Alumnos a WHERE a.correo = :correo")
    , @NamedQuery(name = "Alumnos.findByFechaNacimiento", query = "SELECT a FROM Alumnos a WHERE a.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Alumnos.findByNombre", query = "SELECT a FROM Alumnos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumnos.findByTelefono", query = "SELECT a FROM Alumnos a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumnos.findByTelefonoEmergencia", query = "SELECT a FROM Alumnos a WHERE a.telefonoEmergencia = :telefonoEmergencia")
    , @NamedQuery(name = "Alumnos.findByTipoSangre", query = "SELECT a FROM Alumnos a WHERE a.tipoSangre = :tipoSangre")
    , @NamedQuery(name = "Alumnos.findByActivo", query = "SELECT a FROM Alumnos a WHERE a.activo = :activo")
    , @NamedQuery(name = "Alumnos.findByDiapago", query = "SELECT a FROM Alumnos a WHERE a.diapago = :diapago")})
public class Alumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idalumno")
    private Integer idalumno;
    
    @Column(name = "correo")
    private String correo;
    
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Basic(optional = false)
    @Column(name = "telefono_emergencia")
    private String telefonoEmergencia;
    
    @Column(name = "tipo_sangre")
    private String tipoSangre;
    
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    
    @Basic(optional = false)
    @Column(name = "diapago")
    private int diapago;
    
    @JoinTable(name = "alumnos_grupo_aux", joinColumns = {
        @JoinColumn(name = "idalumno", referencedColumnName = "idalumno")}, inverseJoinColumns = {
        @JoinColumn(name = "idclase", referencedColumnName = "idGrupoClase")})
    @ManyToMany
    private List<GrupoClase> grupoClaseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalumno")
    private List<Inscripciones> inscripcionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalumno")
    private List<Mensualidades> mensualidadesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAlumno")
    private List<Asistencias> asistenciasList;

    public Alumnos() {
    }

    public Alumnos(Integer idalumno) {
        this.idalumno = idalumno;
    }

    public Alumnos(Integer idalumno, Date fechaNacimiento, String nombre, String apellidos, String telefonoEmergencia, boolean activo, int diapago) {
        this.idalumno = idalumno;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefonoEmergencia = telefonoEmergencia;
        this.activo = activo;
        this.diapago = diapago;
    }

    public Integer getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(Integer idalumno) {
        this.idalumno = idalumno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getDiapago() {
        return diapago;
    }

    public void setDiapago(int diapago) {
        this.diapago = diapago;
    }

    @XmlTransient
    public List<GrupoClase> getGrupoClaseList() {
        return grupoClaseList;
    }

    public void setGrupoClaseList(List<GrupoClase> grupoClaseList) {
        this.grupoClaseList = grupoClaseList;
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
        hash += (idalumno != null ? idalumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.idalumno == null && other.idalumno != null) || (this.idalumno != null && !this.idalumno.equals(other.idalumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Alumnos[ idalumno=" + idalumno + " ]";
    }
    
}
