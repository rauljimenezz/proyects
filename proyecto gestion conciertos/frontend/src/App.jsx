import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import CrearUsuario from "./components/CrearUsuarios";
import IniciarSesion from "./components/IniciarSesion";
import Menu from "./pages/Menu";
import MenuAdmin from "./pages/MenuAdmin";
import CrearConcierto from "./components/CrearConcierto";
import EditarUsuario from "./components/EditarUsuario";
import EditarConcierto from "./components/EditarConcierto";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/crear-usuario" element={<CrearUsuario />} />
        <Route path="/iniciar-sesion" element={<IniciarSesion />} />
        <Route path="/menu/:userId" element={<Menu />} />
        <Route path="/editar-usuario/:userId" element={<EditarUsuario />} />
        <Route path="/menu-admin" element={<MenuAdmin />} />
        <Route path="/crear-concierto" element={<CrearConcierto />} />
        <Route path="/editar-concierto" element={<EditarConcierto />} />
        <Route path="/editar-concierto/:id" element={<EditarConcierto />} />
      </Routes>
    </Router>
  );
}

export default App;
