import { useState, useEffect } from "react";
import "../assets/css/Site.css";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "../api/axios";
import useAuth from "../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { Title } from "../logic/Title";
import {toast} from "react-toastify";

const LOGIN_URL = "http://localhost:8000/api/v1/user/login";

const mainStyle = {
  minHeight: "100vh",
  backgroundColor: "dodgerblue",
  backgroundImage: `url("https://upload.wikimedia.org/wikipedia/commons/d/d7/Desfile_de_boyeros_en_San_Isidro_de_Heredia.jpg")`,
  backgroundSize: "cover",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
};

const formStyle = {
  textAlign: "center",
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
  gap: "30px",
};

const Login = () => {
  Title("Ingreso");

  const { auth, setAuth } = useAuth();
  const [email, setEmail] = useState("");
  const [pwd, setPwd] = useState("");
  const [errMsg, setErrMsg] = useState("");

  useEffect(() => {
    setErrMsg("");
  }, [email, pwd]);

  const navigate = useNavigate();

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        LOGIN_URL,
        JSON.stringify({ email, passwordEncrypt: pwd }),
        {
          headers: { "Content-Type": "application/json" },
          //withCredentials: true // Esto no permite un correcto login
        }
      );
      const roles = response?.data?.role ? [0, 1] : [0];
      const userID = response?.data?.idUser;
      sessionStorage.setItem("user", JSON.stringify(response?.data));
      setAuth({ roles, userID });
      setEmail("");
      setPwd("");
      return navigate("/home", { replace: true });
    } catch (err) {
      if (!err?.response) {
        setErrMsg("El servidor no responde");
      } else if (err.response?.status === 400) {
        setErrMsg("Campos faltantes");
      } else if (err.response?.status === 401) {
        setErrMsg("Credenciales erroneos");
      } else {
        setErrMsg("Ingreso fallido");
      }
    }
  };

  return (
    <main style={mainStyle}>
      <div
        className="center-container"
        style={{ width: "400px", minHeight: "500px" }}
      >
        <img
          alt="Logo municipalidad"
          src={require("../assets/logos/municipalidad_logo.png")}
          style={{ maxWidth: "300px" }}
        ></img>
        <h1 style={{ color: "#191970" }}>
          Digite sus
          <br />
          Credenciales
        </h1>

        <form onSubmit={handleLoginSubmit} style={formStyle}>
          <input
            type="text"
            id="email"
            className="rounded-input-block"
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Correo electronico"
          ></input>

          <input
            type="password"
            id="pwd"
            className="rounded-input-block"
            onChange={(e) => setPwd(e.target.value)}
            placeholder="Contraseña"
          ></input>

          <div
            className={
              errMsg ? "alert alert-danger alert-dismissible fade show" : "hide"
            }
            role="alert"
            style={{ margin: "0px" }}
          >
            {errMsg}
          </div>

          <button
            disabled={!email || !pwd ? true : false}
            className={
              !email || !pwd ? "rounded-button-dark-d" : "rounded-button-dark"
            }
            style={{ width: "100%" }}
          >
            Ingresar
          </button>
        </form>
      </div>
    </main>
  );
};

export default Login;
