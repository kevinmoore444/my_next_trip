import { useState, useContext, useEffect } from 'react';
import CityCard from '../components/CityCard'
import AttractionCard from '../components/AttractionCard';
import NavBar from '../components/NavBar'
import '../styles/countrydetail.css';
import UserAuth from '../context/UserAuth';
import CountryCard from '../components/CountryCard';

function MyBucketList() {
    const [cities, setCities] = useState([]);
    const [attractions, setAttractions] = useState([]);
    const [countries, setCountries] = useState([]);
    const auth = useContext(UserAuth);
    const appUserId = auth.user?.app_user_id;
    const token = auth.user?.token

    // Function to toggle the refetch trigger
    const [refresh, setRefresh] = useState(false);
    // Function to toggle refresh state
    const toggleRefresh = () => setRefresh((prev) => !prev);


    useEffect(() => {

        if (!appUserId || !token) return;

        //Get Countries associated with user list
        const getCountries = async () => {
            const response = await fetch(`http://localhost:8080/api/country/user-list/${appUserId}`, {
                method: "GET",
                headers: {
                    "Accept": "application/json",
                    Authorization: `Bearer ${token}`
                },
            });
    
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setCountries(data);
            }
        }

        //Get Cities associated with user list 
        const getCities = async () => {
            const response = await fetch(`http://localhost:8080/api/city/user-list/${appUserId}`, {
                method: "GET",
                headers: {
                "Accept": "application/json",
                Authorization: `Bearer ${token}`
            },
        });
    
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setCities(data);
            }
        }

        //Get Attractions associated with user list
        const getAttractions = async () => {
            const response = await fetch(`http://localhost:8080/api/attraction/user-list/${appUserId}`, {
                method: "GET",
                headers: {
                Accept: "application/json",
                Authorization: `Bearer ${token}`
                },
            });
        
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setAttractions(data);
            }
        }
        
        //Call Methods
        getCountries();
        getCities();
        getAttractions();
    }, [appUserId, token, refresh]);





    return (
        <>
        <NavBar/>
        <div className="main-component">
            <h1>My Bucket list</h1>
            <section>
                <h3>Dream Countries</h3>
                <div className="row">
                    {countries.map((country, index) => (
                        <div className="col-sm-12 col-md-6 col-lg-4 mb-3" key={index}>
                        <CountryCard country={country} toggleRefresh={toggleRefresh}/>
                    </div>
                    ))}
                </div>
            </section>
            <section>
                <h3>Dream Cities</h3>
                <div className="city-cards-row">
                    {cities.slice(0, 3).map((city) => (
                        <CityCard key={city.id} city={city} toggleRefresh={toggleRefresh} />
                    ))}
                </div>
                <div className="city-cards-row">
                    {cities.slice(3, 6).map((city) => (
                        <CityCard key={city.id} city={city}toggleRefresh={toggleRefresh} />
                    ))}
                </div>
            </section>
            <section>
                <h3>Dream Attractions</h3>
                <div className="attraction-cards-row">
                    {attractions.slice(0, 3).map((attraction) => (
                        <AttractionCard key={attraction.id} attraction={attraction} toggleRefresh={toggleRefresh}/>
                    ))}
                </div>
                <div className="attraction-cards-row">
                    {attractions.slice(3, 6).map((attraction) => (
                        <AttractionCard key={attraction.id} attraction={attraction} toggleRefresh={toggleRefresh} />
                    ))}
                </div>
            </section>
        </div>
        </>
    );
};


export default MyBucketList