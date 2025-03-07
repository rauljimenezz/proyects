import { useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

export default function CrearUsuario() {
  const { register, handleSubmit, reset } = useForm();
  const [mensajeError, setMensajeError] = useState("");
  const navigate = useNavigate();

  const onSubmit = async (data) => {
    data.roles = Array.isArray(data.roles) ? data.roles.map((rol) => parseInt(rol)) : [parseInt(data.roles)];

    console.log("Datos procesados antes de enviar:", data);

    try {
      const response = await fetch("http://localhost:8080/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });

      if (!response.ok) throw new Error("Error al crear usuario");

      reset();
      setMensajeError("");
      navigate(`/`);
    } catch (error) {
      setMensajeError("Error: " + error.message);
    }
  };

  return (
    <div className="pagina-body">
      <div className="contenedor">
        <h1 className="titulo">Crear Usuario</h1>
        <form className="formulario" onSubmit={handleSubmit(onSubmit)}>
          <input className="campo" {...register("nombre")} placeholder="Nombre" required/>
          <input className="campo" {...register("apellidos")} placeholder="Apellidos" required/>
          <input className="campo" type="email" {...register("email")} placeholder="Email" required/>
          <input className="campo" type="password" {...register("contraseña")} placeholder="Contraseña" required/>
          <input className="campo" type="date" {...register("fechaNacimiento")} required/>
          <select className="campo" {...register("roles")} multiple>
            <option value="1">Organizador</option>
            <option value="2">Asistente</option>
          </select>
          
          <button className="boton" type="submit">Crear Usuario</button>
        </form>

        {mensajeError && <p className="mensaje">{mensajeError}</p>}

        <button className="boton" style={{backgroundColor: "#718096"}} onClick={() => navigate("/")} >Volver a Inicio</button>
      </div>
    </div>
  );
}