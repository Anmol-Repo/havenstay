import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import  Navbar from "./component/common/Navbar"
import RegisterPage from './component/auth/Register';
import LoginPage from './component/auth/LoginPage';
import HomePage from './component/home/HomePage';

function App() {
  return (
    <BrowserRouter>
    <div className="App">
       <Navbar/>
       <div className="content">
        <Routes>
          <Route path="/register" element={<RegisterPage/>}/>
          <Route path="/login" element={<LoginPage/>}/>
          <Route path="/home" element={<HomePage/>}/>
        </Routes>

       </div>
       <footer/>
    </div>
    </BrowserRouter>  
  );
}

export default App;
