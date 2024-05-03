import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap CSS is included
import Logo from "../assets/my_next_trip_logo.png";
import "../styles/nav.css";

    const NavigationBar = () => {
        return (
        <nav className="navbar navbar-expand-lg navbar-dark navbar-custom">
            <div className="container-fluid">
            <Link className="navbar-brand" to="/"> <img  src={Logo} alt="logo" style={{ width: '100px', height: 'auto' }} /></Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                <li className="nav-item">
                    <Link className="nav-link" to="/browse-countries">Browse</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/top20">Top 20</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/mybucketlist">My Bucket List</Link>
                </li>
                </ul>
                <ul className="navbar-nav">
                <li className="nav-item">
                    <Link className="nav-link" to="/login">Login</Link>
                </li>
                </ul>
            </div>
            </div>
        </nav>
        );
    };
    
    export default NavigationBar;


   