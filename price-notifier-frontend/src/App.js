
import './App.css';
import Login from './components/login';
import RegisterCustomer from './components/RegisterCutomer'
import { BrowserRouter,Route,Routes, } from "react-router-dom"
function App() {

  return (
    <div>
    <BrowserRouter>
    <Routes>
    <Route path='/Login' element={<Login/>} />
    <Route path='/RegisterCustomer' element={<RegisterCustomer/>}/>
    </Routes>
     </BrowserRouter>
     </div>
  );

  }
export default App;
