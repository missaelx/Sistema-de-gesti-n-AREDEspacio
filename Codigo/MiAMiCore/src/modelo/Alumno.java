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
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({ 
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a WHERE a.activo = 1")
        
    //, @NamedQuery(name = "Alumno.findReinscripcionesProximas", query = "SELECT a FROM Alumno a WHERE a.fechaInscripcion BETWEEN :inicio AND :fin")
    //, @NamedQuery(name = "Alumno.findMensualidadesProximas", query = "SELECT a FROM Alumno a WHERE a.diapago BETWEEN :inicio AND :fin")
    , @NamedQuery(name = "Alumno.findReinscripcionesProximas", query = "SELECT a FROM Alumno a WHERE a.fechaInscripcion BETWEEN :inicio AND :fin AND a.activo =1")
    , @NamedQuery(name = "Alumno.findMensualidadesProximas", query = "SELECT a FROM Alumno a WHERE a.diapago BETWEEN :inicio AND :fin and a.activo = 1")
    , @NamedQuery(name = "Alumno.findNoInscritosAGrupo", query = "SELECT a FROM Alumno a WHERE :grupo NOT MEMBER OF a.grupoClaseList and a.activo = 1")    
        
    , @NamedQuery(name = "Alumno.findById", query = "SELECT a FROM Alumno a WHERE a.id = :id")
    , @NamedQuery(name = "Alumno.findByCorreo", query = "SELECT a FROM Alumno a WHERE (0 < LOCATE(:correo, a.correo)) AND a.activo = 1")
    , @NamedQuery(name = "Alumno.findByFechaNacimiento", query = "SELECT a FROM Alumno a WHERE a.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Alumno.findByNombre", query = "SELECT a FROM Alumno a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumno.findByNombreLike", query = "SELECT a FROM Alumno a WHERE ( (0 < LOCATE(:nombre, a.nombre)) OR (0 < LOCATE(:nombre, a.apellidos)) )AND a.activo = 1")
    , @NamedQuery(name = "Alumno.findByApellidos", query = "SELECT a FROM Alumno a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumno.findByTelefono", query = "SELECT a FROM Alumno a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumno.findByTelefonoEmergencia", query = "SELECT a FROM Alumno a WHERE a.telefonoEmergencia = :telefonoEmergencia")
    , @NamedQuery(name = "Alumno.findByTipoSangre", query = "SELECT a FROM Alumno a WHERE a.tipoSangre = :tipoSangre")
    , @NamedQuery(name = "Alumno.findByActivo", query = "SELECT a FROM Alumno a WHERE a.activo = :activo")
    , @NamedQuery(name = "Alumno.findByDiapago", query = "SELECT a FROM Alumno a WHERE a.diapago = :diapago")})
public class Alumno implements Serializable {

    @Column(name = "fecha_inscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;

    @Column(name = "foto")
    private String foto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @Temporal(TemporalType.DATE)
    private Date diapago;
    @JoinTable(name = "alumno_grupo_aux", joinColumns = {
        @JoinColumn(name = "idalumno", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idclase", referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.MERGE})
    private List<GrupoClase> grupoClaseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalumno")
    private List<Mensualidad> mensualidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idalumno")
    private List<Inscripcion> inscripcionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAlumno")
    private List<Asistencia> asistenciaList;

    public Alumno() {
    }

    public Alumno(Integer id) {
        this.id = id;
    }

    public Alumno(Integer id, Date fechaNacimiento, String nombre, String apellidos, String telefonoEmergencia, boolean activo, Date diapago) {
        this.id = id;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefonoEmergencia = telefonoEmergencia;
        this.activo = activo;
        this.diapago = diapago;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDiapago() {
        return diapago;
    }

    public void setDiapago(Date diapago) {
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
    public List<Mensualidad> getMensualidadList() {
        return mensualidadList;
    }

    public void setMensualidadList(List<Mensualidad> mensualidadList) {
        this.mensualidadList = mensualidadList;
    }

    @XmlTransient
    public List<Inscripcion> getInscripcionList() {
        return inscripcionList;
    }

    public void setInscripcionList(List<Inscripcion> inscripcionList) {
        this.inscripcionList = inscripcionList;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
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
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Alumno[ id=" + id + " ]";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
    
}
