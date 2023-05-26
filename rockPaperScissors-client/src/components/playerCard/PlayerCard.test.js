import React from "react";
import { render } from "@testing-library/react";

import PlayerCard from "./PlayerCard";

describe("When_the_players_card_component_is_rendered", () => {

    it("should_render_the_players_name", () => {
        const doc = '/doc.jpg';
        const docImgAltText = 'playerImg';
        const playerName = 'Isabel';

        const component = render(<PlayerCard image={doc} name={playerName} />)

        expect(component.getByText(playerName));
        expect(component.getByAltText(docImgAltText));
    });

});
