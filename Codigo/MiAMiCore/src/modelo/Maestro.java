/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m WHERE m.activo = 1" )
    , @NamedQuery(name = "Maestro.findById", query = "SELECT m FROM Maestro m WHERE m.id = :id")
    , @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Maestro.findByApellidos", query = "SELECT m FROM Maestro m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Maestro.findByNombreLike", query = "SELECT a FROM Maestro a WHERE ( (0 < LOCATE(:nombre, a.nombre)) OR (0 < LOCATE(:nombre, a.apellidos)) )AND a.activo = 1")
    , @NamedQuery(name = "Maestro.findByCorreo", query = "SELECT m FROM Maestro m WHERE m.correo = :correo")
    , @NamedQuery(name = "Maestro.findByTelefono", query = "SELECT m FROM Maestro m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Maestro.findByActivo", query = "SELECT m FROM Maestro m WHERE m.activo = :activo")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaestro")
    private List<GrupoClase> grupoClaseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmaestro")
    private List<Pagodesalario> pagodesalarioList;

    public Maestro() {
    }

    public Maestro(Integer id) {
        this.id = id;
    }

    public Maestro(Integer id, String nombre, String apellidos, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<GrupoClase> getGrupoClaseList() {
        return grupoClaseList;
    }

    public void setGrupoClaseList(List<GrupoClase> grupoClaseList) {
        this.grupoClaseList = grupoClaseList;
    }

    @XmlTransient
    public List<Pagodesalario> getPagodesalarioList() {
        return pagodesalarioList;
    }

    public void setPagodesalarioList(List<Pagodesalario> pagodesalarioList) {
        this.pagodesalarioList = pagodesalarioList;
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
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + " " +apellidos;
    }
    
}
