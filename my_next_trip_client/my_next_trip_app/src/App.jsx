import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useState, useEffect} from 'react';
import { jwtDecode } from "jwt-decode";
import 'bootstrap/dist/css/bootstrap.css';
import Landing from "./pages/Landing";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register"
import AdminAddForm from "./pages/AdminAddForm"
import UserContext from "./context/UserAuth";
import CountryDetail from "./pages/CountryDetail";

const LOCAL_STORAGE_TOKEN_KEY = "countryToken";

function App() {
  const [user, setUser] = useState(null);

  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setRestoreLoginAttemptCompleted(true);
  }, []);

  const login = (token) => {
    // Set the token in localStorage
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);
    // Decode the token
    const { sub: username, authorities : roles, app_user_id } = jwtDecode(token);
    // Create the "user" object
    const user = {
      app_user_id,
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };
    // Log the user for debugging purposes
    console.log(user);
    // Update the user state
    setUser(user);
    // Return the user to the caller
    return user;
  };

  const logout = () => {
    setUser(null);
    // Remove the token from localStorage
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  };

  const auth = {
    user,
    login,
    logout
  };

  if (!restoreLoginAttemptCompleted) {
    return null;
  }





  return(
  <>
  <UserContext.Provider value={auth}>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing/>} />
        <Route path="/add" element={<AdminAddForm/>}/>
        <Route path="/login" element={auth.user ? <Home/> : <Login/>}/>
        <Route path="/register" element={<Register/>}/>
        <Route path="/browse-countries" element={<Home/>} />
        <Route path='/country/view/:countryId' element={<CountryDetail/>}/>
      </Routes>
    </BrowserRouter>
  </UserContext.Provider>
</>
  )

}

export default App
