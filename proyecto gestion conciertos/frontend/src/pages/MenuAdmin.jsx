import { useNavigate } from "react-router-dom";
import ".././Menu.css";

function MenuAdmin() {
  const navigate = useNavigate();
  
  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>Panel de Administración</h1>
        <div className="user-actions">
          <button className="logout-btn" onClick={() => navigate("/")}>Cerrar Sesión</button>
        </div>
      </header>
            
      <main className="dashboard-content">
        <section className="admin-section">
          <h2 className="admin-section-title">Gestión de Eventos</h2>
          <div className="admin-cards-container">
            <div className="admin-card">
              <h3 className="admin-card-title">Crear Concierto</h3>
              <p className="admin-card-description">Añade un nuevo concierto al sistema.</p>
              <button className="admin-card-button" onClick={() => navigate("/crear-concierto")}>
                Crear Concierto
              </button>
            </div>
            
            <div className="admin-card">
              <h3 className="admin-card-title">Editar Concierto</h3>
              <p className="admin-card-description">Modifica la información de conciertos existentes.</p>
              <button className="admin-card-button" onClick={() => navigate("/editar-concierto")}>
                Editar Concierto
              </button>
            </div>
            
            <div className="admin-card">
              <h3 className="admin-card-title">Eliminar Concierto</h3>
              <p className="admin-card-description">Elimina conciertos del sistema.</p>
              <button className="admin-card-button" onClick={() => navigate("/eliminar-concierto")}>
                Eliminar Concierto
              </button>
            </div>
          </div>
        </section>
        
        <section className="admin-section">
          <h2 className="admin-section-title">Gestión de Usuarios</h2>
          <div className="admin-cards-container">
            <div className="admin-card">
              <h3 className="admin-card-title">Administrar Usuarios</h3>
              <p className="admin-card-description">Gestiona los usuarios registrados en el sistema.</p>
              <button className="admin-card-button" onClick={() => navigate("/gestionar-usuarios")}>
                Gestionar Usuarios
              </button>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}

export default MenuAdmin;