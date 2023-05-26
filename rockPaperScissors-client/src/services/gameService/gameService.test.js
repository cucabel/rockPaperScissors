import gameService from './gameService';
import Enum from './../../enum';

import axios from "axios";
import MockAdapter from "axios-mock-adapter";
let mockedAxios;
let postSpy;

const game = { playerSymbol: Enum.paperName, enemySymbol: Enum.rockName };
const playerId = 1;

describe("When_the_game_service", () => {

    describe('requests_to_create_a_game', () => {
        
        beforeAll(() => {
            mockedAxios = new MockAdapter(axios);
            postSpy = jest.spyOn(mockedAxios, 'onPost');
        });

        afterEach(() => {
            mockedAxios.reset();
        });

        describe('with_the_games_data_and_the_player_exist', () => {
            it("should_return_the_result_of_the_game", async () => {
                const mockedReturnedResult = Enum.playerWinResult;
                mockedAxios.onPost(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`, game)
                    .reply(Enum.isOkStatusCode, { data: mockedReturnedResult });

                const returnedResult = await gameService.addGame(playerId, game);

                expect(postSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`, game)
                expect(returnedResult.data.data).toBe(mockedReturnedResult);
            });
        });

        describe('without_the_games_data', () => {
            it("should_return_a_400_error", async () => {
                const mockedReturnedError = new Error(Enum.badRequestErrorMessage);
                mockedAxios.onPost(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`)
                    .reply(Enum.badRequestStatusCode, mockedReturnedError);
                try {
                    await gameService.addGame(playerId);
                } catch (error) {
                    expect(postSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`)
                    expect(error).toEqual(mockedReturnedError);
                }
            });
        });

        describe('and_the_player_does_not_exist', () => {
            it("should_return_a_404_error", async () => {
                const mockedReturnedError = new Error(Enum.notFoundErrorMessage);
                mockedAxios.onPost(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`, game)
                    .reply(Enum.notFoundStatusCode, mockedReturnedError);
                try {
                    await gameService.addGame(playerId, game);
                } catch (error) {
                    expect(postSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players/${playerId}/games`, game)
                    expect(error).toEqual(mockedReturnedError);
                }
            });
        });

    });

});
