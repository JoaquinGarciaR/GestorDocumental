import { Title } from "../logic/Title";

const Unauthorized = () => {
    Title("Ingreso no Autorizado");
    return(
        <main style={{margin:"20px"}}>
            <br/>
            <hr/>
            <h1>Usted no tiene permiso de acceder a esta página.</h1>
            <h1>Revise su sesión.</h1>
            <p>Error 401</p>
            <hr/>
        </main>
    )
}

export default Unauthorized;