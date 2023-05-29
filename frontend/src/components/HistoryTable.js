import Table from 'react-bootstrap/Table';
import {useEffect, useState} from "react";
import axios from "../api/axios";
import React from "react";
import axios1 from "axios";

import {Button} from "react-bootstrap";
import {Modal} from 'rsuite';
import { parseDate } from './dateUtil';

import '@react-pdf-viewer/core/lib/styles/index.css';
import '@react-pdf-viewer/default-layout/lib/styles/index.css';
import {toast} from "react-toastify";



const HistoryTable = ({nameDoc,idDoc}) => {
    const [idDocument, setIdDocument] = useState(idDoc);

    const [document,setDocument] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    //Modal Viewer
    const [open, setOpen] = useState(false);
    const handleClose = () => setOpen(false);

    const handleView = (idOffice) => {
        setIdDocument(idOffice);
        setOpen(!open);
    };

    useEffect(() => {
        const getDocument = async () => {

            setIsLoading(true);
            try {
                const response = await axios.get("http://localhost:8000/files/getVersions/"+nameDoc, {
                    headers: { "Content-Type": "application/json" },
                });

                setDocument(response?.data);
            } catch (err) {
                toast.error(err);
            } finally {
                setIsLoading(false);
            }

        };

        if (nameDoc) {
            getDocument();
        }
        return () => {
            // Cancelar la solicitud en curso si el componente se desmonta antes de recibir una respuesta
            const source = axios1.CancelToken.source();
            source.cancel("Request cancelled");
        };

    }, [nameDoc]);

    if (isLoading) {
        return <div>Cargando...</div>;
    }


    return(
        <>
            <Table hover style={{maxWidth: "80%"}}>
                <thead>

                <tr className="header-dark">
                    <th>Version #</th>
                    <th>Fecha Subida</th>
                    <th>Autor/Editor</th>
                    <th></th>
                    <th></th>
                </tr>

                </thead>

                <tbody>

                {document.map((doc, i) => (
                    <tr key={i}>
                        <td>{doc?.version}</td>
                        <td>{parseDate(doc?.lastUpdated)}</td>
                        <td>
                            {doc?.author?.name +
                                " " +
                                doc?.author?.lastName1 +
                                " " +
                                doc?.author?.lastName2}
                        </td>
                        <td><Button onClick={() => handleView(doc?.idOffice)}>Visualizar</Button></td>
                        <td><Button href={"http://localhost:8000/files/visualizeFile/" + doc?.idOffice}> Descargar</Button></td>
                    </tr>
                ))}


                </tbody>
            </Table>

            <Modal size="full" open={open} onClose={handleClose}>
                <Modal.Header>
                    <Modal.Title>{idDocument + " / " + nameDoc }</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <iframe
                        src = {"http://localhost:8000/files/visualizeFilexx/" + idDocument}
                        title="Documento"
                        width="100%"
                        height="1000px"
                    >
                    </iframe>
                </Modal.Body>
            </Modal>


        </>
    )
}

export default HistoryTable;