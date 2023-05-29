import Header from "../components/Header";
import { Title } from "../logic/Title";
import "../assets/css/Site.css";
import axios from "../api/axios";
import React, { useState, useEffect } from 'react';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';

import PropTable from "../components/PropTable";
import HistoryTable from "../components/HistoryTable";
import DropAndModal from "../components/DropAndModal";


import {useParams} from "react-router-dom";
import {getDocument} from "pdfjs-dist";
import {toast} from "react-toastify";

const mainStyle = {
    backgroundColor: "#FFFFFF",
    justifyContent: "center",
    alignItems: "center",

};

const Properties = () => {
    const [isDocumentActive, setDocumentActive] = useState(true);
    const parametros = useParams()
    const idDoc = parametros?.idDoc;
    const [key, setKey] = useState('properties');
    Title("Pagina de Inicio");
    
    let user = JSON.parse(sessionStorage.getItem("user"));
    let idUser = user?.idUser;
    const [document, setDocument] = useState();

    const [permissions, setPermissions] = useState();

    let docTest = {name:"Doc1", idOffice: 100, valid: true, type:"pdf", author:{name:"Diana", lastName1:"Saenz", lastName2:"Leon"}, lastUpdated:"12/12/2022", dateCreated:"12/12/2020", departament:{name:"Informatica"}, unit:{name:"A"}, version:"1"};


    let body = {idUser, idDoc};
    toast.error(body)
    const getDocument = async () => {

        try {
            const response = await axios.post("http://localhost:8000/files/properties", JSON.stringify(body), {
                headers: { "Content-Type": "application/json" },
            });

            setDocument(response?.data);
            setPermissions(response?.data?.permissions);
            return;
        } catch (err) {
            toast.error(err);
        }

    };


    
    useEffect(() => {
        getDocument();
    }, []);

    const deactivateDocument = async () => {


        try {

            const response = await axios.get(`http://localhost:8000/api/v1/office/desactivatexx/${idDoc}/${idUser}`, {
                headers: { "Content-Type": "application/json" },
            });
            getDocument();
            setDocumentActive(false);
            return;

        } catch (err) {
            toast.error(err);
            }
    }

    return(

        <main style={mainStyle}>
            <Header/>

            <div style={{margin: "2%"}}>

                <Tabs activeKey={key} onSelect={(k) => setKey(k)} className="mb-3">

                    <Tab eventKey="properties" title="Propiedades">

                        <h3 className="header-dark">Propiedades</h3>

                        {document && <PropTable document={document} idDoc={idDoc} />}
                        {isDocumentActive && permissions === "autor" ?
                            <button onClick=

                                {() =>
                                    {
                                        if(window.confirm("Esta seguro que desea desactivar este documento? Esto implica no poder acceder a el mismo en el futuro. **Desacivar el documento no implica eliminarlo**"))
                                        {
                                            deactivateDocument();
                                        }
                                    }
                                } className="rounded-button-danger" style={{minWidth: "14%"}}>Desactivar Oficio</button>
                            : "" }

                    </Tab>
                    
                    {
                        permissions === "autor" ? (
                        
                        <Tab eventKey="permissions" title="Permisos">

                            <h3 className="header-dark">Permisos</h3>

                            <DropAndModal idUser={idUser} viewType={"Hola"} doc={idDoc}/>

                        </Tab>
                        
                        ) : ""
                    }
                    


                        <Tab eventKey="versions" title="Historial de Versiones">

                            <HistoryTable nameDoc={document?.name} idDoc={idDoc}/>

                        </Tab>
                    


                </Tabs>

            </div>

        </main>
    )
}

export default Properties