import playerService from './playerService';
import Enum from './../../enum';

import axios from "axios";
import MockAdapter from "axios-mock-adapter";
let mockedAxios;
let postSpy;
let getSpy;

const playerName = 'Isabel';
const playerId = 1;
const mockedReturnedPlayer = { id: playerId, nickName: playerName };

describe("When_the_player_service", () => {

    beforeAll(() => {
        mockedAxios = new MockAdapter(axios);
        postSpy = jest.spyOn(mockedAxios, 'onPost');
        getSpy = jest.spyOn(mockedAxios, 'onGet');
    });

    afterEach(() => {
        mockedAxios.reset();
    });

    describe('requests_to_create_a_player', () => {
        
        describe('to_the_right_url_and_with_the_players_data', () => {
            it("should_return_a_new_player", async () => {
                const player = { nickName: playerName };
                mockedAxios.onPost(`${process.env.REACT_APP_API_URL}/players`, player)
                .reply(Enum.isOkStatusCode, { data: mockedReturnedPlayer });
                
                const returnedResult = await playerService.addPlayer(player);
                
                expect(postSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players`, player)
                expect(returnedResult.data.data).toEqual(mockedReturnedPlayer);
            });
        });
        
        describe('without_the_players_data', () => {
            it("should_return_a_400_error", async () => {
                const mockedReturnedError = new Error(Enum.badRequestErrorMessage);
                mockedAxios.onPost(`${process.env.REACT_APP_API_URL}/players`)
                    .reply(Enum.badRequestStatusCode, mockedReturnedError);
                try {
                    await playerService.addPlayer();
                } catch (error) {
                    expect(postSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players`)
                    expect(error).toEqual(mockedReturnedError);
                }
            });
        });

    });

    describe('requests_to_get_a_player_by_id', () => {

        describe('when_the_player_exists', () => {
            it("should_return_the_existing_player", async () => {
                mockedAxios.onGet(`${process.env.REACT_APP_API_URL}/players/${playerId}`)
                    .reply(Enum.isOkStatusCode, { data: mockedReturnedPlayer });

                const returnedResult = await playerService.getPlayerById(playerId);

                expect(getSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players/${playerId}`)
                expect(returnedResult.data.data).toEqual(mockedReturnedPlayer);
            });
        });

        describe('when_the_player_does_not_exist', () => {
            it("should_return_a_404_error", async () => {
                const mockedReturnedError = new Error(Enum.notFoundErrorMessage);
                mockedAxios.onGet(`${process.env.REACT_APP_API_URL}/players/${playerId}`)
                    .reply(Enum.notFoundStatusCode, mockedReturnedError);
                try {
                    await playerService.getPlayerById(playerId);
                } catch (error) {
                    expect(getSpy).toHaveBeenCalledWith(`${process.env.REACT_APP_API_URL}/players/${playerId}`)
                    expect(error).toEqual(mockedReturnedError);
                }
            });
        });

    });

});
