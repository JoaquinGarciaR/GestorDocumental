import { Link } from "react-router-dom";
import '../assets/css/Navbar.css';
import '../assets/css/Site.css';
import useAuth from "../hooks/useAuth";
import LogoutLink from "./LogoutLink";
import { useState } from "react";

const Navbar = () => {
    const { auth } = useAuth();
    const [menuShow, setMenuShow] = useState(false);
    return(
        <div style={{display:"block"}}>
            <nav>
                <ul>

                    <li className="li-rightItem" >
                        <b><LogoutLink></LogoutLink></b>
                    </li>

                    <li className="li-rightItem">
                        <Link to="/home" className="white-link">Inicio</Link>
                    </li>

                    {auth.roles.includes(1) ?
                        <li className="li-rightItem" >
                            <Link to="/register" className="white-link">
                                Administrador
                            </Link>
                        </li> : <></>
                    }

                    {auth.userID ?
                        <div onClick={() => {
                            setMenuShow(true)
                        }}>
                            <li className="li-leftItem">
                                <i className="fa fa-bars"
                                   style={{cursor: "pointer", color: "white", paddingLeft: "20px"}}></i>
                            </li>
                        </div> : <></>
                    }
                </ul>
            </nav>

            <div className={menuShow ? "sidebar" : "hide"}>

                <ul>         
                    <li onClick={ () => { setMenuShow(false) } } style={{height:"50px"}}>
                        <i className="fa fa-bars fa-lg" style={{cursor: "pointer", color:"#191970"}}></i>        
                    </li>

                    <li>
                        <Link to="/documents" className="dark-link">
                            <i className="fa fa-book fa-lg" style={{cursor: "pointer", color:"#191970", marginRight:"5px"}}></i>
                            Documentos
                        </Link>
                    </li>
                    <hr/>
                    <li>
                        <Link to="/myDocuments" className="dark-link">
                            <i className="fa fa-user fa-lg" style={{cursor: "pointer", color:"#191970", marginRight:"5px"}}></i>
                            Mis Documentos
                        </Link>
                    </li>
                    <hr/>
                    <li>
                        <Link to="/departDocuments" className="dark-link">
                            <i className="fa fa-users fa-lg" style={{cursor: "pointer", color:"#191970", marginRight:"5px"}}></i>
                            Mi Departamento
                        </Link>
                    </li>
                    <hr/>
                    <li>
                        <Link to="/unitDocuments" className="dark-link">
                            <i className="fa fa-user-circle fa-lg" style={{cursor: "pointer", color:"#191970", marginRight:"5px"}}></i>
                            Mi Unidad
                        </Link>
                    </li>
                    <hr/>
                </ul>
                
            </div>

        </div>
    )
}

export default Navbar;
