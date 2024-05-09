import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import CityCard from '../components/CityCard'
import AttractionCard from '../components/AttractionCard';
import NavBar from '../components/NavBar'
import '../styles/countrydetail.css';

const CountryDetail = () => {
    const [cities, setCities] = useState([]);
    const [attractions, setAttractions] = useState([]);
    const [country, setCountry] = useState([]);
    const { countryId } = useParams();


    useEffect(() => {

        //Get Country using Path Parameter
        const getCountry = async () => {
            const response = await fetch(`http://localhost:8080/api/country/${countryId}`, {
                method: "GET",
                headers: {
                    "Accept": "application/json",
                },
            });
    
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setCountry(data);
            }
        }

        //Get Cities associated with that country 
        const getCities = async () => {
            const response = await fetch(`http://localhost:8080/api/city/list/${countryId}`, {
                method: "GET",
                headers: {
                "Accept": "application/json",
            },
        });
    
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setCities(data);
            }
        }

        //Get Attractions associated with that country.
        const getAttractions = async () => {
            const response = await fetch(`http://localhost:8080/api/attraction/list/${countryId}`, {
                method: "GET",
                headers: {
                Accept: "application/json",
                },
            });
        
            if (response.ok) {
                const data = await response.json();
                console.log(data)
                setAttractions(data);
            }
        }
        
        //Call Methods
        getCountry();
        getCities();
        getAttractions();
    }, [countryId]);

    return (
        <>
        <NavBar/>
        <div className="main-component">
            <h1>{country.name}</h1>
            <section>
                <h3>Cities</h3>
                <div className="city-cards-row">
                    {cities.slice(0, 3).map((city) => (
                        <CityCard key={city.id} city={city} />
                    ))}
                </div>
                <div className="city-cards-row">
                    {cities.slice(3, 6).map((city) => (
                        <CityCard key={city.id} city={city} />
                    ))}
                </div>
            </section>
            <section>
                <h3>Attractions</h3>
                <div className="attraction-cards-row">
                    {attractions.slice(0, 3).map((attraction) => (
                        <AttractionCard key={attraction.id} attraction={attraction} />
                    ))}
                </div>
                <div className="attraction-cards-row">
                    {attractions.slice(3, 6).map((attraction) => (
                        <AttractionCard key={attraction.id} attraction={attraction} />
                    ))}
                </div>
            </section>
        </div>
        </>
    );
};

export default CountryDetail;