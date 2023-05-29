import "../assets/css/Site.css";
import axios from "../api/axios";

import { saveAs } from 'file-saver';
import React, { useState } from 'react';
import Table from 'react-bootstrap/Table';
import { Button } from "react-bootstrap";
import { parseDate } from './dateUtil';
import {toast} from "react-toastify";
const PropTable = ({ document, idDoc }) => {
    const URL = "http://localhost:8000/files/getCertificatexx/";


    const certification = async (file_id,version) => {
        try {

            const response = await axios.get(URL + file_id + "/" + version, {
                responseType: 'blob',
            });
            return response.data

        } catch (err) {
            if (!err?.response) {
                toast.error("ERROR")
            }
        }
    };

    const handleDownload = async (fileId,version) => {
        const fileBlob = await certification(fileId,version);
        if (fileBlob) {
            saveAs(fileBlob, 'certificado.pdf');
        }
    };

    return (
        <Table hover style={{ maxWidth: "50%" }}>
            <tbody>
                <tr>
                    <td>Nombre</td>
                    <td>{document?.name}</td>
                </tr>
                <tr>
                    <td>Autor</td>
                    <td>

                        {
                            document?.author?.name +
                            " " +
                            document?.author?.lastName1 +
                            " " +
                            document?.author?.lastName2
                        }

                    </td>
                </tr>
                <tr>
                    <td>Tipo de Archivo</td>
                    <td>{document?.type}</td>
                </tr>
                <tr>
                    <td>Ultima Modificacion</td>
                    <td>{parseDate(document?.lastUpdated)}</td>
                </tr>
                <tr>
                    <td>Fecha de Creacion</td>
                    <td>{parseDate(document?.dateCreated)}</td>
                </tr>

                <tr>
                    <td>Departamento</td>
                    <td>{document?.departament?.name}</td>
                </tr>
                <tr>
                    <td>Unidad</td>
                    <td>{document?.unit?.name ? document?.unit?.name : "N/A"}</td>
                </tr>
                <tr>
                    <td>Estado del Documento</td>
                    <td>{document?.state === true ? "Activo" : "Inactivo"}</td>
                </tr>
                <tr>
                    <td>Estado Firma</td>
                    <td>{document?.valid === true ? "Valido" : "No Valido"}</td>
                </tr>
                <tr>
                    <td>Version</td>
                    <td>{document?.version}</td>
                </tr>
                {document?.valid === true ? <tr>
                    <td>Firmas Verificadas</td>
                    <td><Button onClick={() => handleDownload(document?.name,document?.version)}>Descargar certificado</Button></td>
                </tr> : <></>}

            </tbody>
        </Table>
    )
}

export default PropTable;