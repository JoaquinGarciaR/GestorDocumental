import {Button, Checkbox, CheckPicker, Container, Dropdown, Sidebar, Stack, Modal} from "rsuite";
import InfoRoundIcon from "@rsuite/icons/InfoRound";
import {Check, Close} from "@rsuite/icons";
import axios from "../api/axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "rsuite/dist/rsuite.css"
import React, {useEffect, useRef, useState} from "react";


const USER_URL = "http://localhost:8000/api/v1/user";
const ACCESS_URL = "http://localhost:8000/files/getAccess/";
const ACCESS_POST_URL = "http://localhost:8000/files/setAccess"

const DropAndModal = ({viewType,idUser, doc,  searchParams = "none"}) => {

    // Data a Arreglar

    // Requests
    // Save a new permissions
    const handleSave = async (file_id) => {

        try {
            const response = await axios.post(
                ACCESS_POST_URL,
                JSON.stringify({ file_id, allDepartmentC: departmentC, allUnitsC: unitC, allDepartmentL: departmentL, allUnitsL: unitL, usersL, usersC }),
                {
                    headers: { "Content-Type": "application/json" }
                }
            );
            if(response?.status === 200){
                toast.success("Permisos Guardados")
            }
            setAllDepartmentC(null)
            setAllDepartmentL(null)
            setAllUnitsC(null)
            setAllUnitsL(null)
        }
        catch (err) {
            if (!err?.response) {
                toast.error(err?.response)
            }
        }
    };

    // load a User to Data

    const loadUsers = async () => {

        try {
            const response = await axios.get(
                USER_URL + `?user_id=${idUser}`,
                JSON.stringify({}),
                {   responseEncoding: 'latin1',
                    headers: { "Content-Type": "application/json" }
                }
            );
            const dataCustom = response?.data.map(
                item => ({ label: item?.idUser + " " + item?.name + " " + item?.lastName1 + " " + item?.lastName2, value: item?.idUser })
            );
            setData(dataCustom);
        } catch (err) {
            if (!err?.response) {
                alert("El servidor no responde");
            }
        }
    };

    // Get Current Access from ID_Document

    const loadAllows = async (file_id) => {

        try {
            const response = await axios.get(
                ACCESS_URL + file_id,
                JSON.stringify({}),
                {
                    headers: { "Content-Type": "application/json" }
                }
            );
            setAllDepartmentC(response?.data?.allDepartmentC) // revisar!!
            setAllDepartmentL(response?.data?.allDepartmentL)
            setAllUnitsL(response?.data?.allUnitsL)
            setAllUnitsC(response?.data?.allUnitsC)
            setUsersC(response?.data?.usersC);
            setUsersLec(response?.data?.usersL);


        } catch (err) {
            if (!err?.response) {
                alert("El servidor no responde");
            }
        }
    };

    // Modal
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    // Stack
    const [direction, setDirection] = useState('column');
    const [justifyContent, setJustifyContent] = useState('space-around');
    const [alignItems, setAlignItems] = useState('flex-start');

    // Values
    const [allDepartmentL, setAllDepartmentL] = useState(null);
    const [allUnitsL, setAllUnitsL] = useState(null);
    const [allDepartmentC, setAllDepartmentC] = useState(null);
    const [allUnitsC, setAllUnitsC] = useState(null);
    const [usersLec, setUsersLec] = useState([]);
    const [usersC, setUsersC] = useState([]);
    const [data, setData] = useState([]);
    let usersL = [];
    let departmentC = false;
    let departmentL = false;
    let unitC = false;
    let unitL = false;
    const [validFile, setValidFile] = useState(false);
    // const [stringFile,setStringFile] = useState("");
    let stringFile = "";
    const [file_id, setFile_id] = useState(0);


    // Refs
    const refDepL = useRef();
    const refUniL = useRef();
    const refDepC = useRef();
    const refUniC = useRef();

    // Save Modal
    const Save = (id) => {
        setAllDepartmentL(refDepL.current?.checked)
        setAllUnitsL(refUniL.current?.checked)
        setAllDepartmentC(refDepC.current?.checked)
        setAllUnitsC(refUniC.current?.checked)
        departmentL = refDepL.current?.checked;
        unitL = refUniL.current?.checked;
        departmentC = refDepC.current?.checked;
        unitC = refUniC.current?.checked;
        usersL = usersLec.filter(x => !usersC.includes(x));
    };

    // UseEffects
    useEffect(() => {
        loadUsers();
    }, [])


    let element = {
        uri: "file:///C:/GestorDocumental/Default.pdf",
        fileName: "Certificacion"
    }

    let docs = [element];

    const change = (url) => {
    }
    useEffect(() => {
        change(stringFile);
        setValidFile(true)
    }, [stringFile])

    // Data a Arreglar





    return(
        <>
            <button className="rounded-button-dark" style={{minWidth: "10%"}} onClick={() => {
                setFile_id(doc);
                loadAllows(doc);
                handleOpen()
            }}>  Administrar Permisos</button>


            <Modal open={open && allDepartmentC !== null} onClose={handleClose}>
                <Modal.Header>
                    <Modal.Title>Gestion de Acceso</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Container>
                        <Sidebar>
                            <Stack
                                spacing={6}
                                direction={direction}
                                alignItems={alignItems}
                                justifyContent={justifyContent}
                            >
                                Lectores
                                <Checkbox defaultChecked={allDepartmentL} inputRef={refDepL}> Mi
                                    Departamento</Checkbox>
                                <Checkbox defaultChecked={allUnitsL} inputRef={refUniL}> Mi Unidad</Checkbox>
                                <CheckPicker value={usersLec} onChange={value => setUsersLec(value)}
                                             label="Usuarios" placeholder="Seleccionar" searchKeywordPlaceholder="Buscar opciones" data={data} style={{ width: 224 }} />
                            </Stack>
                        </Sidebar>
                        <Sidebar>
                            <Stack
                                spacing={6}
                                direction={direction}
                                alignItems={alignItems}
                                justifyContent={justifyContent}
                            >
                                Co-Autores
                                <Checkbox defaultChecked={allDepartmentC} inputRef={refDepC}> Mi
                                    Departamento</Checkbox>
                                <Checkbox defaultChecked={allUnitsC} inputRef={refUniC}> Mi Unidad</Checkbox>
                                <CheckPicker value={usersC} onChange={value => setUsersC(value)} label="Usuarios" placeholder="Seleccionar"
                                             data={data} style={{ width: 224 }} />
                            </Stack>
                        </Sidebar>
                    </Container>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => {
                        Save(file_id);
                        handleSave(file_id);
                        handleClose()
                    }} appearance="primary">
                        Guardar
                    </Button>
                    <Button onClick={handleClose} appearance="subtle">
                        Cancelar
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )

}

export default DropAndModal;