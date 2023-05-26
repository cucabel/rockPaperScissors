import React, { useRef, useState, useEffect } from 'react';
import { Container, Image, Button, Row, Col } from "react-bootstrap";
import { useParams } from "react-router-dom";

import PlayerCard from "../../components/playerCard/PlayerCard";
import playerService from './../../services/playerService/playerService';
import gameService from '../../services/gameService/gameService';
import symbolService from '../../services/symbolService/symbolService';
import Enum from './../../enum';

import './GamePage.css';

const doc = '/doc.jpg';
const grumpy = '/grumpy.jpg';

function GamePage() {
  const symbols = useRef(symbolService.getSymbols());
  const [playerName, setPlayerName] = useState(Enum.emptyString);
  const enemyName = useRef(Enum.enemyName);
  const game = useRef({
    playerSymbol: Enum.emptyString,
    enemySymbol: Enum.emptyString
  });
  const [result, setResult] = useState(Enum.emptyString);
  const [winner, setWinner] = useState(Enum.emptyString);
  const { playerId } = useParams();

  useEffect(() => {
    const getPlayerById = async () => {
      try {
        const response = await playerService.getPlayerById(playerId);
        const { nickName } = response.data;
        setPlayerName(nickName);
      } catch (error) {
        console.log(error);
      }
    };
    getPlayerById();
  }, [playerId]);

  const getWinner = async () => {
    try {
      const response = await gameService.addGame(playerId, game.current);
      const winner = response.data ? playerName : enemyName.current;
      setWinner(winner);
    } catch (error) {
      console.log(error);
    }
  }

  const createGame = (playerThrow) => {
    const symbolsNames = symbols.current.map(symbol => symbol.name);
    const enemyThrow = symbolsNames[Math.floor(Math.random() * symbolsNames.length)];

    game.current = {
      playerSymbol: playerThrow,
      enemySymbol: enemyThrow
    };

    if (game.current.playerSymbol === game.current.enemySymbol) {
      setResult("draw")
    } else {
      getWinner()
      setResult("win")
    }
  }
  const renderSymbolImage = (symbol) => {
    return <Image key={symbol.id} src={symbol.src} alt={`${symbol.name}Img`} id={`${symbol.name}Img`} />
  }

  const renderResult = (result, buttonText) => {
    return (
      <>
        <Col xs={12}>
          <p>{result}</p>
        </Col>
        <Col xs={4} xl={2}>
          <Button onClick={() => { setResult(Enum.emptyString); setWinner(Enum.emptyString) }} variant="primary" id="againButton">
            {buttonText}
          </Button>
        </Col>

      </>
    )
  }

  const draw = () => {
    return renderResult(Enum.drawText, Enum.tryAgainButtonText);
  }

  const win = () => {
    return renderResult(Enum.winText + `${winner}`, Enum.playAgainButtonText);
  }

  return (
    <Container fluid="md">
      {" "}
      <Row className="justify-content-center">
        <Col xs={3} xl={2}>
          <PlayerCard image={doc} name={playerName} />
        </Col>
        <Col xs={{ span: 3, offset: 1 }} xl={{ span: 2, offset: 1 }}>
          <PlayerCard image={grumpy} name={enemyName.current} />
        </Col>
      </Row>
      {
        (!result)
          ?
          <Row className="justify-content-center">
            <Col xs={3} xl={2}>
              {symbols.current.map((symbol) =>
                <Button className="px-0 py-0" key={symbol.id} onClick={() => createGame(symbol.name)} role={`${symbol.name}Button`} id={`${symbol.name}Button`}>
                  {renderSymbolImage(symbol)}
                </Button>
              )}
            </Col>
            <Col xs={{ span: 3, offset: 1 }} xl={{ span: 2, offset: 1 }}>
              {symbols.current.map((symbol) => renderSymbolImage(symbol))}
            </Col>
          </Row>
          :
          <>
            <Row className="justify-content-center">
              <Col xs={3} xl={2}>
                {renderSymbolImage(symbols.current.filter(symbol => symbol.name === game.current.playerSymbol)[0])}
              </Col>
              <Col xs={{ span: 3, offset: 1 }} xl={{ span: 2, offset: 1 }}>
                {renderSymbolImage(symbols.current.filter(symbol => symbol.name === game.current.enemySymbol)[0])}
              </Col>
            </Row>
            <Row className="justify-content-center">
              {(!winner) ? draw() : win()}
            </Row>
          </>
      }
    </Container>
  );

}

export default GamePage;
