import { useRef, useState, useEffect } from "react";
import useAuth from "../hooks/useAuth";
import axios from "../api/axios";
import { Title } from "../logic/Title";
import Header from "../components/Header";
import "../assets/css/Site.css";
import { Link, useNavigate } from "react-router-dom";
import DocsTable from "../components/DocsTable";

const Documents = ({viewType}) => {


    Title("Documentos");

    return(
        <>
            <main style={{backgroundColor: "#DCDCDC", display:"block", minHeight: "100vh"}}>
                <Header/>
                <div className="container" style={{ minWidth: "90%", padding: "4%" }}>
                    <div style={{ margin: "2%" }}>
                        <h3 className="header-dark" style={{ display: "inline" }}>
                            <i className="fa fa-folder" style={{ margin: "5px" }}></i>
                            Documentos
                        </h3>

                        <Link to="/uploadDoc" style={{float:"right"}}>

                            <button className="rounded-button-dark" style={{minWidth:"100px"}}>
                                <i className="fa fa-upload"></i>
                                <b>Subir</b>
                            </button>

                        </Link>

                    </div>
                    <hr/>
                    <DocsTable viewType={viewType}/>
                </div>
            </main>
        </>

    )
}

export default Documents;