import Header from "../components/Header";
import "../assets/css/Site.css";
import { Link } from "react-router-dom";
import { useState } from "react";
import useAuth from "../hooks/useAuth";
import { Title } from "../logic/Title";
import axios from "../api/axios";
import { useNavigate } from "react-router-dom";
import {toast} from "react-toastify";

const mainStyle = {
  minHeight: "100vh",
  backgroundColor: " #DCDCDC",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  padding: "20px",
};

const UploadDoc = () => {
  Title("Subir Archivo");

  const [file, setFile] = useState();
  const [filename, setFilename] = useState();
  const [userID, setUserID] = useState();
  const { auth } = useAuth();

  let UPLOAD_URL = "http://localhost:8000/files/upload";

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    setUserID(auth?.userID);

    let user = JSON.parse(sessionStorage.getItem("user"));

    const formData = new FormData();
    formData.append("file", file);
    formData.append("filename", filename);
    formData.append("userID", user?.idUser);

    try {
      const response = await axios.post(UPLOAD_URL, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      toast.success("Subido Correctamente")
      navigate("/myDocuments")
    } catch (err) {
      toast.error("No tienes permisos para subir este documento")
    }
  };

  const handleChange = (e) => {
    setFile(e.target.files[0]);
    setFilename(e.target.files[0].name);
  };

  return (
    <div>
      <Header />
      <main style={mainStyle}>
        <div
          className="center-container"
          style={{ minHeight: "70vh", minWidth: "50%" }}
        >
          <h3 className="header-dark">Subir Archivo</h3>
          <h1 style={{ color: "#191970" }}>
            <i className="fa fa-cloud-upload fa-2x"></i>
          </h1>
          <form>
            <div>
              <label
                htmlFor="fileUp"
                className={
                  !file
                    ? "file-input-label-unselected"
                    : "file-input-label-selected"
                }
              >
                <input
                  id="fileUp"
                  style={{ display: "none" }}
                  type="file"
                  onChange={handleChange}
                ></input>
                <b>Elegir Archivo</b>
              </label>
            </div>
            <hr />
            <div>
              <button
                className="rounded-button"
                style={{
                  textDecoration: "none",
                  backgroundColor: "dodgerblue",
                  color: "white",
                  padding: "10px",
                }}
                onClick={handleSubmit}>
                
                <b>Enviar</b>
                
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default UploadDoc;
