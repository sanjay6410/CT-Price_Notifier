
import './App.css';
import Login from './components/login';
import AddProduct from './components/AddProduct'
import Registration from './components/customerSignUp';
import { BrowserRouter,Route,Routes, } from "react-router-dom"
function App() {

  return (
    <div>
    <BrowserRouter>
    <Routes>
    <Route path='/login' element={<Login/>} />
    <Route path='/registerCustomer' element={<Registration/>} />

     <Route path='/addProducts/:id' element={<AddProduct/>}/> 
    </Routes>
     </BrowserRouter>
     </div>
  );

  }
export default App;
