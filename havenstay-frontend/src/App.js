import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import  Navbar from "./component/common/Navbar"
import RegisterPage from './component/auth/Register';
import LoginPage from './component/auth/LoginPage';
import HomePage from './component/home/HomePage';
import AllRoomsPage from './component/booking_rooms/AllRoomsPage';
import RoomDetailsPage from './component/booking_rooms/RoomDetailsPage';
import {CustomerRoute} from './service/Guard'
import FindBookingPage from './component/booking_rooms/FindBookingPage';

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
          <Route path="/rooms" element={<AllRoomsPage/>}/>
          <Route path="/find-booking" element={<FindBookingPage/>}/>

          <Route path="/room-details/:roomId" 
          element={<CustomerRoute element={<RoomDetailsPage/>}/>} />

        </Routes>

       </div>
       <footer/>
    </div>
    </BrowserRouter>  
  );
}

export default App;
