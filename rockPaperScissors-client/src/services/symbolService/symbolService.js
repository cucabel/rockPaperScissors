class SymbolService {
    constructor() {
        this.rock = {id: 1, name: "rock", src: "/rock.jpg"};
        this.paper = {id: 2, name: "paper", src: "/paper.jpg"};
        this.scissors = {id: 3, name: "scissors", src: "/scissors.jpg"};
        this.symbols = [this.rock, this.paper, this.scissors];
      }

    getSymbols() {
        return this.symbols;
    }

}

let symbolService = new SymbolService();

export default symbolService;
