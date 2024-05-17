import { Link } from 'react-router-dom';
import "../styles/countrycard.css";
import UserAuth from '../context/UserAuth';
import { useState, useContext, useEffect } from 'react';


const CountryCard = ({ country, toggleRefresh }) => {
    const { id : countryId, name, image, description, costOfLiving, region } = country;
    const [inList, setInList] = useState(false);
    const auth = useContext(UserAuth);
    const appUserId = auth.user?.app_user_id;
    const token = auth.user?.token

    //Check if country is already in userList or not
    useEffect(() => {
        // Only proceed if appUserId is available (logged in).
        if (!appUserId || !token) return;
    
        const checkIfInList = async () => {
            const response = await fetch(`http://localhost:8080/api/country/user-list/${country.id}/${appUserId}`, {
                    method: 'GET',
                    headers: {
                        Accept: 'application/json',
                        Authorization: `Bearer ${token}`
                    }
                });
                
                if (response.ok) {
                    const isInList = await response.json();
                    // Assume API returns a boolean in "isInList" field
                    setInList(isInList);
                } 
        };
        checkIfInList();
    }, [country.id, appUserId, token]);

    function handleDelete(countryId){
        fetch(`http://localhost:8080/api/country/user/${countryId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(res => {
            if (res.ok) {
                toggleRefresh();
            } 
        })
        .catch(console.error);
    }


    function handleToggle(event) {
        event.preventDefault();
        if (inList) {
            // Remove the city from the list
            const success =  removeCountryFromList(country.id);
            if (success) setInList(false);
        } else {
            // Add the city to the list
            const success = addCountryToList(country.id);
            if (success) setInList(true);
        }
    }

    function removeCountryFromList(countryId){
        return fetch(`http://localhost:8080/api/country/user-list/${countryId}/${appUserId}`, {
            method: 'DELETE',
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`
            }
        })
        .then(res => {
            if (res.ok) {
                toggleRefresh();
                return true;
            } else {
                return false;
            }
        })
        .catch(console.error);
    }

    function addCountryToList(countryId) {
        //Return keyword will return the result of the fetch (true or false) to the handle toggle function.
        return fetch(`http://localhost:8080/api/country/user-list/${countryId}/${appUserId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`,
            },
        })
        .then(res => {
            if (res.ok) {
                return true;
            } else if (res.status === 400) {
                return false;
            } else {
              //If the response code is anything else, reject promise and throw code execution to .catch()
                return Promise.reject(
                new Error(`Unexpected status code: ${res.status}`)
                );
            }
            })
        .catch(console.error); 
        }


    return (
        <Link to={`/country/view/${countryId}`} style={{ textDecoration: 'none' }}>
            <div className="card country-card mb-3" style={{ borderColor: '#FFBF47', cursor: 'pointer' }}>
                <img src={image} className="card-img-top" alt={name} style={{ maxHeight: '200px', objectFit: 'cover' }} />
                <div className="card-body" style={{ color: 'white' }}>
                    <h5 className="card-title">{name}</h5>
                    <p className="card-text">{description}</p>
                    <p className="card-text">
                        <small className="text-muted">Cost of Living: {costOfLiving.name}</small>
                    </p>
                    <p className="card-text">
                        <small className="text-muted">Region: {region.name}</small>
                    </p>
                    {/* Toggle Button */}
                    {appUserId && (
                        <button
                            className={`toggle-button ${inList ? 'remove' : 'add'}`}
                            onClick={(event) => handleToggle(event)}
                        >
                        {inList ? 'Remove from List' : 'Add to List'}
                        </button>
                    )}
                    {/* Delete Button only for Admin */}
                    {auth.user && auth.user.hasRole('ADMIN') && (
                        <Link
                            className="btn btn-danger"
                            style={{ position: 'absolute', top: '10px', right: '10px' }}
                            onClick={() => window.confirm("Are you sure you want to Delete?") && handleDelete(countryId)}
                        >
                            Delete
                        </Link>
                    )}
                </div>
            </div>
        </Link>
    );
};

export default CountryCard;