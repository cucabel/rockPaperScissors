import React from "react";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import '@testing-library/jest-dom/extend-expect'
import { act } from "react-dom/test-utils";
import { MemoryRouter, Routes, Route } from "react-router-dom";

import AddPlayers from "./AddPlayer";
import playerService from '../../services/playerService/playerService';
import GamePage from "../../pages/playerGame/GamePage";
import Enum from "./../../enum";

jest.mock('./../../services/playerService/playerService');

const renderNewPlayerForm = async () => {
    render(
        <MemoryRouter>
            <Routes>
                <Route exact path="/" element={<AddPlayers />} />
                <Route exact path="/players/:playerId" element={<GamePage />} />
            </Routes>
        </MemoryRouter>
    );
};

const getInputNode = async labelText => screen.getByLabelText(labelText);

const expectInputNodeToHaveValue = async (labelText, value) => {
    const inputNode = await getInputNode(labelText);
    expect(inputNode.value).toContain(value);
};

const typePlayersNickName = async (labelText, value) => {
    const inputNode = await getInputNode(labelText);
    userEvent.type(inputNode, value);
}

const getAddNewButton = async () => screen.findByText(Enum.addNewButtonText);

const clickAddNewButton = async () => {
    const addNewbutton = await getAddNewButton();
    userEvent.click(addNewbutton);
};

const nickNameLabelText = 'NickName:'
const playerName = 'Isabel';
const playerId = 1;

describe("When_the_add_players_component_is_rendered", () => {

    beforeEach(async () => {
        await renderNewPlayerForm();
    });

    it("should_render_a_form_with_a_field_to_type_the_players_nickName", async () => {
        await expectInputNodeToHaveValue(nickNameLabelText, Enum.emptyString);
    });

    describe('and_the_player_types_its_nickName', () => {
        it("should_change_the_default_players_nickName_by_the_new_typed_one", async () => {
            await typePlayersNickName(nickNameLabelText, playerName);

            await expectInputNodeToHaveValue(nickNameLabelText, playerName);
        });
    });

    describe('and_the_player_press_the_button_ADD_NEW', () => {

        describe('and_a_new_player_is_created', () => {
            it("should_redirect_to_the_game_page", async () => {
                const player = { nickName: playerName };
                const mockedReturnedPlayer = { id: playerId, nickName: playerName }
                playerService.addPlayer = jest.fn().mockResolvedValue({ data: mockedReturnedPlayer });
                playerService.getPlayerById = jest.fn().mockResolvedValue({ data: mockedReturnedPlayer });

                await act(async () => {
                    await typePlayersNickName(nickNameLabelText, playerName);
                    await clickAddNewButton();
                });

                expect(playerService.addPlayer).toHaveBeenCalledWith(player);
                expect(playerService.getPlayerById).toHaveBeenCalledWith(mockedReturnedPlayer.id.toString());
                expect(await screen.findByText(playerName)).toBeInTheDocument();
            });
        });

        describe('and_an_error_is_returned', () => {
            it("should_not_redirect_to_the_game_page", async () => {
                const player = { nickName: Enum.emptyString };
                const mockedReturnedError = new Error("Request failed with status code 400");
                playerService.addPlayer = jest.fn().mockRejectedValue(mockedReturnedError);
                try {
                    await clickAddNewButton();
                } catch (error) {
                    expect(playerService.addPlayer).toHaveBeenCalledWith(player);
                    expect(error).toEqual(mockedReturnedError);
                    expect(playerService.getPlayerById).not.toHaveBeenCalled();
                    expect(await screen.findByText(Enum.enemyName)).not.toBeInTheDocument();
                }
            });
        });

    });

});
