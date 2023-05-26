import React from 'react';
import { Card } from "react-bootstrap";

import './PlayerCard.css';

function PlayerCard(props) {
    return (
        <Card className="cards" id="playerCard">
            <Card.Body>
                <Card.Img src={props.image} alt="playerImg" id="playerImg" />
                <Card.Text id="playerText">
                    {props.name}
                </Card.Text>
            </Card.Body>
        </Card>
    );
}

export default PlayerCard;
