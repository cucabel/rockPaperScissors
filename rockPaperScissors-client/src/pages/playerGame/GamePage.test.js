import React from "react";
import userEvent from "@testing-library/user-event";
import { render, screen } from "@testing-library/react";
import '@testing-library/jest-dom/extend-expect'
import { act } from "react-dom/test-utils";
import { MemoryRouter, Routes, Route } from "react-router-dom";
import { mockRandom } from 'jest-mock-random';

import playerService from '../../services/playerService/playerService';
import gameService from '../../services/gameService/gameService';
import GamePage from "./GamePage";
import Enum from "../../enum";

jest.mock('./../../services/playerService/playerService');
jest.mock('./../../services/gameService/gameService');

const renderGamePage = async () => {
    await act(async () => {
        render(
            <MemoryRouter initialEntries={[`/players/${playerId}`]}>
                <Routes>
                    <Route exact path="/players/:playerId" element={<GamePage />} />
                </Routes>
            </MemoryRouter>
        );
    });
};

const getSymbolButton = async (symbolName) => screen.findByRole(`${symbolName}Button`);

const clickSymbolButton = async (symbolName) => {
    const symbolButton = await getSymbolButton(symbolName);
    userEvent.click(symbolButton);
};

const playerName = 'Isabel';
const playerId = 1;

describe("When_the_game_page_is_rendered", () => {

    beforeEach(async () => {
        const mockedReturnedPlayer = { id: playerId, nickName: playerName }
        playerService.getPlayerById = jest.fn().mockResolvedValue({ data: mockedReturnedPlayer });
        await renderGamePage();
    });

    it("should_render_the_players_name", async () => {
        expect(await screen.findByText(playerName)).toBeInTheDocument();
    });

    describe("the_player_clicks_one_symbol_button", () => {

        describe("and_there_is_a_draw", () => {
            it("should_render_there_is_a_draw_and_a_button_to_try_again", async () => {
                mockRandom(Enum.rockRandom);

                await act(async () => {
                    await clickSymbolButton(Enum.rockName);
                });

                expect(await screen.findByText(Enum.drawText)).toBeInTheDocument();
                expect(await screen.findByText(Enum.tryAgainButtonText)).toBeInTheDocument();
            });
        });

        describe("and_there_is_a_winner", () => {

            describe('and_a_new_game_is_created', () => {

                describe("and_the_player_symbol_wins_the_enemy_symbol", () => {
                    it("should_render_the_winner_is_the_player_and_a_button_to_play_again", async () => {
                        mockRandom(Enum.scissorsRandom);
                        const game = { playerSymbol: Enum.rockName, enemySymbol: Enum.scissorsName };
                        gameService.addGame = jest.fn().mockResolvedValue({ data: Enum.playerWinResult });

                        await act(async () => {
                            await clickSymbolButton(Enum.rockName);
                        });

                        expect(gameService.addGame).toHaveBeenCalledWith(playerId.toString(), game);
                        expect(await screen.findByText(Enum.winText + playerName)).toBeInTheDocument();
                        expect(await screen.findByText(Enum.playAgainButtonText)).toBeInTheDocument();
                    });
                });

                describe("and_the_enemy_symbol_wins_the_player_symbol", () => {
                    it("should_render_the_winner_is_the_enemy_and_a_button_to_play_again", async () => {
                        mockRandom(Enum.paperRandom);
                        const game = { playerSymbol: Enum.rockName, enemySymbol: Enum.paperName };
                        gameService.addGame = jest.fn().mockResolvedValue({ data: Enum.playerLoseResult });

                        await act(async () => {
                            await clickSymbolButton(Enum.rockName);
                        });

                        expect(gameService.addGame).toHaveBeenCalledWith(playerId.toString(), game);
                        expect(await screen.findByText(Enum.winText + Enum.enemyName)).toBeInTheDocument();
                        expect(await screen.findByText(Enum.playAgainButtonText)).toBeInTheDocument();
                    });
                });

            });

            describe('and_an_error_is_returned', () => {

                it("should_not_render_neither_the_winner_name_nor_a_button_to_play_again", async () => {
                    mockRandom(Enum.paperRandom);
                    const game = { playerSymbol: Enum.rockName, enemySymbol: Enum.paperName };
                    const mockedReturnedError = new Error(Enum.badRequestErrorMessage);
                    gameService.addGame = jest.fn().mockRejectedValue(mockedReturnedError);
                    try {
                        await clickSymbolButton(Enum.rockName);
                    } catch (error) {
                        expect(gameService.addGame).toHaveBeenCalledWith(playerId.toString(), game);
                        expect(await screen.findByText(Enum.winText + Enum.enemyName)).not.toBeInTheDocument();
                        expect(await screen.findByText(Enum.playAgainButtonText)).not.toBeInTheDocument();
                    }
                });

            });

        });

    });

});
