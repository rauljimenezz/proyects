import { useNavigate } from "react-router-dom";
import '.././Home.css';

function Home() {
  const navigate = useNavigate();

  return (
    <div className="home-container">
      <h1 className="home-title">Bienvenido a ticketer</h1>
      <div className="home-buttons">
        <button className="home-button" onClick={() => navigate("/crear-usuario")}>Registrar Usuario</button>
        <button className="home-button" onClick={() => navigate("/iniciar-sesion")}>Iniciar Sesión</button>
      </div>
    </div>
  );
}

export default Home;