import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ComprarEntrada({ conciertoId, userId, precioEntrada, onCompraExitosa }) {
  const [comprando, setComprando] = useState(false);
  const [mensaje, setMensaje] = useState("");
  const navigate = useNavigate();

  const handleComprar = async () => {
    setComprando(true);
    setMensaje("");

    try {
      const response = await fetch("http://localhost:8080/entradas/comprar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({
          conciertoId: conciertoId,
          usuarioId: userId,
          precio: precioEntrada
        }),
      });

      const data = await response.json();
      
      if (!response.ok) {
        throw new Error(data.mensaje || "Error al comprar entrada");
      }

      setMensaje(data.mensaje);
      if (onCompraExitosa) {
        onCompraExitosa();
      }
      
      setTimeout(() => {
        if (onCompraExitosa) {
          onCompraExitosa("entradas");
        }
      }, 1000);
      
    } catch (error) {
      setMensaje(error.message);
    } finally {
      setComprando(false);
    }
  };

  return (
    <div className="comprar-entrada-container">
      <button 
        className="comprar-btn" 
        onClick={handleComprar} 
        disabled={comprando}
      >
        {comprando ? "Procesando..." : "Comprar Entrada"}
      </button>
      {mensaje && <p className="mensaje">{mensaje}</p>}
    </div>
  );
}