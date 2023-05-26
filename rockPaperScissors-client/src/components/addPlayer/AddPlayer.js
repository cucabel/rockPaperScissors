import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import { Button, Col, Form, Row } from 'react-bootstrap';

import playerService from '../../services/playerService/playerService';
import Enum from './../../enum';

function AddPlayers() {
  const [player, setPlayer] = useState({
    nickName: Enum.emptyString
  });
  const navigate = useNavigate();

  const handleChange = (event) => {
    const { name, value } = event.target;
    setPlayer((prevState) => ({ ...prevState, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await playerService.addPlayer(player);
      const playerId = response.data.id;
      navigate(`/players/${playerId}`);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Row className="justify-content-center">
      <Col md={6}>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="row">
            <Form.Label htmlFor="nickName" className="my-2">NickName:</Form.Label>
            <Form.Control className="mb-3" type="text" name="nickName" id="nickName" value={player.nickName} onChange={handleChange} />
          </Form.Group>
          <Button type="submit" value="Submit" variant="primary" id="fluid-button">{Enum.addNewButtonText}</Button>
        </Form>
      </Col>
    </Row>
  );
}

export default AddPlayers;
