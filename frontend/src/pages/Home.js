import Header from "../components/Header";
import { Title } from "../logic/Title";
import axios from "../api/axios";

const mainStyle = {
    minHeight: "100vh",
    backgroundImage: `linear-gradient(to bottom right, #ffe866 0%, #ccff66 100%)`,
    backgroundSize: "cover",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
};
const titleStyle = {
    color: "#191970",
    fontSize: "5rem",
    textAlign: "center",
    width: "70%",
};

const Home = () => {
    Title("Pagina de Inicio");
    return(
        <main style={{display:"block"}}>
            <Header/>
            <div style={mainStyle}>
                <h3 style={titleStyle}>
                    Bienvenido
                    <br></br>
                    <br></br>
                    Gestor Documental
                </h3>
            </div>
        </main>
    )
}

export default Home;