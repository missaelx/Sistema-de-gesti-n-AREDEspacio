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
@Table(name = "maestros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestros.findAll", query = "SELECT m FROM Maestros m")
    , @NamedQuery(name = "Maestros.findByIdmaestro", query = "SELECT m FROM Maestros m WHERE m.idmaestro = :idmaestro")
    , @NamedQuery(name = "Maestros.findByNombre", query = "SELECT m FROM Maestros m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Maestros.findByApellidos", query = "SELECT m FROM Maestros m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Maestros.findByCorreo", query = "SELECT m FROM Maestros m WHERE m.correo = :correo")
    , @NamedQuery(name = "Maestros.findByTelefono", query = "SELECT m FROM Maestros m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Maestros.findByActivo", query = "SELECT m FROM Maestros m WHERE m.activo = :activo")})
public class Maestros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmaestro")
    private Integer idmaestro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "activo")
    private String activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmaestro")
    private List<Pagosdesalario> pagosdesalarioList;

    public Maestros() {
    }

    public Maestros(Integer idmaestro) {
        this.idmaestro = idmaestro;
    }

    public Maestros(Integer idmaestro, String activo) {
        this.idmaestro = idmaestro;
        this.activo = activo;
    }

    public Integer getIdmaestro() {
        return idmaestro;
    }

    public void setIdmaestro(Integer idmaestro) {
        this.idmaestro = idmaestro;
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<Pagosdesalario> getPagosdesalarioList() {
        return pagosdesalarioList;
    }

    public void setPagosdesalarioList(List<Pagosdesalario> pagosdesalarioList) {
        this.pagosdesalarioList = pagosdesalarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmaestro != null ? idmaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestros)) {
            return false;
        }
        Maestros other = (Maestros) object;
        if ((this.idmaestro == null && other.idmaestro != null) || (this.idmaestro != null && !this.idmaestro.equals(other.idmaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "recursos.Maestros[ idmaestro=" + idmaestro + " ]";
    }
    
}
