import { BrowserRouter, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import Landing from "./pages/Landing";
import Home from "./pages/Home";
import Login from "./pages/Login";
import AdminAddForm from "./pages/AdminAddForm"

function App() {

  return(
  <>
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<Landing/>} />
      <Route path="/add" element={<AdminAddForm/>}/>
      <Route path="/login" element={<Login/>}/>
      <Route path="/browse-countries" element={<Home/>} />
    </Routes>
  </BrowserRouter>
</>
  )

}

export default App
