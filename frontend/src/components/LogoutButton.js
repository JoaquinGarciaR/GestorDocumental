import "../assets/css/Site.css";
import  useAuth  from "../hooks/useAuth";
import { useNavigate } from "react-router-dom";

export const LogoutButton = () => {
  const { auth,setAuth } = useAuth();
  const navigate = useNavigate();

  const handleClick = () => {
    setAuth(null);
    sessionStorage.removeItem("user");
    navigate("/login");
  };

  return (

    <div
      onClick={handleClick}
      style={{
        color:"#FFD700",
        cursor:"pointer"
      }}
      className="rounded-button">
      {!auth.userID ? "Iniciar Sesion" : "Cerrar sesión"}
    </div>
  );
};
