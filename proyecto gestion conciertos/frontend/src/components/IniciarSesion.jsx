import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ".././Estilos.css";

export default function IniciarSesion() {
    const [email, setEmail] = useState("");
    const [contraseña, setContraseña] = useState("");
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setMensaje("");

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                credentials: "include",
                body: JSON.stringify({ email, contraseña }),
            });

            if (!response.ok) throw new Error("Credenciales incorrectas");

            const data = await response.json();
            setMensaje(data.mensaje);

            if (data.rol === "organizador") {
                navigate("/menu-admin");
            } else {
                navigate(`/menu/${data.id}`);
            }
        } catch (error) {
            setMensaje(error.message);
        }
    };

    return (
        <div className="pagina-body">
            <div className="contenedor">
                <h1 className="titulo">Iniciar Sesión</h1>
                <form className="formulario" onSubmit={handleLogin}>
                    <input className="campo" type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                    <input className="campo" type="password" placeholder="Contraseña" value={contraseña} onChange={(e) => setContraseña(e.target.value)} required/>
                    <button className="boton" type="submit">Iniciar Sesión</button>
                </form>
                {mensaje && <p className="mensaje">{mensaje}</p>}
                <button className="boton" style={{backgroundColor: "#718096"}} onClick={() => navigate("/")}>Volver a Inicio</button>
            </div>
        </div>
    );
}