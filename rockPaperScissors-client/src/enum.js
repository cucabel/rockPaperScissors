import symbolService from './services/symbolService/symbolService';

const symbols = symbolService.getSymbols();

const Enum = Object.freeze({
    emptyString: '',
    enemyName: 'Grumpy',
    playerWinResult: true,
    playerLoseResult: false,
    rockName: symbols[0].name,
    paperName: symbols[1].name,
    scissorsName: symbols[2].name,
    rockRandom: 0.1,
    paperRandom: 0.4,
    scissorsRandom: 0.9,
    addNewButtonText: 'ADD NEW',
    playAgainButtonText: 'PLAY AGAIN',
    tryAgainButtonText: 'TRY AGAIN',
    drawText: 'There is a draw',
    winText: 'The winner is ',
    isOkStatusCode: 200,
    badRequestStatusCode: 400,
    notFoundStatusCode: 404,
    badRequestErrorMessage: 'Request failed with status code 400',
    notFoundErrorMessage: 'Request failed with status code 404',
  })

export default Enum;
