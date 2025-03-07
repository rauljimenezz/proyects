import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function CrearConcierto() {
  const navigate = useNavigate();
  
  const [concierto, setConcierto] = useState({
    nombre: "",
    descripcion: "",
    lugar: "",
    capacidad: "",
    fecha: "",
    precioEntrada: ""
  });
  const [organizadores, setOrganizadores] = useState([]);
  const [selectedOrganizadorId, setSelectedOrganizadorId] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const cargarOrganizadores = async () => {
      try {
        setLoading(true);
        const response = await fetch("http://localhost:8080/usuarios");
        if (!response.ok) throw new Error("No se pudieron cargar los organizadores");
        
        const usuarios = await response.json();
        const soloOrganizadores = usuarios.filter(usuario => 
          usuario.roles.some(rol => rol.nombre === "organizador")
        );
        
        setOrganizadores(soloOrganizadores);
        if (soloOrganizadores.length > 0) {
          setSelectedOrganizadorId(soloOrganizadores[0].id);
        }
      } catch (err) {
        console.error("Error al cargar organizadores:", err);
        setError("No se pudieron cargar los organizadores. Por favor, intente más tarde.");
      } finally {
        setLoading(false);
      }
    };

    cargarOrganizadores();
  }, []);

  const handleChange = (e) => {
    setConcierto({
      ...concierto,
      [e.target.name]: e.target.value,
    });
  };

  const handleOrganizadorChange = (e) => {
    setSelectedOrganizadorId(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!selectedOrganizadorId) {
      alert("Por favor, seleccione un organizador.");
      return;
    }

    try {
      console.log("Enviando concierto con organizadorId:", selectedOrganizadorId);
      const response = await fetch(`http://localhost:8080/conciertos/crear?organizadorId=${selectedOrganizadorId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(concierto),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.mensaje || "Error al crear el concierto");
      }

      navigate(`/menu-admin`);

      const data = await response.json();
      setConcierto({
        nombre: "",
        descripcion: "",
        lugar: "",
        capacidad: "",
        fecha: "",
        precioEntrada: ""
      });
    } catch (error) {
      console.error("Error al crear concierto:", error);
      alert("Hubo un error al crear el concierto: " + error.message);
    }
  };

  if (loading) return <div>Cargando organizadores...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="pagina-body">
      <div className="contenedor">
        <h1 className="titulo">Crear Concierto</h1>
        <form className="formulario" onSubmit={handleSubmit}>
          <select value={selectedOrganizadorId} onChange={handleOrganizadorChange} required className="campo">
            <option value="">Seleccionar Organizador</option>
            {organizadores.map(org => (
              <option key={org.id} value={org.id}>
                {org.nombre} {org.apellidos} ({org.email})
              </option>
            ))}
          </select>
          
          <input className="campo" type="text" name="nombre" placeholder="Nombre del concierto" value={concierto.nombre} onChange={handleChange} required/>
          <textarea className="campo" name="descripcion" placeholder="Descripción" value={concierto.descripcion} onChange={handleChange} required rows="3"/>
          <input className="campo" type="text" name="lugar" placeholder="Lugar" value={concierto.lugar} onChange={handleChange} required/>
          <input className="campo" type="number" name="capacidad" placeholder="Capacidad" value={concierto.capacidad} onChange={handleChange} required/>
          <input className="campo" type="date" name="fecha" value={concierto.fecha} onChange={handleChange} required/>
          <input className="campo" type="number" name="precioEntrada" placeholder="Precio de entrada (€)" value={concierto.precioEntrada} onChange={handleChange} required step="0.01" min="0"/>
          <button className="boton" type="submit">Crear Concierto</button>
        </form>

        <button className="boton" style={{ backgroundColor: "#718096" }} onClick={() => navigate("/menu-admin")}>Volver al menú</button>
      </div>
    </div>
  );
}

export default CrearConcierto;