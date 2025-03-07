import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ".././Menu.css";

function EditarConcierto() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [conciertos, setConciertos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [modoEdicion, setModoEdicion] = useState(false);
    const [conciertoSeleccionado, setConciertoSeleccionado] = useState({
        nombre: "",
        descripcion: "",
        lugar: "",
        capacidad: "",
        fecha: "",
        precioEntrada: ""
    });
    const [organizadores, setOrganizadores] = useState([]);
    const [selectedOrganizadorId, setSelectedOrganizadorId] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    useEffect(() => {
        const cargarConciertos = async () => {
            try {
                setLoading(true);
                const response = await fetch("http://localhost:8080/conciertos");
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                const data = await response.json();
                setConciertos(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        cargarConciertos();
    }, []);

    useEffect(() => {
        const cargarOrganizadores = async () => {
            try {
                const response = await fetch("http://localhost:8080/usuarios");
                if (!response.ok) throw new Error("No se pudieron cargar los organizadores");

                const usuarios = await response.json();
                const soloOrganizadores = usuarios.filter(usuario =>
                    usuario.roles.some(rol => rol.nombre === "organizador")
                );

                setOrganizadores(soloOrganizadores);
            } catch (err) {
                console.error("Error al cargar organizadores:", err);
            }
        };

        if (modoEdicion) {
            cargarOrganizadores();
        }
    }, [modoEdicion]);

    useEffect(() => {
        const cargarDatosConcierto = async () => {
            if (id) {
                try {
                    const response = await fetch(`http://localhost:8080/conciertos/${id}`);
                    if (!response.ok) throw new Error("No se pudo cargar el concierto");

                    const data = await response.json();
                    setConciertoSeleccionado({
                        nombre: data.nombre || "",
                        descripcion: data.descripcion || "",
                        lugar: data.lugar || "",
                        capacidad: data.capacidad || "",
                        fecha: data.fecha || "",
                        precioEntrada: data.precioEntrada || ""
                    });

                    if (organizadores.length > 0) {
                        const organizadorEncontrado = organizadores.find(
                            org => org.nombre === data.organizadorNombre
                        );

                        if (organizadorEncontrado) {
                            setSelectedOrganizadorId(organizadorEncontrado.id);
                        } else {
                            setSelectedOrganizadorId(organizadores[0].id);
                        }
                    }

                    setModoEdicion(true);
                } catch (err) {
                    console.error("Error al cargar datos del concierto:", err);
                    setError("No se pudo cargar el concierto seleccionado.");
                }
            }
        };

        cargarDatosConcierto();
    }, [id, organizadores]);

    const handleEditar = async (conciertoId) => {
        navigate(`/editar-concierto/${conciertoId}`);
    };

    const formatearFecha = (fechaString) => {
        if (!fechaString) return "";
        const fecha = new Date(fechaString);
        return fecha.toLocaleDateString();
    };

    const formatearPrecio = (precio) => {
        if (!precio) return "Precio no disponible";
        return `${Number(precio).toFixed(2)} €`;
    };

    const handleChange = (e) => {
        setConciertoSeleccionado({
            ...conciertoSeleccionado,
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
            const response = await fetch(`http://localhost:8080/conciertos/${id}?organizadorId=${selectedOrganizadorId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(conciertoSeleccionado),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.mensaje || "Error al actualizar el concierto");
            }

            setSuccessMessage("Concierto actualizado correctamente");
            setTimeout(() => {
                navigate("/editar-concierto");
                setModoEdicion(false);
            }, 0);
        } catch (error) {
            console.error("Error al actualizar concierto:", error);
            alert("Hubo un error al actualizar el concierto: " + error.message);
        }
    };

    const cancelarEdicion = () => {
        navigate("/editar-concierto");
        setModoEdicion(false);
    };

    if (modoEdicion) {
        return (
            <div className="pagina-body">
                <div className="contenedor">
                    <h1 className="titulo">Editar Concierto</h1>
                    <form className="formulario" onSubmit={handleSubmit}>
                        <select value={selectedOrganizadorId} onChange={handleOrganizadorChange} required className="campo">
                            <option value="">Seleccionar Organizador</option>
                            {organizadores.map(org => (
                                <option key={org.id} value={org.id}>
                                    {org.nombre} {org.apellidos || ""} ({org.email || ""})
                                </option>
                            ))}
                        </select>
                        <input className="campo" type="text" name="nombre" placeholder="Nombre del concierto" value={conciertoSeleccionado.nombre} onChange={handleChange} required />
                        <textarea className="campo" name="descripcion" placeholder="Descripción" value={conciertoSeleccionado.descripcion} onChange={handleChange} required rows="3" />
                        <input className="campo" type="text" name="lugar" placeholder="Lugar" value={conciertoSeleccionado.lugar} onChange={handleChange} required />
                        <input className="campo" type="number" name="capacidad" placeholder="Capacidad" value={conciertoSeleccionado.capacidad} onChange={handleChange} required />
                        <input className="campo" type="date" name="fecha" value={conciertoSeleccionado.fecha} onChange={handleChange} required />
                        <input className="campo" type="number" name="precioEntrada" placeholder="Precio de entrada (€)" value={conciertoSeleccionado.precioEntrada} onChange={handleChange} required step="0.01" min="0" />
                        <div className="botones-grupo">
                            <button className="boton" type="submit">Guardar Cambios</button>
                            <button className="boton" type="button" style={{ backgroundColor: "#718096" }} onClick={cancelarEdicion}>Cancelar</button>
                        </div>
                        {successMessage && (
                            <div className="mensaje-exito">{successMessage}</div>
                        )}
                    </form>
                </div>
            </div>
        );
    }

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <h1>Editar Conciertos</h1>
                <div className="user-actions">
                    <button className="logout-btn" onClick={() => navigate("/menu-admin")}>Volver al panel de administracion</button>
                </div>
            </header>

            <main className="dashboard-content">
                <section className="conciertos-section">
                    <h2>Listado de Conciertos</h2>

                    {loading ? (
                        <div className="loading-container">
                            <p>Cargando conciertos...</p>
                        </div>
                    ) : error ? (
                        <div className="error-container">
                            <p>Error al cargar los conciertos: {error}</p>
                        </div>
                    ) : conciertos.length === 0 ? (
                        <div className="empty-container">
                            <p>No hay conciertos disponibles para editar.</p>
                        </div>
                    ) : (
                        <div className="conciertos-grid">
                            {conciertos.map((concierto) => (
                                <div className="concierto-card" key={concierto.id}>
                                    <h3>{concierto.nombre}</h3>
                                    <p className="concierto-description">{concierto.descripcion}</p>
                                    <div className="concierto-details">
                                        <span>Lugar: {concierto.lugar}</span>
                                        <span>Capacidad: {concierto.capacidad}</span>
                                    </div>
                                    <p className="concierto-date">Fecha: {formatearFecha(concierto.fecha)}</p>
                                    <p className="concierto-price">Precio: {formatearPrecio(concierto.precioEntrada)}</p>
                                    {concierto.estado && (
                                        <div className="concierto-status">
                                            <span className={`status-badge ${concierto.estado.nombre?.toLowerCase()}`}>{concierto.estado.nombre}</span>
                                        </div>
                                    )}
                                    <button className="comprar-btn editar-btn" onClick={() => handleEditar(concierto.id)}>Editar Concierto</button>
                                </div>
                            ))}
                        </div>
                    )}
                </section>
            </main>
        </div>
    );
}

export default EditarConcierto;