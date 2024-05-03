import { Link } from "react-router-dom";
import Logo from "../assets/my_next_trip_logo.png";
import "../styles/landing.css";
import Footer from "../components/Footer";

const Landing = () => {
  return (
    <>
      <div className="containerStyle">
          <img src={Logo} alt="My Next Trip Logo" className="logoStyle" />
          <Link className="buttonStyle" to="/browse-countries">Start Browsing</Link>
      </div>
      <Footer/>
    </>
  );
};

export default Landing;