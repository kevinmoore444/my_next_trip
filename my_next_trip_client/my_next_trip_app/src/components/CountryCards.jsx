import CountryCard from "./CountryCard";
import { useEffect, useState } from "react";


const CountryCards = () => {

const [countries, setCountries] = useState([]);
// Function to toggle the refetch trigger
const [refresh, setRefresh] = useState(false);
// Function to toggle refresh state
const toggleRefresh = () => setRefresh((prev) => !prev);

    useEffect(() => {
        const getCountries = async () => {
        const response = await fetch("http://localhost:8080/api/country", {
            method: "GET",
            headers: {
            Accept: "application/json",
            },
        });

        if (response.ok) {
            const data = await response.json();
            console.log(data)
            setCountries(data);
        }
        };
        getCountries();
    }, [refresh]);


        return (
        <div className="container mt-4">
            <div className="row">
            {countries.map((country, index) => (
                <div className="col-sm-12 col-md-6 col-lg-4 mb-3" key={index}>
                <CountryCard country={country} toggleRefresh={toggleRefresh}/>
                </div>
            ))}
            </div>
        </div>
        );
    };
    
    export default CountryCards;