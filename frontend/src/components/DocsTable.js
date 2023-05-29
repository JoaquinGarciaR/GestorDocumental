import "../assets/css/Site.css";
import axios from "../api/axios";
import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import ReactTable from './ReactTable'
import { parseDate } from './dateUtil';
import Properties from "../pages/Properties";
import {toast} from "react-toastify";
import PropTable from "./PropTable";

const DocsTable = ({ viewType, searchParams = "none" }) => {



  let BASE_URL = "";

  const { auth } = useAuth();
  let id = auth?.id;

  const navigate = useNavigate();
  const [documents, setDocuments] = useState([]);
  const [menuShow, setMenuShow] = useState(false);

  const docs = [
    { name: "Doc1", type: "pdf", author: { name: "Maria", lastName1: "Rojas", lastName2: "Pereira" }, lastUpdated: "12/12/2022", departament: { name: "Informatica" }, unit: { name: "A" } },
    { name: "Doc2", type: "pdf", author: { name: "Maria", lastName1: "Rojas", lastName2: "Pereira" }, lastUpdated: "12/12/2022", departament: { name: "Informatica" }, unit: { name: "" } }
  ]


  useEffect(() => {
    let body = {};
    let user = JSON.parse(sessionStorage.getItem("user"));
    let idUser = user?.idUser;
    let idDepartament = user?.departament?.idDepartament;
    let idUnit = user?.unit?.idUnit;

    switch (viewType) {
      case "documents":
        BASE_URL = "http://localhost:8000/api/v1/office/user";
        body = { idUser, departament: { idDepartament }, unit: { idUnit } };
        break;
      case "myDocuments":
        body = { idUser };
        BASE_URL = "http://localhost:8000/api/v1/office/author-user";
        break;
      case "departDocuments":

        body = { idDepartament };
        BASE_URL = "http://localhost:8000/api/v1/office/departament";
        break;
      case "unitDocuments":
        body = { idUnit };
        BASE_URL = "http://localhost:8000/api/v1/office/unit";
        break;
      case "search":
        body = { searchParams };
        BASE_URL = "http://localhost:8000/api/v1/office/search";
        break;
    }

    const getFiles = async () => {
      try {
        const response = await axios.post(BASE_URL, JSON.stringify(body), {
          headers: { "Content-Type": "application/json" },
        });
        setDocuments(response?.data);
        return;
      } catch (err) {
      }
    };
    getFiles();
  }, [viewType]);



  const iconPicker = (type) => {
    switch (type) {
      case "pdf":
        return "fa fa-file-pdf fa-lg";
      case "xlsx":
      case "xlsxs":
        return "fa fa-file-excel fa-lg";
      case "docxs":
      case "docx":
        return "fa fa-file-word fa-lg";
      default:
        return "fa fa-file fa-lg";
    }
  };

  const zfill = (number, width) => {
    width -= number.toString().length;
    if (width > 0) {
      return new Array(width + (/\./.test(number) ? 2 : 1)).join('0') + number;
    }
    return number + "";
  }


  const columns = [
    {
      Header: 'Nombre',
      accessor: 'name',
    },
    {
      Header: 'Tipo',
      accessor: 'type',
      Cell: ({ row }) => (
        <div>
          <i className={iconPicker(row.original.type)} style={{ marginRight: "5px" }}></i>
          {row.original.type.toUpperCase()}
        </div>
      ),
    },
    {
      Header: 'Autor',
      accessor: (row) => `${row.author.name} ${row.author.lastName1} ${row.author.lastName2}`,
    },
    {
      Header: 'Ult. Modificacion',
      accessor: (row) => `${parseDate(row.lastUpdated)}`,
    },
    {
      Header: 'Departamento',
      accessor: 'departament.name',
    },
    {
      Header: 'Unidad',
      accessor: 'unit.name',
    },
    {
      Header: 'link',
      Cell: ({ row }) => (
        <Link to={`/properties/${row.original.idOffice}`}>
          <button style={{ backgroundColor: "white" }} >
            <i className="fa fa-ellipsis-h" ></i>
          </button>
        </Link>
      ),
    },
  ]

  return (
    <div>
      <ReactTable columns={columns} data={documents}/>
    </div>
  )
}

export default DocsTable;