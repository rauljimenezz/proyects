import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import ".././EditarUsuario.css";

export default function EditarUsuario() {
    const { register, handleSubmit, reset } = useForm();
    const [usuario, setUsuario] = useState(null);
    const [loading, setLoading] = useState(true);
    const [mensajeExito, setMensajeExito] = useState("");
    const [mensajeError, setMensajeError] = useState("");
    const navigate = useNavigate();
    const { userId } = useParams();

    useEffect(() => {
        const obtenerUsuario = async () => {
            try {
                const response = await fetch(`http://localhost:8080/usuarios/${userId}`);
                if (!response.ok) {
                    throw new Error(`Error: ${response.status}`);
                }
                const data = await response.json();
                setUsuario(data);

                const formattedDate = data.fechaNacimiento ?
                    new Date(data.fechaNacimiento).toISOString().split('T')[0] : '';

                reset({
                    nombre: data.nombre,
                    apellidos: data.apellidos,
                    email: data.email,
                    fechaNacimiento: formattedDate,
                });

                setLoading(false);
            } catch (err) {
                setMensajeError("Error al cargar datos del usuario: " + err.message);
                setLoading(false);
            }
        };

        obtenerUsuario();
    }, [userId, reset]);

    const onSubmit = async (data) => {
        if (!data.contraseña) {
            delete data.contraseña;
        }

        try {
            const response = await fetch(`http://localhost:8080/usuarios/${userId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data),
            });

            if (!response.ok) throw new Error("Error al actualizar usuario");

            setMensajeExito("Usuario actualizado correctamente");
            setMensajeError("");

            navigate(`/menu/${userId}`);
        } catch (error) {
            setMensajeError("Error: " + error.message);
            setMensajeExito("");
        }
    };

    if (loading) {
        return (
            <div className="pagina-body">
                <div className="contenedor">
                    <h1 className="titulo">Cargando datos del usuario...</h1>
                </div>
            </div>
        );
    }

    return (
        <div className="pagina-body">
            <div className="contenedor">
                <h1 className="titulo">Editar Perfil</h1>
                <form className="formulario" onSubmit={handleSubmit(onSubmit)}>
                    <div className="campo-grupo">
                        <label htmlFor="nombre">Nombre</label>
                        <input id="nombre" className="campo" {...register("nombre")} required/>
                    </div>

                    <div className="campo-grupo">
                        <label htmlFor="apellidos">Apellidos</label>
                        <input id="apellidos" className="campo" {...register("apellidos")} required/>
                    </div>

                    <div className="campo-grupo">
                        <label htmlFor="email">Email</label>
                        <input id="email" className="campo" type="email" {...register("email")} required/>
                    </div>

                    <div className="campo-grupo">
                        <label htmlFor="contraseña">Contraseña</label>
                        <input id="contraseña" className="campo" type="password" {...register("contraseña")}/>
                    </div>

                    <div className="campo-grupo">
                        <label htmlFor="fechaNacimiento">Fecha de Nacimiento</label>
                        <input id="fechaNacimiento" className="campo" type="date" {...register("fechaNacimiento")} required/>
                    </div>

                    <div className="botones-grupo">
                        <button className="boton guardar" type="submit">Guardar Cambios</button>
                        <button className="boton cancelar" type="button" onClick={() => navigate(`/menu/${userId}`)} >Cancelar</button>
                    </div>
                </form>

                {mensajeExito && <p className="mensaje exito">{mensajeExito}</p>}
                {mensajeError && <p className="mensaje error">{mensajeError}</p>}
            </div>
        </div>
    );
}