import { Title } from "../logic/Title";


const Missing = () => {
    Title("Pagina no Encontrada");
    return(
        <main style={{margin:"20px"}}>
            <br/>
            <hr/>
            <h1>Esta página no existe, revise la URL especificada.</h1>
            <p>Error 404</p>
            <hr/>
        </main>
    )
}

export default Missing;