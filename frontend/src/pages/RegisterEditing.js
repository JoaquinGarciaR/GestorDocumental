import React, { useRef, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from '../api/axios';
import {toast} from 'react-toastify';
import { Title } from '../logic/Title';
import "bootstrap/dist/css/bootstrap.min.css";
import '../assets/css/Site.css';

const API_BASE_URL = 'http://localhost:8000/api/v1';

const REGISTER_URL = `${API_BASE_URL}/user/register`;
const DEPARTMENT_URL = `${API_BASE_URL}/departament`;
const UNIT_URL = `${API_BASE_URL}/unit/Units`;

const mainStyle = {
    minHeight: "100vh",
    backgroundImage: "radial-gradient(rgb(239, 184, 16) 31%, rgb(212, 175, 55) 75%)",
    justifyContent: "left",
    alignItems: "left"
};

const formStyle = {
    textAlign: "left",
    justifyContent: "left",
    alignItems: "left",
    gap: "30px"
};

const Register = () => {
    const navigate = useNavigate();

    const [condition, setCondition] = useState(false);
    const [departments, setDepartments] = useState([]);
    const [units, setUnits] = useState([]);

    const refDep = useRef();
    const refUni = useRef();
    const refRole = useRef();
    const refBoss = useRef(false);
    const refState = useRef(false);
    const emailRef = useRef();

    const [email, setEmail] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const [lastName1, setLastName1] = useState('');
    const [lastName2, setlastName2] = useState('');
    const [name, setName] = useState('');
    const [boss, setBoss] = useState(false);
    const [telephone, setTelephone] = useState('');
    const [user, setUser] = useState('');
    const [state, setState] = useState(false);
    const [depa, setDepa] = useState('');
    const [uni, setUni] = useState('');
    const [idUser, setIdUser] = useState('');
    const [role, setRole] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        handleDepartmentsSubmit();
    }, []);

    const handleRegisterSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post(
                REGISTER_URL,
                JSON.stringify({
                    idUser,
                    name,
                    lastName1,
                    lastName2,
                    boss,
                    role,
                    telephone,
                    email,
                    passwordEncrypt: pwd,
                    state,
                    departament: depa,
                    unit: uni
                }),
                {
                    headers: { "Content-Type": "application/json" }
                }
            );

            if (response?.status === 200) {
                toast.success('Registro Correcto');
            }

            return navigate("/login");
        } catch (err) {
            if (!err?.response) {
                toast.error("El servidor no responde");
            } else if (err.response?.status === 400) {
                toast.error("Correo Existente");
            } else if (err.response?.status === 401) {
                toast.error("Usuario Existente");
            } else if (err.response?.status === 403) {
                toast.error("Contraseña Invalida");
            } else {
                toast.error("Registro fallido");
            }
        }
    };

    const handleUnitSubmit = async (url) => {
        try {
            const response = await axios.get(url, {
                headers: { "Content-Type": "application/json" },
            });
            setUnits(response?.data);
        } catch (err) {
            if (!err?.response) {
                toast.error("El servidor no responde");
            }
        }
    };

    const handleDepartmentsSubmit = async () => {
        try {
            const response = await axios.get(DEPARTMENT_URL, {
                headers: { "Content-Type": "application/json" },
            });
            setDepartments(response?.data);
        } catch (err) {
            if (!err?.response) {
                toast.error("El servidor no responde");
            }
        }
    };

    const loadUnits = (e) => {
        e.preventDefault();
        let url = `${UNIT_URL}/${refDep.current.value}`;
        handleUnitSubmit(url);
        setCondition(true);
    };

    const filt = () => {
        setDepa(departments.find((x) => x.idDepartament === parseInt(refDep.current.value)));
        setUni(units.find((x) => x.idUnit === parseInt(refUni.current.value)));
    };

    const cancelRegister = () => {
        navigate("/home");
    };

    const validColor = (e, valid) =>{
        if(valid){
            e.target.classList.remove('invalid');
            e.target.classList.add('valid');
        }
        else{
            e.target.classList.remove('valid');
            e.target.classList.add('invalid');
        }
    }

    const handleNumChange = (e, setStateCallback) => {
        const input = e.target.value;
        const sanitizedInput = input.replace(/\D/g, ''); // Eliminar todos los caracteres no numéricos
        if (input !== sanitizedInput) {
            toast.error('Solo se permiten números en este campo.');
        }
        else{
            validColor(e,true)
        }
        setStateCallback(sanitizedInput);
    };

    const handleStrChange = (e, setStateCallback) => {
        const input = e.target.value;
        const sanitizedInput = input.replace(/[\d!@#$%^&*(),.?":{}|<>]/g, ''); // Eliminar todos los caracteres no numéricos
        if (input !== sanitizedInput) {
            toast.error('Solo se permiten letras en este campo.');
        }
        else{
            validColor(e,true)
        }
        setStateCallback(sanitizedInput);

    };

    const handleEmailChange = (e) => {
        const input = e.target.value;
        const isValidFormat = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(input); // Verificar formato de email
        const isValidDomain = input.endsWith('@sanisidro.go.cr'); // Verificar dominio

        if (isValidFormat && isValidDomain) {
            validColor(e,true)
        } else {
            validColor(e,false)
            toast.error('Ingrese un correo electrónico válido con el dominio @sanisidro.go.cr');
        }
        setEmail(input);
    };

    const handlePasswordBlur = (e) => {
        const input = e.target.value;
        const isValidPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$/.test(input);

        if (isValidPassword) {
            validColor(e,true)
        } else {
            validColor(e,false)
            toast.error('Debe tener al menos un carácter numérico.\n' +
                'Debe tener al menos un carácter en minúscula.\n' +
                'Debe tener al menos un carácter en mayúscula.\n' +
                'Debe tener al menos un símbolo especial entre @#$%.\n' +
                'La longitud de la contraseña debe estar entre 8 y 20 caracteres.');
        }
        setPwd(input)
    };

    const ready = (event) => {
        if( !email ||
            !pwd ||
            !idUser ||
            !name ||
            !lastName1 ||
            !lastName2 ||
            !role ||
            !telephone ||
            !depa ||
            !uni){
            toast.error("Recuerda llenar todos los campos obligatorios")
        }
        else {
            handleRegisterSubmit(event)
        }
    }


    Title("Registro de Usuarios");

    return (
        <main style={mainStyle}>
            <div>
                <img
                    alt="Logo municipalidad"
                    src={require("../assets/logos/municipalidad_logo.png")}
                    style={{ maxWidth: "100px" }}
                ></img>
                <h1 style={{ color: "#191970", alignItems: "center", textAlign: "center" }}>
                    Registro de Usuarios
                </h1>
            </div>
            <form style={formStyle} onSubmit={event => ready(event)}>
                <div className="container" style={{ width: "100%", minHeight: "500px" }}>
                    Correo:
                    <input
                        type="text"
                        id="email"
                        className="rounded-input-block"
                        onBlur={handleEmailChange}
                        placeholder="Correo electrónico"
                        required
                    />
                    <br />
                    <br />
                    Contraseña:
                    <input
                        type="password"
                        id="password"
                        className={'rounded-input-block'}
                        onBlur={handlePasswordBlur}
                        placeholder="Contraseña"
                        required
                    />
                    <br />
                    <br />
                    Cédula
                    <input
                        id="ID"
                        className="rounded-input-block"
                        type="text"
                        value={idUser}
                        onChange={(e) => handleNumChange(e, setIdUser)}
                        placeholder="Cédula"
                        maxLength="10"
                        required
                    />
                    <br />
                    <br />
                    Nombre
                    <input
                        id="name"
                        className="rounded-input-block"
                        type="text"
                        onChange={(e) => handleStrChange(e, setName)}
                        value={name}
                        placeholder="Nombre"
                        required
                    />
                    <br />
                    <br />
                    Primer Apellido
                    <input
                        id="lName1"
                        className="rounded-input-block"
                        type="text"
                        onChange={(e) => handleStrChange(e, setLastName1)}
                        value={lastName1}
                        placeholder="Primer apellido"
                        required
                    />
                    <br />
                    <br />
                    Segundo Apellido
                    <input
                        id="lName2"
                        className="rounded-input-block"
                        type="text"
                        onChange={(e) => handleStrChange(e, setlastName2)}
                        value={lastName2}
                        placeholder="Segundo Apellido"
                        required
                    />
                    <br />
                    <br />
                    Teléfono
                    <input
                        id="phone"
                        className="rounded-input-block"
                        type="tel" // Añadido: tipo de campo "tel"
                        value={telephone}
                        onChange={(e) => handleNumChange(e, setTelephone)}
                        placeholder="Teléfono"
                        maxLength="10" // Añadido: longitud máxima del número de teléfono
                        pattern="[0-9]+" // Añadido: patrón para permitir solo dígitos numéricos
                        required // Opcional: hacer que el campo sea obligatorio
                    />
                    <br />
                    <br />
                    Jefe <input type="checkbox" name ="Jefe" style={{ width: "50px" }} onChange={(e) => setBoss(!boss)} />
                    <br />
                    <br />
                    Estado (Activo o Inactivo){" "}
                    <input type="checkbox" name="Stat" style={{ width: "50px" }} onChange={(e) => setState(!state)} />
                    <br />
                    <br />
                    <div>
                        Departamento
                        <select id="departments" ref={refDep} onChange={(e) => loadUnits(e)}>
                            {!refDep.current?.value && <option value="">Seleccione</option>}
                            {departments.map((item, i) => (
                                <option key={item.idDepartament} value={item.idDepartament}>
                                    {item.name}
                                </option>
                            ))}
                        </select>
                        <br />
                        <br />
                        Unidad
                        {condition ? (
                            <select id="units" ref={refUni} onChange={filt}>
                                {!refUni.current?.value && <option value="">Seleccione</option>}
                                {units.map((item, i) => (
                                    <option key={item.idUnit} value={item.idUnit}>
                                        {item.name}
                                    </option>
                                ))}
                            </select>
                        ) : (
                            <strong></strong>
                        )}
                    </div>
                    <br />
                    Rol
                    <select ref={refRole} onChange={(e) => setRole(e.target.value)}>
                        {!refRole.current?.value && <option value="">Seleccione</option>}
                        <option value="0">Funcionario</option>
                        <option value="1">Admin</option>
                    </select>

                    <br />
                    <br />
                    <div style={{ textAlign: "center", justifyContent: "center", alignItems: "center" }}>
                        <button
                            className={
                                !email ||
                                !pwd ||
                                !idUser ||
                                !name ||
                                !lastName1 ||
                                !lastName2 ||
                                !role ||
                                !telephone ||
                                !depa ||
                                !uni
                                    ? "rounded-button-dark-d"
                                    : "rounded-button-dark"
                            }
                            style={{ width: "25%" }}
                        >
                            Agregar
                        </button>
                        <button
                            onClick={cancelRegister}
                            id="cancelar"
                            className={"rounded-button-dark"}
                            style={{ width: "25%" }}
                        >
                            Cancelar
                        </button>
                    </div>
                </div>
            </form>
        </main>
    );
};

export default Register;
