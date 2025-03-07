import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { QRCodeSVG } from "qrcode.react";
import ComprarEntrada from "../components/ComprarEntrada";
import ".././Menu.css";

function Menu() {
  const navigate = useNavigate();
  const { userId } = useParams();
  const [conciertos, setConciertos] = useState([]);
  const [entradas, setEntradas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState("conciertos");
  const [qrModalOpen, setQrModalOpen] = useState(false);
  const [selectedEntrada, setSelectedEntrada] = useState(null);

  useEffect(() => {
    const obtenerDatos = async () => {
      try {
        const conciertoResponse = await fetch("http://localhost:8080/conciertos");
        if (!conciertoResponse.ok) {
          throw new Error(`Error al cargar conciertos: ${conciertoResponse.status}`);
        }
        const conciertoData = await conciertoResponse.json();
        setConciertos(conciertoData);

        const entradasResponse = await fetch(`http://localhost:8080/entradas/usuario/${userId}`);
        if (!entradasResponse.ok) {
          throw new Error(`Error al cargar entradas: ${entradasResponse.status}`);
        }
        const entradasData = await entradasResponse.json();
        setEntradas(entradasData);
        
        setLoading(false);
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    obtenerDatos();
  }, [userId]);

  const formatearFecha = (fechaString) => {
    if (!fechaString) return "";
    const fecha = new Date(fechaString);
    return fecha.toLocaleDateString();
  };

  const formatearPrecio = (precio) => {
    if (!precio) return "Precio no disponible";
    return `${Number(precio).toFixed(2)} €`;
  };

  const handleEditarPerfil = () => {
    navigate(`/editar-usuario/${userId}`);
  };

  const actualizarDatosTrasCompra = async () => {
    try {
      const conciertoResponse = await fetch("http://localhost:8080/conciertos");
      const conciertoData = await conciertoResponse.json();
      setConciertos(conciertoData);

      const entradasResponse = await fetch(`http://localhost:8080/entradas/usuario/${userId}`);
      const entradasData = await entradasResponse.json();
      setEntradas(entradasData);
    } catch (err) {
      setError("Error al actualizar datos tras la compra");
    }
  };

  const handleDevolverEntrada = async (entradaId) => {
    try {
      const response = await fetch(`http://localhost:8080/entradas/devolver/${entradaId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error('Error al devolver la entrada');
      }

      actualizarDatosTrasCompra();
    } catch (error) {
      setError(`Error al devolver entrada: ${error.message}`);
    }
  };

  const handleEliminarEntrada = async (entradaId) => {
    try {
      const response = await fetch(`http://localhost:8080/entradas/${entradaId}`, {
        method: 'DELETE'
      });

      if (!response.ok) {
        throw new Error('Error al eliminar la entrada');
      }

      actualizarDatosTrasCompra();
    } catch (error) {
      setError(`Error al eliminar entrada: ${error.message}`);
    }
  };

  const handleShowQR = (entrada) => {
    setSelectedEntrada(entrada);
    setQrModalOpen(true);
  };

  const generateQRContent = (entrada) => {
    const qrData = {
      entradaId: entrada.id,
      concierto: entrada.concierto.nombre,
      lugar: entrada.concierto.lugar,
      fecha: entrada.concierto.fecha,
      usuario: userId,
      fechaCompra: entrada.fechaCompra
    };
    return JSON.stringify(qrData);
  };

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Bienvenido a ticketer</h1>
        <div className="user-actions">
          <button className="edit-profile-btn" onClick={handleEditarPerfil}>Editar perfil</button>
          <button className="logout-btn" onClick={() => navigate("/")}>Cerrar sesion</button>
        </div>
      </header>

      <div className="tabs-container">
        <button 
          className={`tab-button ${activeTab === "conciertos" ? "active" : ""}`} 
          onClick={() => setActiveTab("conciertos")}
        >
          Conciertos Disponibles
        </button>
        <button 
          className={`tab-button ${activeTab === "entradas" ? "active" : ""}`} 
          onClick={() => setActiveTab("entradas")}
        >
          Mis Entradas
        </button>
      </div>

      <main className="dashboard-content">
        {activeTab === "conciertos" && (
          <section className="conciertos-section">
            <h2>Conciertos Disponibles</h2>

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
                <p>No hay conciertos disponibles en este momento.</p>
              </div>
            ) : (
              <div className="conciertos-grid">
                {conciertos.map((concierto) => (
                  <div className="concierto-card" key={concierto.id}>
                    <h3>{concierto.nombre}</h3>
                    <p className="concierto-description">{concierto.descripcion}</p>
                    <div className="concierto-details">
                      <span>Lugar: {concierto.lugar}</span>
                      <span>Capacidad disponible: {concierto.capacidad}</span>
                    </div>
                    <p className="concierto-date">Fecha: {formatearFecha(concierto.fecha)}</p>
                    <p className="concierto-price">Precio: {formatearPrecio(concierto.precioEntrada)}</p>
                    {concierto.estado && (
                      <div className="concierto-status">
                        <span className={`status-badge ${concierto.estado.nombre.toLowerCase()}`}>{concierto.estado.nombre}</span>
                      </div>
                    )}
                    {concierto.capacidad > 0 ? (
                      <ComprarEntrada 
                        conciertoId={concierto.id} 
                        userId={userId} 
                        precioEntrada={concierto.precioEntrada}
                        onCompraExitosa={actualizarDatosTrasCompra}
                      />
                    ) : (
                      <div className="agotado">Entradas agotadas</div>
                    )}
                  </div>
                ))}
              </div>
            )}
          </section>
        )}

        {activeTab === "entradas" && (
          <section className="entradas-section">
            <h2>Mis Entradas</h2>
            
            {loading ? (
              <div className="loading-container">
                <p>Cargando entradas...</p>
              </div>
            ) : error ? (
              <div className="error-container">
                <p>Error al cargar las entradas: {error}</p>
              </div>
            ) : entradas.length === 0 ? (
              <div className="empty-container">
                <p>No has comprado entradas todavía.</p>
              </div>
            ) : (
              <div className="entradas-grid">
                {entradas.map((entrada) => (
                  <div className="entrada-card" key={entrada.id}>
                    <h3>{entrada.concierto.nombre}</h3>
                    <p className="entrada-details">Lugar: {entrada.concierto.lugar}</p>
                    <p className="entrada-date">Fecha: {formatearFecha(entrada.concierto.fecha)}</p>
                    <p className="entrada-price">Precio pagado: {formatearPrecio(entrada.precio)}</p>
                    <p className="entrada-purchase-date">Comprada el: {new Date(entrada.fechaCompra).toLocaleString()}</p>
                    {entrada.estado && (
                      <div className="entrada-status">
                        <span className={`status-badge ${entrada.estado.nombre.toLowerCase()}`}>{entrada.estado.nombre}</span>
                      </div>
                    )}
                    
                    <div className="entrada-actions">
                      {entrada.estado && entrada.estado.id === 1 && (
                        <>
                          <button 
                            className="qr-btn"
                            onClick={() => handleShowQR(entrada)}
                          >
                            Ver QR
                          </button>
                          <button 
                            className="devolver-btn"
                            onClick={() => handleDevolverEntrada(entrada.id)}
                          >
                            Devolver entrada
                          </button>
                        </>
                      )}
                      
                      {entrada.estado && entrada.estado.id === 2 && (
                        <button 
                          className="eliminar-btn"
                          onClick={() => handleEliminarEntrada(entrada.id)}
                        >
                          Borrar entrada
                        </button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </section>
        )}
      </main>

      {qrModalOpen && selectedEntrada && (
        <div className="qr-modal-overlay" onClick={() => setQrModalOpen(false)}>
          <div className="qr-modal-content" onClick={(e) => e.stopPropagation()}>
            <h3>Entrada para {selectedEntrada.concierto.nombre}</h3>
            <div className="qr-container">
              <QRCodeSVG 
                value={generateQRContent(selectedEntrada)} 
                size={200}
                level={"H"}
                includeMargin={true}
              />
            </div>
            <p>ID Entrada: {selectedEntrada.id}</p>
            <p>Fecha: {formatearFecha(selectedEntrada.concierto.fecha)}</p>
            <p>Lugar: {selectedEntrada.concierto.lugar}</p>
            <button className="close-qr-btn" onClick={() => setQrModalOpen(false)}>Cerrar</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Menu;