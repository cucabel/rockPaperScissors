import symbolService from './symbolService';

it("should_render_a_player_card_component", () => {
    const rock = {id: 1, name: "rock", src: "/rock.jpg"};
    const paper = {id: 2, name: "paper", src: "/paper.jpg"};
    const scissors = {id: 3, name: "scissors", src: "/scissors.jpg"};
    const expectedSymbols = [rock, paper, scissors];

    const symbols = symbolService.getSymbols();
  
    expect(symbols).toEqual(expectedSymbols);
});
