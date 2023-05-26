import React, { useRef } from 'react';
import { Col, Container, Image, Row } from "react-bootstrap";

import symbolService from '../../services/symbolService/symbolService';
import AddPlayer from "../../components/addPlayer/AddPlayer";

import './../../App.css';

function HomePage() {
  const symbols = useRef(symbolService.getSymbols());

  return (
    <Container fluid="md">
      {" "}
      <Row className="justify-content-center">
        {symbols.current.map((symbol) =>
          <Col xs={4}>
            <Image key={symbol.id} src={symbol.src} alt={`${symbol.name}Img`} id={`${symbol.name}Img`} />
          </Col>
        )}
      </Row>
      <AddPlayer />
    </Container>
  );
}

export default HomePage;
