import { Link } from 'react-router-dom';
import "../styles/countrycard.css";


const CountryCard = ({ country }) => {
    const { id : countryId, name, image, description, costOfLiving, region } = country;

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
                </div>
            </div>
        </Link>
    );
};

export default CountryCard;