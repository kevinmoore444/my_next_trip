import '../styles/citycard.css';
import { useState, useContext, useEffect } from 'react';
import UserAuth from '../context/UserAuth';


const CityCard = ({ city }) => {
    const [inList, setInList] = useState(false);
    const auth = useContext(UserAuth);
    const appUserId = auth.user.app_user_id;

    //Check if city is already in userList or not
    useEffect(() => {
        const checkIfInList = async () => {
            const response = await fetch(`http://localhost:8080/api/city/user-list/${city.id}/${appUserId}`, {
                    method: 'GET',
                    headers: {
                        Accept: 'application/json',
                        Authorization: `Bearer ${auth.user.token}`
                    }
                });
                
                if (response.ok) {
                    const isInList = await response.json();
                    // Assume API returns a boolean in "isInList" field
                    setInList(isInList);
                } 
        };
        checkIfInList();
    }, [city.id, appUserId, auth.user.token]);




    function handleToggle(event) {
        event.preventDefault();
        if (inList) {
            // Remove the city from the list
            const success =  removeCityFromList(city.id);
            console.log(success);
            if (success) setInList(false);
        } else {
            // Add the city to the list
            const success = addCityToList(city.id);
            console.log(success);
            if (success) setInList(true);
        }
    }


    function removeCityFromList(cityId){
        return fetch(`http://localhost:8080/api/city/user-list/${cityId}/${appUserId}`, {
            method: 'DELETE',
            headers: {
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`
            }
        })
        .then(res => {
            if (res.ok) {
                console.log(`City with ID ${cityId} successfully removed.`);
                return true;
            } else {
                console.error(`Failed to remove city. Status: ${res.status}`);
                return false;
            }
        })
        .catch(console.error);
    }

    function addCityToList(cityId) {
        //Define payload
        const payload = {
            cityId,
            appUserId
        };
        //Return keyword will return the result of the fetch (true or false) to the handle toggle function.
        return fetch(`http://localhost:8080/api/city/user-list/${cityId}/${appUserId}`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                Accept: "application/json",
                Authorization: `Bearer ${auth.user.token}`,
            },
            //Stringify payload and include in body of request. 
            body: JSON.stringify(payload),
        })
        .then(res => {
            if (res.ok) {
                console.log(`City with ID ${cityId} successfully added.`);
                return true;
            } else if (res.status === 400) {
                console.log(res);
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
            <div className="card city-card mb-3" style={{ borderColor: '#FFBF47', cursor: 'pointer' }}>
                <img src={city.image} className="card-img-top" alt={city.name} style={{ maxHeight: '200px', objectFit: 'cover' }} />
                <div className="card-body" style={{ color: 'white' }}>
                    <h5 className="card-title">{city.name}</h5>
                    <p className="card-text">{city.description}</p>
                {/* Toggle Button */}
                <button
                    className={`toggle-button ${inList ? 'remove' : 'add'}`}
                    onClick={(event) => handleToggle(event)}
                >
                    {inList ? 'Remove from List' : 'Add to List'}
                </button>
                </div>
            </div>
    );
};

export default CityCard;