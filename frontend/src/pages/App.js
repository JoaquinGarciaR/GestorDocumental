import "../assets/css/App.css";
import Login from "./Login";
import { Routes, Route } from "react-router-dom";
import Layout from "./Layout";
import RequireAuth from "../components/RequireAuth";
import Missing from "./Missing";
import Register from "./RegisterEditing";
import Documents from "./Documents";
import Properties from "./Properties";
import Home from "./Home";
import Unauthorized from "./Unauthorized";
import Provider from "../context/provider";
import UploadDoc from "./UploadDoc";
import { ToastContainer} from 'react-toastify';

const ROLES = {
  User: 0,
  Admin: 1,
}; //this isnt ideal

function App() {
  return (
      <>
        <ToastContainer/>
        <Routes>
          
            <Route path="/" element={<Layout />}>

            <Route path="login" element={<Login />} />

            <Route path="*" element={<Missing />} />

            <Route path="unauthorized" element={<Unauthorized />} />


            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route path="/properties/:idDoc" element={<Properties/>}/>
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route path="/" element={<Home />} />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route path="home" element={<Home />} />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route
                path="documents"
                element={<Documents viewType="documents" />}
                />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route
                path="myDocuments"
                element={<Documents viewType="myDocuments" />}
                />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route
                    path="departDocuments"
                    element={<Documents viewType="departDocuments" />}
                />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route
                    path="unitDocuments"
                    element={<Documents viewType="unitDocuments" />}
                />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.User]} />}>
                <Route path="uploadDoc" element={<UploadDoc />} />
            </Route>

            <Route element={<RequireAuth allowedRoles={[ROLES.Admin]} />}>
                <Route path="register" element={<Register />} />
            </Route>

            </Route>
        </Routes>

      </>
  );
}

export default App;
